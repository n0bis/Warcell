package warcell;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.io.File;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.services.IEntityProcessingService;
import warcell.common.services.IGamePluginService;
import warcell.common.services.IPostEntityProcessingService;
import warcell.core.managers.GameInputProcessor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import warcell.core.managers.GameAssetManager;
import warcell.core.managers.SaveGameManager;
import warcell.gamestates.GUIStateManager;

public class Game implements ApplicationListener {

    private OrthographicCamera cam;
    private ShapeRenderer sr;
    private SpriteBatch textureSpriteBatch;
    private final GameData gameData = new GameData();
    private World world = new World();
    private final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private GameAssetManager gameAssetManager;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    float w = gameData.getDisplayWidth();
    float h = gameData.getDisplayHeight();
    private GUIStateManager guiManager;
    private SaveGameManager sgm;
    private Music music;

    private float unitScale = 1 / 128f;

    public Game() {
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
        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/EpicMusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
    }

    @Override
    public void create() {
        guiManager = new GUIStateManager(this, world, gameData);
        sgm = new SaveGameManager(new File("saves.sav"), gameData);

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.setToOrtho(false, gameData.getDisplayWidth(), gameData.getDisplayHeight());
        //cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        map = new TmxMapLoader().load("maps/ZombieMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

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
        guiManager.update(Gdx.graphics.getDeltaTime());
        guiManager.render(textureSpriteBatch);
        gameData.getKeys().update();
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
        gameData.setGamePlugins(gamePluginList);
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        plugin.start(gameData, world);
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        gameData.setGamePlugins(gamePluginList);
        plugin.stop(gameData, world);
    }

    public World getWorld() {
        return world;
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public List<IEntityProcessingService> getEntityProcessorList() {
        return entityProcessorList;
    }

    public List<IPostEntityProcessingService> getPostEntityProcessorList() {
        return postEntityProcessorList;
    }

    public GameData getGameData() {
        return gameData;
    }

    public TiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public ShapeRenderer getSr() {
        return sr;
    }

    public SpriteBatch getTextureSpriteBatch() {
        return textureSpriteBatch;
    }

    public GameAssetManager getGameAssetManager() {
        return gameAssetManager;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public SaveGameManager getSgm() {
        return sgm;
    }

}
