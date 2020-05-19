/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import warcell.Game;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.GameKeys;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.ScorePart;
import warcell.common.data.entityparts.TexturePart;
import warcell.common.player.Player;
import warcell.common.services.IEntityProcessingService;
import warcell.common.services.IGamePluginService;
import warcell.common.services.IPostEntityProcessingService;
import warcell.common.weapon.parts.InventoryPart;

/**
 *
 * @author birke
 */
public class PlayState extends State {

    private PositionPart camPos;
    private boolean paused;
    private BitmapFont font;
    private Texture weaponSprite;
            
    public PlayState(GUIStateManager guiStateManager, Game game, World world, GameData gameData) {
        super(guiStateManager, game, world, gameData);
        paused = false;

    }
    
    @Override
    public void init() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/Western Bang Bang.otf")
        );   
        
        font = gen.generateFont(50);
        font.setColor(Color.WHITE);
    }

    @Override
    public void update(float dt) {
        if (getGame().getGameData().getKeys().isPressed(GameKeys.ESCAPE)) {
            paused = !paused;
        }
        if (!paused) {
            // Update
            for (IEntityProcessingService entityProcessorService : getGame().getEntityProcessorList()) {
                entityProcessorService.process(getGameData(), getWorld());
            }

            // Post Update
            for (IPostEntityProcessingService postEntityProcessorService : getGame().getPostEntityProcessorList()) {
                postEntityProcessorService.process(getGameData(), getWorld());
            }    
        }
        if (getGameData().isGameOver()){
            getGuiStateManager().setState(GUIStateManager.GAMEOVER);
        }
        
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        //System.out.println("Delta: " + getGameData().getDelta());      // debug
        for (Entity e : getWorld().getEntities(Player.class)) {
            PositionPart posPart = e.getPart(PositionPart.class);
            if (posPart != null) {
                camPos = posPart;
            }            
        } 
        getGame().getCam().position.set(camPos.getX(), camPos.getY(), 0);
        getGame().getCam().update();
        getGame().getMapRenderer().setView(getGame().getCam());
        getGame().getMapRenderer().render();

        drawTextures();
        drawAnimations();
        draw();   
        
        if (paused) {
            getGame().getTextureSpriteBatch().setProjectionMatrix(getGame().getCam().combined);
            getGame().getTextureSpriteBatch().begin();
            font.draw(
                getGame().getTextureSpriteBatch(),
                "Paused",
                camPos.getX(),
                camPos.getY()
            );
            
        getGame().getTextureSpriteBatch().end();
        }
        // show score
        for (Entity entity : getGame().getWorld().getEntities(Player.class)) {
            getGame().getTextureSpriteBatch().begin();
            InventoryPart invPart = entity.getPart(InventoryPart.class);
            

            if (invPart.getCurrentWeapon() != null && invPart.getCurrentWeapon().getDescription() != null) {
                weaponSprite = new Texture(Gdx.files.internal(invPart.getCurrentWeapon().getIconPath()));
                getGame().getTextureSpriteBatch().draw(weaponSprite, camPos.getX() + 460, camPos.getY() + 250);
            }

            ScorePart sp = entity.getPart(ScorePart.class);
            LifePart lp = entity.getPart(LifePart.class);
            font.draw(
                getGame().getTextureSpriteBatch(),
                "Points: " + String.valueOf(sp.getScore()),
                camPos.getX() - 640,
                camPos.getY() + 360
            );
            
            font.draw(getGame().getTextureSpriteBatch(), "HP: " + String.valueOf(lp.getLife()), camPos.getX()+500, camPos.getY()+350);
            getGame().getTextureSpriteBatch().end();
        }     
    }

    private void draw() {
        for (Entity entity : getWorld().getEntities()) {
            getGame().getSr().setColor(1, 1, 1, 1);
            getGame().getSr().setProjectionMatrix(getGame().getCam().combined);
            getGame().getSr().begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                getGame().getSr().line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }
            getGame().getSr().end();
        }
    }



    private void drawTextures() {
        getGame().getTextureSpriteBatch().setProjectionMatrix(getGame().getCam().combined);
        getGame().getTextureSpriteBatch().begin();


        for (Entity e : getWorld().getEntities()) {
            TexturePart tp = e.getPart(TexturePart.class);
            PositionPart pp = e.getPart(PositionPart.class);


            if (tp != null && pp != null) {
                TextureRegion texture = new TextureRegion(getGame().getGameAssetManager().getTexture(e.getClass(), tp.getSrcPath()));
                if (tp.getHeight() + tp.getWidth() == 0) {
                    getGame().getTextureSpriteBatch().draw(texture, pp.getX(), pp.getY());
                } else {
                    /* draw(Texture texture, float x, float y,
                        float originX, float originY, float width, float height,
                        float scaleX, float scaleY, float rotation,
                        int srcX, int srcY, int srcWidth, int srcHeight,
                        boolean flipX, boolean flipY) */
                    getGame().getTextureSpriteBatch().draw(texture, pp.getX(), pp.getY(), pp.getX(), pp.getY(), tp.getWidth(), tp.getHeight(), tp.getScaleX(), tp.getScaleY(), pp.getRadians());
                }

            }

        }
        getGame().getTextureSpriteBatch().end();
    }

    private void drawAnimations() {
        getGame().getTextureSpriteBatch().setProjectionMatrix(getGame().getCam().combined);
        getGame().getTextureSpriteBatch().begin();

        for (Entity e : getWorld().getEntities()) {
            AnimationTexturePart animationTexturePart = e.getPart(AnimationTexturePart.class);
            PositionPart pp = e.getPart(PositionPart.class);

            if (animationTexturePart != null && pp != null) {
                animationTexturePart.updateStateTime(getGameData().getDelta());
                Animation animation = getGame().getGameAssetManager().getAnimation(e.getClass(), animationTexturePart);

                if (animation == null) {
                    continue;
                }
                
                TextureRegion currentFrame = animation.getKeyFrame(animationTexturePart.getStateTime(), true);
                if (animationTexturePart.getHeight() + animationTexturePart.getWidth() == 0) {
                    getGame().getTextureSpriteBatch().draw(currentFrame,
                        pp.getX(),
                        pp.getY());
                } else {
                    getGame().getTextureSpriteBatch().draw(currentFrame, pp.getX(), pp.getY(), animationTexturePart.getWidth()/2, animationTexturePart.getHeight()/2, animationTexturePart.getWidth(), animationTexturePart.getHeight(), animationTexturePart.getScaleX(), animationTexturePart.getScaleY(), pp.getRadians());
                }
            }

        }
        getGame().getTextureSpriteBatch().end();
    }
    
    @Override
    public void handleInput() {}
    @Override
    public void dispose() {}
}
