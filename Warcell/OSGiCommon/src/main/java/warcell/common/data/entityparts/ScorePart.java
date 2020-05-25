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
 * @author birke
 */
public class ScorePart implements EntityPart {

    private String name;
    private int score;

    public ScorePart(String name) {
        this.name = name;
        this.score = 0;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (score > 500) {
            float multi = ((float) score) / 5000f;
            gameData.setDifficultyMultiplier(multi);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
