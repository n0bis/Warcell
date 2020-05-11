/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;

public class LifePart implements EntityPart {
    private boolean dead = false;
    private int life;
    private boolean isHit = false;


    public LifePart(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
    
    public boolean isDead() {
        return dead;
    }
    
    public void takeDamage(int damage) {
        this.life -= damage;
    }

    
    
    @Override
    public void process(GameData gameData, Entity entity) {
        if (isHit) {
            life =- 1;
            isHit = false;
        }
        if (life <= 0) {
            dead = true;
        }
        
    }
}
