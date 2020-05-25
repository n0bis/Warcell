/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.bullet;

import java.util.Random;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.weapon.entities.Bullet;
import warcell.common.services.IGamePluginService;

/**
 *
 * @author birke
 */
public class BulletPlugin implements IGamePluginService {

    Random rand = new Random();

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }

    }
}
