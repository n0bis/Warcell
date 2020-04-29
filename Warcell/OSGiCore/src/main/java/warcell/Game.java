package warcell;



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.services.IEntityProcessingService;
import warcell.common.services.IGamePluginService;
import warcell.common.services.IPostEntityProcessingService;
import warcell.core.managers.GameInputProcessor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.TexturePart;
import warcell.common.data.entityparts.TiledMapPart;
import warcell.core.managers.GameAssetManager;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private SpriteBatch textureSpriteBatch;
    private GameAssetManager gameAssetManager;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    private float unitScale = 1 / 128f;

    public Game(){
        gameAssetManager = new GameAssetManager();
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Warcell";
        cfg.width = 1280;
        cfg.height = 768;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        float w = gameData.getDisplayWidth();
        float h = gameData.getDisplayHeight();

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.setToOrtho(false, gameData.getDisplayWidth(), gameData.getDisplayHeight());
        //cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        textureSpriteBatch = new SpriteBatch();
        textureSpriteBatch.setProjectionMatrix(cam.combined);

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        
        update();
        draw();
        drawMap();
        drawTextures();
        drawAnimations();
    }

    private void update() {

        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            sr.setColor(1, 1, 1, 1);

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

    private void drawMap() {
        for (Entity e : world.getEntities()) {
            TiledMapPart tiledMap = e.getPart(TiledMapPart.class);

            if (tiledMap != null) {
                map = new TmxMapLoader().load(tiledMap.getSrcPath());
                mapRenderer = new OrthogonalTiledMapRenderer(map);

                mapRenderer.setView(cam);
                mapRenderer.render();
            }
        }
    }

    private void drawTextures() {
        textureSpriteBatch.setProjectionMatrix(cam.combined);
        textureSpriteBatch.begin();


        for (Entity e : world.getEntities()) {
            TexturePart tp = e.getPart(TexturePart.class);
            PositionPart pp = e.getPart(PositionPart.class);


            if (tp != null && pp != null) {
                Texture texture = gameAssetManager.getTexture(e.getClass(), tp.getSrcPath());
                if (tp.getHeight() + tp.getWidth() == 0) {
                    textureSpriteBatch.draw(texture, pp.getX(), pp.getY());
                } else {
                    /* draw(Texture texture, float x, float y,
                        float originX, float originY, float width, float height,
                        float scaleX, float scaleY, float rotation,
                        int srcX, int srcY, int srcWidth, int srcHeight,
                        boolean flipX, boolean flipY) */
                    textureSpriteBatch.draw(texture, pp.getX(), pp.getY(), pp.getX(), pp.getY(), tp.getWidth(), tp.getHeight(), tp.getScaleX(), tp.getScaleY(), pp.getRadians(), 0, 0, 0, 0, true, true);
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
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        textureSpriteBatch.dispose();
    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.add(plugin);
        plugin.start(gameData, world);

    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }

}
