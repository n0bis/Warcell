/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.utils.Vector2D;

/**
 *
 * @author madsfalken
 */
public class AnimationTexturePart implements EntityPart {
    
    private float stateTime;
    private Vector2D position;
    private String srcPath;
    private int frameCols;
    private int frameRows;
    private float frameInterval;
    private float width;
    private float height;
    private float scaleX;
    private float scaleY;
    
    public AnimationTexturePart(Vector2D position, String srcPath, int frameCols, int frameRows, float frameInterval) {
        this.position = position;
        this.srcPath = srcPath;
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.frameInterval = frameInterval;
        this.stateTime = 0.0f;
    }
    
    public AnimationTexturePart(Vector2D position, String srcPath, int frameCols, int frameRows, float frameInterval, float width, float height, float scaleX, float scaleY) {
        this.position = position;
        this.srcPath = srcPath;
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.frameInterval = frameInterval;
        this.stateTime = 0.0f;
        this.width = width;
        this.height = height;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public int getFrameCols() {
        return frameCols;
    }

    public int getFrameRows() {
        return frameRows;
    }

    public float getStateTime() {
        return stateTime;
    }

    public Vector2D getPosition() {
        return position;
    }

    public String getSrcPath() {
        return srcPath;
    }
    
    public float getFrameInterval() {
        return frameInterval;
    }

    public void updateStateTime(float delta) {
        stateTime += delta;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
