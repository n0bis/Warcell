package warcell.osgienemy;

import java.util.Random;
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
import warcell.common.services.IGamePluginService;
import warcell.common.utils.Vector2D;

public class EnemyPlugin implements IGamePluginService {

    private float enemyRadius = 20f;
    private final String walkAnimationPath = "ZombieWalk.png";
    private final int walkAnimationFrameColumns = 17;
    private final int walkAnimationFrameRows = 1;
    private int amountOfEnemies;
    private Random random = new Random();

    public EnemyPlugin() {
        amountOfEnemies = 1;
    }

    public EnemyPlugin(int amountOfEnemies) {
        this.amountOfEnemies = amountOfEnemies;
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        for (int i = 0; i < amountOfEnemies; i++) {
            Entity enemy = createEnemyZombie(gameData);
            world.addEntity(enemy);
        }
    }

    private Entity createEnemyZombie(GameData gameData) {
        Entity enemyZombie = new Enemy();

        float acceleration = 100;
        float maxSpeed = 150;
        float x = 310;
        float y = 470;
        float radians = 3.1415f / 2;
        int maxLife = 50;
        float centreX = 0;
        float centreY = 0;
        enemyZombie.add(new CirclePart(centreX, centreY, enemyRadius));
        enemyZombie.add(new LifePart(maxLife));
        enemyZombie.add(new DamagePart(2, 3));
        enemyZombie.add(new MovingPart(acceleration + acceleration * random.nextFloat(), maxSpeed + maxSpeed * random.nextFloat(), true));
        enemyZombie.add(new PositionPart(x, y, radians));
        enemyZombie.add(new CollisionPart(true, 3));
        enemyZombie.add(new AnimationTexturePart(new Vector2D(x, y), walkAnimationPath, walkAnimationFrameColumns, walkAnimationFrameRows, 0.09f, 56f, 63f, 1f, 1f));

        return enemyZombie;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity enemyZombie : world.getEntities(Enemy.class)) {
            world.removeEntity(enemyZombie);
        }
    }

}
