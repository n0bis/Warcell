/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.bullet;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Random;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TimerPart;
import warcell.common.services.IEntityProcessingService;
import warcell.common.weapon.entities.Bullet;

/**
 *
 * @author birke
 */
public class BulletProcessor implements IEntityProcessingService {
    Random rand = new Random();
    private Entity bullet;
    
    @Override
    public void process(GameData gameData, World world) {
    
        for (Entity b : world.getEntities(Bullet.class)) {
            PositionPart ppb = b.getPart(PositionPart.class);
            MovingPart mpb = b.getPart(MovingPart.class);
            TimerPart btp = b.getPart(TimerPart.class);
            mpb.setBulletUp(true);
            btp.reduceExpiration(gameData.getDelta());
            LifePart lpb = b.getPart(LifePart.class);
            //If duration is exceeded, remove the bullet.
            if (btp.getExpiration() < 0) {
                world.removeEntity(b);
            }

            ppb.process(gameData, b);
            mpb.process(gameData, b);
            btp.process(gameData, b);
            lpb.process(gameData, b);

            updateShape(b);
        }
    }

     private void updateShape(Entity entity) {
        float[] shapex = new float[2];
        float[] shapey = new float[2];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());



        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
