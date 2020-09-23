package com.study.marvelstudio.repository

import com.study.marvelstudio.database.MarvelDatabase
import com.study.marvelstudio.network.MarvelClient
import com.study.marvelstudio.repository.base.BaseRepository
import com.study.marvelstudio.response.data.MarvelHeroesModel
import com.study.marvelstudio.response.data.MarvelListModel
import timber.log.Timber


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HeroDetailsRepository(private val marvelClient: MarvelClient?, private val marvelDatabase: MarvelDatabase) : BaseRepository() {

    suspend fun fetchComics(heroId: Int): MarvelListModel? {
        val response = try {
            marvelClient?.getMarvelComicsAsync(heroId)?.await()
        } catch (e: Exception) {
            return null
        }

        val marvelModelList = ArrayList(response?.heroData?.results?.map { marvelHero ->
            return@map MarvelHeroesModel(
                marvelHero.id,
                marvelHero.title ?: "",
                marvelHero.description ?: "",
                marvelHero.thumbnail.path + "." + marvelHero.thumbnail.extension)
        })
        return MarvelListModel(marvelModelList)
    }

}