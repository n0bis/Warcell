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
import org.xguzm.pathfinding.PathFindingException;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.heuristics.ManhattanDistance;
import warcell.astar.AStar;
import warcell.astar.AStarNode;
import warcell.common.ai.AISPI;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TiledMapPart;

/**
 *
 * @author madsfalken
 */
public class AIPlugin implements AISPI {
    
    private AStar ai;
    private AStarNode[][] nodes;
    private Map map;
    private NavigationTiledMapLayer navigationLayer;
    private AStarGridFinder<GridCell> finder = new AStarGridFinder(GridCell.class);
    
    @Override
    public void startAI(TiledMapPart tiledMap) {
        if (map == null) {
            map = new NavTmxMapLoader().load(tiledMap.getSrcPath());
            navigationLayer = (NavigationTiledMapLayer) map.getLayers().get("navigation");
            
            GridCell[][] gridCells = navigationLayer.getNodes();
            int rows = gridCells.length;
            int columns = gridCells[0].length;
            
            nodes = new AStarNode[rows][columns];
            
            System.out.println("rows: " + rows);
            System.out.println("columns: " + columns);
           
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    GridCell gridCell = gridCells[i][j];
                    System.out.println("gridX: " + gridCell.getX() + " gridY: " + gridCell.getY());
                    AStarNode node = new AStarNode(gridCell.getX(), gridCell.getY());
                    node.setW(gridCell.isWalkable() ? 1000 : 0);
                    node.setBlock(gridCell.isWalkable());
                    nodes[i][j] = node;
                }
            }
            
            ai = new AStar(rows, columns);
            ai.setSearchArea(nodes);
        }
    }

    @Override
    public List<PositionPart> getPath(PositionPart sourcePart, PositionPart targetPart) {
        
        AStarNode source = new AStarNode(Math.round(sourcePart.getX() / 32), Math.round(sourcePart.getY() / 32));
        AStarNode target = new AStarNode(Math.round(targetPart.getX() / 32), Math.round(targetPart.getY() / 32));
        ai.setSourceAndTargetNode(source, target);
        
        ArrayList<AStarNode> nodePath = new ArrayList<>();
        try {
            nodePath = (ArrayList<AStarNode>) ai.findPath();
        } catch (ArrayIndexOutOfBoundsException e) {
            return new ArrayList<>();
        }
        
        ArrayList<PositionPart> path = new ArrayList<>();
        
        for (AStarNode node : nodePath) {
            int x = node.getRow();
            int y = node.getCol();

            path.add(new PositionPart(x, y, 0));
        }
        
        return path;
    }

}
