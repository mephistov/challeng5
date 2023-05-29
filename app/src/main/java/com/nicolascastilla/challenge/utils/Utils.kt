package com.nicolascastilla.challenge.utils

import android.graphics.Bitmap
import com.nicolascastilla.entities.Song


object Utils {

    const val PLAYPAUSE = "PLAYPAUSE"
    const val PREVIUS = "PREVIUS"
    const val NEXT = "NEXT"

    var currentSong:Song? = null
    var listSongsPlayable: List<Song>? = null

    fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }
}