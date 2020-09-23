package com.study.marvelstudio.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import com.study.marvelstudio.R
import com.study.marvelstudio.commons.BUNDLE
import com.study.marvelstudio.commons.application.MarvelApplication
import com.study.marvelstudio.commons.utils.PagerSnapCallbackHelper
import com.study.marvelstudio.database.MarvelDatabase
import com.study.marvelstudio.repository.HeroDetailsRepository
import com.study.marvelstudio.repository.base.BaseViewModelFactory
import com.study.marvelstudio.response.data.MarvelHeroesModel
import com.study.marvelstudio.ui.adapter.HeroDetailsRecyclerViewAdapter
import com.study.marvelstudio.ui.base.BaseActivity
import com.study.marvelstudio.viewModel.HeroDetailsViewModel
import kotlinx.android.synthetic.main.activity_hero_details.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class HeroDetailsActivity : BaseActivity<HeroDetailsViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)
        initViewModel()
        initLayout()
        if (viewModel?.hero?.isFavorite == true) {
            favouriteButtonDetails.setImageResource(R.drawable.ic_favourite_red)
        } else {
            favouriteButtonDetails.visibility = View.GONE
        }

    }

    private fun initLayout() {
        backButtonImageView.setOnClickListener { onBackPressed() }
        toolbarTitleTextView.text = getString(R.string.dashboard_toolbar_title)
        Picasso.get().load(viewModel?.hero?.thumbnail).resize(1500, 0).into(heroImageView)
        heroTitleTextView.text = viewModel?.hero?.name
        heroDescriptionTextView.text =
            if (viewModel?.hero?.description.isNullOrEmpty()) getString(R.string.dummy_description) else viewModel?.hero?.description
    }

    private fun initViewModel() {
        val heroDetailsViewModelFactory = BaseViewModelFactory {
            HeroDetailsViewModel(
                HeroDetailsRepository(
                    MarvelApplication.get()?.marvelClient,
                    MarvelDatabase.get(this)
                ),
                intent?.extras?.getParcelable<MarvelHeroesModel>(
                    BUNDLE.HERO_DETAILS
                )
            )
        }
        viewModel = ViewModelProviders.of(this, heroDetailsViewModelFactory)
            .get(HeroDetailsViewModel::class.java)

        viewModel?.getComics()?.observe(this, Observer { heroes ->
            heroes?.let {
                heroDetailsRecyclerView.setHasFixedSize(true)
                val linearLayoutManager = GridLayoutManager(this, 2)

                heroDetailsRecyclerView.layoutManager = linearLayoutManager
                heroDetailsRecyclerView.adapter = HeroDetailsRecyclerViewAdapter(
                    it.toMutableList()
                )

                val pagerSnapCallbackHelper = PagerSnapCallbackHelper()
                pagerSnapCallbackHelper.attachToRecyclerView(heroDetailsRecyclerView)
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
}
