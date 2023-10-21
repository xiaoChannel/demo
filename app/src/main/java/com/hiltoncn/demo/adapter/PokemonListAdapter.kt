package com.hiltoncn.demo.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hiltoncn.demo.PokemonspeciesQuery
import com.hiltoncn.demo.databinding.ItemPokemonListBinding

class PokemonListAdapter(
    val pokemonsList: List<PokemonspeciesQuery.Pokemon_v2_pokemonspecy>,
    mContext: Context
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    var context: Context = mContext
    var itemOnClick: ItemOnClick? = null
    override fun getItemCount(): Int {
        return pokemonsList.size
    }


    class ViewHolder(val binding: ItemPokemonListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemons = pokemonsList[position]

        holder.binding.layout.setBackgroundColor(
            getColor(
                pokemons.pokemon_v2_pokemoncolor?.name ?: ""
            )
        )
        holder.binding.rate.text = pokemons.capture_rate.toString()
        if (pokemons.pokemon_v2_pokemons.isNotEmpty()) {
            holder.binding.name.text = pokemons.pokemon_v2_pokemons.first().name
            val pokemonsAdapter = PokemonsAdapter(pokemons.pokemon_v2_pokemons)
            holder.binding.rvPokemons.layoutManager = GridLayoutManager(context, 3)
            holder.binding.rvPokemons.adapter = pokemonsAdapter
            pokemonsAdapter.notifyDataSetChanged()

        }
        holder.binding.layout.let {
            it.isClickable = true
            it.setOnClickListener {
                itemOnClick?.click(pokemons.pokemon_v2_pokemons.first())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonListBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ViewHolder(binding)
    }

    /**
     * Not all colors returned by the API can be successfully parsed
     * Default White
     */
    private fun getColor(tag: String): Int {

        if (tag.trim().isEmpty()) {
            return Color.WHITE
        } else {
            try {
                return Color.parseColor(tag)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return Color.WHITE
        }
    }

    interface ItemOnClick {
        fun click(pokemon: PokemonspeciesQuery.Pokemon_v2_pokemon)
    }


}