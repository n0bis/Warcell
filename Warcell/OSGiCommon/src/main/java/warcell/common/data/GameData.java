package warcell.common.data;

import com.badlogic.gdx.graphics.OrthographicCamera;
import warcell.common.events.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import warcell.common.services.IGamePluginService;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private List<Event> events = new CopyOnWriteArrayList<>();
    private List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private boolean gameOver = false;
    private int finalScore;
    private String name;
    private float difficultyMultiplier = 0.1f;

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getEvents() {
        return events;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setGamePlugins(List<IGamePluginService> gamePluginList) {
        this.gamePluginList = gamePluginList;
    }

    public List<IGamePluginService> getGamePlugins() {
        return this.gamePluginList;
    }

    public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                r.add(event);
            }
        }

        return r;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the difficulty
     */
    public float getDifficultyMultiplier() {
        return difficultyMultiplier;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficultyMultiplier(float difficulty) {
        this.difficultyMultiplier = difficulty;
    }



}
