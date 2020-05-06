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
import warcell.common.data.entityparts.BulletMovingPart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.SquarePart;
import warcell.common.data.entityparts.TimerPart;
import warcell.common.services.IEntityProcessingService;
import warcell.common.weapon.entities.Bullet;

/**
 *
 * @author birke
 */
public class BulletProcessor implements IEntityProcessingService {
    Random rand = new Random();
    
    @Override
    public void process(GameData gameData, World world) {
    
        for (Entity b : world.getEntities(Bullet.class)) {
            PositionPart ppb = b.getPart(PositionPart.class);
            BulletMovingPart mpb = b.getPart(BulletMovingPart.class);
            TimerPart btp = b.getPart(TimerPart.class);
            LifePart lpb = b.getPart(LifePart.class);
            SquarePart sp = b.getPart(SquarePart.class);
            
            mpb.setUp(true);
            
            sp.setCentreX(ppb.getX());
            sp.setCentreY(ppb.getY());
            
            btp.reduceExpiration(gameData.getDelta());
         
            //If duration is exceeded, remove the bullet.
            if (btp.getExpiration() < 0 || mpb.getIsOut()) {
                world.removeEntity(b);
            }
            
            

            ppb.process(gameData, b);
            mpb.process(gameData, b);
            btp.process(gameData, b);
            lpb.process(gameData, b);
            
            if (lpb.isDead()) {
                world.removeEntity(b);
            }

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

        shapex[0] = (float) (x + Math.cos(radians) * 20);
        shapey[0] = (float) (y + Math.sin(radians) * 20);


        shapex[1] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[1] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);



        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
