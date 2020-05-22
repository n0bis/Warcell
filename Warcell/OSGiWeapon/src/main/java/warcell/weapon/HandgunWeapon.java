/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.weapon;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.BulletMovingPart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.DamagePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.CirclePart;
import warcell.common.data.entityparts.TimerPart;
import warcell.common.weapon.entities.Bullet;
import warcell.common.weapon.parts.ProjectilePart;
import warcell.common.weapon.parts.ShootingPart;
import warcell.common.weapon.service.WeaponsSPI;

/**
 *
 * @author Patrick
 */
public class HandgunWeapon implements WeaponsSPI {
    private final String name = "Handgun";
    private final String description = "Revolver";
    private final String iconPath = "handgun.png";
    private Entity bullet;
    private final int bulletVelocity = 1200;
    private int ammo = 10;
    private int ammoCapacity = 10;
    private float reloadTime = 4;
    private float fireRate = 1f;
    
    

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getIconPath() {
        return this.iconPath;
    }

    @Override
    public void shoot(Entity entity, GameData gameData, World world) {
        if (entity.getPart(ShootingPart.class) != null) {

            ShootingPart shootingPart = entity.getPart(ShootingPart.class);
            //Shoot if isShooting is true, ie. space is pressed.
            if (shootingPart.isShooting()) {
                PositionPart positionPart = entity.getPart(PositionPart.class);
                AnimationTexturePart animationTexturePart = entity.getPart(AnimationTexturePart.class);
                   
                float angle = (float) Math.toRadians(positionPart.getRadians());
                float bulletX = (float) (positionPart.getX() + animationTexturePart.getWidth()/2 + 
                        (10 * Math.cos(angle) - 25 * Math.sin(angle)));
                float bulletY = (float) (positionPart.getY() + animationTexturePart.getHeight()/2 + 
                        (10 * Math.sin(angle) + 25 * Math.cos(angle)));
                
                //Add entity radius to initial position to avoid immideate collision.
                bullet = createBullet(bulletX, bulletY, 
                        positionPart.getRadians(), shootingPart.getID());
                shootingPart.setIsShooting(false);
                world.addEntity(bullet);
            }
        }
    }

    @Override
    public Entity createBullet(float x, float y, float radians, String uuid) {
        Entity b = new Bullet();

        b.add(new PositionPart(x, y, (float) Math.toRadians(radians+90)));
        b.add(new BulletMovingPart(0, 50000, bulletVelocity, 0));
        b.add(new TimerPart(5));
        b.add(new LifePart(1));
        b.add(new DamagePart(100));
        b.add(new CirclePart(x, y, 5f));
        b.add(new CollisionPart(true, 0.5f));
        // Projectile Part only used for better collision detection     
        b.add(new ProjectilePart(uuid.toString()));
        b.setRadius(5);

        return b;
    }

    @Override
    public int getAmmo() {
        return this.ammo;
    }

    @Override
    public void setAmmo(int i) {
        this.ammo = i;
    }

    @Override
    public int getAmmoCapacity() {
        return this.ammoCapacity;
    }

    @Override
    public float getReloadTime() {
        return this.reloadTime;
    }

    @Override
    public float getFireRate() {
        return this.fireRate;
    }
    
}
