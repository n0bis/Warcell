/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import static java.lang.Math.sqrt;
import warcell.common.data.GameData;

public class MovingPart implements EntityPart {

    private float dx, dy;
    private float deceleration, acceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up, down;
    private boolean wrap;
    private boolean isMoving;
    

    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed, boolean wrap) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
        this.wrap = wrap;
        this.isMoving = false;
    }
    
    public MovingPart(float acceleration, float maxSpeed, boolean wrap){
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.wrap = wrap;
        this.isMoving = false;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }
    
    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    
    public void setSpeed(float speed) {
        this.acceleration = speed;
        this.maxSpeed = speed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }    

    
    public void setDown(boolean down){
        this.down = down;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();
        
        
        float vec = (float) sqrt(dx * dx + dy * dy);
        
        // moving
        if (left) {
            
            if (!(up||down)) {
                dy = 0;
            }
            
            if (vec < maxSpeed) { 
                dx -= acceleration * dt;
            } else {
                dx = -maxSpeed;
            }
        }

        if (right) {
            
            if (!(up||down)) {
                dy = 0;
            }
            
            if (vec < maxSpeed) { 
                dx += acceleration * dt;
            } else {
                dx = maxSpeed;
            }
        }
           
        if (up) {
            
            if (!(left||right)) {
                dx = 0;
            }
            
            if (vec < maxSpeed) { 
                dy += acceleration * dt;
            } else {
                dy = maxSpeed;
            }
        }
        
        if (down) {
            
            if (!(left||right)) {
                dx = 0;
            }
            
            if (vec < maxSpeed) { 
                dy -= acceleration * dt;
            } else {
                dy = -maxSpeed;
            }
        }
        if (down || up || left || right) {
            isMoving = true;
        }
        


        // deccelerating
        if (!(down||up||left||right)) {
            dx = 0;
            dy = 0;
            isMoving = false;
        }
        // set position
        
        if (wrap) {
            x += dx * dt;
            // wrapping
            if (x > gameData.getDisplayWidth() + 150 && x < gameData.getDisplayWidth() + 300) {
                x = gameData.getDisplayWidth() + 100;
            }
            else if (x < -150 && x > -300) {
                x = -100;
            }

            y += dy * dt;

            if (y > gameData.getDisplayHeight() + 150 && y < gameData.getDisplayHeight() + 300) {
                y = gameData.getDisplayHeight() + 100;
            }
            else if (y < -150 && y > - 300) {
                y = -100;
            }
        } else {
            x += dx * dt;
            // wrapping
            if (x > gameData.getDisplayWidth()) {
                x = gameData.getDisplayWidth();
            }
            else if (x < -100) {
                x = -100;
            }

            y += dy * dt;

            // wrapping
            if (y > gameData.getDisplayHeight()) {
                y = gameData.getDisplayHeight();
            }
            else if (y < -100) {
                y = -100;
            }
        }

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

    /**
     * @return the isMoving
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * @param isMoving the isMoving to set
     */
    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

}
