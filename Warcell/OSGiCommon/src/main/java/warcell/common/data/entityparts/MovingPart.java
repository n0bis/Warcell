/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class MovingPart implements EntityPart {

    private float dx, dy;
    private float deceleration, acceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up, down;
    

    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }
    
    public MovingPart(float acceleration, float maxSpeed){
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
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
        


        // deccelerating
        if (!(down||up||left||right)) {
            dx = 0;
            dy = 0;
        }
        // set position
        x += dx * dt;
        
        // wrapping
        if (x > gameData.getDisplayWidth()) {
            x = 0;
        }
        else if (x < 0) {
            x = gameData.getDisplayWidth();
        }

        y += dy * dt;

        // wrapping
        if (y > gameData.getDisplayHeight()) {
            y = 0;
        }
        else if (y < 0) {
            y = gameData.getDisplayHeight();
        }

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

}
