/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.osgispawner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.spawner.Spawner;

/**
 *
 * @author madsfalken
 */
public class SpawnerPluginTest {

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
     * Test of start method, of class SpawnerPlugin.
     */
    @Test
    public void testStart() {
        SpawnerPlugin instance = new SpawnerPlugin();
        instance.start(gameData, world);
        int expected = 8;
        int result = world.getEntities(Spawner.class).size();

        assertEquals(expected, result);
    }

    /**
     * Test of stop method, of class SpawnerPlugin.
     */
    @Test
    public void testStop() {
        SpawnerPlugin instance = new SpawnerPlugin();
        instance.stop(gameData, world);
        int expected = 0;
        int result = world.getEntities(Spawner.class).size();

        assertEquals(expected, result);
    }

}
