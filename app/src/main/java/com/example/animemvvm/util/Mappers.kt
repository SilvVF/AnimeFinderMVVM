package com.example.animemvvm.util

import com.example.animemvvm.modelsT.Top
import com.example.animemvvm.modelsT.search.Result
fun Result.toTop(): Top{
    return Top(
        end_date = this.end_date,
        start_date = this.start_date,
        episodes = this.episodes.toString(),
        image_url = this.image_url,
        mal_id = this.mal_id,
        members = this.members,
        rank = 0,
        score = this.score,
        title = this.title,
        type = this.type,
        url = this.url
    )
}