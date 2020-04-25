package warcell.osgidefense;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.TexturePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.defense.Defense;
import warcell.common.services.IGamePluginService;

public class DefensePlugin implements IGamePluginService {
    private String entityID;
    private final String texturePath = "TestChair.png";
    
    public DefensePlugin() {
    }


    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        if (world == null || gameData == null) {
            throw new IllegalArgumentException("World or gamedata is null");
        }
        Entity defense = createDefense(gameData);
        entityID = world.addEntity(defense);
    }
    
    /**
     * creates a defense entity
     * @param gameData the GameData of the Game class
     * @return the created defense
     */
    private Entity createDefense(GameData gameData) {
        Entity defense = new Defense();

        float x = gameData.getDisplayWidth() / 3;
        float y = gameData.getDisplayHeight() / 3;
        float radians = 3.1415f / 2;
        int maxLife = 100;
        defense.add(new LifePart(maxLife));
        defense.add(new PositionPart(x, y, radians));
        defense.add(new TexturePart(texturePath, x, y, 0.5f, 0.5f));
        

        return defense;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(entityID);
    }
}
