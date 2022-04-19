package com.example.animemvvm.modelsT

data class JikanV3Response(
    val API_DEPRECATION: Boolean,
    val API_DEPRECATION_DATE: String,
    val API_DEPRECATION_INFO: String,
    val request_cache_expiry: Int,
    val request_cached: Boolean,
    val request_hash: String,
    val top: MutableList<Top>
)