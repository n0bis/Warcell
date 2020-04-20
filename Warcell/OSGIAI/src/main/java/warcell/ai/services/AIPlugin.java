/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.ai.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import warcell.common.ai.AISPI;
import warcell.common.data.entityparts.PositionPart;

/**
 *
 * @author madsfalken
 */
public class AIPlugin implements AISPI {
    
    NavigationTiledMapLayer navigationLayer;
    AStarGridFinder finder;

    @Override
    public void startAI() {
        TiledMap map = new NavTmxMapLoader().load("your/tmx/file.tmx");
        navigationLayer = (NavigationTiledMapLayer) map.getLayer("navigation");
    }

    @Override
    public List<PositionPart> getPath() {
        finder = new AStarGridFinder(navigationLayer.getClass());
        List<GridCell> path = finder.findPath(0, 0, 0, 0, navigationLayer);
                
        return path.stream()
            .map(gridCell -> new PositionPart(gridCell.getX(), gridCell.getY(), 0))
            .collect(Collectors.toList());
    }
    
}
