package com.study.marvelstudio.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.study.marvelstudio.R
import com.study.marvelstudio.commons.BUNDLE
import com.study.marvelstudio.commons.Definitions
import com.study.marvelstudio.commons.application.MarvelApplication
import com.study.marvelstudio.commons.extensions.isListFavorito
import com.study.marvelstudio.database.MarvelDatabase
import com.study.marvelstudio.repository.DashboardRepository
import com.study.marvelstudio.repository.base.BaseViewModelFactory
import com.study.marvelstudio.response.data.MarvelHeroesModel
import com.study.marvelstudio.ui.PaginationScrollListener
import com.study.marvelstudio.ui.adapter.DashboardRecyclerViewAdapter
import com.study.marvelstudio.ui.base.BaseActivity
import com.study.marvelstudio.viewModel.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.layout_pagination_recyclerview.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class FavoritesActivity : BaseActivity<DashboardViewModel>() {

    private var dashboardRecyclerViewAdapter: DashboardRecyclerViewAdapter? = null
    private var paginationScrollListener: PaginationScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        initLayout()
        initViewModel()
    }

    private fun initViewModel() {
        val dashboardViewModelFactory = BaseViewModelFactory {
            DashboardViewModel(
                DashboardRepository(
                    MarvelApplication.get()?.marvelClient,
                    MarvelDatabase.get(this)
                ),
                intent?.extras?.getParcelable<MarvelHeroesModel>(
                    BUNDLE.HERO_DETAILS
                )
            )
        }

        viewModel = ViewModelProviders.of(this, dashboardViewModelFactory)
            .get(DashboardViewModel::class.java)
        viewModel?.getHeroes()?.observe(this, Observer { heroes ->
            moreProgressView?.visibility = View.GONE
            heroes?.let { list ->

                dashboardRecyclerViewAdapter?.setHeroesList(list.isListFavorito().toMutableList())
            } ?: run {
                emptyView.visibility = View.VISIBLE

            }
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
        toolbarTitleTextView.text = getString(R.string.dashboard_toolbar_title_favorites)
        backButtonImageView.visibility = View.VISIBLE
        closeButtonImageView.visibility = View.VISIBLE
        closeButtonImageView.setOnClickListener {
            startActivity(Intent(this, SeriesActivity::class.java))
        }

        val linearLayoutManager = GridLayoutManager(this, 2)
        dashboardRecyclerView.layoutManager = linearLayoutManager
        dashboardRecyclerViewAdapter = DashboardRecyclerViewAdapter(
            onFavouriteClicked = { },
            onHeroClicked = { })

        paginationScrollListener =
            PaginationScrollListener(
                linearLayoutManager,
                {
                    moreProgressView?.visibility = View.VISIBLE
                    viewModel?.loadHeroes()
                },
                Definitions.PAGINATION_SIZE
            )
        paginationScrollListener?.let {
            dashboardRecyclerView.addOnScrollListener(it)
        }


        dashboardRecyclerView.adapter = dashboardRecyclerViewAdapter

    }

}

