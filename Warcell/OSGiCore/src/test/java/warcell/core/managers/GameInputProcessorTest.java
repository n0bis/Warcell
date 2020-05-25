/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.core.managers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import warcell.common.data.GameData;
import warcell.common.data.GameKeys;

/**
 *
 * @author madsfalken
 */
public class GameInputProcessorTest {

    private GameData gameData;
    private GameInputProcessor instance;

    @Before
    public void setUp() {
        gameData = new GameData();
        instance = new GameInputProcessor(gameData);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of keyDown method, of class GameInputProcessor.
     */
    @Test
    public void testKeyDown() {
        int k = 51;
        boolean expResult = true;
        boolean result = instance.keyDown(k);
        assertEquals(expResult, result);
        assertEquals(expResult, gameData.getKeys().isDown(GameKeys.W));
    }

    /**
     * Test of keyUp method, of class GameInputProcessor.
     */
    @Test
    public void testKeyUp() {
        int k = 51;
        boolean expResult = false;
        boolean result = instance.keyUp(k);
        assertEquals(true, result);
        assertEquals(expResult, gameData.getKeys().isDown(GameKeys.W));
    }

}
