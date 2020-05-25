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

    private int originalMaxAmount;
    private int maxEnemyAmount;
    private int originalDelay;
    private int spawnDelay;
    private int radius;
    private float timer;
    private boolean ready;

    public SpawnerPart(int maxEnemyAmount, int spawnDelay, int radius) {
        this.maxEnemyAmount = maxEnemyAmount;
        this.originalMaxAmount = maxEnemyAmount;
        this.spawnDelay = spawnDelay;
        this.originalDelay = spawnDelay;
        this.radius = radius;
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
        timer += gameData.getDelta();
        maxEnemyAmount = (int) (gameData.getDifficultyMultiplier() * originalMaxAmount);
    }

    /**
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isReady() {
        if (timer > spawnDelay) {
            return true;
        }
        return false;
    }

    public void resetTimer() {
        timer = 0;
    }
}
