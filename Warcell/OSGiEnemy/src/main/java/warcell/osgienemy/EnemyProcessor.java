package warcell.osgienemy;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.SquarePart;
import warcell.common.enemy.Enemy;
import warcell.common.services.IEntityProcessingService;


public class EnemyProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Enemy.class)) {

            PositionPart positionPart = entity.getPart(PositionPart.class);
            MovingPart movingPart = entity.getPart(MovingPart.class);
            CollisionPart collisionPart = entity.getPart(CollisionPart.class);
            SquarePart circlePart = entity.getPart(SquarePart.class);
            AnimationTexturePart animationTexturePart = entity.getPart(AnimationTexturePart.class);
            
            circlePart.setCentreX(positionPart.getX() + animationTexturePart.getWidth()/2);
            circlePart.setCentreY(positionPart.getY() + animationTexturePart.getHeight()/2);
            
            double random = Math.random();
            movingPart.setLeft(random < 0.2);
            movingPart.setRight(random > 0.3 && random < 0.5);
            movingPart.setUp(random > 0.7 && random < 0.9);

           

            movingPart.process(gameData, entity);
            positionPart.process(gameData, entity);
            collisionPart.process(gameData, entity);
            circlePart.process(gameData, entity);
            

        }
    }

    
}
