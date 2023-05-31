package com.nicolascastilla.data.local.dao

import androidx.room.*
import com.nicolascastilla.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(song :FavoriteEntity)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE id= :id")
    fun getFavoriteById(id:Long): FavoriteEntity?

    @Delete
    fun delete(song: FavoriteEntity)

}