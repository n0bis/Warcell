/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data.entityparts;

import warcell.common.data.Entity;
import warcell.common.data.GameData;

/**
 *
 * @author Patrick
 */
public class SpawnerPart implements EntityPart {
    
    private int maxEnemyAmount;
    private int spawnDelay;
    
    public SpawnerPart(int maxEnemyAmount, int spawnDelay) {
        this.maxEnemyAmount = maxEnemyAmount;
        this.spawnDelay = spawnDelay;
    }

    /**
     * @return the maxEnemyAmount
     */
    public int getMaxEnemyAmount() {
        return maxEnemyAmount;
    }

    /**
     * @param maxEnemyAmount the maxEnemyAmount to set
     */
    public void setMaxEnemyAmount(int maxEnemyAmount) {
        this.maxEnemyAmount = maxEnemyAmount;
    }

    /**
     * @return the spawnDelay
     */
    public int getSpawnDelay() {
        return spawnDelay;
    }

    /**
     * @param spawnDelay the spawnDelay to set
     */
    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
