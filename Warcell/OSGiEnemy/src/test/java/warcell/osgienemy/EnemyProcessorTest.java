/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.osgienemy;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import warcell.common.ai.AISPI;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.DamagePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.CirclePart;
import warcell.common.enemy.Enemy;
import warcell.common.player.Player;
import warcell.common.utils.Vector2D;

/**
 *
 * @author madsfalken
 */
public class EnemyProcessorTest {

    private AISPI ai;
    private World world;
    private Entity player, enemy;
    private GameData gameData;
    private EnemyProcessor instance;

    @Before
    public void setUp() {
        ai = mock(AISPI.class);
        world = new World();
        gameData = new GameData();
        gameData.setDelta(0.0165346f);
        instance = new EnemyProcessor(false);
        float radians = 3.1415f / 2;
        doNothing().when(ai).startAI(anyObject());
        when(ai.getPath(anyObject(), anyObject())).thenReturn(Arrays.asList(new PositionPart(0, 50, 0)));
        instance.setAIService(ai);

        player = new Player();
        player.add(new PositionPart(0, 50, 0));

        enemy = new Enemy();
        enemy.add(new CirclePart(1, 1, 75f));
        enemy.add(new LifePart(1));
        enemy.add(new DamagePart(2, 3));
        enemy.add(new MovingPart(100, 150, true));
        enemy.add(new PositionPart(0, 0, 0));
        enemy.add(new CollisionPart(true, 3));
        enemy.add(new AnimationTexturePart(new Vector2D(0, 0), "Zomies", 17, 1, 0.09f, 43f, 59f, 1f, 1f));

        world.addEntity(player);
        world.addEntity(enemy);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class EnemyProcessor.
     */
    @Test
    public void testProcess() {
        instance.process(gameData, world);
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        float startYPosition = 0;
        float expected = 90.0f;
        float result = positionPart.getRadians();

        assertEquals("Expected angle to be 90 degress", expected, result, 5);
        assertEquals("Expected new psoition of y to be close to 0.0273393, given delta 0.0165346f", 0.0273393, positionPart.getY(), 5);
        assertTrue("Expected enemy to have moved towards player", startYPosition < positionPart.getY());
    }

}
