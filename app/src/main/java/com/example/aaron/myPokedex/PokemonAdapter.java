package com.example.aaron.myPokedex;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.Normalizer;
import java.util.ArrayList;

/**
 * Created by Aaron on 3/10/2016.
 * An adapter class to allow the use of recyclerView
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.CustomViewHolder> {

    Context context;
    ArrayList<Pokedex.Pokemon> pokemon;

    PokemonAdapter(Context context, ArrayList<Pokedex.Pokemon> pokemon) {
        this.context = context;
        this.pokemon = pokemon;

    }

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_view, null, false);
        return new CustomViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return pokemon.size();
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Pokedex.Pokemon pokeData = pokemon.get(position);
        Glide.with(context).load(findUrl(pokeData.name)).into(holder.image);
        holder.name.setText(pokeData.name);
    }

    private String findUrl(String name) {
        name = Normalizer.normalize(name.toLowerCase(), Normalizer.Form.NFD);
        name = name.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").replaceAll("\\p{P}", "");
        //format the url to allow returning of different pokemon pictures based on the name
        return String.format("http://img.pokemondb.net/artwork/%s.jpg", name);
    }

    public void setList(ArrayList<Pokedex.Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        CustomViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textView);
            image = (ImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(v.getContext(), PokemonInfo.class);
                    myIntent.putExtra("NAME", name.getText().toString());
                    v.getContext().startActivity(myIntent);
                }
            });
        }
    }
}