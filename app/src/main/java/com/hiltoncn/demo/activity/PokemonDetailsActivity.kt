package com.hiltoncn.demo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.hiltoncn.demo.PokemonspeciesQuery
import com.hiltoncn.demo.databinding.ActivityPokemonDetailsBinding

class PokemonDetailsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PokemonDetails"
        fun startActivity(activity: AppCompatActivity, pokemonDetails: String) {
            val intent = Intent(activity, PokemonDetailsActivity::class.java)
            intent.putExtra(TAG, pokemonDetails)
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityPokemonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra(TAG)?.let {
            Gson().fromJson(it, PokemonspeciesQuery.Pokemon_v2_pokemon::class.java)
                ?.let { pokemon ->
                    binding.tvName.text = pokemon.name

                    val buffer = StringBuffer()
                    pokemon.pokemon_v2_pokemonabilities.forEach { ability ->
                        buffer.append(ability.pokemon_v2_ability?.name)
                        buffer.append("\n")
                    }
                    binding.tvAbility.text = buffer.toString()
                }
        }
        binding.tvReturn.setOnClickListener { finish() }

    }
}