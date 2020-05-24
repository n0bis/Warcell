/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.osgispawner;

import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
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
    private GameData gameData;
    private World world;
    private Entity spawner, enemy;
    private SpawnerProcessor instance;
    private float radians = 3.1415f / 2;
    
    @Before
    public void setUp() {
        plugin = mock(plugin.getClass());
        doReturn("warcell.osgienemy.EnemyPlugin").when(plugin.getClass().getCanonicalName());
        doNothing().when(plugin).start(gameData, world);
        
        spawner = new Spawner();
        spawner.add(new PositionPart(448, 3200, radians));
        spawner.add(new TexturePart("SpawnerDebug.png"));
        spawner.add(new SpawnerPart(140, 5, 10));
        
        enemy = new Enemy();
        enemy.add(new PositionPart(0, 0, radians));
        
        gameData = new GameData();
        gameData.setDelta(radians);
        gameData.setGamePlugins(Arrays.asList(plugin));
        
        world = new World();
        world.addEntity(enemy);
        world.addEntity(spawner);
        
        instance = new SpawnerProcessor();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class SpawnerProcessor.
     */
    @Test
    public void testProcess() {
        instance.process(gameData, world);
    }
    
    @Test
    public void testEnemyNotMovedBackToSpawnPoint() {
        System.out.println("Teeest");
    }
    
}
