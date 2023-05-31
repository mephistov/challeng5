package com.nicolascastilla.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nicolascastilla.data.local.converters.AlbumTypeConverter
import com.nicolascastilla.data.local.converters.ArtistTypeConverter
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.entities.FavoriteEntity

@Database(entities = [
    FavoriteEntity::class,
], version = 1,
)
@TypeConverters(AlbumTypeConverter::class, ArtistTypeConverter::class)
abstract class ChallengeDataBase(): RoomDatabase() {
    abstract fun getChalengeDao(): ChallengeDao


}