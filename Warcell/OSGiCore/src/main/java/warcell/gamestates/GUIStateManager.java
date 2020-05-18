/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import warcell.Game;
import warcell.common.data.GameData;
import warcell.common.data.World;

/**
 *
 * @author birke
 */
public final class GUIStateManager {
    // current game state
    private State gameState;
    private final Game game;
    private GameData gameData;
    private World world;

    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int HELP = 2;       
    public static final int GAMEOVER = 3;    
    public static final int HIGHSCORE = 4;    

    public GUIStateManager(Game game, World world, GameData gameData) {
        this.game = game;
        this.world = world;
        this.gameData = gameData;
        setState(MENU);

    }

    public void setState(int state) {
        if(gameState != null) gameState.dispose();
        if(state == MENU) {
            gameState = new MenuState(this, game, world, gameData);
        }
        if(state == PLAY) {
            gameState = new PlayState(this, game, world, gameData);
        }
        if(state == HELP) {
            gameState = new HelpState(this, game, world, gameData);
        }            
        if(state == GAMEOVER) {
            gameState = new GameOverState(this, game, world, gameData);
            
        }if(state == HIGHSCORE) {
            gameState = new ScoreState(this, game, world, gameData);
        }

    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        gameState.render(spriteBatch);
    }
}
