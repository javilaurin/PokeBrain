
package com.laurinware.pokebrain.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokemon {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("base_experience")
    @Expose
    private int baseExperience;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("is_default")
    @Expose
    private boolean isDefault;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("abilities")
    @Expose
    private List<Ability> abilities = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Pokemon() {
    }

    /**
     * 
     * @param id
     * @param weight
     * @param height
     * @param baseExperience
     * @param abilities
     * @param order
     * @param isDefault
     * @param name
     */
    public Pokemon(int id, String name, int baseExperience, int height, boolean isDefault, int order, int weight, List<Ability> abilities) {
        super();
        this.id = id;
        this.name = name;
        this.baseExperience = baseExperience;
        this.height = height;
        this.isDefault = isDefault;
        this.order = order;
        this.weight = weight;
        this.abilities = abilities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

}
