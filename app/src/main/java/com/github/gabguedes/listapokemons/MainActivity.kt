package com.github.gabguedes.listapokemons

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.github.gabguedes.listapokemons.data.PokemonRepository
import com.github.gabguedes.listapokemons.data.api.ApiClient
import com.github.gabguedes.listapokemons.data.api.model.Pokemon
import com.github.gabguedes.listapokemons.databinding.ActivityMainBinding
import com.github.gabguedes.listapokemons.ui.pokemonlist.MainListAdapter
import com.github.gabguedes.listapokemons.ui.pokemonlist.MainViewModel
import com.github.gabguedes.listapokemons.ui.pokemonlist.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(PokemonRepository(ApiClient.createPokeApiService()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = MainListAdapter { view, pokemon ->
            // Configurar a troca de tela
            val intent = Intent(this, PokemonDetailActivity::class.java)
            var pokemonDetail = Pokemon(
                id = pokemon.id,
                name = pokemon.name,
                imageUrl = pokemon.imageUrl
            )

            intent.putExtra("pokemon", pokemonDetail)
            startActivity(intent)

//            Toast.makeText(this, pokemon.name, Toast.LENGTH_LONG).show()
        }
        //binding.pokemonRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pokemonRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.pokemonRecyclerView.adapter = adapter
        viewModel.pokemonList.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}