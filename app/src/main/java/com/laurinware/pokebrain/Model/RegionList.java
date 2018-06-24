
package com.laurinware.pokebrain.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionList {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<RegionItem> results = null;
    @SerializedName("next")
    @Expose
    private Object next;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RegionList() {
    }

    /**
     * 
     * @param results
     * @param previous
     * @param count
     * @param next
     */
    public RegionList(Integer count, Object previous, List<RegionItem> results, Object next) {
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

    public List<RegionItem> getResults() {
        return results;
    }

    public void setResults(List<RegionItem> results) {
        this.results = results;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

}
