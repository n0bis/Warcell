/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.core.managers;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import warcell.common.data.entityparts.AnimationTexturePart;

/**
 *
 * @author madsfalken
 */
public class GameAssetManager {
    private Map<String, Animation> animationMap;
    private Map<String, Texture> textureMap;


    public GameAssetManager() {
        animationMap = new HashMap<>();
        textureMap = new HashMap<>();
    }
    
    public Texture getTexture(Class objectClass, String path) {
        Texture texture = textureMap.get(path);

        if (texture == null) {

            InputStream is = objectClass.getClassLoader().getResourceAsStream(
                    path
            );
            try {
                if (is == null)
                    return null;
                
                Gdx2DPixmap gmp = new Gdx2DPixmap(is, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
                Pixmap pix = new Pixmap(gmp);
                texture = new Texture(pix);
                textureMap.put(path, texture);
                pix.dispose();
                is.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return texture;
    }
    
    public Animation getAnimation(Class objectClass, AnimationTexturePart animationTexture) {
        Animation animation = animationMap.get(animationTexture.getSrcPath());
        
        if (animation == null) {
            InputStream is = objectClass.getClassLoader().getResourceAsStream(
                    animationTexture.getSrcPath()
            );
            
            try {
                if (is == null)
                    return null;
                
                Gdx2DPixmap gmp = new Gdx2DPixmap(is, Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
                Pixmap pix = new Pixmap(gmp);
                Texture texture = new Texture(pix);
                TextureRegion textureRegion = new TextureRegion(texture);
                TextureRegion[][] tmp = textureRegion.split(texture.getWidth() / animationTexture.getFrameCols(), texture.getHeight() / animationTexture.getFrameRows());
                TextureRegion[] walkFrames = new TextureRegion[animationTexture.getFrameCols() * animationTexture.getFrameRows()];
                int index = 0;
                for (int i = 0; i < animationTexture.getFrameRows(); i++) {
                    for (int j = 0; j < animationTexture.getFrameCols(); j++) {
                        walkFrames[index++] = tmp[i][j];
                    }
                }
                animation = new Animation(animationTexture.getFrameInterval(), walkFrames);
                animation.setPlayMode(Animation.PlayMode.LOOP);
                animationMap.put(animationTexture.getSrcPath(), animation);
                
                pix.dispose();
                is.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        return animation;
    }
}
