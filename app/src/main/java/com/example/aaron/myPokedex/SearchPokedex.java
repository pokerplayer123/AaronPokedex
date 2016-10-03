package com.example.aaron.myPokedex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Aaron on 3/10/2016.
 */

public class SearchPokedex extends AppCompatActivity{
    private Pokedex pokedex = new Pokedex();
    private ArrayList<Pokedex.Pokemon> pokeList = pokedex.getPokemon();
    private PokemonAdapter pokemonAdapter;

    private RecyclerView pokemonSearchView;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        pokemonSearchView = (RecyclerView) findViewById(R.id.pokemon_search_list);
        pokemonSearchView.setLayoutManager(new GridLayoutManager(this, 2));
        pokemonAdapter = new PokemonAdapter(getApplicationContext(), pokeList);
        pokemonSearchView.setAdapter(pokemonAdapter);

        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Pokedex.Pokemon> searchResults = new ArrayList<Pokedex.Pokemon>();
                for (Pokedex.Pokemon p : pokeList) {
                    if (containsIgnoreCase(p.name, s.toString()))
                        searchResults.add(p);
                }
                pokemonAdapter.setList(searchResults);
                pokemonAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }
}
