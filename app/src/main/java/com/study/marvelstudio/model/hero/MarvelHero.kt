package com.study.marvelstudio.model.hero

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.MarvelThumbnail

data class MarvelHero(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail") val thumbnail: MarvelThumbnail
)