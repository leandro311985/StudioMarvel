package com.study.marvelstudio.network

import com.study.marvelstudio.commons.Definitions
import com.study.marvelstudio.commons.Definitions.PARAM_CHARACTER_ID
import com.study.marvelstudio.model.hero.MarvelHeroResponse
import com.study.marvelstudio.model.series.MarvelSeriesResponse
import com.study.marvelstudio.model.comics.MarvelComicsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getMarvelHeroesAsync(@Query(Definitions.PARAM_LIMIT) limit: Int, @Query(Definitions.PARAM_OFFSET) offset: Int): Deferred<MarvelHeroResponse>

    @GET("characters/{$PARAM_CHARACTER_ID}/comics")
    fun getMarvelComicsAsync(@Path(PARAM_CHARACTER_ID) heroId: Int): Deferred<MarvelComicsResponse>

    @GET("series")
    fun getMarvelSeries(@Query(Definitions.PARAM_LIMIT) limit: Int, @Query(Definitions.PARAM_OFFSET) offset: Int): Deferred<MarvelSeriesResponse>

}