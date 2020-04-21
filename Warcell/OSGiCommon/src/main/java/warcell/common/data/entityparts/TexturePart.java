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

    /**
     * Creates a new texture part with a given source path
     * @param srcPath String
     */
    public TexturePart(String srcPath) {
        this.srcPath = srcPath;
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
