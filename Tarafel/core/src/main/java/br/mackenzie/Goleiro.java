package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Goleiro extends GameObject {

    private enum State {
        IDLE, DIVING, DIVE_OVER
    }

    private enum AltTextures{
        IDLE, DIVING, DIVE_OVER
    }

    private State currentState = State.IDLE;
    private Texture diveTexture;
    private Texture fallTexture;
    private float xPosDefault, yPosDefault;
    private float xPosStart;
    private float xPosTarget, yPosTarget;
    private float stateTime = 0f;
    private float diveDuration = 0.5f; // Duração do pulo em segundos
    private float diveDistanceX = 100f; // Distância horizontal do pulo
    private float diveDistanceY = 75f; // Distância vertical do pulo
    private float sideDiveHeight = 35f; // Altura do arco nos pulos laterais
    private Direction diveDirection;
    private boolean diveQueued;
    private static final float SPRITE_SCALE = 0.5f;


    
    public Goleiro(String texturePath, String diveTexturePath, String fallTexturePath, String soundPath) {
        super(texturePath, soundPath);
        this.diveTexture = new Texture(diveTexturePath);
        this.fallTexture = new Texture(fallTexturePath);
        aplicarTextura(texture);
    }

    public Goleiro(String texturePath, String diveTexturePath, String fallTexturePath) {
        super(texturePath);
        this.diveTexture = new Texture(diveTexturePath);
        this.fallTexture = new Texture(fallTexturePath);
        aplicarTextura(texture);
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        switch (currentState) {
            case IDLE:
                // Lógica para estado parado
                break;
            case DIVING:
                // Lógica para estado pulando
                updateDiving();
                break;
            case DIVE_OVER:
                updateDiveOver();
                break;
            default:
                break;
        }
        // Implementar lógica

    }

    public void updateDiving() {
        float progress = stateTime / diveDuration;

        if (progress >= 1f) {
            sprite.setPosition(xPosTarget, yPosTarget);
            stateTime = 0f;
            currentState = State.DIVE_OVER;
            if (diveDirection == Direction.UP) {
                switchTexture(AltTextures.IDLE);
            } else {
                switchTexture(AltTextures.DIVE_OVER);
            }
            return;
        }

        float newX = MathUtils.lerp(xPosStart, xPosTarget, progress);
        float newY = MathUtils.lerp(yPosDefault, yPosTarget, progress);

        if (diveDirection == Direction.LEFT || diveDirection == Direction.RIGHT) {
            newY += MathUtils.sin(progress * MathUtils.PI) * sideDiveHeight;
        }

        sprite.setPosition(newX, newY);
    }

    public void updateDiveOver() {
        if (stateTime >= 1f) { // Tempo para retornar ao estado IDLE
            resetPosition();
            switchTexture(AltTextures.IDLE);
            currentState = State.IDLE;
            stateTime = 0f;
        }
    }

    public void setDefaultPosition(float x, float y) {
        xPosDefault = x;
        yPosDefault = y;
        sprite.setPosition(xPosDefault, yPosDefault);
    }
 
    public void resetPosition() {
        switchTexture(AltTextures.IDLE);
        sprite.setPosition(xPosDefault, yPosDefault);
        xPosTarget = xPosDefault;
        yPosTarget = yPosDefault;
        currentState = State.IDLE;
        diveDirection = null;
        diveQueued = false;
        stateTime = 0f;
    }

    public void switchTexture(AltTextures alt) {
        switch (alt) {
            case IDLE:
                aplicarTextura(texture);
                sprite.setFlip(false, false);
                sprite.setRotation(0f);
                break;
            case DIVING:
                aplicarTextura(diveTexture);
                sprite.setFlip(diveDirection == Direction.LEFT, false);
                sprite.setRotation(diveDirection == Direction.UP ? 90f : 0f);
                break;
            case DIVE_OVER:
                aplicarTextura(fallTexture);
                sprite.setFlip(diveDirection == Direction.LEFT, false);
                sprite.setRotation(0f);
                break;
            default:
                break;
        }  
    }

    private void aplicarTextura(Texture novaTextura) {
        float centerX = sprite.getX() + sprite.getWidth() / 2f;
        float y = sprite.getY();
        sprite.setRegion(novaTextura);
        sprite.setSize(novaTextura.getWidth(), novaTextura.getHeight());
        sprite.setOriginCenter();
        sprite.setScale(SPRITE_SCALE, SPRITE_SCALE);
        sprite.setPosition(centerX - sprite.getWidth() / 2f, y);
    }

    public void dive() {
        if (currentState != State.IDLE) return;
        if (!diveQueued) return; // No input — stay idle, don't animate

        currentState = State.DIVING;
        stateTime = 0f;
        switchTexture(AltTextures.DIVING);

        float defaultCenterX = xPosDefault + texture.getWidth() / 2f;
        xPosStart = defaultCenterX - sprite.getWidth() / 2f;
        sprite.setPosition(xPosStart, yPosDefault);

        if (diveDirection == Direction.LEFT) {
            xPosTarget = defaultCenterX - diveDistanceX - sprite.getWidth() / 2f;
            yPosTarget = yPosDefault;
        } else if (diveDirection == Direction.RIGHT) {
            xPosTarget = defaultCenterX + diveDistanceX - sprite.getWidth() / 2f;
            yPosTarget = yPosDefault;
        } else if (diveDirection == Direction.UP) {
            xPosTarget = defaultCenterX - sprite.getWidth() / 2f;
            yPosTarget = yPosDefault + diveDistanceY;
        }

        diveQueued = false;
    }

    public void queueDive(Direction direction) {
        diveQueued = true;
        diveDirection = direction;
    }

    public Direction getDiveDirection() {
        return diveDirection;
    }

    public boolean isDiving() {
        return currentState == State.DIVING;
    }
    // Necessário implementar detecção de colisão entre a bola e o goleiro
}
