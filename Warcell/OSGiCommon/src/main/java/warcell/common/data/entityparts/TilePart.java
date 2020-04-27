package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.TileType;

/**
 * Object containing information about a tile
 *
 */
public class TilePart implements EntityPart {

    /**
     * The tile type
     */
    private TileType type;

    public TilePart(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
