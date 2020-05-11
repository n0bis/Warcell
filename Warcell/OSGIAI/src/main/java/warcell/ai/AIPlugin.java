/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.ai;

import com.badlogic.gdx.maps.Map;
import java.util.ArrayList;
import java.util.List;
import org.xguzm.pathfinding.PathFindingException;
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
    
    private final int DEFAULT_TILE_SIZE = 128;
    private Map map;
    private NavigationTiledMapLayer navigationLayer;
    private AStarGridFinder<GridCell> finder = new AStarGridFinder(GridCell.class);
    
    @Override
    public void startAI(TiledMapPart tiledMap) {
        if (map == null) {
            map = new NavTmxMapLoader().load(tiledMap.getSrcPath());
            navigationLayer = (NavigationTiledMapLayer) map.getLayers().get("navigation");
        }
    }

    @Override
    public List<PositionPart> getPath(PositionPart source, PositionPart target) {
        int sourceX = Math.round(source.getX() / DEFAULT_TILE_SIZE);
        int sourceY = Math.round(source.getY() / DEFAULT_TILE_SIZE);
        
        int targetX = Math.round(target.getX() / DEFAULT_TILE_SIZE);
        int targetY = Math.round(target.getY() / DEFAULT_TILE_SIZE);
        
        //System.out.println("sourceX: " + sourceX + " sourceY: " + sourceY);
        //System.out.println("targetX: " + targetX + " targetY: " + targetY);
        List<GridCell> thePath = new ArrayList<>();
        try {
            thePath = finder.findPath(sourceX, sourceY, targetX, targetY, navigationLayer);
        } catch (PathFindingException e) {
            return new ArrayList<>();
        }
        
        if(thePath == null)
            return new ArrayList<>();
        
        List<PositionPart> pathToEnd = new ArrayList<>();
        
        for (GridCell path : thePath) {
            pathToEnd.add(new PositionPart(path.getX() * DEFAULT_TILE_SIZE, path.getY() * DEFAULT_TILE_SIZE, 0));
        }
        
        return pathToEnd;
    }

}
