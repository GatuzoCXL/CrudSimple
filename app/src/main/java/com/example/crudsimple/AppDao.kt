package com.example.crudsimple

import androidx.room.*

@Dao
interface AppDao {
    @Query("SELECT * FROM app_records")
    suspend fun getAll(): List<App>

    @Insert
    suspend fun insert(record: App)

    @Update
    suspend fun update(record: App)

    @Delete
    suspend fun delete(record: App)

    @Query("SELECT * FROM app_records ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecord(): App?
}