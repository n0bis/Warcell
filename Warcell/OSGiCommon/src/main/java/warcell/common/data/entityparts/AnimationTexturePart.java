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
    
    public AnimationTexturePart(Vector2D position, String srcPath, int frameCols, int frameRows, float frameInterval) {
        this.position = position;
        this.srcPath = srcPath;
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.frameInterval = frameInterval;
        this.stateTime = 0.0f;
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

    @Override
    public void process(GameData gameData, Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
