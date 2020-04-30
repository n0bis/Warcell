package warcell.osgienemy;

import java.util.List;
import warcell.common.ai.AISPI;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TiledMapPart;
import warcell.common.enemy.Enemy;
import warcell.common.player.Player;
import warcell.common.services.IEntityProcessingService;


public class EnemyProcessor implements IEntityProcessingService {

    private AISPI ai;
    private TiledMapPart tiledMap;
    private PositionPart playerPos;
    
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            tiledMap = entity.getPart(TiledMapPart.class);
            if (tiledMap != null)
                ai.startAI(tiledMap);
        }
        for (Entity entity : world.getEntities(Player.class)) {
            playerPos = entity.getPart(PositionPart.class);
        }
        for (Entity entity : world.getEntities(Enemy.class)) {
            
            PositionPart positionPart = entity.getPart(PositionPart.class);
            List<PositionPart> path = ai.getPath(positionPart, playerPos);
            MovingPart movingPart = entity.getPart(MovingPart.class);
            if (!path.isEmpty()) {
                double zombieAngle = Math.abs(Math.toDegrees(positionPart.getRadians() * 1));
                double angle = getAngle(positionPart, path.get(0));
                positionPart.setRadians((float)angle);

                if (zombieAngle > angle - 4 && zombieAngle < angle + 4) {
                    movingPart.setUp(true);
                }
                if (zombieAngle < angle - 4 || Math.abs(zombieAngle - angle) > 330) {
                    movingPart.setLeft(true);
                }
                if (zombieAngle > angle + 4 && Math.abs(zombieAngle - angle) < 330) {
                    movingPart.setRight(true);
                }
            }

            movingPart.process(gameData, entity);
            positionPart.process(gameData, entity);
        }
    }
    
    private double getAngle(PositionPart source, PositionPart target) {
        double angle = (double) Math.toDegrees(Math.atan2(target.getY() - source.getY(), target.getX() - source.getX()));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
    
    /**
     * Declarative service set AI service
     *
     * @param ai AI service
     */
    public void setAIService(AISPI ai) {
        this.ai = ai;

    }

    /**
     * Declarative service remove AI service
     *
     * @param ai AI service
     */
    public void removeAIService(AISPI ai) {
        this.ai = null;
    }
}
