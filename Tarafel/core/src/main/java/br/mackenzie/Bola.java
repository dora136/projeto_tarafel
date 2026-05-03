package br.mackenzie;

public class Bola extends GameObject {
    public Bola(String texturePath, String soundPath) {
        super(texturePath, soundPath);
    }
    
    public Bola(String texturePath) {
        super(texturePath);
    }

    @Override
    public void update(float deltaTime) {
        // Implementar lógica de movimento da bola 

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

    // Bola precisa ficar menor enquanto se move para dar a impressão de que está indo em direção ao gol
    public void shootLeft() {
        // Implementar movimento e som
    }

    public void shootRight() {
        // Implementar movimento e som
    }

    public void shootUp() {
        // Implementar movimento e som
    }

    // Necessário implementar detecção de colisão entre a bola e o goleiro
    // Bola ajusta trajetória caso o goleiro defenda
}

