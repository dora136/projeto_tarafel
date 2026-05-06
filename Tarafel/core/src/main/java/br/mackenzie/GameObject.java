package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;


public abstract class GameObject {
    protected Texture texture;
    protected Sprite sprite;
    protected Sound sound;

    public GameObject(Texture texture) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        sprite.setOriginCenter();
        sprite.setScale(1, 1);
    }

    public GameObject(String texturePath) {
        this.texture = new Texture(texturePath);
        this.sprite = new Sprite(texture);
        sprite.setOriginCenter();
        sprite.setScale(1, 1);
    }

    public GameObject(String texturePath, String soundPath) {
        this.texture = new Texture(texturePath);
        this.sprite = new Sprite(texture);
        setSound(soundPath);
        sprite.setOriginCenter();
        sprite.setScale(1, 1);
    }

    public void setSound(String soundPath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
    }

    public abstract void update(float deltaTime);

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void playSound() {
        sound.play();
    }

    public Sprite getSprite() {
        return sprite;
    }

}