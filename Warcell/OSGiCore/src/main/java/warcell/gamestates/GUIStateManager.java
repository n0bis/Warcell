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
    public static final int HIGHSCORE = 2;

    public GUIStateManager(Game game, World world, GameData gameData) {
        this.game = game;
        this.world = world;
        this.gameData = gameData;
        setState(PLAY);

    }

    public void setState(int state) {
            if(gameState != null) gameState.dispose();
            if(state == MENU) {
                gameState = new MenuState(game, world, gameData);
            }
            if(state == PLAY) {
                if (game == null) {
                    System.out.println("it's null");
                } else {
                    gameState = new PlayState(game, world, gameData);
                }
            }
            if(state == HIGHSCORE) {
                gameState = new ScoreState(game, world, gameData);
            }

    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        gameState.render(spriteBatch);
    }
}
