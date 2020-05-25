package warcell.osgidefense;

import com.badlogic.gdx.Gdx;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.GameKeys;
import warcell.common.data.World;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.defense.Defense;
import warcell.common.services.IEntityProcessingService;

public class DefenseProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Defense.class)) {

            PositionPart positionPart = entity.getPart(PositionPart.class);
            LifePart lifePart = entity.getPart(LifePart.class);

            if (lifePart.isDead()) {
                world.removeEntity(entity);
            }

            lifePart.process(gameData, entity);
            positionPart.process(gameData, entity);
        }
    }
}
