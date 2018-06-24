package com.laurinware.pokebrain.Data;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.laurinware.pokebrain.Model.Pokemon;
import com.laurinware.pokebrain.Model.PokemonList;
import com.laurinware.pokebrain.Model.RegionItem;
import com.laurinware.pokebrain.Model.RegionList;

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



    public MutableLiveData<PokemonList> pokemonList;
    public MutableLiveData<Pokemon> pokemonItem;
    public MutableLiveData<RegionList> regionList;

    public RemoteDataSource(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(PokeApiEndpointInterface.class);

        pokemonList = new MutableLiveData<PokemonList>();
        pokemonItem = new MutableLiveData<Pokemon>();
        regionList = new MutableLiveData<RegionList>();
    }
//TODO LiveData
    public void getAllPokemon(){
        Call<PokemonList> call = apiService.getAllPokemon(appname);
        call.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                int statusCode = response.code();
                pokemonList.setValue(response.body());
                Log.d("RESPONSE", pokemonList.getValue().getResults().get(0).getName());
            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {
                // Log error here since request failed
                Log.d("FAILURE", "Failure on getting data" +
                        "\nURL: " + call.request().url() +
                        "\nTHROWABLE: " + t.getMessage());
            }
        });
    }

    public void getPokemon(String url){

        String[] separated = url.split("/");

        Call<Pokemon> call = apiService.getPokemon(appname,Integer.parseInt(separated[separated.length-1]));
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                int statusCode = response.code();
                pokemonItem.setValue(response.body());
                Log.d("RESPONSE", pokemonItem.getValue().getName());
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                // Log error here since request failed
                Log.d("FAILURE", "Failure on getting data" +
                        "\nURL: " + call.request().url() +
                        "\nTHROWABLE: " + t.getMessage());
            }
        });
    }

    public void getAllRegions(){

        Call<RegionList> call = apiService.getAllRegions(appname);
        call.enqueue(new Callback<RegionList>() {
            @Override
            public void onResponse(Call<RegionList> call, Response<RegionList> response) {
                int statusCode = response.code();
                regionList.setValue(response.body());
                Log.d("RESPONSE", regionList.getValue().getResults().get(0).getName());
            }

            @Override
            public void onFailure(Call<RegionList> call, Throwable t) {
                // Log error here since request failed
                Log.d("FAILURE", "Failure on getting data" +
                        "\nURL: " + call.request().url() +
                        "\nTHROWABLE: " + t.getMessage());
            }
        });
    }
}
