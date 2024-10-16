package com.github.gabguedes.listapokemons.ui.pokemonlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.gabguedes.listapokemons.data.api.model.Pokemon
import com.github.gabguedes.listapokemons.databinding.PokemonItemBinding

class MainListAdapter(
    private val onItemClick: (View, Pokemon) -> Unit
) : PagingDataAdapter<Pokemon, MainListAdapter.PokemonViewHolder>(POKEMON_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        pokemon?.let {
            holder.bind(it, onItemClick)
        }
    }

    inner class PokemonViewHolder(private val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon, onItemClick: (View, Pokemon) -> Unit) {
            binding.pokemonName.text = pokemon.name.capitalize()
            Glide.with(binding.pokemonImage.context)
                .load(pokemon.imageUrl)
                .into(binding.pokemonImage)
            // Configuração do clique para navegação com animação compartilhada
            itemView.setOnClickListener {
                onItemClick(binding.pokemonImage, pokemon)
            }
        }
    }

    companion object {
        private val POKEMON_COMPARATOR = object :
            DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon):
                    Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon):
                    Boolean =
                oldItem == newItem
        }
    }
}