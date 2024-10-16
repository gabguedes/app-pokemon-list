package com.github.gabguedes.listapokemons

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.gabguedes.listapokemons.data.api.model.Pokemon
import com.github.gabguedes.listapokemons.databinding.ActivityPokemonDetailBinding
import com.github.gabguedes.listapokemons.databinding.PokemonItemBinding

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val pokemon = intent.getParcelableExtra("pokemon", Pokemon::class.java)

        binding.tvNome.text = pokemon?.name
        binding.tvNumero.text = pokemon?.id.toString()

        Glide.with(this).load(pokemon?.imageUrl).error(R.drawable.baseline_browser_not_supported_24).into(binding.rvSprites)
    }

}