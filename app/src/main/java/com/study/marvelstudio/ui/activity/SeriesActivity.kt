package com.study.marvelstudio.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.marvelstudio.R
import com.study.marvelstudio.commons.application.MarvelApplication
import com.study.marvelstudio.repository.SeriesRepository
import com.study.marvelstudio.repository.base.BaseViewModelFactory
import com.study.marvelstudio.ui.adapter.SeriesRecyclerViewAdapter
import com.study.marvelstudio.ui.base.BaseActivity
import com.study.marvelstudio.viewModel.SeriesViewModel
import kotlinx.android.synthetic.main.activity_series.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SeriesActivity : BaseActivity<SeriesViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series)
        initLayout()
        initViewModel()
    }

    private fun initViewModel() {
        val seriesViewModelFactory = BaseViewModelFactory {
            SeriesViewModel(SeriesRepository(MarvelApplication.get()?.marvelClient))
        }

        viewModel = ViewModelProviders.of(this, seriesViewModelFactory).get(SeriesViewModel::class.java)
        viewModel?.getSeries()?.observe(this, Observer { series ->
            series?.let {
                seriesRecyclerView.layoutManager = LinearLayoutManager(this)
                seriesRecyclerView.adapter = SeriesRecyclerViewAdapter(it.toMutableList())
            } ?: run { emptyView.visibility = View.VISIBLE }
        })

        viewModel?.getIsLoading()?.observe(this, Observer { value ->
            value?.let { show ->
                loadingView.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        viewModel?.shouldShowError()?.observe(this, Observer { value ->
            value?.let { show ->
                emptyView.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }

    private fun initLayout() {
        backButtonImageView.setOnClickListener { onBackPressed() }
        toolbarTitleTextView.text = getString(R.string.series_toolbar_title)
    }
}
