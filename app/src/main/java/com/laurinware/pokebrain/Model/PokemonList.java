
package com.laurinware.pokebrain.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonList {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private java.util.List<PokemonListItem> results = null;
    @SerializedName("next")
    @Expose
    private String next;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PokemonList() {
    }

    /**
     * 
     * @param results
     * @param previous
     * @param count
     * @param next
     */
    public PokemonList(Integer count, Object previous, java.util.List<PokemonListItem> results, String next) {
        super();
        this.count = count;
        this.previous = previous;
        this.results = results;
        this.next = next;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public java.util.List<PokemonListItem> getResults() {
        return results;
    }

    public void setResults(java.util.List<PokemonListItem> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

}
