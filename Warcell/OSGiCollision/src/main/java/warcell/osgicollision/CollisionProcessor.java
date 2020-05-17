/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcell.osgicollision;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.BulletMovingPart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.DamagePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.SquarePart;
import warcell.common.data.entityparts.TiledMapPart;
import warcell.common.services.IPostEntityProcessingService;
import warcell.common.enemy.Enemy;
import warcell.common.map.Tile;
import warcell.common.player.Player;
import warcell.common.weapon.entities.Bullet;

/**
 *
 * @author Jonas
 */
public class CollisionProcessor implements IPostEntityProcessingService {

    float timer1 = 0;
    float timer2 = 0;
    private Map map;
    MapObjects objects;
    MapObjects border;

    @Override
    public void process(GameData gameData, World world) {
        if (world == null) {
            throw new IllegalArgumentException("World is null");
        }
        if (map == null) {
            for (Entity tile : world.getEntities(Tile.class)) {
                TiledMapPart tiledMap = tile.getPart(TiledMapPart.class);
                map = new TmxMapLoader().load(tiledMap.getSrcPath());
                objects = map.getLayers().get("objectlayer").getObjects();
                border = map.getLayers().get("border").getObjects();
            }
        } else {
            for (Entity entity1 : world.getEntities()) {
                for (Entity entity2 : world.getEntities(Enemy.class)) {
                    if (!entity1.equals(entity2)) {
                        if (hasCollided(entity1, entity2)) {
                            CollisionPart collisionPart1 = entity1.getPart(CollisionPart.class);
                            CollisionPart collisionPart2 = entity2.getPart(CollisionPart.class);
                            LifePart lp1 = entity1.getPart(LifePart.class);
                            LifePart lp2 = entity2.getPart(LifePart.class);
                            DamagePart dp1 = entity1.getPart(DamagePart.class);
                            DamagePart dp2 = entity2.getPart(DamagePart.class);

                            if (timer1 > collisionPart1.getMinTimeBetweenCollision()) {
                                lp2.takeDamage(dp1.getDamage());
                                timer1 = 0;
                            }

                            if (timer2 > collisionPart2.getMinTimeBetweenCollision()) {
                                lp1.takeDamage(dp2.getDamage());
                            }
                        }
                        timer1 += gameData.getDelta();
                        timer2 += gameData.getDelta();
                    }
                }
            }

            for (Entity player : world.getEntities(Player.class)) {
                MovingPart movingPart = player.getPart(MovingPart.class);
                PositionPart positionPart = player.getPart(PositionPart.class);
                
                for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
                    Rectangle rectangle = rectangleObject.getRectangle();
                    Rectangle playerRectangle = new Rectangle(positionPart.getX(), positionPart.getY(), 35, 35);
                    
                    if (Intersector.overlaps(rectangle, playerRectangle)) {
                        movingPart.setIsInWalls(true);
                    }
                }
                for (RectangleMapObject rectangleObject : border.getByType(RectangleMapObject.class)) {
                    Rectangle rectangle = rectangleObject.getRectangle();
                    Rectangle playerRectangle = new Rectangle(positionPart.getX(), positionPart.getY(), 35, 35);
                    
                    if (Intersector.overlaps(rectangle, playerRectangle)) {
                        movingPart.setIsInWalls(true);
                    }
                }
                if (!movingPart.isIsInWalls()) {
                    movingPart.setLastX(positionPart.getX());
                    movingPart.setLastY(positionPart.getY());
                }
            }
            for (Entity bullet : world.getEntities(Bullet.class)) {
                BulletMovingPart movingPart = bullet.getPart(BulletMovingPart.class);
                PositionPart positionPart = bullet.getPart(PositionPart.class);
                
                for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
                    Rectangle rectangle = rectangleObject.getRectangle();
                    Rectangle playerRectangle = new Rectangle(positionPart.getX(), positionPart.getY(), 5, 5);
                    
                    if (Intersector.overlaps(rectangle, playerRectangle)) {
                        world.removeEntity(bullet);
                        
                    }
                }
            }
        }

    }

    private boolean hasCollided(Entity entity1, Entity entity2) {
        SquarePart squarePart1 = entity1.getPart(SquarePart.class);
        SquarePart squarePart2 = entity2.getPart(SquarePart.class);

        //no collision if no circle part
        if (squarePart1 == null || squarePart2 == null || entity1.getClass().equals(entity2.getClass())) {
            return false;
        }
        float distX = squarePart1.getCentreX() - squarePart2.getCentreX();
        float distY = squarePart1.getCentreY() - squarePart2.getCentreY();
        float distance = (float) (Math.sqrt(distX * distX + distY * distY));
        return distance < (squarePart1.getRadius() + squarePart2.getRadius());

    }
}
