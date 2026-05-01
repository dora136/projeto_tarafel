package br.mackenzie;

public class GameState {
    private boolean paused = false;
    private boolean gameOver = false;
    private int score = 0;
    private int lives = 3;
    private int level = 1;
    private int levelDefenses;

    public static final int life_max = 3;
    public static final int defensesAdvance = 5;

    private GameTransitionListener transitionListener;

    public interface GameTransitionListener {
        void onGameOver();

        void onNextLevel(int newLevel);
    }

    public GameState() {
        reset();
    }

    public void setTransitinListener(GameTransitionListener listener) {
        this.transitionListener = listener;
    }

    public void defense() {
        if (gameOver || paused)
            return;

        addScore(1);
        levelDefenses++;
        checkLevelProgress();
    }

    public void goal() {
        if (gameOver || paused)
            return;

        removeLife(1);
        checkGameOver();
    }

    public void checkLevelProgress() {
        if (levelDefenses >= defensesAdvance) {
            level++;
            levelDefenses = 0;

            if (transitionListener != null) {
                transitionListener.onNextLevel(level);
            }
        }

    }

    public void reset() {
        paused = false;
        this.gameOver = false;
        score = 0;
        lives = life_max;
        level = 1;
        gameOver = false;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public void gameOver() {
        gameOver = true;
    }

    public void nextLevel() {
        level++;
    }

    public void loseLife() {
        lives--;
        checkGameOver();
    }

    public void checkGameOver() {
        if (lives <= 0) {
            gameOver();

            if (transitionListener != null) {
                transitionListener.onGameOver();
            }
        }
    }

    private void addScore(int points) {
        this.score += points;
    }

    private void removeLife(int amount) {
        this.lives = Math.max(0, this.lives - amount);
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelDefenses() {
        return levelDefenses;
    }

    
}
