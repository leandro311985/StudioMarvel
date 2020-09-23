package com.study.marvelstudio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.study.marvelstudio.repository.DashboardRepository
import com.study.marvelstudio.repository.HeroDetailsRepository
import com.study.marvelstudio.response.data.MarvelHeroesModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class HeroDetailsViewModel(private val heroDetailRepository: HeroDetailsRepository?,
                           var hero: MarvelHeroesModel?
) : BaseViewModel() {

    private lateinit var comics: MutableLiveData<List<MarvelHeroesModel>>

    fun getComics(): LiveData<List<MarvelHeroesModel>> {
        if (!::comics.isInitialized) {
            loadComics()
        }
        return comics
    }

    private fun loadComics() {
        comics = MutableLiveData()
        uiScope.launch {
            try {
                showLoading.value = true
                val response = withContext(bgDispatcher) {
                    heroDetailRepository?.fetchComics(hero?.id ?: 0)
                }
                response?.let {
                    showError.value = false
                    comics.value = it.marvelHeroes.toList()
                } ?: run {
                    showError.value = true
                }
            } catch (e: Exception) {
                Timber.e(e.toString())
                showError.value = true
            } finally {
                showLoading.value = false
            }
        }
    }

}