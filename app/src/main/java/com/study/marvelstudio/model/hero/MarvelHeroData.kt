package com.study.marvelstudio.model.hero

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.hero.MarvelHero

data class MarvelHeroData(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<MarvelHero>
)