/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.ai;

import java.util.List;
import warcell.common.data.entityparts.PositionPart;

/**
 *
 * @author madsfalken
 */
public interface AISPI {
    void startAI();
    List<PositionPart> getPath();
}
