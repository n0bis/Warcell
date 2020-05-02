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
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
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

    private final String name = "M870 Remington";
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
                    bullet = createBullet(positionPart.getX() + animationTexturePart.getWidth()/2, positionPart.getY() + animationTexturePart.getHeight()/2, 
                            positionPart.getRadians() + (rand.nextFloat() * (10-20)), shootingPart.getID());
                    bulletArray.add(bullet);
                }   
            
                for (Entity e : bulletArray) {
                    world.addEntity(e);
                }
                bulletArray.removeAll(bulletArray);
                shootingPart.setIsShooting(false);
                fireDelay += rateOfFire;
                System.out.println("shotgun pew");

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
        // Projectile Part only used for better collision detection     
        b.add(new ProjectilePart(uuid.toString()));
        b.setRadius(5);

        return b;
    }
    
    /*
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
    bullet.add(new MovingPart(0, 5000, speed, 5));
    bullet.add(new TimerPart(1));
    
    bullet.setShapeX(new float[2]);
    bullet.setShapeY(new float[2]);
    
    bulletArray.add(bullet);
    }
    
    for (Entity e : bulletArray) {
    world.addEntity(e);
    }*/
 }

