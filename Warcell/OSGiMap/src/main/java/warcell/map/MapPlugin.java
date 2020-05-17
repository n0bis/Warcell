package warcell.map;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.TiledMapPart;
import warcell.common.map.Tile;
import warcell.common.services.IGamePluginService;


public class MapPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Entity tiledMap = new Tile();
        tiledMap.add(new TiledMapPart("/maps/ZombieMap.tmx"));
        world.addEntity(tiledMap);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity tiledMap : world.getEntities(Tile.class))
            world.removeEntity(tiledMap);
    }

}