package com.nicolascastilla.entities.deezeresponse

import com.nicolascastilla.entities.Song

data class TrendingResponse(
    val `data`: List<Song>,
    val total: Int
)