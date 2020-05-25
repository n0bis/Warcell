/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.map.Tile;

/**
 *
 * @author madsfalken
 */
public class MapPluginTest {

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
     * Test of start method, of class MapPlugin.
     */
    @Test
    public void testStart() {
        MapPlugin instance = new MapPlugin();
        instance.start(gameData, world);
        int expected = 1;
        int result = world.getEntities(Tile.class).size();

        assertEquals(expected, result);
    }

    /**
     * Test of stop method, of class MapPlugin.
     */
    @Test
    public void testStop() {
        MapPlugin instance = new MapPlugin();
        instance.stop(gameData, world);
        int expected = 0;
        int result = world.getEntities(Tile.class).size();

        assertEquals(expected, result);
    }

}
