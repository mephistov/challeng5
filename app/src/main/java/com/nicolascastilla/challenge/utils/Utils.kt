package com.nicolascastilla.challenge.utils

import android.graphics.Bitmap




object Utils {

    fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }
}