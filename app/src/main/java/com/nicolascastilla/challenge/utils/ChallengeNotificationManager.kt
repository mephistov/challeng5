package com.nicolascastilla.challenge.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nicolascastilla.challenge.MainActivity
import com.nicolascastilla.challenge.R
import com.nicolascastilla.entities.Song
import javax.inject.Inject

class ChallengeNotificationManager@Inject constructor(
    private val context: Context,
) {

    fun setLocalNotification( isPlaying: Boolean = true){
        val notificationLayout = RemoteViews("com.nicolascastilla.challenge", R.layout.simple_notification)
        val notificationLayoutExpanded = RemoteViews("com.nicolascastilla.challenge", R.layout.full_notification)

        //simple
        notificationLayout.setTextViewText(R.id.textView3, Utils.currentSong?.title)
        notificationLayout.setTextViewText(R.id.textView4, Utils.currentSong?.artist?.name ?: "...")

        //full
        notificationLayoutExpanded.setTextViewText(R.id.textView3, Utils.currentSong?.title)
        notificationLayoutExpanded.setTextViewText(R.id.textView4,Utils.currentSong?.artist?.name ?: "...")
        if(isPlaying)
            notificationLayoutExpanded.setImageViewResource(R.id.button3,android.R.drawable.ic_media_pause)
        else
            notificationLayoutExpanded.setImageViewResource(R.id.button3,android.R.drawable.ic_media_play)

        //
        setListeners(notificationLayoutExpanded,context)
        val resultIntent = Intent(context, MainActivity::class.java).apply {
            //putExtra("SONG_POSITION",0)
            //setAction("$id")
            flags= Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val resultPendingIntent = PendingIntent.getActivity(context,0,resultIntent, PendingIntent.FLAG_IMMUTABLE)
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = CHANNEL_ID
        val channel = NotificationChannel(
            channelId,
            "Some song",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.music_icon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(resultPendingIntent)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .setAutoCancel(false)
            .setOngoing(isPlaying)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            with(NotificationManagerCompat.from(context)){
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                notify(0,builder.build())
            }
        }else{
            notificationManager.notify(0, builder.build())
        }

    }

    fun setListeners(remoteViews: RemoteViews, context: Context?){

        //--- actions
        val playPause = Intent(context,IntentBoradcastReciver::class.java).apply {
            action = "ACTION_PLAYPAUSE"

        }
        val prevPause = Intent(context, IntentBoradcastReciver::class.java).apply {
            action = "ACTION_PREV"
        }
        val nextPause = Intent(context, IntentBoradcastReciver::class.java).apply {
            action = "ACTION_NEXT"
        }
        /*
             val playPausePendingIntent: PendingIntent = PendingIntent.getService(context, 0, playPause, 0)
             val prevPendingIntent: PendingIntent = PendingIntent.getService(context, 0, prevPause, 0)
             val nextPendingIntent: PendingIntent = PendingIntent.getService(context, 0, nextPause, 0)
     */
        val playPausePendingIntent = PendingIntent.getBroadcast(context, 124, playPause,  PendingIntent.FLAG_UPDATE_CURRENT )
        val prevPendingIntent = PendingIntent.getBroadcast(context, 124, prevPause,  PendingIntent.FLAG_UPDATE_CURRENT )
        val nextPendingIntent = PendingIntent.getBroadcast(context, 124, nextPause,  PendingIntent.FLAG_UPDATE_CURRENT )


        remoteViews.setOnClickPendingIntent(R.id.button3,playPausePendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button2,prevPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button4,nextPendingIntent);

    }


    companion object {
        const val CHANNEL_ID = "MusicServiceChannel"

    }
}