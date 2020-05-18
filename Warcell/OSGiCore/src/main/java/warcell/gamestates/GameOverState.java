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
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import warcell.Game;
import warcell.common.data.GameData;
import warcell.common.data.GameKeys;
import warcell.common.data.SaveGame;
import warcell.common.data.World;
import warcell.common.services.IGamePluginService;
import warcell.core.managers.SaveGameManager;

/**
 *
 * @author birke
 */
public class GameOverState extends State {
    private BitmapFont font;
    private BitmapFont title;
    private SaveGame save;
    
    public GameOverState(GUIStateManager guiStateManager, Game game, World world, GameData gameData) {
        super(guiStateManager, game, world, gameData);
    }

    
    @Override
    public void init() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/Western Bang Bang.otf")
        );   
               
        font = gen.generateFont(50);
        font.setColor(Color.WHITE);
        title = gen.generateFont(100);
        title.setColor(Color.RED);
        
        getGame().getCam().setToOrtho(false, getGameData().getDisplayWidth(), getGameData().getDisplayHeight());
        
        getGame().getSgm().save();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        getGame().getTextureSpriteBatch().setProjectionMatrix(getGame().getCam().combined);
        getGame().getTextureSpriteBatch().begin();

        font.draw(
            getGame().getTextureSpriteBatch(),
            "Points: " + String.valueOf(getGameData().getFinalScore()),
            20,
            200
        );
     
        title.draw(
            getGame().getTextureSpriteBatch(),
            "You died",
            20,
            100
        );
        getGame().getTextureSpriteBatch().end();
    }

    @Override
    public void handleInput() {
        if (getGameData().getKeys().isPressed(GameKeys.ESCAPE)) {
            getGuiStateManager().setState(GUIStateManager.MENU);
            System.out.println("escape");
        }
    }

    /**
     * restarts components: OSGiEnemy, OSGiPlayer, OSGiWeapon
     */
    @Override
    public void dispose() {
        title.dispose();
        font.dispose();    
        
        for (IGamePluginService plugin : getGameData().getGamePlugins()) {
            if (plugin.getClass().getCanonicalName().matches("warcell.osgienemy.EnemyPlugin")) {
                plugin.stop(getGameData(), getWorld());
                plugin.start(getGameData(), getWorld());
            }
            if (plugin.getClass().getCanonicalName().matches("warcell.osgiplayer.PlayerPlugin")) {
                plugin.stop(getGameData(), getWorld());
                plugin.start(getGameData(), getWorld());
            }
            if (plugin.getClass().getCanonicalName().matches("warcell.weapon.WeaponPlugin")) {
                plugin.stop(getGameData(), getWorld());
                plugin.start(getGameData(), getWorld());
            }
        }
    }
    

    
}
