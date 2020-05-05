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
import warcell.common.weapon.parts.InventoryPart;
import warcell.common.weapon.parts.ShootingPart;
import java.util.UUID;
import warcell.common.data.entityparts.DamagePart;

public class PlayerPlugin implements IGamePluginService {
    private String entityID;
    private float playerRadius = 75f;
    private final String walkAnimationPath = "RifleIdle.png";
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
        int maxLife = 1000;
        float centreX = 0;
        float centreY = 0;
        player.add(new SquarePart(centreX, centreY, playerRadius));
        player.add(new LifePart(maxLife));
        player.add(new DamagePart(0));
        player.add(new CollisionPart(true, 3));
        player.add(new MovingPart(acceleration, maxSpeed, false));
        player.add(new PositionPart(x, y, radians));
        player.add(new AnimationTexturePart(new Vector2D(x, y), walkAnimationPath, walkAnimationFrameColumns, walkAnimationFrameRows, 0.09f, 172f, 235f, 0.5f, 0.5f));
        UUID uuid = UUID.randomUUID();
        player.add(new ShootingPart(uuid.toString()));
        player.add(new InventoryPart());

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(entityID);
    }
}
