/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.weapon.service;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;

/**
 *
 * @author birke
 */
public interface WeaponsSPI {
    String getName();
    String getDescription();
    String getIconPath();
    
    void shoot(Entity entity, GameData gameData, World world);
}
