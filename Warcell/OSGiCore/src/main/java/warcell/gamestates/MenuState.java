/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import warcell.Game;
import warcell.common.data.GameData;
import warcell.common.data.GameKeys;
import warcell.common.data.World;

/**
 *
 * @author birke
 */
public class MenuState extends State{
    private BitmapFont titleFont;
    private BitmapFont font;
    private int currentItem;
    private String[] menuItems;
    private final String title = "Warcell"; 
    
    public MenuState(GUIStateManager guiStateManager, Game game, World world, GameData gameData) {
        super(guiStateManager, game, world, gameData);
    }

    @Override
    public void init() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/Western Bang Bang.otf")
        );   

        titleFont = gen.generateFont(100);
        titleFont.setColor(Color.WHITE);

        font = gen.generateFont(50);

        menuItems = new String[] {
            "Play",
            "Help",
            "Quit"
        };      
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        getGame().getTextureSpriteBatch().setProjectionMatrix(getGame().getCam().combined);
        getGame().getTextureSpriteBatch().begin();
        
        // draw title
        titleFont.draw(
            getGame().getTextureSpriteBatch(),
            title,
            20,
            300
        );

        // draw menu
        for(int i = 0; i < menuItems.length; i++) {
            if(currentItem == i) font.setColor(Color.RED);
            else font.setColor(Color.WHITE);
            font.draw(
                getGame().getTextureSpriteBatch(),
                menuItems[i],
                20,
                180 - 35 * i
            );
        }

        getGame().getTextureSpriteBatch().end();
    }

    @Override
    public void handleInput() {       
        if(getGameData().getKeys().isPressed(GameKeys.UP)) {
            if(currentItem > 0) {
                currentItem--;
            }
        }
        if(getGameData().getKeys().isPressed(GameKeys.DOWN)) {
            if(currentItem < menuItems.length - 1) {
                currentItem++;
            }
        }
        if(getGameData().getKeys().isPressed(GameKeys.ENTER)) {
            select();
        }   
    }
    
    private void select() {
        // play
        if(currentItem == 0) {
            getGuiStateManager().setState(GUIStateManager.PLAY);
        }
        // high scores
        else if(currentItem == 1) {
            getGuiStateManager().setState(GUIStateManager.HELP);
        }
        else if(currentItem == 2) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        titleFont.dispose();
        font.dispose();    
    }
}
