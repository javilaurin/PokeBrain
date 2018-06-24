
package com.laurinware.pokebrain.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonListItem {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PokemonListItem() {
    }

    /**
     * 
     * @param name
     * @param url
     */
    public PokemonListItem(String url, String name) {
        super();
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
