/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.osgienemy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.enemy.Enemy;

/**
 *
 * @author madsfalken
 */
public class EnemyPluginTest {

    private World world;
    private GameData gameData;

    @Before
    public void setUp() {
        world = new World();
        gameData = new GameData();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class EnemyPlugin.
     */
    @Test
    public void testStart() {
        EnemyPlugin instance = new EnemyPlugin();
        instance.start(gameData, world);
        int expected = 1;
        int result = world.getEntities(Enemy.class).size();

        assertEquals(expected, result);
    }

    /**
     * Test of start method, of class EnemyPlugin.
     */
    @Test
    public void testStartOf5() {
        EnemyPlugin instance = new EnemyPlugin(5);
        instance.start(gameData, world);
        int expected = 5;
        int result = world.getEntities(Enemy.class).size();

        assertEquals(expected, result);
    }

    /**
     * Test of stop method, of class EnemyPlugin.
     */
    @Test
    public void testStop() {
        EnemyPlugin instance = new EnemyPlugin();
        instance.stop(gameData, world);
        int expected = 0;
        int result = world.getEntities(Enemy.class).size();

        assertEquals(expected, result);
    }

}
