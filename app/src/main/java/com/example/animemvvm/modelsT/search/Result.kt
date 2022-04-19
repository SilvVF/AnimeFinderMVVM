package com.example.animemvvm.modelsT.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "Result"
)
data class Result(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val airing: Boolean,
    val end_date: String,
    val episodes: Int,
    val image_url: String,
    val mal_id: Int,
    val members: Int,
    val rated: String,
    val score: Double,
    val start_date: String,
    val synopsis: String,
    val title: String,
    val type: String,
    val url: String
) : Serializable