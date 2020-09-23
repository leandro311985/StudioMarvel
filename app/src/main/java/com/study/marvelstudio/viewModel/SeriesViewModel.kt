package com.study.marvelstudio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.study.marvelstudio.model.series.MarvelSeries
import com.study.marvelstudio.repository.SeriesRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SeriesViewModel(private val seriesRepository: SeriesRepository?) : BaseViewModel() {

    private lateinit var series: MutableLiveData<List<MarvelSeries>>

    fun getSeries(): LiveData<List<MarvelSeries>> {
        if (!::series.isInitialized) {
            loadSeries()
        }
        return series
    }

    private fun loadSeries() {
        series = MutableLiveData()
        uiScope.launch {
            try {
                showLoading.value = true
                val response = withContext(bgDispatcher) { seriesRepository?.fetchSeries() }
                response?.let {
                    showError.value = false
                    series.value = it.seriesData.results
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