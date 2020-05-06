/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.weapon.rifle;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.player.Player;
import warcell.common.services.IGamePluginService;
import warcell.common.weapon.parts.InventoryPart;
import warcell.common.weapon.service.WeaponsSPI;

/**
 *
 * @author birke
 */
public class WeaponPlugin implements IGamePluginService {
    private final WeaponsSPI rifle = new RifleWeapon();
    private final WeaponsSPI shotgun = new ShotgunWeapon();
    
    @Override
    public void start(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Player.class)) {
            InventoryPart inv = entity.getPart(InventoryPart.class);
            inv.addWeapon(rifle);
            inv.addWeapon(shotgun);
        }

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Player.class)) {
            InventoryPart inv = entity.getPart(InventoryPart.class);
            inv.removeAllWeapons();
        }
    }
}
