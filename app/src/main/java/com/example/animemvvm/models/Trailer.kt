package com.example.animemvvm.models

import com.example.animemvvm.models.ImagesX

data class Trailer(
    val embed_url: String,
    val images: ImagesX,
    val url: String,
    val youtube_id: String
)