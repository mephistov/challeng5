package com.nicolascastilla.data.local.mappers

import com.nicolascastilla.data.local.entities.FavoriteEntity
import com.nicolascastilla.entities.Song

internal fun FavoriteEntity.toSong() = Song(
    album = album,
    artist = artist,
    duration = duration,
    explicit_content_cover = explicit_content_cover,
    explicit_content_lyrics = explicit_content_lyrics,
    explicit_lyrics = explicit_lyrics,
    id = id,
    link = link,
    md5_image = md5_image,
    position = position,
    preview = preview,
    rank = rank,
    title = title,
    title_short = title_short,
    title_version = title_version,
    type = type,
    isFavorite = isFavorite
)

internal fun Song.toFavorite() = FavoriteEntity(
    album = album,
    artist = artist,
    duration = duration,
    explicit_content_cover = explicit_content_cover,
    explicit_content_lyrics = explicit_content_lyrics,
    explicit_lyrics = explicit_lyrics,
    id = id,
    link = link,
    md5_image = md5_image,
    position = position,
    preview = preview,
    rank = rank,
    title = title,
    title_short = title_short,
    title_version = title_version,
    type = type,
    isFavorite = isFavorite
)

internal fun List<FavoriteEntity>.toEntities() = map{it.toSong()}