package warcell.osgiplayer;

import com.badlogic.gdx.Gdx;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.GameKeys;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.player.Player;
import warcell.common.services.IEntityProcessingService;
import warcell.common.weapon.parts.InventoryPart;


public class PlayerProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Player.class)) {
            
            PositionPart positionPart = entity.getPart(PositionPart.class);
            MovingPart movingPart = entity.getPart(MovingPart.class);
            InventoryPart inventoryPart = entity.getPart(InventoryPart.class);

            inventoryPart.process(gameData, entity);
            AnimationTexturePart animationTexturePart = entity.getPart(AnimationTexturePart.class);
            
            // move the Player
            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
            
            // mouse position
            double mouseX = Gdx.input.getX();
            double mouseY = Gdx.input.getY();
            double newMouseY = inverseYAxis(mouseY, gameData);
            
            
            // angle between the Mouse and the Player
            double angle = angleBetweenTwoPoints((positionPart.getX() + animationTexturePart.getWidth()/2), (positionPart.getY() + animationTexturePart.getHeight()/2), mouseX, newMouseY);
            positionPart.setRadians((float)angle);
            
            movingPart.process(gameData, entity);
            positionPart.process(gameData, entity);
            
            //Cycle weapons
            if (gameData.getKeys().isDown(GameKeys.Q)) {
                inventoryPart.nextWeapon();
                System.out.println("q");
            } else if (gameData.getKeys().isDown(GameKeys.E)) {
                System.out.println("e");
                inventoryPart.previousWeapon();
            }
            
            if (gameData.getKeys().isDown(GameKeys.SPACE) && inventoryPart.getCurrentWeapon() != null) {
                inventoryPart.getCurrentWeapon().shoot(entity, gameData, world);
                System.out.println("pew");
            }
        }
    }
    /**
     * Calculates the angle between 2 points
     * @param originX the x value of the origin
     * @param originY the y value of the origin
     * @param compareX the x value of the compared point
     * @param compareY the y value of the compare point
     * @return the angle between two points 
     */
    private double angleBetweenTwoPoints (double originX, double originY, double compareX, double compareY) {
        double dx = compareX - originX;
        double dy = compareY - originY;
        
        double angle = Math.toDegrees(Math.atan2(dy, dx) - 90*Math.PI/180);
        
        if (angle < 0 ) {
            angle += 360;
        }
        return angle;
    }
    
    /**
     * Inverses the y-axis, since the mouse coordinates are reversed
     * @param originalPoint the point that gets reversed
     * @param gameData the GameData object of the game class
     * @return the new inverted point on the y axis
     */
    private double inverseYAxis (double originalPoint, GameData gameData) {
        double newPoint = 0;
        
        if (originalPoint < gameData.getDisplayHeight()/2) {
        newPoint = gameData.getDisplayHeight() - originalPoint ;
            } else if (originalPoint > gameData.getDisplayHeight()/2) {
                newPoint = Math.abs(originalPoint - gameData.getDisplayHeight());
            } else if (originalPoint == 0) {
                newPoint = gameData.getDisplayHeight();
            }
        return newPoint;
    }
}
