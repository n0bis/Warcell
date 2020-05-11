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
           
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    GridCell gridCell = gridCells[i][j];
                    AStarNode node = new AStarNode(gridCell.getX(), gridCell.getY());
                    node.setW(gridCell.isWalkable() ? 1000 : 0);
                    node.setBlock(gridCell.isWalkable());
                    nodes[i][j] = node;
                }
            }
            
            System.out.println(Arrays.toString(nodes));
            
            ai = new AStar(rows, columns);
            ai.setSearchArea(nodes);
        }
    }

    @Override
    public List<PositionPart> getPath(PositionPart sourcePart, PositionPart targetPart) {
        
        /*AStarNode source = new AStarNode(Math.round(sourcePart.getX()), Math.round(sourcePart.getY()));
        AStarNode target = new AStarNode(Math.round(targetPart.getX()), Math.round(targetPart.getY()));
        ai.setSourceAndTargetNode(source, target);
        
        ArrayList<AStarNode> nodePath = (ArrayList<AStarNode>) ai.findPath();*/
        
        ArrayList<PositionPart> path = new ArrayList<>();
        
        /*nodePath.forEach((node) -> {
            path.add(new PositionPart(node.getRow(), node.getCol(), 0));
        });*/
        
        return path;
    }

}
