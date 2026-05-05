package br.mackenzie;

import com.badlogic.gdx.math.MathUtils;

public class Bola extends GameObject {

    private enum State {
        IDLE, MOVING, ARRIVED
    }
    private State currentState = State.IDLE;

    private float xPosDefault, yPosDefault;
    private float xPosTarget, yPosTarget;
    private float stateTime = 0f;
    private float moveDuration = 0.6f;
    private float scaleDefault = 1.0f;  // Escala inicial (bola grande, perto do jogador)
    private float scaleTarget  = 0.3f;  // Escala final  (bola pequena, longe no gol)


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
        if (currentState != State.MOVING) return;

        stateTime += deltaTime;
        float progress = Math.min(stateTime / moveDuration, 1f);

        float newX = MathUtils.lerp(xPosDefault, xPosTarget, progress);
        float newY = MathUtils.lerp(yPosDefault, yPosTarget, progress);
        sprite.setPosition(newX, newY);

        float newScale = MathUtils.lerp(scaleDefault, scaleTarget, progress);
        sprite.setScale(newScale);

        if (progress >= 1f) {
            stateTime = 0f;
            currentState = State.ARRIVED;
        }
    }

    public void shoot(String direction) {
        switch (direction) {
            case "left":
                shootLeft();
                break;
            case "right":
                shootRight();
                break;
            case "up":
                shootUp();
                break;
            default:
                break;
        }
    }

    public void shootLeft() {
        if (currentState != State.IDLE) return;
        prepareShoot();
        xPosTarget = xPosDefault - 200f;
        yPosTarget = yPosDefault + 300f;
    }

    public void shootRight() {
        if (currentState != State.IDLE) return;
        prepareShoot();
        xPosTarget = xPosDefault + 200f;
        yPosTarget = yPosDefault + 300f;
    }

    public void shootUp() {
        if (currentState != State.IDLE) return;
        prepareShoot();
        xPosTarget = xPosDefault;
        yPosTarget = yPosDefault + 300f;
    }

    private void prepareShoot() {
        currentState = State.MOVING;
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

    // Necessário implementar detecção de colisão entre a bola e o goleiro
    // Bola ajusta trajetória caso o goleiro defenda
}

