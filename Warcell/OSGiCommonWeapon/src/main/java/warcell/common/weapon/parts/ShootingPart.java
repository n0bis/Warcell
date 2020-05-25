package warcell.common.weapon.parts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.entityparts.EntityPart;

public class ShootingPart implements EntityPart {

    private float timeSinceLastShot;
    private boolean canShoot;
    private boolean isShooting;
    private String ID;

    public ShootingPart(String id) {
        this.ID = id;
        this.timeSinceLastShot = 0;
        this.canShoot = true;
    }

    public boolean isShooting() {
        return this.isShooting;
    }

    public void setIsShooting(boolean b) {
        this.isShooting = b;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    public void setTimeSinceLastShot(float timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }

    public boolean CanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

}
