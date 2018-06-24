package com.laurinware.pokebrain;

import android.graphics.Region;
import android.util.Log;

import com.laurinware.pokebrain.Model.Pokemon;
import com.laurinware.pokebrain.Model.PokemonList;
import com.laurinware.pokebrain.Model.RegionList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {

    public static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private final String appname = "PokeBrain";

    Retrofit retrofit;
    PokeApiEndpointInterface apiService;
    PokemonList pokemonList;
    Pokemon pokemonItem;
    RegionList regionList;

    public RemoteDataSource(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(PokeApiEndpointInterface.class);
    }
//TODO LiveData
    public PokemonList getAllPokemon(){
        Call<PokemonList> call = apiService.getAllPokemon(appname);
        call.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                int statusCode = response.code();
                pokemonList = response.body();
                Log.d("RESPONSE", pokemonList.getResults().get(0).getName());
            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {
                // Log error here since request failed
                Log.d("FAILURE", "Failure on getting data" +
                        "\nURL: " + call.request().url() +
                        "\nTHROWABLE: " + t.getMessage());
            }
        });
        return pokemonList;
    }

    public Pokemon getPokemon(String URL){

        String[] separated = URL.split("/");

        Call<Pokemon> call = apiService.getPokemon(appname,Integer.parseInt(separated[separated.length-1]));
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                int statusCode = response.code();
                pokemonItem = response.body();
                Log.d("RESPONSE", pokemonItem.getName());
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                // Log error here since request failed
                Log.d("FAILURE", "Failure on getting data" +
                        "\nURL: " + call.request().url() +
                        "\nTHROWABLE: " + t.getMessage());
            }
        });
        return pokemonItem;
    }

    public RegionList getAllRegions(){

        Call<RegionList> call = apiService.getAllRegions(appname);
        call.enqueue(new Callback<RegionList>() {
            @Override
            public void onResponse(Call<RegionList> call, Response<RegionList> response) {
                int statusCode = response.code();
                regionList = response.body();
                Log.d("RESPONSE", regionList.getResults().get(0).getName());
            }

            @Override
            public void onFailure(Call<RegionList> call, Throwable t) {
                // Log error here since request failed
                Log.d("FAILURE", "Failure on getting data" +
                        "\nURL: " + call.request().url() +
                        "\nTHROWABLE: " + t.getMessage());
            }
        });
        return regionList;
    }
}
