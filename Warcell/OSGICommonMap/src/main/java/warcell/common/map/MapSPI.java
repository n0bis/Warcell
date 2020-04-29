package warcell.common.map;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.TileType;

/**
 * The SPI for a map
 *
 */
public interface MapSPI {
    Tile getTile(Entity p, World world);
    double getPositionWeight(Entity p, World world);
    void loadFromFile(String fileName, GameData gameData, World world);
    void createMap(TileType[][] d, GameData gameData, World world);
    Tile[][] getLoadedMap();
    int[] getTileXandY(Tile t);
    void saveMapToFile(int[][] data, String MapName);
    String[] getMapNames();
    void removeAll(World world);
}
