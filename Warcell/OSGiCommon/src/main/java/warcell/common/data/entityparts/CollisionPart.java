package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;

/**
 *
 * @author Jonas
 */
public class CollisionPart implements EntityPart {
    
    private boolean canCollide;
    private boolean isHitEntity;
    private boolean isHitGameMap;
    private float timeSinceLastCollision;
    private float minTimeBetweenCollision;

    public CollisionPart() {
    }

    public CollisionPart(boolean canCollide, float minTimeBetweenCollision) {
        this.canCollide = canCollide;
        this.minTimeBetweenCollision = minTimeBetweenCollision;
        this.isHitEntity = false;
        this.isHitGameMap = false;
        this.timeSinceLastCollision = 0;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    
    
    
    
    public boolean isCanCollide() {
        return canCollide;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    public boolean isHitEntity() {
        return isHitEntity;
    }

    public void setIsHitEntity(boolean isHitEntity) {
        this.isHitEntity = isHitEntity;
    }

    public boolean isHitGameMap() {
        return isHitGameMap;
    }

    public void setIsHitGameMap(boolean isHitGameMap) {
        this.isHitGameMap = isHitGameMap;
    }

    public float getTimeSinceLastCollision() {
        return timeSinceLastCollision;
    }

    public void setTimeSinceLastCollision(float timeSinceLastCollision) {
        this.timeSinceLastCollision = timeSinceLastCollision;
    }

    public float getMinTimeBetweenCollision() {
        return minTimeBetweenCollision;
    }

    public void setMinTimeBetweenCollision(float minTimeBetweenCollision) {
        this.minTimeBetweenCollision = minTimeBetweenCollision;
    }
    
    
    
    
    
}
