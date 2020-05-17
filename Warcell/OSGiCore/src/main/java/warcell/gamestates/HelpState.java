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
public class HelpState extends State {
    private BitmapFont titleFont;
    private BitmapFont font;

    public HelpState(GUIStateManager guiStateManager, Game game, World world, GameData gameData) {
        super(guiStateManager, game, world, gameData);
    }
    
    

    @Override
    public void init() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/Western Bang Bang.otf")
        );   


        titleFont = gen.generateFont(80);
        titleFont.setColor(Color.WHITE);
        font = gen.generateFont(40);    
        font.setColor(Color.WHITE);
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        getGame().getTextureSpriteBatch().setProjectionMatrix(getGame().getCam().combined);
        getGame().getTextureSpriteBatch().begin();
        
        titleFont.draw(
            getGame().getTextureSpriteBatch(),
            "Controls",
            20,
            700
        );        
        font.drawMultiLine(
            getGame().getTextureSpriteBatch(),
            "Movement: W, A, S, D \nShoot: Left mouse button \nChange weapon: Q or E \nPause: Escape",
            20,
            550
        );        
        font.drawMultiLine(
            getGame().getTextureSpriteBatch(),
            "Press ESCAPE to return",
            20,
            100
        );
        
        
        getGame().getTextureSpriteBatch().end();    
    }

    @Override
    public void handleInput() {
        if (getGameData().getKeys().isPressed(GameKeys.ESCAPE)) {
            getGuiStateManager().setState(0);
        }
    }

    @Override
    public void dispose() {
        titleFont.dispose();
        font.dispose();    
    }
    
}
