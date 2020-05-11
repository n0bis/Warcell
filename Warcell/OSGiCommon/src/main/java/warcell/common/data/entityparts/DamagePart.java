/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;

/**
 *
 * @author Patrick
 */
public class DamagePart implements EntityPart {
    
    private int damage;
    private float multiplier;
    private int originalDamage;
    
    public DamagePart(int damage) {
        this.damage = damage;
        this.originalDamage = damage;
        this.multiplier = 1;
    }
    
    public DamagePart(int damage, float multiplier) {
        this.damage = Math.round(damage * multiplier);
        this.originalDamage = damage;
    }
    
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
    public void resetDamage() {
        this.damage = originalDamage;
    }

    
    /**
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    

    /**
     * @return the multiplier
     */
    public float getMultiplier() {
        return multiplier;
    }

    /**
     * @param multiplier the multiplier to set
     */
    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
        damage = Math.round(damage * multiplier);
    }

}
