package com.study.marvelstudio.repository

import com.study.marvelstudio.database.MarvelDatabase
import com.study.marvelstudio.database.MarvelTable
import com.study.marvelstudio.network.MarvelClient
import com.study.marvelstudio.repository.base.BaseRepository
import com.study.marvelstudio.response.data.MarvelHeroesModel
import com.study.marvelstudio.response.data.MarvelListModel
import timber.log.Timber

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DashboardRepository(private val marvelClient: MarvelClient?, private val marvelDatabase: MarvelDatabase) : BaseRepository() {

    suspend fun fetchHeroes(offset: Int, limit: Int): MarvelListModel? {
        val response = try {
            marvelClient?.getMarvelHeroesAsync(limit, offset)?.await()
        } catch (e: Exception) {
            return null
        }

        val marvelModelList = ArrayList(response?.heroData?.results?.map { marvelHero ->
            marvelDatabase.marvelDao().insertHero(MarvelTable(marvelHero.id, false))

            return@map MarvelHeroesModel(
                marvelHero.id,
                marvelHero.name,
                marvelHero.description,
                marvelHero.thumbnail.path + "." + marvelHero.thumbnail.extension,
                marvelDatabase.marvelDao().isHeroFavotite(marvelHero.id)
            )
        })
        return MarvelListModel(marvelModelList)
    }

    fun updateFavourite(marvelHeroId: Int) {
        Timber.d("Hero Favorite Clicked %s", marvelHeroId)
        marvelDatabase.marvelDao().updateFavorite(marvelHeroId)
    }

}