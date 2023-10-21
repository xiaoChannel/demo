package com.hiltoncn.demo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.google.gson.Gson
import com.hiltoncn.demo.PokemonspeciesQuery
import com.hiltoncn.demo.R
import com.hiltoncn.demo.adapter.PokemonListAdapter
import com.hiltoncn.demo.api.HttpClient
import com.hiltoncn.demo.databinding.ActivityMainBinding
import com.hiltoncn.demo.type.Pokemon_v2_pokemonspecies_bool_exp
import com.hiltoncn.demo.type.String_comparison_exp
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pokenmons = mutableListOf<PokemonspeciesQuery.Pokemon_v2_pokemonspecy>()
    private var adapter: PokemonListAdapter? = null
    private val limit = 4
    private var offset = 0
    private var page = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.btSearch.setOnClickListener {
            page = 0
            offset = 0
            pokenmons.clear()
            adapter?.notifyDataSetChanged()
            binding.progressBar.visibility = View.VISIBLE
            serchPokenmon(binding.etSearch.text.toString().trim(), limit, offset)
        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    setButtonColor(R.color.purple_500)
                    pokenmons.clear()
                    adapter?.notifyDataSetChanged()
                } else {
                    setButtonColor(R.color.grey)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        adapter = PokemonListAdapter(pokenmons, this)
        binding.rvSearchList.layoutManager = LinearLayoutManager(this)
        binding.rvSearchList.adapter = adapter
        binding.btNext.setOnClickListener {
            page++
            offset = page * limit
            pokenmons.clear()
            adapter?.notifyDataSetChanged()
            binding.progressBar.visibility = View.VISIBLE
            serchPokenmon(binding.etSearch.text.toString().trim(), limit, offset)
        }
        adapter?.itemOnClick = object :PokemonListAdapter.ItemOnClick{
            override fun click(pokemon: PokemonspeciesQuery.Pokemon_v2_pokemon) {
                PokemonDetailsActivity.startActivity(this@MainActivity,Gson().toJson(pokemon))
            }
        }

    }


    private fun setButtonColor(color: Int) {
        binding.btSearch.background =
            ContextCompat.getDrawable(this@MainActivity, color)
    }

    /**
     * For simple and convenient use
     * try ..
     */
    private fun serchPokenmon(name: String, mLimit: Int, mOffset: Int) {
        lifecycleScope.launch {

            try {
                HttpClient.client()?.query(
                    PokemonspeciesQuery(
                        offset = Optional.present(mOffset),
                        limit = Optional.present(mLimit),
                        where = Optional.present(
                            Pokemon_v2_pokemonspecies_bool_exp(
                                name = Optional.present(
                                    String_comparison_exp(_regex = Optional.present(name))
                                )
                            )
                        )
                    )
                )?.toFlow()?.collect {
                    it.data?.pokemon_v2_pokemonspecies?.let {
                        pokenmons.addAll(it)
                        adapter?.notifyDataSetChanged()
                    }
                }
            } catch (e: ApolloException) {
                println(e)
            }
            binding.progressBar.visibility = View.GONE

        }

    }
}