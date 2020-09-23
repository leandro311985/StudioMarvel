package com.study.marvelstudio.model.series

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.series.MarvelSeries

data class MarvelSeriesData(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("total") val total: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("results") val results: List<MarvelSeries>
)