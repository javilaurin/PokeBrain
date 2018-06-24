
package com.laurinware.pokebrain.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ability {

    @SerializedName("is_hidden")
    @Expose
    private boolean isHidden;
    @SerializedName("slot")
    @Expose
    private int slot;
    @SerializedName("ability")
    @Expose
    private Ability_ ability;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Ability() {
    }

    /**
     * 
     * @param isHidden
     * @param slot
     * @param ability
     */
    public Ability(boolean isHidden, int slot, Ability_ ability) {
        super();
        this.isHidden = isHidden;
        this.slot = slot;
        this.ability = ability;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Ability_ getAbility() {
        return ability;
    }

    public void setAbility(Ability_ ability) {
        this.ability = ability;
    }

}
