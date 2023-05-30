package com.nicolascastilla.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nicolascastilla.entities.Album


class AlbumTypeConverter {
    @TypeConverter
    fun fromAlbum(album: Album): String {
        return Gson().toJson(album)
    }

    @TypeConverter
    fun toAlbum(albumString: String): Album {
        return Gson().fromJson(albumString, Album::class.java)
    }
}