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
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
    
    public Texture checkGetTexture(Class objectClass, String path) {
        Texture texture = textureMap.get(path);

        if (texture == null) {

            InputStream is = objectClass.getClassLoader().getResourceAsStream(
                    path
            );
            try {
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
}
