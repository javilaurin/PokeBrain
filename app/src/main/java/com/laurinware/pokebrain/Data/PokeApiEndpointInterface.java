package com.laurinware.pokebrain.Data;

import com.laurinware.pokebrain.Model.Pokemon;
import com.laurinware.pokebrain.Model.PokemonList;
import com.laurinware.pokebrain.Model.RegionList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiEndpointInterface {

    // ALL POKEMON
    @GET("pokemon/?limit=802&offset=0")
    Call<PokemonList> getAllPokemon(@Header("User-Agent") String appname);

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Header("User-Agent") String appname, @Path("id") int id);

    // ALL REGIONS
    @GET("region/")
    Call<RegionList> getAllRegions(@Header("User-Agent") String appname);

    @GET("region/{id}")
    Call<Pokemon> getRegion(@Header("User-Agent") String appname, @Path("id") int id);
}
