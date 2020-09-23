package com.study.marvelstudio.repository.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.study.marvelstudio.repository.SeriesRepository
import com.study.marvelstudio.viewModel.SeriesViewModel


@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory< T>(val creator: () -> T) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}


class SeriesViewModelFactory<SeriesViewModel>(private val seriesRepository: SeriesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SeriesViewModel(seriesRepository) as T
    }
}