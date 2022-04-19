package com.example.animemvvm.models

data class JikanResponse(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta,
    val pagination: Pagination
)