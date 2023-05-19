package com.nicolascastilla.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.entities.Example

@Database(entities = [
    Example::class,
], version = 1,
)
abstract class ChallengeDataBase(): RoomDatabase() {
    abstract fun getChalengeDao(): ChallengeDao


}