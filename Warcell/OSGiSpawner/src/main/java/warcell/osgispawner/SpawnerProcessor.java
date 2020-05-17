package warcell.osgispawner;

import java.util.HashMap;
import java.util.Map;
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
    private static Map<String, Entity> enemies = new HashMap<>();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Spawner.class)) {
            PositionPart positionPart = entity.getPart(PositionPart.class);
            SpawnerPart spawnerPart = entity.getPart(SpawnerPart.class);
            spawnerPart.process(gameData, entity);
           
            for (IGamePluginService plugin : gameData.getGamePlugins()) {
                if (plugin.getClass().getCanonicalName().matches("warcell.osgienemy.EnemyPlugin")) {
                    if (igp == null) {
                        igp = plugin;
                    }
                    //Found the plugin just call start()
                    if (world.getEntities(Enemy.class).size() < spawnerPart.getMaxEnemyAmount()) {
                        if (spawnerPart.isReady()) {
                            for (Entity enemy : world.getEntities(Enemy.class)) {
                                if (!enemies.containsKey(enemy.getID())) {
                                    enemies.put(enemy.getID(), enemy);
                                    plugin.start(gameData, world);
                                    PositionPart ppE = enemy.getPart(PositionPart.class);
                                    ppE.setX(positionPart.getX() + r.nextInt(spawnerPart.getRadius()*2) - spawnerPart.getRadius());
                                    ppE.setY(positionPart.getY() + r.nextInt(spawnerPart.getRadius()*2) - spawnerPart.getRadius());
                                    System.out.println("enemy Spawned at: "+ ppE.getX()+ " " + ppE.getY());
                                    spawnerPart.resetTimer();
                                }
                            }    
                        }
                    }
                }
            }
        }
    }
}