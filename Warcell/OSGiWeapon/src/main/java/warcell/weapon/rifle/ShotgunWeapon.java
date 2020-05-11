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
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.BulletMovingPart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.DamagePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.SquarePart;
import warcell.common.data.entityparts.TimerPart;
import warcell.common.weapon.entities.Bullet;
import warcell.common.weapon.parts.ProjectilePart;
import warcell.common.weapon.parts.ShootingPart;
import warcell.common.weapon.service.WeaponsSPI;

/**
 *
 * @author birke
 */
public class ShotgunWeapon implements WeaponsSPI {
    Random rand = new Random();

    private final String name = "Shotgun";
    private final String description = "Pump Shotgun";
    private final String iconPath = "";
    private Entity bullet;
    private float rateOfFire = (float) 1;
    private float fireDelay;
    private final int bulletVelocity = 600;
    private ArrayList<Entity> bulletArray = new ArrayList();

    
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
    public void shoot(Entity entity, GameData gameData, World world) {
        
        fireDelay -= gameData.getDelta();
        if (entity.getPart(ShootingPart.class) != null) {

            ShootingPart shootingPart = entity.getPart(ShootingPart.class);
            //Shoot if isShooting is true, ie. space is pressed.
            if (shootingPart.isShooting() && fireDelay <= 0) {
                PositionPart positionPart = entity.getPart(PositionPart.class);
                AnimationTexturePart animationTexturePart = entity.getPart(AnimationTexturePart.class);
               
                for (int i = 0; i <= 8; i++) {
                //Add entity radius to initial position to avoid immideate collision.
                    float angle = (float) Math.toRadians(positionPart.getRadians());
                float bulletX = (float) (positionPart.getX() + animationTexturePart.getWidth()/2 + 
                        (15 * Math.cos(angle) - 65 * Math.sin(angle)));
                float bulletY = (float) (positionPart.getY() + animationTexturePart.getHeight()/2 + 
                        (15 * Math.sin(angle) + 65 * Math.cos(angle)));
                
                
                    bullet = createBullet(bulletX, bulletY, 
                            positionPart.getRadians() + (rand.nextFloat() * (10-20)), shootingPart.getID());
                    bulletArray.add(bullet);
                }   
            
                for (Entity e : bulletArray) {
                    world.addEntity(e);
                }
                bulletArray.removeAll(bulletArray);
                shootingPart.setIsShooting(false);
                fireDelay += rateOfFire;
            }
        }
    }
    
    //Could potentially do some shenanigans with differing colours for differing sources.
    @Override
    public Entity createBullet(float x, float y, float radians, String uuid) {
        Entity b = new Bullet();

        b.add(new PositionPart(x, y, (float) Math.toRadians(radians+90)));
        b.add(new BulletMovingPart(0, 50000, bulletVelocity, 0));
        b.add(new TimerPart(3));
        b.add(new LifePart(1));
        b.add(new DamagePart(20));
        b.add(new SquarePart(x, y, 5f));
        b.add(new CollisionPart(true, 1));
        // Projectile Part only used for better collision detection     
        b.add(new ProjectilePart(uuid.toString()));
        b.setRadius(5);

        return b;
    }
    
 }

