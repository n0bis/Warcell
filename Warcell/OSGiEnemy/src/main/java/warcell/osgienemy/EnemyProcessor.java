package warcell.osgienemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.List;
import warcell.common.ai.AISPI;
import warcell.common.data.Entity;
import warcell.common.data.GameData;
import warcell.common.data.World;
import warcell.common.data.entityparts.AnimationTexturePart;
import warcell.common.data.entityparts.CollisionPart;
import warcell.common.data.entityparts.LifePart;
import warcell.common.data.entityparts.MovingPart;
import warcell.common.data.entityparts.PositionPart;
import warcell.common.data.entityparts.ScorePart;
import warcell.common.data.entityparts.CirclePart;
import warcell.common.data.entityparts.TiledMapPart;
import warcell.common.enemy.Enemy;
import warcell.common.player.Player;
import warcell.common.services.IEntityProcessingService;

public class EnemyProcessor implements IEntityProcessingService {

    private AISPI ai;
    private TiledMapPart tiledMap;
    private PositionPart playerPos;
    private Sound sound;

    public EnemyProcessor(boolean playSound) {
        this.sound = playSound ? Gdx.audio.newSound(Gdx.files.internal("Audio/zombieDeath.mp3")) : null;
    }

    public EnemyProcessor() {
        this(true);
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            tiledMap = entity.getPart(TiledMapPart.class);
            if (tiledMap != null) {
                ai.startAI(tiledMap);
            }
        }
        for (Entity entity : world.getEntities(Player.class)) {
            playerPos = entity.getPart(PositionPart.class);
        }
        for (Entity entity : world.getEntities(Enemy.class)) {

            PositionPart positionPart = entity.getPart(PositionPart.class);
            MovingPart movingPart = entity.getPart(MovingPart.class);
            CollisionPart collisionPart = entity.getPart(CollisionPart.class);
            CirclePart circlePart = entity.getPart(CirclePart.class);
            AnimationTexturePart animationTexturePart = entity.getPart(AnimationTexturePart.class);
            LifePart lifePart = entity.getPart(LifePart.class);

            List<PositionPart> path = ai.getPath(positionPart, playerPos);

            circlePart.setCentreX(positionPart.getX() + animationTexturePart.getWidth() / 2);
            circlePart.setCentreY(positionPart.getY() + animationTexturePart.getHeight() / 2);

            if (!path.isEmpty()) {
                double angle = getAngle(positionPart, path.get(0));
                positionPart.setRadians((float) angle);

                if ((angle > 315 && angle < 360) || (angle < 45 && angle > 0)) {
                    movingPart.setRight(true);
                    movingPart.setUp(false);
                    movingPart.setLeft(false);
                    movingPart.setDown(false);
                } else if ((angle >= 45 && angle < 135)) {
                    movingPart.setUp(true);
                    movingPart.setRight(false);
                    movingPart.setLeft(false);
                    movingPart.setDown(false);
                } else if ((angle >= 135 && angle < 225)) {
                    movingPart.setLeft(true);
                    movingPart.setRight(false);
                    movingPart.setDown(false);
                    movingPart.setUp(false);
                } else if ((angle >= 225 && angle < 315)) {
                    movingPart.setDown(true);
                    movingPart.setRight(false);
                    movingPart.setLeft(false);
                    movingPart.setUp(false);
                }
            }

            // Process parts
            movingPart.process(gameData, entity);
            positionPart.process(gameData, entity);
            collisionPart.process(gameData, entity);
            circlePart.process(gameData, entity);
            lifePart.process(gameData, entity);

            // Check if dead
            if (lifePart.isDead()) {
                world.removeEntity(entity);
                if (sound != null) {
                    sound.play(0.4f);
                }
                for (Entity player : world.getEntities(Player.class)) {
                    ScorePart sp = player.getPart(ScorePart.class);
                    int score = sp.getScore();
                    sp.setScore(score += 10);
                }
            }

            movingPart.setRight(false);
            movingPart.setLeft(false);
            movingPart.setUp(false);
            movingPart.setDown(false);

        }
    }

    private double getAngle(PositionPart source, PositionPart target) {
        double angle = Math.toDegrees(Math.atan2(target.getY() - source.getY(), target.getX() - source.getX()));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    private double distance(PositionPart source, PositionPart target) {
        return (float) Math.sqrt(Math.pow(target.getX() - source.getX(), 2) + Math.pow(target.getY() - source.getY(), 2));
    }

    /**
     * Declarative service set AI service
     *
     * @param ai AI service
     */
    public void setAIService(AISPI ai) {
        this.ai = ai;

    }

    /**
     * Declarative service remove AI service
     *
     * @param ai AI service
     */
    public void removeAIService(AISPI ai) {
        this.ai = null;
    }
}
