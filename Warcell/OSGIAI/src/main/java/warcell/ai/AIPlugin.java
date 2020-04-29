/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.ai;

import com.badlogic.gdx.maps.Map;
import java.util.List;
import java.util.stream.Collectors;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import warcell.common.ai.AISPI;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TiledMapPart;

/**
 *
 * @author madsfalken
 */
public class AIPlugin implements AISPI {

    NavigationTiledMapLayer navigationLayer;
    AStarGridFinder<GridCell> finder;
    
    @Override
    public void startAI(TiledMapPart tiledMap) {
        Map map = new NavTmxMapLoader().load(tiledMap.getSrcPath());
        navigationLayer = (NavigationTiledMapLayer) map.getLayers().get("navigation");
    }

    @Override
    public List<PositionPart> getPath(PositionPart source, PositionPart target) {
        finder = new AStarGridFinder(navigationLayer.getClass());
        List<GridCell> thePath = finder.findPath(Math.round(source.getX()), Math.round(source.getY()), Math.round(target.getX()), Math.round(target.getY()), navigationLayer);
        
        List<PositionPart> pathToEnd = null;
        
        for (GridCell path : thePath) {
            pathToEnd.add(new PositionPart(path.getX(), path.getY(), 0));
        }
        
        return pathToEnd;
    }

}
