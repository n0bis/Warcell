package warcell.smallenemy;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TexturePart;
import warcell.common.enemy.Enemy;
import warcell.common.services.IGamePluginService;
import warcell.common.utils.Vector2D;

public class EnemyPlugin implements IGamePluginService {

    private String enemyID;
    private final String walkAnimationPath = "WalkingAnimation.png";
    private final int walkAnimationFrameColumns = 3;
    private final int walkAnimationFrameRows = 5;
    private final int AMOUNTOFENEMIES;

    public EnemyPlugin() {
        AMOUNTOFENEMIES = 5;
    }

    public EnemyPlugin(int amountOfEnemies) {
        AMOUNTOFENEMIES = amountOfEnemies;
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        if (world == null || gameData == null) {
            throw new IllegalArgumentException("World or gamedata is null");
        }
        for (int i = 0; i < AMOUNTOFENEMIES; i++) {

            Entity enemy = createSmallZombie(gameData);
            enemyID = world.addEntity(enemy);
        }
    }

    private Entity createSmallZombie(GameData gameData) {
        Entity enemyZombie = new Enemy();

        
        float acceleration = 3000;
        float maxSpeed = 1000;
        float rotationSpeed = 3500;
        float x = gameData.getDisplayWidth() / 3;
        float y = gameData.getDisplayHeight() / 3;
        float radians = 3.1415f / 2;
        int maxLife = 25;
        enemyZombie.add(new LifePart(maxLife));
        enemyZombie.add(new MovingPart(acceleration, maxSpeed, rotationSpeed));
        enemyZombie.add(new PositionPart(x, y, radians));
        enemyZombie.add(new AnimationTexturePart(new Vector2D(x, y), walkAnimationPath, walkAnimationFrameColumns, walkAnimationFrameRows, 0.25f));

        return enemyZombie;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemyID);
    }

}
