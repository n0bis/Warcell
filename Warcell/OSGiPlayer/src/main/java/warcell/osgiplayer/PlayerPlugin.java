package warcell.osgiplayer;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.SquarePart;
import warcell.common.player.Player;
import warcell.common.services.IGamePluginService;
import warcell.common.utils.Vector2D;

public class PlayerPlugin implements IGamePluginService {
    private String entityID;
    private float playerRadius = 20f;
    private final String walkAnimationPath = "KnifeIdle.png";
    private final int walkAnimationFrameColumns = 20;
    private final int walkAnimationFrameRows = 1;
    
    public PlayerPlugin() {
    }


    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        if (world == null || gameData == null) {
            throw new IllegalArgumentException("World or gamedata is null");
        }
        Entity player = createPlayer(gameData);
        entityID = world.addEntity(player);
    }
    
    /**
     * creates a player entity
     * @param gameData the GameData of the Game class
     * @return the created player
     */
    private Entity createPlayer(GameData gameData) {
        Entity player = new Player();

        float acceleration = 2450;
        float maxSpeed = 350;
        float x = gameData.getDisplayWidth() / 3;
        float y = gameData.getDisplayHeight() / 3;
        float radians = 3.1415f / 2;
        int maxLife = 100;
        float centreX = (float) (Math.random() * (gameData.getDisplayWidth()*0.8+gameData.getDisplayWidth()*0.1));
        float centreY = gameData.getDisplayHeight();
        player.add(new SquarePart(centreX, centreY, playerRadius));
        player.add(new LifePart(maxLife));
        player.add(new CollisionPart(true, 0));
        player.add(new MovingPart(acceleration, maxSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new AnimationTexturePart(new Vector2D(x, y), walkAnimationPath, walkAnimationFrameColumns, walkAnimationFrameRows, 0.09f, 224f, 289f, 1f, 1f));
        

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(entityID);
    }
}
