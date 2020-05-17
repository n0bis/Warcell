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
    private PositionPart camPos;
    private OrthographicCamera cam;
    private TiledMapRenderer mapRenderer;
    private ShapeRenderer sr;
    private SpriteBatch textureSpriteBatch;
    private final List<IEntityProcessingService> entityProcessorList;
    private List<IPostEntityProcessingService> postEntityProcessorList;
    private GameAssetManager gameAssetManager;

    public PlayState(Game game, World world, GameData gameData) {
        super(game, world, gameData);
        this.game = game;
        this.world = world;
        this.gameData = gameData;
        this.mapRenderer = getGame().getMapRenderer();
        this.cam = getGame().getCam();
        this.sr = getGame().getSr();
        this.textureSpriteBatch = getGame().getTextureSpriteBatch();
        this.entityProcessorList = getGame().getEntityProcessorList();
        this.postEntityProcessorList = getGame().getPostEntityProcessorList();
        this.gameAssetManager = getGame().getGameAssetManager();
    }

    
   

    @Override
    public void init() {
    }

    @Override
    public void update(float dt) {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }    
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        //System.out.println("Delta: " + gameData.getDelta());      // debug
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
        draw();    
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            sr.setColor(1, 1, 1, 1);
            sr.setProjectionMatrix(cam.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }



    private void drawTextures() {
        textureSpriteBatch.setProjectionMatrix(cam.combined);
        textureSpriteBatch.begin();


        for (Entity e : world.getEntities()) {
            TexturePart tp = e.getPart(TexturePart.class);
            PositionPart pp = e.getPart(PositionPart.class);


            if (tp != null && pp != null) {
                TextureRegion texture = new TextureRegion(gameAssetManager.getTexture(e.getClass(), tp.getSrcPath()));
                if (tp.getHeight() + tp.getWidth() == 0) {
                    textureSpriteBatch.draw(texture, pp.getX(), pp.getY());
                } else {
                    /* draw(Texture texture, float x, float y,
                        float originX, float originY, float width, float height,
                        float scaleX, float scaleY, float rotation,
                        int srcX, int srcY, int srcWidth, int srcHeight,
                        boolean flipX, boolean flipY) */
                    textureSpriteBatch.draw(texture, pp.getX(), pp.getY(), pp.getX(), pp.getY(), tp.getWidth(), tp.getHeight(), tp.getScaleX(), tp.getScaleY(), pp.getRadians());
                }

            }

        }
        textureSpriteBatch.end();
    }

    private void drawAnimations() {
        textureSpriteBatch.setProjectionMatrix(cam.combined);
        textureSpriteBatch.begin();

        for (Entity e : world.getEntities()) {
            AnimationTexturePart animationTexturePart = e.getPart(AnimationTexturePart.class);
            PositionPart pp = e.getPart(PositionPart.class);

            if (animationTexturePart != null && pp != null) {
                animationTexturePart.updateStateTime(gameData.getDelta());
                Animation animation = gameAssetManager.getAnimation(e.getClass(), animationTexturePart);

                if (animation == null) {
                    continue;
                }
                
                TextureRegion currentFrame = animation.getKeyFrame(animationTexturePart.getStateTime(), true);
                if (animationTexturePart.getHeight() + animationTexturePart.getWidth() == 0) {
                    textureSpriteBatch.draw(currentFrame,
                        pp.getX(),
                        pp.getY());
                } else {
                    textureSpriteBatch.draw(currentFrame, pp.getX(), pp.getY(), animationTexturePart.getWidth()/2, animationTexturePart.getHeight()/2, animationTexturePart.getWidth(), animationTexturePart.getHeight(), animationTexturePart.getScaleX(), animationTexturePart.getScaleY(), pp.getRadians());
                }
            }

        }
        textureSpriteBatch.end();
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
