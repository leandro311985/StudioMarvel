package com.study.marvelstudio.commons.application

import android.app.Application
import com.study.marvelstudio.R
import com.study.marvelstudio.BuildConfig
import com.study.marvelstudio.network.MarvelClient

import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.lang.ref.WeakReference

class MarvelApplication : Application() {

    companion object {
        private var instance: WeakReference<MarvelApplication>? = null

        @JvmStatic
        fun get(): MarvelApplication? {
            return instance?.get()
        }
    }

    val marvelClient: MarvelClient by lazy {
        return@lazy MarvelClient()
    }

    override fun onCreate() {
        super.onCreate()
        instance = WeakReference(this)
        initCalligraphy()
        initTimber()
    }

    private fun initCalligraphy() {
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Marvel-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

}