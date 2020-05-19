/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.weapon;

import java.util.ArrayList;
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
 * @author Patrick
 */
public class KnifeWeapon implements WeaponsSPI {

    private final String name = "Knife";
    private final String description = "Hunters Knife";
    private final String iconPath = "knife.png";
    private Entity bullet;
    private final int bulletVelocity = 300;
    private int ammo = 0;
    private final int ammoCapacity = Integer.MAX_VALUE;
    private float reloadTime = 0;
    private float fireRate = 0.7f;
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
        if (entity.getPart(ShootingPart.class) != null) {

            ShootingPart shootingPart = entity.getPart(ShootingPart.class);
            //Shoot if isShooting is true, ie. space is pressed.
            if (shootingPart.isShooting()) {
                PositionPart positionPart = entity.getPart(PositionPart.class);
                AnimationTexturePart animationTexturePart = entity.getPart(AnimationTexturePart.class);

                for (int i = 0; i <= 8; i++) {
                    //Add entity radius to initial position to avoid immideate collision.
                    float angle = (float) Math.toRadians(positionPart.getRadians());
                    float bulletX = (float) (positionPart.getX() + animationTexturePart.getWidth() / 2 + (5 * Math.cos(angle) - 15 * Math.sin(angle)));
                    float bulletY = (float) (positionPart.getY() + animationTexturePart.getHeight() / 2 + (5 * Math.sin(angle) + 15 * Math.cos(angle)));

                    bullet = createBullet(bulletX + i, bulletY + i,
                            positionPart.getRadians() + (i * 2), shootingPart.getID());
                    bulletArray.add(bullet);
                }

                for (Entity e : bulletArray) {
                    world.addEntity(e);
                }
                bulletArray.removeAll(bulletArray);
                shootingPart.setIsShooting(false);
            }
        }
    }

    //Could potentially do some shenanigans with differing colours for differing sources.
    @Override
    public Entity createBullet(float x, float y, float radians, String uuid) {
        Entity b = new Bullet();

        b.add(new PositionPart(x, y, (float) Math.toRadians(radians + 90)));
        b.add(new BulletMovingPart(0, 50000, bulletVelocity, 0));
        b.add(new TimerPart(0.05f));
        b.add(new LifePart(1000));
        b.add(new DamagePart(50));
        b.add(new SquarePart(x, y, 5f));
        b.add(new CollisionPart(true, 1));
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
