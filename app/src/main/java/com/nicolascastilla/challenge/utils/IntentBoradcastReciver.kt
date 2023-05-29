package com.nicolascastilla.challenge.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nicolascastilla.challenge.di.ServiceManagerEntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class IntentBoradcastReciver:BroadcastReceiver() {

    lateinit var musicManager: ServiceManager

    override fun onReceive(context: Context?, intent: Intent?) {

        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            context!!.applicationContext,
            ServiceManagerEntryPoint::class.java
        )
        musicManager = hiltEntryPoint.serviceManager()
        musicManager.init()

        intent?.let {
            val infoMap = it.getSerializableExtra("DATASONGI") as? HashMap<String, String>

            when (it.action) {
                "ACTION_PLAYPAUSE" -> {
                    // Handle the next action
                    musicManager.setOutsiderOptions(1)

                }
                "ACTION_PREV" -> {
                    // Handle the next action
                    //changeMusic()
                    // musicManager.changeMusic("")
                    musicManager.setOutsiderOptions(2)
                }
                "ACTION_NEXT" -> {
                    // Handle the next action
                    //changeMusic
                    musicManager.setOutsiderOptions(3)
                }
                // Add other actions as needed
            }
        }



    }
}