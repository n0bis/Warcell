package warcell.osgispawner;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.services.IEntityProcessingService;
import warcell.common.spawner.Spawner;
import warcell.common.enemy.Enemy;
import warcell.common.services.IGamePluginService;

public class SpawnerProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Spawner.class)) {
            PositionPart positionPart = entity.getPart(PositionPart.class);

            for (IGamePluginService plugin : gameData.getGamePlugins()) {
                for (Entity e : world.getEntities(Enemy.class)) {
                    if (plugin.getClass().getCanonicalName().matches("warcell.osgienemy.EnemyPlugin")) {
                        //Found the plugin just call start()
                        while (world.getEntities(Enemy.class).size() < 5) {
                            plugin.start(gameData, world);
                            PositionPart ppE = world.getEntities(Enemy.class).get(world.getEntities(Enemy.class).size()-1).getPart(PositionPart.class);
                            ppE.setX(positionPart.getX());
                            ppE.setY(positionPart.getY());
                        }
                    }
                }
            }
            
            positionPart.process(gameData, entity);
        }
    }
}