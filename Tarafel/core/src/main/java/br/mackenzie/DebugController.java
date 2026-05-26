package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DebugController {
    private final PenaltyController penaltyController;
    private final GameState gameState;

    public DebugController(PenaltyController penaltyController, GameState gameState) {
        this.penaltyController = penaltyController;
        this.gameState = gameState;
    }

    public void update() {
        // F: fire next penalty immediately, skipping the wait timer
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            penaltyController.triggerNow();
        }

        // R: reset game state and restart the penalty loop
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            gameState.reset();
            penaltyController.start();
        }
    }
}
