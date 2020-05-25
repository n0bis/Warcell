/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.osgicollision;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.DamagePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.CirclePart;
import warcell.common.enemy.Enemy;
import warcell.common.player.Player;

/**
 *
 * @author madsfalken
 */
public class CollisionProcessorTest {

    private World world;
    private GameData gameData;
    private Entity player, enemy;
    private CollisionProcessor instance;

    @Before
    public void setUp() {
        float radius = 75f;
        int centreX = 0, centreY = 0;
        float x = 0, y = 0;
        int life = 1;
        float radians = 3.1415f / 2;
        player = new Player();
        player.add(new CirclePart(centreX, centreY, radius));
        player.add(new LifePart(life));
        player.add(new DamagePart(1));
        player.add(new CollisionPart(true, -1));
        player.add(new PositionPart(x, y, radians));

        enemy = new Enemy();
        enemy.add(new CirclePart(centreX, centreY, radius));
        enemy.add(new LifePart(life));
        enemy.add(new DamagePart(1));
        enemy.add(new CollisionPart(true, -1));
        enemy.add(new PositionPart(x, y, radians));

        world = new World();
        world.addEntity(player);
        world.addEntity(enemy);

        gameData = new GameData();

        instance = new CollisionProcessor();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class CollisionProcessor.
     */
    @Test
    public void testCollision() {
        instance.process(gameData, world);
        LifePart lp1 = player.getPart(LifePart.class);
        LifePart lp2 = enemy.getPart(LifePart.class);
        DamagePart dp1 = player.getPart(DamagePart.class);
        DamagePart dp2 = enemy.getPart(DamagePart.class);
        int expected = 0;

        assertEquals(expected, lp1.getLife());
        assertEquals(expected, lp2.getLife());
    }

    /**
     * Test of process method, of class CollisionProcessor.
     */
    @Test
    public void testDoesNotCollide() {
        player.add(new CirclePart(150, 150, 75f));
        instance.process(gameData, world);
        LifePart lp1 = player.getPart(LifePart.class);
        LifePart lp2 = enemy.getPart(LifePart.class);
        DamagePart dp1 = player.getPart(DamagePart.class);
        DamagePart dp2 = enemy.getPart(DamagePart.class);
        int expected = 1;

        assertEquals(expected, lp1.getLife());
        assertEquals(expected, lp2.getLife());
    }

}
