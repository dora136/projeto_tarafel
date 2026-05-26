package br.mackenzie;

import com.badlogic.gdx.math.MathUtils;

public class Bola extends GameObject {

    private enum State {
        IDLE, SLOWMOTION, MOVING, ARRIVED, RETURNING
    }

    private State currentState = State.IDLE;

    private float xPosDefault, yPosDefault;
    private float xPosTarget, yPosTarget;
    private float stateTime = 0f;
    private float moveDuration = 0.6f;
    private float scaleDefault = 0.5f;  // Escala inicial (bola grande, perto do jogador)
    private float scaleTarget  = 0.2f;  // Escala final  (bola pequena, longe no gol)
    private float slowMotionSpeed = 0.2f; // Quão lento é o slow motion (20% da velocidade normal)

    private Direction direction;

    public Bola(String texturePath, String soundPath) {
        super(texturePath, soundPath);
        sprite.setScale(scaleDefault);
    }
    
    public Bola(String texturePath) {
        super(texturePath);
        sprite.setScale(scaleDefault);
    }

    @Override
    public void update(float deltaTime) {
        if (currentState == State.IDLE || currentState == State.ARRIVED) return;

        if (currentState == State.RETURNING) {
            updateReturning(deltaTime);
            return;
        }

        float speed = (currentState == State.SLOWMOTION) ? slowMotionSpeed : 1f;
        stateTime += deltaTime * speed;

        float progress = Math.min(stateTime / moveDuration, 1f);
        sprite.setPosition(MathUtils.lerp(xPosDefault, xPosTarget, progress),
                           MathUtils.lerp(yPosDefault, yPosTarget, progress));
        sprite.setScale(MathUtils.lerp(scaleDefault, scaleTarget, progress));

        // Quando a bola passou o trecho inicial, sai do slow motion
        if (currentState == State.SLOWMOTION && progress >= slowMotionBound) {
            currentState = State.MOVING;
        }

        if (progress >= 1f) {
            stateTime = 0f;
            currentState = State.ARRIVED;
        }
    }

    private void updateReturning(float deltaTime) {
        stateTime += deltaTime;
        float progress = Math.min(stateTime / moveDuration, 1f);
        sprite.setPosition(MathUtils.lerp(xPosTarget, xPosDefault, progress),
                           MathUtils.lerp(yPosTarget, yPosDefault, progress));
        sprite.setScale(MathUtils.lerp(scaleTarget, scaleDefault, progress));
        if (progress >= 1f) {
            resetPosition();
        }
    }

    /** Called by PenaltyController when reaction time expires — ball transitions to full speed. */
    public void endSlowMotion() {
        if (currentState == State.SLOWMOTION) {
            currentState = State.MOVING;
        }
    }

    /** Called by PenaltyController on a save — ball retraces its path back to origin. */
    public void deflect() {
        if (currentState == State.ARRIVED) {
            currentState = State.RETURNING;
            stateTime = 0f;
        }
    }

    public void shoot(Direction dir) {
        switch (dir) {
            case LEFT:  shootLeft();  break;
            case RIGHT: shootRight(); break;
            case UP:    shootUp();    break;
        }
    }

    public void shootLeft() {
        if (currentState != State.IDLE) return;
        direction = Direction.LEFT;
        prepareShoot();
        xPosTarget = xPosDefault - 185f; //185
        yPosTarget = yPosDefault + 100f; //210
    }

    public void shootRight() {
        if (currentState != State.IDLE) return;
        direction = Direction.RIGHT;
        prepareShoot();
        xPosTarget = xPosDefault + 185f; //185
        yPosTarget = yPosDefault + 100f; //212
    }

    public void shootUp() {
        if (currentState != State.IDLE) return;
        direction = Direction.UP;
        prepareShoot();
        xPosTarget = xPosDefault;
        yPosTarget = yPosDefault + 225f; //300
    }

    private void prepareShoot() {
        currentState = State.SLOWMOTION;
        stateTime = 0f;
    }

    public void setDefaultPosition(float x, float y) {
        xPosDefault = x;
        yPosDefault = y;
        sprite.setPosition(x, y);
    }

    public void resetPosition() {
        sprite.setPosition(xPosDefault, yPosDefault);
        sprite.setScale(scaleDefault);
        currentState = State.IDLE;
        stateTime = 0f;
    }

    public boolean isArrived() {
        return currentState == State.ARRIVED;
    }

    public boolean isIdle() {
        return currentState == State.IDLE;
    }

    public Direction getDirection() {
        return direction;
    }

    // Implementar método que sinaliza para a bola que foi defendida e ajusta trajetória 

    // Necessário implementar detecção de colisão entre a bola e o goleiro
    // Bola ajusta trajetória caso o goleiro defenda
}
