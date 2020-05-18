/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.core.managers;

import com.badlogic.gdx.Gdx;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import warcell.common.data.GameData;
import warcell.common.data.SaveGame;

/**
 *
 * @author birke
 */
public class SaveGameManager {
    private File file;
    private ArrayList<SaveGame> saves;
    private ArrayList<SaveGame> loadSaves;
    private SaveGame save;
    private GameData gameData;

    public SaveGameManager(File file, GameData gameData) {
        this.file = file;
        this.gameData = gameData;
        saves = new ArrayList();
    }
  
    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("saves.sav")
            );
            if (saves == null) {
                load();
            }
            saves.add(new SaveGame(gameData.getName(), gameData.getFinalScore()));
            out.writeObject(saves);
            out.close();
            
        } catch (Exception e) {
            
        }

    }
    public void load() {
        try {
            if(!saveFileExists()) {
                return;
            }
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("saves.sav")
            );
            saves = (ArrayList<SaveGame>) in.readObject();
            in.close();
            Collections.sort(saves);
        } catch (Exception e) {
            
        }

    }
    
    public boolean saveFileExists() {
        return file.exists();
    }

    public ArrayList<SaveGame> getSaves() {
        return saves;
    }
    
    
}
