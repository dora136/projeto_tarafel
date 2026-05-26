package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputController {
    private Goleiro goleiro;
    private boolean keeperActionable;

    public InputController(Goleiro goleiro) {
        this.goleiro = goleiro;
        this.keeperActionable = false;
    }

    public void update() {
        if (keeperActionable) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                goleiro.queueDive(Direction.LEFT);
                keeperActionable = false; // Impede múltiplos pulos
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                goleiro.queueDive(Direction.RIGHT);
                keeperActionable = false; // Impede múltiplos pulos
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                goleiro.queueDive(Direction.UP);
                keeperActionable = false; // Impede múltiplos pulos
            }
        }
    }

    public void keeperActionable(boolean actionable) {
        keeperActionable = actionable;
    }

    public boolean isKeeperActionable() {
        return keeperActionable;
    }


}
