/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import warcell.common.data.entityparts.CirclePart;
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
    private final String iconPath = "shotgun.png";
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("Audio/shotgunShot.mp3"));
    private Entity bullet;
    private final int bulletVelocity = 600;
    private ArrayList<Entity> bulletArray = new ArrayList();
    private int ammo = 8;
    private final int ammoCapacity = 8;
    private float reloadTime = 0;
    private float fireRate = 0.8f;

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
                    float bulletX = (float) (positionPart.getX() + animationTexturePart.getWidth() / 2
                            + (10 * Math.cos(angle) - 25 * Math.sin(angle)));
                    float bulletY = (float) (positionPart.getY() + animationTexturePart.getHeight() / 2
                            + (10 * Math.sin(angle) + 25 * Math.cos(angle)));

                    bullet = createBullet(bulletX, bulletY,
                            positionPart.getRadians() + (rand.nextFloat() * (10 - 20)), shootingPart.getID());
                    bulletArray.add(bullet);
                }

                for (Entity e : bulletArray) {
                    world.addEntity(e);
                }
                sound.play(0.05f);
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
        b.add(new TimerPart(3));
        b.add(new LifePart(1));
        b.add(new DamagePart(20));
        b.add(new CirclePart(x, y, 5f));
        b.add(new CollisionPart(true, 1));
        // Projectile Part only used for better collision detection     
        b.add(new ProjectilePart(uuid.toString()));
        b.setRadius(5);

        return b;
    }

    @Override
    public int getAmmoCapacity() {
        return this.ammoCapacity;
    }

    @Override
    public float getReloadTime() {
        if (ammo == 8) {
            reloadTime = 0;
        } else if (ammo == 7) {
            reloadTime = 1.33f;
        } else {
            reloadTime = (float) (ammoCapacity - ammo) * 1.33f;
        }
        return this.reloadTime;
    }

    @Override
    public int getAmmo() {
        return ammo;
    }

    @Override
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    @Override
    public float getFireRate() {
        return this.fireRate;
    }

}
