package com.example.animemvvm.models

import com.example.animemvvm.models.Items

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)