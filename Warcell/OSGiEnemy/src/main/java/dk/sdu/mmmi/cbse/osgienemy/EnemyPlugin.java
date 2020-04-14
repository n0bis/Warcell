package dk.sdu.mmmi.cbse.osgienemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TexturePart;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private String enemyID;
    private final String walkAnimationPath = "WalkAnimation.png";
    private final int walkAnimationFrameColumns = 6;
    private final int walkAnimationFrameRows = 3;
    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        Entity enemy = createEnemyZombie(gameData);
        enemyID = world.addEntity(enemy);
        
    }

    private Entity createEnemyZombie(GameData gameData) {
        Entity enemyZombie = new Enemy();

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 3;
        float y = gameData.getDisplayHeight() / 3;
        float radians = 3.1415f / 2;
        enemyZombie.add(new LifePart(3));
       // enemyZombie.setRadius(4);
        enemyZombie.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyZombie.add(new PositionPart(x, y, radians));
        enemyZombie.add(new TexturePart(walkAnimationFrameColumns, walkAnimationFrameRows, walkAnimationPath));
        
        return enemyZombie;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemyID);
    }

}
