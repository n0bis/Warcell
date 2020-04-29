package warcell.osgispawner;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.services.IGamePluginService;
import warcell.common.spawner.Spawner;

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
        Entity spawner = new Spawner();

        float x = 500;
        float y = 500;
        float radians = 3.1415f / 2;

        spawner.add(new PositionPart(x, y, radians));

        return spawner;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(entityID);
    }
}
