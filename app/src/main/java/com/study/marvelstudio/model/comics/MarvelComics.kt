package com.study.marvelstudio.model.comics

import com.google.gson.annotations.SerializedName
import com.study.marvelstudio.model.MarvelThumbnail


data class MarvelComics(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String ?,
    @SerializedName("description") val description: String ?,
    @SerializedName("thumbnail") val thumbnail: MarvelThumbnail
)