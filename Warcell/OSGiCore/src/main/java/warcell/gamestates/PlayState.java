/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import warcell.Game;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TexturePart;
import warcell.common.player.Player;
import warcell.common.services.IEntityProcessingService;
import warcell.common.services.IGamePluginService;
import warcell.common.services.IPostEntityProcessingService;
import warcell.core.managers.GameAssetManager;
import warcell.core.managers.GameInputProcessor;

/**
 *
 * @author birke
 */
public class PlayState extends State {
    private final Game game;
    private final World world;
    private final GameData gameData;
    /*    private PositionPart camPos;
    private OrthographicCamera cam;
    private TiledMapRenderer mapRenderer;
    private ShapeRenderer sr;
    private SpriteBatch textureSpriteBatch;
    private final List<IEntityProcessingService> entityProcessorList;
    private List<IPostEntityProcessingService> postEntityProcessorList;
    private GameAssetManager gameAssetManager;*/

    public PlayState(Game game, World world, GameData gameData) {
        super(game, world, gameData);
        this.game = game;
        this.world = world;
        this.gameData = gameData;
        /*        this.mapRenderer = game.getMapRenderer();
        this.cam = getGame().getCam();
        this.sr = getGame().getSr();
        this.textureSpriteBatch = getGame().getTextureSpriteBatch();
        this.entityProcessorList = getGame().getEntityProcessorList();
        this.postEntityProcessorList = getGame().getPostEntityProcessorList();
        this.gameAssetManager = getGame().getGameAssetManager();*/
    }

    @Override
    public void init() {
    }

    @Override
    public void update(float dt) {
        /*        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
        entityProcessorService.process(gameData, world);
        }
        
        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
        postEntityProcessorService.process(gameData, world);
        }    */
        System.out.println("test");
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        /*        //System.out.println("Delta: " + gameData.getDelta());      // debug
        for (Entity e : world.getEntities(Player.class)) {
        PositionPart posPart = e.getPart(PositionPart.class);
        if (posPart != null) {
        camPos = posPart;
        } else {
        camPos.setX(gameData.getDisplayWidth());
        camPos.setY(gameData.getDisplayHeight());
        }
        
        }
        cam.position.set(camPos.getX(), camPos.getY(), 0);
        cam.update();
        mapRenderer.setView(cam);
        mapRenderer.render();
        
        drawTextures();
        drawAnimations();
        draw();    */
    }


    
    @Override
    public void handleInput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
