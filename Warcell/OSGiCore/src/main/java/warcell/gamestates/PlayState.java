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
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
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
import warcell.common.weapon.parts.ShootingPart;

/**
 *
 * @author birke
 */
public class PlayState extends State {

    private PositionPart camPos;
    private boolean paused;
    private BitmapFont font;
    private BitmapFont smallFont;
    private Texture weaponSprite;
    private int currentItem;
    private String[] menuItems;
    private HAlignment hAlign;

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
        smallFont = gen.generateFont(25);
        smallFont.setColor(Color.WHITE);

        menuItems = new String[]{
            "Player (loaded)",
            "Enemy (loaded)",
            "Weapons (loaded)"
        };
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
        } else {
            handleInput();
        }
        if (getGameData().isGameOver()) {
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

            for (int i = 0; i < menuItems.length; i++) {
                if (currentItem == i) {
                    font.setColor(Color.RED);
                } else {
                    font.setColor(Color.WHITE);
                }
                font.draw(
                        getGame().getTextureSpriteBatch(),
                        menuItems[i],
                        camPos.getX(),
                        (camPos.getY() - 100) - 35 * i
                );
            }

            getGame().getTextureSpriteBatch().end();
        }
        // show score
        for (Entity entity : getGame().getWorld().getEntities(Player.class)) {
            getGame().getTextureSpriteBatch().begin();
            InventoryPart invPart = entity.getPart(InventoryPart.class);
            ShootingPart shootingPart = entity.getPart(ShootingPart.class);

            if (invPart.getCurrentWeapon() != null) {
                smallFont.drawMultiLine(
                        getGame().getTextureSpriteBatch(),
                        "Ammo: " + String.valueOf(invPart.getCurrentWeapon().getAmmo()),
                        camPos.getX() + 620,
                        camPos.getY() + 230,
                        5f,
                        hAlign.RIGHT
                );
                smallFont.drawMultiLine(
                        getGame().getTextureSpriteBatch(),
                        invPart.getCurrentWeapon().getName(),
                        camPos.getX() + 620,
                        camPos.getY() + 250,
                        5f,
                        hAlign.RIGHT
                );
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
            font.draw(
                    getGame().getTextureSpriteBatch(),
                    "HP: " + String.valueOf(lp.getLife()),
                    camPos.getX() + 500,
                    camPos.getY() + 350
            );
            if (shootingPart.CanShoot() == false) {
                font.draw(
                        getGame().getTextureSpriteBatch(),
                        "Press R to reload!",
                        camPos.getX() - 640,
                        camPos.getY() - 320
                );
            }
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
                    getGame().getTextureSpriteBatch().draw(currentFrame, pp.getX(), pp.getY(), animationTexturePart.getWidth() / 2, animationTexturePart.getHeight() / 2, animationTexturePart.getWidth(), animationTexturePart.getHeight(), animationTexturePart.getScaleX(), animationTexturePart.getScaleY(), pp.getRadians());
                }
            }

        }
        getGame().getTextureSpriteBatch().end();
    }

    @Override
    public void handleInput() {
        if (getGameData().getKeys().isPressed(GameKeys.UP)) {
            if (currentItem > 0) {
                currentItem--;
            }
        }
        if (getGameData().getKeys().isPressed(GameKeys.DOWN)) {
            if (currentItem < menuItems.length - 1) {
                currentItem++;
            }
        }
        if (getGameData().getKeys().isPressed(GameKeys.ENTER)) {
            try {
                select();
            } catch (BundleException ex) {
                System.out.println(ex.getCause());
            }
        }
    }

    private void select() throws BundleException {
        BundleContext ctx = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        // Player
        if (currentItem == 0) {
            Bundle bundle = getBundle(ctx, "OSGIPlayer");

            if (menuItems[currentItem].equals("Player (loaded)")) {
                bundle.stop();
                menuItems[currentItem] = "Player (unloaded)";
            } else {
                menuItems[currentItem] = "Player (loaded)";
                bundle.start();
            }
        } //Enemy
        else if (currentItem == 1) {
            Bundle bundle = getBundle(ctx, "OSGIEnemy");
            if (menuItems[currentItem].equals("Enemy (loaded)")) {
                menuItems[currentItem] = "Enemy (unloaded)";
                bundle.stop();
            } else {
                menuItems[currentItem] = "Enemy (loaded)";
                bundle.start();
            }
        } //Weapons
        else if (currentItem == 2) {
            Bundle bundle = getBundle(ctx, "OSGiWeapon");
            if (menuItems[currentItem].equals("Weapons (loaded)")) {
                menuItems[currentItem] = "Weapons (unloaded)";
                bundle.stop();
            } else {
                menuItems[currentItem] = "Weapons (loaded)";
                bundle.start();
            }
        }
        }
        
    }

    private Bundle getBundle(BundleContext ctx, String symbolicName) {
	for (Bundle b : ctx.getBundles()) {
		if (symbolicName.equals(b.getSymbolicName())) {
			return b;
		}
	}
	return null;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
