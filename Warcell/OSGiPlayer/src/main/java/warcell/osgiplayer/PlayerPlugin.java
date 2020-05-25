package warcell.osgiplayer;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.CirclePart;
import warcell.common.player.Player;
import warcell.common.services.IGamePluginService;
import warcell.common.utils.Vector2D;
import warcell.common.weapon.parts.InventoryPart;
import warcell.common.weapon.parts.ShootingPart;
import java.util.UUID;
import warcell.common.data.entityparts.DamagePart;
import warcell.common.data.entityparts.ScorePart;
import warcell.common.data.entityparts.TimerPart;

public class PlayerPlugin implements IGamePluginService {

    private String entityID;
    private float playerRadius = 20f;
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
     *
     * @param gameData the GameData of the Game class
     * @return the created player
     */
    private Entity createPlayer(GameData gameData) {
        Entity player = new Player();
        gameData.setGameOver(false);

        float acceleration = 2450;
        float maxSpeed = 350;
        float x = 2000;
        float y = 2000;
        float radians = 3.1415f / 2;
        int maxLife = 1000;
        float centreX = 0;
        float centreY = 0;
        player.add(new CirclePart(centreX, centreY, playerRadius));
        player.add(new LifePart(maxLife));
        player.add(new DamagePart(0));
        player.add(new CollisionPart(true, 3));
        player.add(new MovingPart(acceleration, maxSpeed, true));
        player.add(new PositionPart(x, y, radians));
        player.add(new AnimationTexturePart(new Vector2D(x, y), walkAnimationPath, walkAnimationFrameColumns, walkAnimationFrameRows, 0.09f, 43f, 59f, 1f, 1f));
        UUID uuid = UUID.randomUUID();
        player.add(new ShootingPart(uuid.toString()));
        player.add(new InventoryPart());
        player.add(new TimerPart(0));
        player.add(new ScorePart("John Doe"));

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(entityID);
    }
}
