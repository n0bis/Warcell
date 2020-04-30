/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.ai;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.heuristics.ManhattanDistance;
import warcell.common.ai.AISPI;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TiledMapPart;

/**
 *
 * @author madsfalken
 */
public class AIPlugin implements AISPI {

    NavigationTiledMapLayer navigationLayer;
    GridFinderOptions opt = new GridFinderOptions();
    AStarGridFinder<GridCell> finder = new AStarGridFinder(GridCell.class, opt);
    
    @Override
    public void startAI(TiledMapPart tiledMap) {
        opt.heuristic = new ManhattanDistance();
        finder = new AStarGridFinder(GridCell.class, opt);
        Map map = new NavTmxMapLoader().load(tiledMap.getSrcPath());
        navigationLayer = (NavigationTiledMapLayer) map.getLayers().get("navigation");
    }

    @Override
    public List<PositionPart> getPath(PositionPart source, PositionPart target) {

        System.out.println(navigationLayer.getHeight());

        int sourceX = Math.round(source.getX()) / 128;
        int sourceY = Math.round(source.getY()) / 128;
        
        int targetX = Math.round(target.getX()) / 128;
        int targetY = Math.round(target.getY()) / 128;
        
        List<GridCell> thePath = finder.findPath(sourceX, sourceY, targetX, targetY, navigationLayer);
        
        System.out.println(thePath);
        if(thePath == null)
            return null;
        
        List<PositionPart> pathToEnd = new ArrayList<>();
        
        for (GridCell path : thePath) {
            pathToEnd.add(new PositionPart(path.getX(), path.getY(), 0));
        }
        
        return pathToEnd;
    }

}
