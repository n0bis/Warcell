package warcell.osgidefense;

import java.util.Random;
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
    private Random r = new Random();
    private final String[] textures = {"TestChair1.png", "TestChair2.png", "TestChair3.png", "TestChair4.png", "TestChair5.png", "TestChair6.png", "TestChair7.png"};
    private String texturePath = "";
    
    public DefensePlugin() {
    }


    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        if (world == null || gameData == null) {
            throw new IllegalArgumentException("World or gamedata is null");
        }
        texturePath = textures[r.nextInt(6)];
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
        defense.add(new PositionPart(300, 300, radians));
        defense.add(new TexturePart(texturePath, 100, 100, 1f, 1f));
        

        return defense;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(entityID);
    }
}
