/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.common.data;

import java.io.Serializable;

/**
 *
 * @author birke
 */
public class SaveGame implements Serializable, Comparable<SaveGame> {
    

    String name;
    int Score;

    public SaveGame(String name, int Score) {
        this.name = name;
        this.Score = Score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }
    
    

    @Override
    public int compareTo(SaveGame o) {
        int i = o.getScore() - this.getScore();
        return i;
    }
    
    
}
