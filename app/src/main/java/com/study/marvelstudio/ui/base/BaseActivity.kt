package com.study.marvelstudio.ui.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.study.marvelstudio.viewModel.BaseViewModel

@SuppressLint("Registered")
open class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    protected var viewModel: T? = null
}