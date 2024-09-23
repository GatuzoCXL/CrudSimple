package com.example.crudsimple


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_records")
data class App(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nombre") val name: String,
    @ColumnInfo(name = "descripcion") val description: String,
    @ColumnInfo(name = "tipo") val type: String // e.g., "Food", "Exercise"
)