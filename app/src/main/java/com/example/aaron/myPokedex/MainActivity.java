package com.example.aaron.myPokedex;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Aaron on 2/10/2016.
 */

public class MainActivity extends AppCompatActivity {
    private Pokedex pokedex = new Pokedex();
    private ArrayList<Pokedex.Pokemon> pokeList = pokedex.getPokemon();
    private PokemonAdapter pokemonAdapter;

    private RecyclerView pokemonListView;
    private boolean[] buttonToggle = new boolean[]{true, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonListView = (RecyclerView) findViewById(R.id.pokemon_list);
        pokemonListView.setLayoutManager(new GridLayoutManager(this, 2));
        pokemonAdapter = new PokemonAdapter(getApplicationContext(), pokeList);
        pokemonListView.setAdapter(pokemonAdapter);
    }

    public void startSearch(View v) {
        startActivity(new Intent(getApplicationContext(), SearchPokedex.class));
    }

    public void switchLayoutManager(View v) {
        Button btn = (Button) v;
        if (btn.getText().toString().equals(getString(R.string.linear))) {
            pokemonListView.setLayoutManager(new LinearLayoutManager(this));
            btn.setText(R.string.grid);
        } else {
            pokemonListView.setLayoutManager(new GridLayoutManager(this, 2));
            btn.setText(R.string.linear);
        }
    }

    public void toggleWeak(View v) {
        Button b = (Button) v;
        if (buttonToggle[0])
            b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.holo_orange_dark));
        else
            b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.default_button));
        buttonToggle[0] = !buttonToggle[0];
        filterPokemonList();
    }

    public void toggleMedium(View v) {
        Button b = (Button) v;
        if (buttonToggle[1])
            b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.holo_blue_blue_bright));
        else
            b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.default_button));
        buttonToggle[1] = !buttonToggle[1];
        filterPokemonList();
    }

    public void toggleStrong(View v) {
        Button b = (Button) v;
        if (buttonToggle[2])
            b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.holo_purple_));
        else
            b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.default_button));
        buttonToggle[2] = !buttonToggle[2];
        filterPokemonList();
    }

    private boolean isWeak(int x) {
        return 0 <= x && x < 50;
    }

    private boolean isMedium(int x) {
        return 50 <= x && x <= 100;
    }

    private boolean isStrong(int x) {
        return x > 100;
    }

    private boolean filter(int x) {
        return buttonToggle[0] && isWeak(x) ||
                buttonToggle[1] && isMedium(x) ||
                buttonToggle[2] && isStrong(x);
    }

    private void filterPokemonList() {
        ArrayList<Pokedex.Pokemon> pokeListCopy = new ArrayList<>();
        for (Pokedex.Pokemon p : pokeList) {
            if (filter(Integer.parseInt(p.defense)))
                pokeListCopy.add(p);
        }
        pokemonAdapter.setList(pokeListCopy);
        pokemonAdapter.notifyDataSetChanged();
    }
}
