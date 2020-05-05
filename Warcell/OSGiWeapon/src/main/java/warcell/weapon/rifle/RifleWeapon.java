/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.weapon.rifle;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.BulletMovingPart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.DamagePart;
import warcell.common.data.entityparts.LifePart;
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
public class RifleWeapon implements WeaponsSPI {
    private final String name = "Rifle";
    private final String description = "Automatic Carbine";
    private final String iconPath = "";
    private Entity bullet;
    private float rateOfFire = (float) 0.2;
    private float fireDelay;
    private final int bulletVelocity = 1200;

    
    @Override
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
               
                
                //Add entity radius to initial position to avoid immideate collision.
                bullet = createBullet(positionPart.getX() + animationTexturePart.getWidth()/2, positionPart.getY() + animationTexturePart.getHeight()/2, 
                        positionPart.getRadians(), shootingPart.getID());
                shootingPart.setIsShooting(false);
                world.addEntity(bullet);
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
        b.add(new CollisionPart(true, 0.5f));
        // Projectile Part only used for better collision detection     
        b.add(new ProjectilePart(uuid.toString()));
        b.setRadius(5);

        return b;
    }
    
        /*    @Override
    public void shoot(Entity shooter, GameData gd, World world) {
    System.out.println("rifle pew");
    PositionPart shooterPos = shooter.getPart(PositionPart.class);
    
    float x = shooterPos.getX();
    float y = shooterPos.getY();
    float radians = shooterPos.getRadians();
    float speed = 350;
    
    Entity bullet = new Bullet();
    bullet.setRadius(2);
    
    float bx = (float) cos(radians) * shooter.getRadius() * bullet.getRadius();
    float by = (float) sin(radians) * shooter.getRadius() * bullet.getRadius();
    
    bullet.add(new PositionPart(bx + x, by + y, radians));
    bullet.add(new MovingPart(0, 5000, speed, 5));
    bullet.add(new TimerPart(1));
    
    bullet.setShapeX(new float[2]);
    bullet.setShapeY(new float[2]);
    
    world.addEntity(bullet);
    }*/

}
