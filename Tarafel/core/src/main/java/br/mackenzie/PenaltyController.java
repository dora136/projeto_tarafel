package br.mackenzie;

public class PenaltyController {

    public interface PenaltyListener {
        void onSave();
        void onGoal();
    }

    public enum PenaltyResult {
        NULL, GOAL, SAVE
    }

    private enum PenaltyState {
        WAITING, SLOW_MOTION, IN_PROGRESS
    }

    private final Goleiro goleiro;
    private final Bola bola;
    private final GameState gameState;
    private final InputController inputController;
    private PenaltyListener listener;

    private PenaltyState state = PenaltyState.WAITING;
    private PenaltyResult lastResult = PenaltyResult.NULL;
    private float currentTime = 0f;
    private float waitTimer = 0f;

    public PenaltyController(Goleiro goleiro, Bola bola, GameState gameState) {
        this.goleiro = goleiro;
        this.bola = bola;
        this.gameState = gameState;
        this.inputController = new InputController(goleiro);
    }

    public void setListener(PenaltyListener listener) {
        this.listener = listener;
    }

    /** Call once to begin the penalty loop after a game start/reset. */
    public void start() {
        goleiro.resetPosition();
        bola.resetPosition();
        inputController.keeperActionable(false);
        lastResult = PenaltyResult.NULL;
        waitTimer = 1.0f;
        currentTime = 0f;
        state = PenaltyState.WAITING;
    }

    public void update(float deltaTime) {
        if (gameState.isGameOver()) {
            goleiro.update(deltaTime);
            return;
        }

        goleiro.update(deltaTime);
        bola.update(deltaTime);

        switch (state) {
            case WAITING:
                waitTimer -= deltaTime;
                if (waitTimer <= 0f) {
                    shootPenalty();
                }
                break;
            case SLOW_MOTION:
            case IN_PROGRESS:
                updatePenalty(deltaTime);
                break;
        }
    }

    private void shootPenalty() {
        goleiro.resetPosition();
        bola.resetPosition();
        inputController.keeperActionable(true);
        bola.shoot(RandomPicker.pickRandom(Direction.class));
        currentTime = 0f;
        state = PenaltyState.SLOW_MOTION;
    }

    private void updatePenalty(float deltaTime) {
        currentTime += deltaTime;

        if (state == PenaltyState.SLOW_MOTION) {
            inputController.update();
            if (currentTime >= getReactionTime()) {
                inputController.keeperActionable(false);
                goleiro.dive();
                bola.endSlowMotion(); // keeper dives and ball accelerates at the same moment
                state = PenaltyState.IN_PROGRESS;
            }
        }

        if (state == PenaltyState.IN_PROGRESS && bola.isArrived()) {
            resolveResult();
        }
    }

    private void resolveResult() {
        boolean saved = goleiro.getDiveDirection() != null
            && goleiro.getDiveDirection() == bola.getDirection();

        if (saved) {
            lastResult = PenaltyResult.SAVE;
            bola.deflect();
            gameState.defense();
            if (listener != null) listener.onSave();
        } else {
            lastResult = PenaltyResult.GOAL;
            gameState.goal();
            if (listener != null) listener.onGoal();
        }

        waitTimer = getWaitInterval();
        state = PenaltyState.WAITING;
    }

    /** Reaction window shrinks from 0.8s (level 1) to 0.5s (level 4). */
    private float getReactionTime() {
        int level = Math.min(gameState.getLevel(), 4);
        return 0.8f - (level - 1) * 0.1f;
    }

    /** Wait interval between kicks shrinks from 1.2s (level 1) to 0.6s (level 4). */
    private float getWaitInterval() {
        int level = Math.min(gameState.getLevel(), 4);
        return 1.2f - (level - 1) * 0.2f;
    }

    /** Skips the current wait timer and fires the next penalty immediately. */
    public void triggerNow() {
        if (state == PenaltyState.WAITING) {
            waitTimer = 0f;
        }
    }

    public PenaltyResult getLastResult() {
        return lastResult;
    }
}
