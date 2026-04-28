package br.mackenzie;

public class Goleiro extends GameObject {
    public Goleiro(String texturePath, String soundPath) {
        super(texturePath, soundPath);
    }

    @Override
    public void update() {

    }

    public void dive(String direction) {
        switch (direction) {
            case "left":
                diveLeft();
                break;
            case "right":
                diveRight();
                break;
            case "up":
                diveUp();
                break;
            default:
                break;
        }
    }

    public void diveLeft() {
        // Implementar movimento e som
    }

    public void diveRight() {
        // Implementar movimento e som
    }

    public void diveUp() {
        // Implementar movimento e som
    }

    // Necessário implementar detecção de colisão entre a bola e o goleiro
}
