package com.nicolascastilla.entities.deezeresponse

import com.nicolascastilla.entities.Song

data class DeezerResponse(
    val `data`: List<Song>,
    val total: Int
)