/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;

/**
 *
 * @author madsfalken
 */
public class TexturePart implements EntityPart {

    private String srcPath;
    private float width;
    private float height;
    private float scaleX;
    private float scaleY;

    /**
     * Creates a new texture part with a given source path
     * @param srcPath String
     */
    public TexturePart(String srcPath) {
        this.srcPath = srcPath;
    }

    public TexturePart(String srcPath, float width, float height, float scaleX, float scaleY) {
        this.srcPath = srcPath;
        this.width = width;
        this.height = height;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
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

    /**
     * gets path to texture source
     * @return String
     */
    public String getSrcPath() {
        return srcPath;
    }

    /**
     * Sets the path to the texture source
     * @param srcPath String
     */
    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
