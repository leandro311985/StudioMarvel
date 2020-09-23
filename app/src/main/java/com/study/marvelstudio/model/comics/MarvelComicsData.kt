package com.study.marvelstudio.model.comics

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.comics.MarvelComics

data class MarvelComicsData(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<MarvelComics>
)