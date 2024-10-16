package com.github.gabguedes.listapokemons.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.gabguedes.listapokemons.data.api.PokeApiService
import com.github.gabguedes.listapokemons.data.api.model.Pokemon

class PokemonPagingSource(private val apiService: PokeApiService) :
    PagingSource<Int, Pokemon>() {
    private val LIMIT = 21
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,
            Pokemon> {
        return try {
            val position = params.key ?: 0
            val response = apiService.getPokemonList(
                limit = LIMIT,
                offset = position * LIMIT
            )
            var pokeId = (position * LIMIT) + 1
            val pokemons = response.results.map { pokemon ->
//                val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${ pokeId }.png"
                val imageUrl =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${ pokeId }.png"
                pokeId++
                Pokemon(pokeId, pokemon.name, imageUrl)
            }
            LoadResult.Page(
                data = pokemons,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (pokemons.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }
    }
}