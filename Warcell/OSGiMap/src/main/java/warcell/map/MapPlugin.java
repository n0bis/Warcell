package warcell.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.entityparts.TexturePart;
import warcell.common.data.World;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TilePart;
import warcell.common.map.MapSPI;
import warcell.common.map.Tile;
import warcell.common.data.TileType;
import warcell.common.services.IGamePluginService;


public class MapPlugin implements IGamePluginService {

    /**
     * Tile in current map
     */
    private Tile[][] map;

    /**
     * Current map name
     */
    private static String currentMapName;

    public MapPlugin() {

    }

    @Override
    public void start(GameData gameData, World world) {
        
        System.out.print("hejsa");
            loadFromFile("DefaultMap.txt", gameData, world);

        
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
    
    

      /**
     * Finds the tile weight for the tile closed to the entity
     *
     * @param p the entity
     * @param world the world
     * @return the weight of the closed tile
     */
    public double getPositionWeight(Entity p, World world) {
        Entity closedTile = getTile(p, world);
        TilePart tp = closedTile.getPart(TilePart.class);
        return tp.getType().getPenalty();
    }

    public int[] getTileXandY(Tile t) {
        int[] result = new int[2];

        int r = map.length;
        int c = map[0].length;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j] == t) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }


       /**
     * Finds the tile the entity is on
     *
     * @param p
     * @param world
     * @return the tile the entity is on
     */
    public Tile getTile(Entity p, World world) {
        Entity closedTile = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Entity tile : world.getEntities(Tile.class)) {
            if (closedTile == null) {
                closedTile = tile;
                shortestDistance = calculateDistance(p, tile);
                continue;
            }
            double nextDistance = calculateDistance(p, tile);
            if (nextDistance < shortestDistance) {
                closedTile = tile;
                shortestDistance = nextDistance;
            }
        }
        return (Tile) closedTile;
    }


    /**
     * Calculates the distance between to entities based center of the image
     *
     * @param t1 entity one
     * @param t2 entity two
     * @return the distance between the centers of the two entities
     */
    private double calculateDistance(Entity t1, Entity t2) {
        PositionPart p1 = t1.getPart(PositionPart.class);
        PositionPart p2 = t2.getPart(PositionPart.class);

        TexturePart i1 = t1.getImage();
        TexturePart i2 = t2.getImage();
        double x = Math.pow((p2.getX() + (i2.getWidth() / 2)) - (p1.getX() + (i1.getWidth() / 2)), 2);
        double y = Math.pow((p2.getY() + (i2.getHeight() / 2)) - (p1.getY() + (i1.getHeight() / 2)), 2);
        return Math.sqrt(x + y);
    }

    /**
     * Creates a new map based on a comma separated file
     *
     * @param fileName the file name
     * @param gameData the game data
     * @param world the world data
     */
    public void loadFromFile(String fileName, GameData gameData, World world) {
        try {
            InputStream is;
            if (fileName.equals("DefaultMap.txt")) {
                is = getClass().getClassLoader().getResourceAsStream("/maps/" + fileName);
            } else {
                String path = System.getProperty("user.home") + "/racing_game/maps/";
                String file = path + fileName;

                is = new FileInputStream(file);
            }
            ArrayList<int[]> data = new ArrayList<>();
            try (Scanner sc = new Scanner(is)) {
                while (sc.hasNextLine()) {
                    String nextLine = sc.nextLine();
                    String[] values = nextLine.split(",");
                    int[] row = new int[values.length];
                    for (int i = 0; i < values.length; i++) {
                        row[i] = Integer.parseInt(values[i]);
                    }
                    data.add(row);
                }
                int[][] map = new int[data.size()][data.get(0).length];
                for (int i = 0; i < data.size(); i++) {
                    map[i] = data.get(i);
                }
                createMap(map, gameData, world);
                currentMapName = fileName;
            }
            is.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MapPlugin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new map based on the input arguments
     *
     * @param d a two dimensional array with TileTypes
     * @param gameData the GameData
     * @param world the World
     */
    public void createMap(TileType[][] d, GameData gameData, World world) {
        float tileHeight = 70;
        float tileWeight = 70;

        map = new Tile[d.length][d[0].length];
        
        int rOffSet = d.length - 1;
        for (int r = 0; r < d.length; r++) {
            for (int c = 0; c < d[r].length; c++) {
                Tile t = new Tile();
                PositionPart p = new PositionPart(c * tileWeight, (r + rOffSet) * tileHeight, 0);
                t.add(p);
                t.add(new TilePart(d[r][c]));

                //t.setImage(new GameImage(d[r][c].getImagePath(), tileWeight, tileHeight));
                System.out.println(d[r][c].getImagePath());
                t.add(new TexturePart(d[r][c].getImagePath()));
                

                world.addEntity(t);
                map[r][c] = t;
            }
            rOffSet -= 2;
        }
    }

    /**
     * Creates a new map based on the input arguments
     *
     * @param d a two dimensional array with integers representing the TileType
     * @param gameData the GameData
     * @param world the World
     */
    private void createMap(int[][] d, GameData gameData, World world) {
        TileType[][] tmpTiles = new TileType[d.length][d[0].length];
        for (int r = 0; r < d.length; r++) {
            for (int c = 0; c < d[r].length; c++) {
                tmpTiles[r][c] = TileType.values()[d[r][c]];
            }
        }
        createMap(tmpTiles, gameData, world);
    }

    public Tile[][] getLoadedMap() {
        return map;
    }
    
    public String[] getMapNames() {
        File folder = new File(System.getProperty("user.home") + "/racing_game/maps/");
        File[] listOfFiles = folder.listFiles();

        String[] s;
        if (listOfFiles != null) {
            s = new String[listOfFiles.length + 1];
        } else {
            s = new String[1];
        }

        s[0] = "DefaultMap.txt";
        if (listOfFiles != null) {
            int i = 1;
            for (File f : listOfFiles) {
                s[i] = f.getName();
                i++;
            }
        }
        return s;
    }

    
    public void removeAll(World world) {
        for (Entity tile : world.getEntities(Tile.class)) {
            world.removeEntity(tile);
        }
    }
}