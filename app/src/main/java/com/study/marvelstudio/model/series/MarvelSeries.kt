package com.study.marvelstudio.model.series

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.MarvelThumbnail

data class MarvelSeries(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String? ="",
    @SerializedName("thumbnail") val thumbnail: MarvelThumbnail
){

    fun getEventImage():String {
        return  thumbnail.path +"."+thumbnail.extension
    }

}
