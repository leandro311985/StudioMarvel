package com.study.marvelstudio.model.comics

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.MarvelCommonResponse

data class MarvelComicsResponse(
    @SerializedName("data") val heroData: MarvelComicsData
): MarvelCommonResponse()