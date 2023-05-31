package com.nicolascastilla.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nicolascastilla.data.local.converters.AlbumTypeConverter
import com.nicolascastilla.entities.Album
import com.nicolascastilla.entities.Artist

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @TypeConverters(AlbumTypeConverter::class) val album: Album,
    @TypeConverters(Artist::class) val artist: Artist,
    val duration: Int,
    val explicit_content_cover: Int,
    val explicit_content_lyrics: Int,
    val explicit_lyrics: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val link: String,
    val md5_image: String,
    val position: Int,
    val preview: String,
    val rank: Int,
    val title: String,
    val title_short: String,
    val title_version: String,
    val type: String,
    var isFavorite: Boolean = true

)
