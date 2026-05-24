package br.mackenzie;

public class PenaltyController {
    private Goleiro goleiro;
    private Bola bola;
    private InputController inputController;
    private float playerReactionTime = 0.8f; // Tempo em segundos para o jogador reagir enquanto a bola se mexe em slow-motion
    // Ajustar o valor acima torna o jogo mais fácil ou difícl
    // Tempo precisa ser passado para a bola (tempo em slow-mo) e para o InputController (tempo que o jogador pode realizar inputs)
    // Timer interno determina se o goleiro pode ou não pular

    private float penaltyTravelTime = 2.0f; // Tempo em segundos que a bola leva para chegar ao gol
    private float totalPenaltyTime = playerReactionTime + penaltyTravelTime + 1f; // Tempo total do penalty, incluindo reação do goleiro, bola em movimento, e finalização
    private float currentTime = 0f;
    private boolean penaltyInProgress = false;

    private enum PenaltyResult {
        NULL, GOAL, SAVE
    }
    private PenaltyResult penaltyResult = PenaltyResult.NULL;

    private enum PenaltyState {
        NOT_STARTED, SLOW_MOTION, IN_PROGRESS, ENDING, DONE
    }
    private PenaltyState penaltyState = PenaltyState.NOT_STARTED;


    public PenaltyController(Goleiro goleiro, Bola bola) {
        this.goleiro = goleiro;
        this.bola = bola;
        this.inputController = new InputController(goleiro);
    }

    public void penalty() {
        goleiro.resetPosition();
        bola.resetPosition();
        // Goleiro acionável
        inputController.keeperActionable(true);
        // Bola chuta direção aleatória
        bola.shoot(RandomPicker.pickRandom(Direction.class));
        currentTime = 0f; // Reinicia o timer
        penaltyInProgress = true;

        penaltyResult = PenaltyResult.NULL;
        penaltyState = PenaltyState.SLOW_MOTION; // Inicia o penalty em slow-motion
    }

    public void update(float deltaTime) {
        if (penaltyInProgress) {
            updatePenalty(deltaTime);
        }
    }

    public void updatePenalty(float deltaTime) {
        currentTime += deltaTime;
        bola.update(deltaTime);
        goleiro.update(deltaTime);
        if (penaltyState == PenaltyState.SLOW_MOTION) {
            inputController.update();
            if (currentTime >= playerReactionTime) {
                inputController.keeperActionable(false); // Goleiro não pode mais reagir
                goleiro.dive(); // Executa o pulo quando a bola terminar o slow-motion
                penaltyState = PenaltyState.IN_PROGRESS; // Bola continua se movendo normalmente
            }
        }
        if (currentTime >= penaltyTravelTime + playerReactionTime && penaltyState == PenaltyState.IN_PROGRESS) {
            penaltyState = PenaltyState.ENDING; // Finaliza o penalty
            if (goleiro.isDiving() && goleiro.getDiveDirection() == bola.getDirection()) {
                // Goleiro defendeu
                penaltyResult = PenaltyResult.SAVE;
                // Bola deve ajustar trajetória para simular a defesa
                // Som de defesa
            } else {
                // Gol
                penaltyResult = PenaltyResult.GOAL;
                // Bola segue trajetória normal
                // Som de gol
            }
        }
        if (currentTime >= totalPenaltyTime && penaltyState == PenaltyState.ENDING) {
            penaltyInProgress = false;
            penaltyState = PenaltyState.DONE; // Penalty finalizado, resultado definido
        }

    }

    public PenaltyResult getPenaltyResult() {
        return penaltyResult;
    }
}
