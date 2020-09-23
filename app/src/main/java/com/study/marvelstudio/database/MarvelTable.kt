package com.study.marvelstudio.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.study.marvelstudio.model.MarvelThumbnail


@Entity(tableName = "MarvelTable")
class MarvelTable(
    @PrimaryKey
    var id: Int,
    var isFavourite: Boolean = false
)