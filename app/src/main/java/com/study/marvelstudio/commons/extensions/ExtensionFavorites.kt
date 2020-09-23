package com.study.marvelstudio.commons.extensions

import com.study.marvelstudio.response.data.MarvelHeroesModel

fun List<MarvelHeroesModel>.isListFavorito() = this.filter { it.isFavorite }
