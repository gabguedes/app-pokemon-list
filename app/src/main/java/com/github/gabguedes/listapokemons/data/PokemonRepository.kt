package com.github.gabguedes.listapokemons.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.github.gabguedes.listapokemons.data.api.PokeApiService

class PokemonRepository(private val apiService: PokeApiService) {
    fun getPokemonList() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PokemonPagingSource(apiService) }
    ).liveData
}