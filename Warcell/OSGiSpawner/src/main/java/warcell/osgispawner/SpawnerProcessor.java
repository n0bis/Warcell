package warcell.osgispawner;

import java.util.Random;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.SpawnerPart;
import warcell.common.services.IEntityProcessingService;
import warcell.common.spawner.Spawner;
import warcell.common.enemy.Enemy;
import warcell.common.services.IGamePluginService;

public class SpawnerProcessor implements IEntityProcessingService {

    Random r = new Random();
    IGamePluginService igp;
    float timer = 0;

    @Override
    public void process(GameData gameData, World world) {
        timer += gameData.getDelta();
        for (Entity entity : world.getEntities(Spawner.class)) {
            PositionPart positionPart = entity.getPart(PositionPart.class);
            SpawnerPart spawnerPart = entity.getPart(SpawnerPart.class);
           
            for (IGamePluginService plugin : gameData.getGamePlugins()) {
                if (plugin.getClass().getCanonicalName().matches("warcell.osgienemy.EnemyPlugin")) {
                    if (igp == null) {
                        igp = plugin;
                    }
                    //Found the plugin just call start()
                    if (world.getEntities(Enemy.class).size() < spawnerPart.getMaxEnemyAmount()) {
                        if (spawnerPart.getSpawnDelay() < timer) {
                            plugin.start(gameData, world);
                            PositionPart ppE = world.getEntities(Enemy.class).get(world.getEntities(Enemy.class).size() - 1).getPart(PositionPart.class);
                            ppE.setX(positionPart.getX() + r.nextInt(100) - 50);
                            
                            ppE.setY(positionPart.getY() + r.nextInt(100) - 50);
                            timer = 0;
                        }
                    }
                }
            }
        }
    }
}
