package warcell.osgispawner;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.SpawnerPart;
import warcell.common.data.entityparts.TexturePart;
import warcell.common.services.IGamePluginService;
import warcell.common.spawner.Spawner;

public class SpawnerPlugin implements IGamePluginService {
    private String entityID;
    private final String debugPath = "SpawnerDebug.png";

    
    public SpawnerPlugin() {
    }


    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        if (world == null || gameData == null) {
            throw new IllegalArgumentException("World or gamedata is null");
        }
        Entity spawner1 = createSpawner1(gameData);
        entityID = world.addEntity(spawner1);
        Entity spawner2 = createSpawner2(gameData);
        entityID = world.addEntity(spawner2);
        Entity spawner3 = createSpawner3(gameData);
        entityID = world.addEntity(spawner3);
        Entity spawner4 = createSpawner4(gameData);
        entityID = world.addEntity(spawner4);
        Entity spawner5 = createSpawner5(gameData);
        entityID = world.addEntity(spawner5);
        Entity spawner6 = createSpawner6(gameData);
        entityID = world.addEntity(spawner6);
        Entity spawner7 = createSpawner7(gameData);
        entityID = world.addEntity(spawner7);
        Entity spawner8 = createSpawner8(gameData);
        entityID = world.addEntity(spawner8);
    }
    
    /**
     * creates a player entity
     * @param gameData the GameData of the Game class
     * @return the created player
     */
    private Entity createSpawner1(GameData gameData) {
        Entity spawner = new Spawner();

        float radians = 3.1415f / 2;
        spawner.add(new PositionPart(448, 3200, radians));
        spawner.add(new TexturePart(debugPath));
        spawner.add(new SpawnerPart(140, 5, 100));

        return spawner;
    }
    private Entity createSpawner2(GameData gameData) {
        Entity spawner = new Spawner();

        float radians = 3.1415f / 2;
        spawner.add(new PositionPart(1376, 3200, radians));
        spawner.add(new TexturePart(debugPath));
        spawner.add(new SpawnerPart(150, 4, 100));

        return spawner;
    }
    private Entity createSpawner3(GameData gameData) {
        Entity spawner = new Spawner();

        float radians = 3.1415f / 2;
        spawner.add(new PositionPart(2784, 3200, radians));
        spawner.add(new TexturePart(debugPath));
        spawner.add(new SpawnerPart(140, 5, 100));

        return spawner;
    }
    private Entity createSpawner4(GameData gameData) {
        Entity spawner = new Spawner();

        float radians = 3.1415f / 2;
        spawner.add(new PositionPart(3200, 1152, radians));
        spawner.add(new TexturePart(debugPath));
        spawner.add(new SpawnerPart(100, 5, 30));

        return spawner;
    }
    private Entity createSpawner5(GameData gameData) {
        Entity spawner = new Spawner();

        float radians = 3.1415f / 2;
        spawner.add(new PositionPart(3200, 2208, radians));
        spawner.add(new TexturePart(debugPath));
        spawner.add(new SpawnerPart(150, 6, 100));

        return spawner;
    }
    private Entity createSpawner6(GameData gameData) {
        Entity spawner = new Spawner();

        float radians = 3.1415f / 2;
        spawner.add(new PositionPart(2336, 0, radians));
        spawner.add(new TexturePart(debugPath));
        spawner.add(new SpawnerPart(130, 4, 100));

        return spawner;
    }
    private Entity createSpawner7(GameData gameData) {
        Entity spawner = new Spawner();

        float radians = 3.1415f / 2;
        spawner.add(new PositionPart(1216, 0, radians));
        spawner.add(new TexturePart(debugPath));
        spawner.add(new SpawnerPart(100, 6, 50));

        return spawner;
    }
    private Entity createSpawner8(GameData gameData) {
        Entity spawner = new Spawner();

        float radians = 3.1415f / 2;
        spawner.add(new PositionPart(0, 1184, radians));
        spawner.add(new TexturePart(debugPath));
        spawner.add(new SpawnerPart(120, 10, 10));

        return spawner;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity spawner : world.getEntities(Spawner.class))
            world.removeEntity(spawner);
    }
}
