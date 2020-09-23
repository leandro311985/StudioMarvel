package com.study.marvelstudio.model.hero

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.MarvelCommonResponse

data class MarvelHeroResponse(@SerializedName("data") val heroData: MarvelHeroData) :
    MarvelCommonResponse()