package warcell.common.services;

import warcell.common.data.GameData;
import warcell.common.data.World;

public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
