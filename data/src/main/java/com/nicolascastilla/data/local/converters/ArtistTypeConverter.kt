package com.nicolascastilla.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nicolascastilla.entities.Artist

class ArtistTypeConverter {
    @TypeConverter
    fun fromArtist(artist: Artist): String {
        return Gson().toJson(artist)
    }

    @TypeConverter
    fun toArtist(artistString: String): Artist {
        return Gson().fromJson(artistString, Artist::class.java)
    }
}