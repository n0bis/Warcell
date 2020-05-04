/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.weapon.parts;

import java.util.ArrayList;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.entityparts.EntityPart;
import warcell.common.weapon.service.WeaponsSPI;

/**
 *
 * @author birke
 */
public class InventoryPart implements EntityPart {
    private ArrayList<WeaponsSPI> weaponsArray;
    private WeaponsSPI currentWeapon; 

    public InventoryPart() {
        if (weaponsArray == null) {
            this.weaponsArray = new ArrayList();
        }
    }

    
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
        if (!weaponsArray.isEmpty() && currentWeapon == null) {
             currentWeapon = weaponsArray.get(0);
        }
    }
   
    /**
     * sets the current weapon to the given weapon
     * @param weapon
     */
    public void setCurrentWeapon(WeaponsSPI weapon) {
        this.currentWeapon = weapon;
    }
    /**
     * sets the weapon to the index given
     * @param i
     */
    public void setCurrentWeapon(int i) {
        this.currentWeapon = weaponsArray.get(i);
    }

    public WeaponsSPI getCurrentWeapon() {
        return this.currentWeapon;
    }
    
    /**
     * sets current weapon to the next in the list
     * @return
     */
    public WeaponsSPI nextWeapon() {
        if (weaponsArray.size() != 0) {
            int i = weaponsArray.indexOf(this.currentWeapon);
            i++;

            i = Math.floorMod(i, weaponsArray.size());

            if (!weaponsArray.isEmpty()) {
                this.currentWeapon = weaponsArray.get(i);
            } else {
                this.currentWeapon = null;
            }

            return this.currentWeapon;
        }
        this.currentWeapon = null;
        return this.currentWeapon;
    }

    /**
     * sets the current weapon to previous in the list
     * @return
     */
    public WeaponsSPI previousWeapon() {
        if (weaponsArray.size() != 0) {
            int i = weaponsArray.indexOf(this.currentWeapon);
            i--;

            i = Math.floorMod(i, weaponsArray.size());

            if (i == -1) {
                this.currentWeapon = null;
            } else {
                this.currentWeapon = weaponsArray.get(i);
            }

            return this.currentWeapon;
        }
        this.currentWeapon = null;
        return this.currentWeapon;
    }

    public void addWeapon(WeaponsSPI weapon) {
        if (!weaponsArray.contains(weapon)) {
            this.weaponsArray.add(weapon);
        }
    }    
    
    public void removeAllWeapons() {
        this.weaponsArray.removeAll(weaponsArray);
    }
    
}
