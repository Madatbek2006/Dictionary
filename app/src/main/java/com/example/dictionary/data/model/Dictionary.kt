package com.example.dictionary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class Dictionary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val english: String,

    val type: String,

    val transcript: String,

    val uzbek: String,

    val countable: String,

    var is_favourite: Int // Change Boolean to Int
)