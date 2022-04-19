package com.example.animemvvm.modelsT

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "Top"
)
data class Top(
    val end_date: String?,
    val episodes: String?,
    val image_url: String?,
    @PrimaryKey(autoGenerate = false)val mal_id: Int?,
    val members: Int?,
    val rank: Int?,
    val score: Double?,
    val start_date: String?,
    val title: String?,
    val type: String?,
    val url: String?
) : Serializable