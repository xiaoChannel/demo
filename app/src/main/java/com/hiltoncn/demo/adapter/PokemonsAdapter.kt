package com.hiltoncn.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hiltoncn.demo.PokemonspeciesQuery
import com.hiltoncn.demo.databinding.ItemPokemonsBinding

class PokemonsAdapter(val pokemons: List<PokemonspeciesQuery.Pokemon_v2_pokemon>) :
    RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return pokemons.size
    }


    class ViewHolder(val binding: ItemPokemonsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.binding.tvName.text = pokemon.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonsBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ViewHolder(binding)
    }
}