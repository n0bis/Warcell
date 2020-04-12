/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.ai;

import java.util.ArrayList;
import warcell.common.data.Entity;
import warcell.common.data.World;
import warcell.common.data.entityparts.PositionPart;

/**
 *
 * @author madsfalken
 */
public interface AISPI {
    void setSourceNode(Entity p, World world, int checkpointCount);
    void startAI();
    ArrayList<PositionPart> getPath();
}
