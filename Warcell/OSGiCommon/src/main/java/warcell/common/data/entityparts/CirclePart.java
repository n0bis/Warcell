/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;

/**
 *
 * @author Jonas
 */
public class CirclePart implements EntityPart {

    private float centreX;
    private float centreY;
    private float radius;

    @Override
    public void process(GameData gameData, Entity entity) {

    }

    public CirclePart() {
    }

    public CirclePart(float centreX, float centreY, float radius) {
        this.centreX = centreX;
        this.centreY = centreY;
        this.radius = radius;
    }

    public float getCentreX() {
        return centreX;
    }

    public void setCentreX(float centreX) {
        this.centreX = centreX;
    }

    public float getCentreY() {
        return centreY;
    }

    public void setCentreY(float centreY) {
        this.centreY = centreY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}
