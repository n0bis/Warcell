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
    private float lastX, lastY;
    private float deceleration, acceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up, down;
    private boolean wrap;
    private boolean isMoving;
    private boolean isInWalls;

    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed, boolean wrap) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
        this.wrap = wrap;
        this.isMoving = false;
    }

    public MovingPart(float acceleration, float maxSpeed, boolean wrap) {
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

    public void setDown(boolean down) {
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

            if (!(up || down)) {
                dy = 0;
            }

            if (vec < maxSpeed) {
                dx -= acceleration * dt;
            } else {
                dx = -maxSpeed;
            }
        }

        if (right) {

            if (!(up || down)) {
                dy = 0;
            }

            if (vec < maxSpeed) {
                dx += acceleration * dt;
            } else {
                dx = maxSpeed;
            }
        }

        if (up) {

            if (!(left || right)) {
                dx = 0;
            }

            if (vec < maxSpeed) {
                dy += acceleration * dt;
            } else {
                dy = maxSpeed;
            }
        }

        if (down) {

            if (!(left || right)) {
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
        if (!(down || up || left || right)) {
            dx = 0;
            dy = 0;
            isMoving = false;
        }
        // set position

        x += dx * dt;

        // wrapping
        if (x > 3200) {
            x = 3200;
        } else if (x < 0) {
            x = 0;
        }

        y += dy * dt;

        // wrapping
        if (y > 3200) {
            y = 3200;
        } else if (y < 0) {
            y = 0;
        }

        if (isIsInWalls()) {
            x = getLastX() + (x - getLastX()) / 2;
            y = getLastY() + (y - getLastY()) / 2;
            setIsInWalls(false);
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

    /**
     * @return the isInWalls
     */
    public boolean isIsInWalls() {
        return isInWalls;
    }

    /**
     * @param isInWalls the isInWalls to set
     */
    public void setIsInWalls(boolean isInWalls) {
        this.isInWalls = isInWalls;
    }

    /**
     * @return the lastX
     */
    public float getLastX() {
        return lastX;
    }

    /**
     * @param lastX the lastX to set
     */
    public void setLastX(float lastX) {
        this.lastX = lastX;
    }

    /**
     * @return the lastY
     */
    public float getLastY() {
        return lastY;
    }

    /**
     * @param lastY the lastY to set
     */
    public void setLastY(float lastY) {
        this.lastY = lastY;
    }
}
