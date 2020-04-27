/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.weapon.rifle;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Random;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TimerPart;
import warcell.common.weapon.entities.Bullet;
import warcell.common.weapon.service.WeaponsSPI;

/**
 *
 * @author birke
 */
public class ShotgunWeapon implements WeaponsSPI {
    Random rand = new Random();

    private final String name = "M870 Remington";
    private final String description = "Pump Shotgun";
    private final String iconPath = "";
    
    
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public void shoot(Entity shooter, GameData gd, World world) {
        System.out.println("shotgun pew, PAF");
        PositionPart shooterPos = shooter.getPart(PositionPart.class);

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        float speed = 350;
        ArrayList<Entity> bulletArray = new ArrayList();
        
        for (int i = 0; i <= 8; i++) {
            Entity bullet = new Bullet();
            bullet.setRadius(2);

            float bx = (float) cos(radians) * shooter.getRadius() * bullet.getRadius();
            float by = (float) sin(radians) * shooter.getRadius() * bullet.getRadius();

            bullet.add(new PositionPart(bx + x, by + y, radians + (rand.nextFloat() * (1-2)))); //Accuracy attempt
            bullet.add(new MovingPart(0, 5000000, speed, 5));
            bullet.add(new TimerPart(1));

            bullet.setShapeX(new float[2]);
            bullet.setShapeY(new float[2]); 
            
            bulletArray.add(bullet);
        }
        
        for (Entity e : bulletArray) {
            world.addEntity(e);
        }
    }
}
