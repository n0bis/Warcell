/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.gamestates;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import warcell.Game;
import warcell.common.data.GameData;
import warcell.common.data.World;
/**
 *
 * @author birke
 */
public abstract class State {
    
    private final Game game;
    private final World world;
    private final GameData gameData;
    private final OrthographicCamera cam = new OrthographicCamera();

    
    public State(Game game, World world, GameData gameData) {
        this.game = game;
        this.world = world;
        this.gameData = gameData;
        init();
    }

    public abstract void init();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void handleInput();
    public abstract void dispose();

    public Game getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public GameData getGameData() {
        return gameData;
    }

    public OrthographicCamera getCam() {
        return cam;
    }
    
    
    
}
