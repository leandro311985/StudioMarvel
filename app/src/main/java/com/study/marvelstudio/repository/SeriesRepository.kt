package com.study.marvelstudio.repository

import com.study.marvelstudio.model.series.MarvelSeriesResponse
import com.study.marvelstudio.network.MarvelClient
import com.study.marvelstudio.repository.base.BaseRepository

class SeriesRepository(private val marvelClient: MarvelClient?) : BaseRepository() {

    suspend fun fetchSeries(): MarvelSeriesResponse? {
        return  try {
            marvelClient?.getMarvelSeriesAsync(20, 0)?.await()
        } catch (e: Exception) {
            return null
        }
    }
}