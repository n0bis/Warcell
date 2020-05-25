/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.osgispawner;

import java.util.Arrays;
import java.util.Random;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.SpawnerPart;
import warcell.common.data.entityparts.TexturePart;
import warcell.common.enemy.Enemy;
import warcell.common.services.IGamePluginService;
import warcell.common.spawner.Spawner;

/**
 *
 * @author madsfalken
 */
public class SpawnerProcessorTest {
    
    private IGamePluginService plugin;
    private Random random;
    private GameData gameData;
    private World world;
    private Entity spawner, enemy;
    private SpawnerProcessor instance;
    private float radians = 3.1415f / 2;
    
    @Before
    public void setUp() {
        plugin = mock(IGamePluginService.class);
        random = mock(Random.class);
        when(random.nextInt(anyInt())).thenReturn(1);
        
        spawner = new Spawner();
        spawner.add(new PositionPart(20, 20, radians));
        spawner.add(new TexturePart("SpawnerDebug.png"));
        spawner.add(new SpawnerPart(140, 1, 10));
        
        enemy = new Enemy();
        enemy.add(new PositionPart(0, 0, radians));
        
        gameData = new GameData();
        gameData.setDelta(radians);
        gameData.setGamePlugins(Arrays.asList(plugin));
        
        world = new World();
        world.addEntity(enemy);
        world.addEntity(spawner);
        
        doNothing().when(plugin).start(gameData, world);
        
        instance = new SpawnerProcessor(random);
        instance.enemyGamePluginServices.add(plugin);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class SpawnerProcessor.
     */
    //@Test
    public void testProcess() {
        instance.process(gameData, world);
        PositionPart enemyPosition = enemy.getPart(PositionPart.class);
        PositionPart spawnerPosition = spawner.getPart(PositionPart.class);
        SpawnerPart spawnerPart = spawner.getPart(SpawnerPart.class);

        float spawnX = spawnerPosition.getX() + 1 - spawnerPart.getRadius();
        float spawnY = spawnerPosition.getY() + 1 - spawnerPart.getRadius();
        assertEquals("Expected enemy position x to be equals to spawn x", spawnX, enemyPosition.getX(), 5);
        assertEquals("Expected enemy position y to be equals to spawn y", spawnY, enemyPosition.getY(), 5);
    }
    
    @Test
    public void testEnemyNotMovedBackToSpawnPoint() {
        instance.process(gameData, world);
        PositionPart enemyPosition = enemy.getPart(PositionPart.class);
        PositionPart spawnerPosition = spawner.getPart(PositionPart.class);
        SpawnerPart spawnerPart = spawner.getPart(SpawnerPart.class);

        float spawnX = spawnerPosition.getX() + 1 - spawnerPart.getRadius();
        float spawnY = spawnerPosition.getY() + 1 - spawnerPart.getRadius();
        assertEquals("Expected enemy position x to be equals to spawn x", spawnX, enemyPosition.getX(), 5);
        assertEquals("Expected enemy position y to be equals to spawn y", spawnY, enemyPosition.getY(), 5);
        
        // enemy have moved, do not return to spawn point
        PositionPart updateEnemyPosition = new PositionPart(2, 2, radians);
        enemy.add(updateEnemyPosition);
        instance.process(gameData, world);
        PositionPart newEnemyPosition = enemy.getPart(PositionPart.class);
        
        assertNotEquals("Expected enemy position x to NOT be equals to spawn x", spawnX, newEnemyPosition.getX(), 5);
        assertNotEquals("Expected enemy position y to NOT be equals to spawn y", spawnY, newEnemyPosition.getY(), 5);
    }
    
}
