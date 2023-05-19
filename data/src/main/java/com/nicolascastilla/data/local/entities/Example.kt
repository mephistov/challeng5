package com.nicolascastilla.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableName")
data class Example(
    @PrimaryKey(autoGenerate = true) val id:Int=0
)
