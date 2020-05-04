/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.weapon.parts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.entityparts.EntityPart;

/**
 * Only used as a bullet identifier for better collision
 *
 * @author Phillip O
 */
public class ProjectilePart implements EntityPart {

    String ID;

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public ProjectilePart(String id) {
        this.ID = id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
