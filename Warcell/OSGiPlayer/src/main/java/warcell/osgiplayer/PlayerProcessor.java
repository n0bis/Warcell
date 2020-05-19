package warcell.osgiplayer;

import com.badlogic.gdx.Gdx;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.GameKeys;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.ScorePart;
import warcell.common.data.entityparts.SquarePart;
import warcell.common.player.Player;
import warcell.common.services.IEntityProcessingService;
import warcell.common.weapon.parts.InventoryPart;
import warcell.common.weapon.parts.ShootingPart;


public class PlayerProcessor implements IEntityProcessingService {
    private PlayerState playerstate;
    
    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Player.class)) {
            
            PositionPart positionPart = entity.getPart(PositionPart.class);
            MovingPart movingPart = entity.getPart(MovingPart.class);
            InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
            ShootingPart shootingPart = entity.getPart(ShootingPart.class);
            AnimationTexturePart animationTexturePart = entity.getPart(AnimationTexturePart.class);
            SquarePart sqp = entity.getPart(SquarePart.class);
            LifePart lifePart = entity.getPart(LifePart.class);
            ScorePart scorePart = entity.getPart(ScorePart.class);
            
            playerstate = PlayerState.IDLE;
            
            sqp.setCentreX(positionPart.getX() + animationTexturePart.getWidth()/2);
            sqp.setCentreY(positionPart.getY() + animationTexturePart.getHeight()/2);
            
            // move the Player
            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.A));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.D));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.W));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.S));
            
            // mouse position
            double mouseX = Gdx.input.getX();
            double mouseY = Gdx.input.getY();
            double newMouseY = inverseYAxis(mouseY, gameData);
            
            // angle between the Mouse and the Player
            double angle = angleBetweenTwoPoints(gameData.getDisplayWidth()/2,gameData.getDisplayHeight()/2 , mouseX, newMouseY);
            positionPart.setRadians((float)angle);
            
            
            // Cycle weapons
            if (gameData.getKeys().isPressed(GameKeys.Q)) {
                inventoryPart.nextWeapon();
            } else if (gameData.getKeys().isPressed(GameKeys.E)) {
                inventoryPart.previousWeapon();
            }
            
            if (movingPart.isMoving()) {
                playerstate = PlayerState.MOVING;
            }
            
            // Shooting
            if (gameData.getKeys().isDown(GameKeys.LM) && inventoryPart.getCurrentWeapon() != null) {
                shootingPart.setIsShooting(true);
                inventoryPart.getCurrentWeapon().shoot(entity, gameData, world);
                playerstate = PlayerState.SHOOTING; 
            }
            
            // process parts
            inventoryPart.process(gameData, entity);
            movingPart.process(gameData, entity);
            positionPart.process(gameData, entity);
            lifePart.process(gameData, entity);
            scorePart.process(gameData, entity);
            
            if (inventoryPart.getCurrentWeapon() != null) {
                changeSprite(playerstate, inventoryPart.getCurrentWeapon().getName(), animationTexturePart);
            }
            
            // Check if dead
            if (lifePart.isDead()) {
                gameData.setFinalScore(scorePart.getScore());
                gameData.setName(scorePart.getName());
                world.removeEntity(entity);
                System.out.println("PLAYER DEAD");
                gameData.setGameOver(true);
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
    
    private void changeSprite(PlayerState ps, String weapon, AnimationTexturePart atp) {
        switch(ps) {
            case IDLE:
                if (weapon.equals("Rifle")) {
                    atp.setSrcPath("RifleIdle.png", 39, 66, 20, 1, 0.09f);
                } else if (weapon.equals("Shotgun")) {
                    atp.setSrcPath("ShotgunIdle.png", 39, 66, 20, 1, 0.09f);
                }
                break;
            case MOVING:
                if (weapon.equals("Rifle")) {
                    atp.setSrcPath("RifleMove.png", 39, 66, 20, 1, 0.09f);
                } else if (weapon.equals("Shotgun")) {
                    atp.setSrcPath("ShotgunMove.png", 39, 66, 20, 1, 0.09f);
                }
                break;
            case SHOOTING:
                if (weapon.equals("Rifle")) {
                    atp.setSrcPath("RifleShoot.png", 39, 66, 3, 1, 0.09f);
                } else if (weapon.equals("Shotgun")) {
                    atp.setSrcPath("ShotgunShoot.png", 39, 66, 3, 1, 0.30f);
                }
                break;
        }
    }
}
