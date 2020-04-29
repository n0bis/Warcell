package warcell.osgispawner;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.services.IEntityProcessingService;


public class SpawnerProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Spawner.class)) {
            
            PositionPart positionPart = entity.getPart(PositionPart.class);            
            
            positionPart.process(gameData, entity);
        }
    }
}
