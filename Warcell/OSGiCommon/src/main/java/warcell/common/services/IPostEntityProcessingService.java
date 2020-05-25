package warcell.common.services;

import warcell.common.data.GameData;
import warcell.common.data.World;

public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}
