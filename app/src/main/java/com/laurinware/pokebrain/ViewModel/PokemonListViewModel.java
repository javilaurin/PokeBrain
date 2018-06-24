package com.laurinware.pokebrain.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.laurinware.pokebrain.Model.Pokemon;
import com.laurinware.pokebrain.Model.PokemonList;
import com.laurinware.pokebrain.Data.RemoteDataSource;
import com.laurinware.pokebrain.Model.RegionItem;
import com.laurinware.pokebrain.Model.RegionList;

public class PokemonListViewModel extends ViewModel {

    RemoteDataSource remoteDataSource = new RemoteDataSource();

    public LiveData<PokemonList> getPokemonList(){
        remoteDataSource.getAllPokemon();
        return remoteDataSource.pokemonList;
    }
    public LiveData<Pokemon> getPokemon(String url){
        remoteDataSource.getPokemon(url);
        return remoteDataSource.pokemonItem;
    }
    public LiveData<RegionList> getAllRegions(){
        remoteDataSource.getAllRegions();
        return remoteDataSource.regionList;
    }
}
