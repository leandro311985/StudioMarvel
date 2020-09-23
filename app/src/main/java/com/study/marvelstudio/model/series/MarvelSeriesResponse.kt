package com.study.marvelstudio.model.series

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.MarvelCommonResponse

data class MarvelSeriesResponse(@SerializedName("data") val seriesData: MarvelSeriesData): MarvelCommonResponse()