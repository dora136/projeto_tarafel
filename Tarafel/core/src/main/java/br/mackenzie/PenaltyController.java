package br.mackenzie;

public class PenaltyController {
    private Goleiro goleiro;
    private Bola bola;
    private InputController inputController;
    private float playerReactionTime = 0.8f; // Tempo em segundos para o jogador reagir enquanto a bola se mexe em slow-motion
    // Ajustar o valor acima torna o jogo mais fácil ou difícl
    // Tempo precisa ser passado para a bola (tempo em slow-mo) e para o InputController (tempo que o jogador pode realizar inputs)
    // Timer interno muda se o goleiro pode ou não pular

    private float currentTime = 0f;


    public PenaltyController(Goleiro goleiro, Bola bola) {
        this.goleiro = goleiro;
        this.bola = bola;
        this.inputController = new InputController(goleiro);
    }

        
}
