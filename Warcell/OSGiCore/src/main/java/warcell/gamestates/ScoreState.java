/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.gamestates;

<<<<<<< HEAD
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import warcell.Game;
import warcell.common.data.GameData;
import warcell.common.data.World;
=======
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import warcell.Game;
import warcell.common.data.GameData;
import warcell.common.data.GameKeys;
import warcell.common.data.SaveGame;
import warcell.common.data.World;
import warcell.core.managers.SaveGameManager;
>>>>>>> master

/**
 *
 * @author birke
 */
public class ScoreState extends State {
<<<<<<< HEAD

=======
    private BitmapFont titleFont;
    private BitmapFont font;
    
>>>>>>> master
    public ScoreState(GUIStateManager guiStateManager, Game game, World world, GameData gameData) {
        super(guiStateManager, game, world, gameData);
        
    }
<<<<<<< HEAD

    
    
    

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
    
    @Override
    public void init() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/Western Bang Bang.otf")
        );   
        
        titleFont = gen.generateFont(80);
        titleFont.setColor(Color.WHITE);
        font = gen.generateFont(40);    
        font.setColor(Color.WHITE); 
        
        getGame().getSgm().load();
>>>>>>> master
    }

    @Override
    public void update(float dt) {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        handleInput();
>>>>>>> master
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        getGame().getTextureSpriteBatch().setProjectionMatrix(getGame().getCam().combined);
        getGame().getTextureSpriteBatch().begin();
        
        titleFont.draw(
            getGame().getTextureSpriteBatch(),
            "Highscore",
            20,
            700
        ); 
        if (getGame().getSgm().getSaves() != null) {
            int i = 0;
            int counter = 0;
            for (SaveGame save : getGame().getSgm().getSaves()) {
                font.draw(
                    getGame().getTextureSpriteBatch(),
                    "Name: " + save.getName() + " | Score: " + save.getScore(),
                    20,
                    600 - i
                );
                i += 40;
                counter += 1;
                if (counter > 4) {
                    break;
                }
            }
        }  

        font.drawMultiLine(
            getGame().getTextureSpriteBatch(),
            "Press ESCAPE to return",
            20,
            50
        );
        
        
        getGame().getTextureSpriteBatch().end();    
>>>>>>> master
    }

    @Override
    public void handleInput() {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
        if (getGameData().getKeys().isPressed(GameKeys.ESCAPE)) {
            getGuiStateManager().setState(0);
        }
>>>>>>> master
    }

    @Override
    public void dispose() {
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
=======
    }


>>>>>>> master
    
}
