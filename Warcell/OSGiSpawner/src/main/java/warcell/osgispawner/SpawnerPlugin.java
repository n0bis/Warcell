package warcell.osgispawner;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.services.IGamePluginService;
import warcell.common.utils.Vector2D;

public class SpawnerPlugin implements IGamePluginService {
    private String entityID;

    
    public SpawnerPlugin() {
    }


    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        if (world == null || gameData == null) {
            throw new IllegalArgumentException("World or gamedata is null");
        }
        Entity spawner = createSpawner(gameData);
        entityID = world.addEntity(spawner);
    }
    
    /**
     * creates a player entity
     * @param gameData the GameData of the Game class
     * @return the created player
     */
    private Entity createSpawner(GameData gameData) {
        Entity player = new Spawner();

        float x = gameData.getDisplayWidth() / 3;
        float y = gameData.getDisplayHeight() / 3;
        float radians = 3.1415f / 2;

        player.add(new PositionPart(x, y, radians));
        

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(entityID);
    }
}
