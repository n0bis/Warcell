/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.ai;

import java.util.List;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TiledMapPart;

/**
 *
 * @author madsfalken
 */
public interface AISPI {
    void startAI(TiledMapPart tiledMap);
    List<PositionPart> getPath(PositionPart from, PositionPart target);
}
