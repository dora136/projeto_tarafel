package br.mackenzie;

public class GameState {
    public boolean paused = false;
    public boolean gameOver = false;
    public int score = 0;
    public int lives = 3;
    public int level = 1;



    public void reset() {
        paused = false;
        score = 0;
        lives = 3;
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
        }
    }
}
