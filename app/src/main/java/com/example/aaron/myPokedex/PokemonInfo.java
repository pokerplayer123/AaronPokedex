package com.example.aaron.myPokedex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.text.Normalizer;
import java.util.ArrayList;

/**
 * Created by Aaron on 3/10/2016.
 * This class is for the pokemon_info activity
 */

public class PokemonInfo extends AppCompatActivity {
    private Pokedex pokedex = new Pokedex();
    private ArrayList<Pokedex.Pokemon> pokeList = pokedex.getPokemon();
    private Pokedex.Pokemon pokemon = null;
    private ImageView pkInfo;
    private TextView pkName,
            pkNumber,
            pkAttack,
            pkDefense,
            pkType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        for (Pokedex.Pokemon p : pokeList)
            if (name.equals(p.name))
                pokemon = p;
        bindViews();
        populateViewsWithPokemonData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri searchUri = Uri.parse(String.format("https://pokemondb.net/pokedex/%s", pokemon.name));
                startActivity(new Intent(Intent.ACTION_VIEW, searchUri));
                //Search on pokemonDB website (This allows the user to learn more about the pokemon than what is displayed in the app
            }
        });
    }

    private void bindViews() {
        pkInfo = (ImageView) findViewById(R.id.pokemon_info_picture);
        pkName = (TextView) findViewById(R.id.pokemon_info_name);
        pkNumber = (TextView) findViewById(R.id.pokemon_info_number);
        pkAttack = (TextView) findViewById(R.id.pokemon_info_attack);
        pkDefense = (TextView) findViewById(R.id.pokemon_info_defense);
        pkType = (TextView) findViewById(R.id.pokemon_info_type);
    }
    //binds the pokemon's details to each textView

    private void populateViewsWithPokemonData() {
        Glide.with(this).load(findUrl(pokemon.name)).into(pkInfo);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(pokemon.name);
        pkName.setText(pokemon.name);
        pkNumber.setText("#" + pokemon.number);
        pkAttack.setText(pokemon.attack);
        pkDefense.setText(pokemon.defense);
        pkType.setText(pokemon.type);
    }
//This method populates the view with pokemon information
    private String findUrl(String name) {
        name = Normalizer.normalize(name.toLowerCase(), Normalizer.Form.NFD);
        name = name.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").replaceAll("\\p{P}", "");
        return String.format("http://img.pokemondb.net/artwork/%s.jpg", name);
    }
    //Method for finding the pokemon artworks from the pokemondb images, using the pokemon's name.
}
