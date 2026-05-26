package br.mackenzie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private enum Tela {
        MENU, COMO_JOGAR, PONTUACAO, JOGO
    }

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private BitmapFont font;
    private GlyphLayout layout;
    private Texture fundoMenu;
    private Texture[] fundosJogo;
    private Texture vidaLuva;
    private Texture pontuacaoMais10;
    private Texture goleiroParado;
    private Texture goleiroPulando;
    private Goleiro goleiro;
    private Bola bola;
    private GameState gameState;
    private Music musicaAtual;
    private ComoJogarView comoJogarView;
    private Tela telaAtual = Tela.MENU;
    private String somAtual;
    private PontuacaoView pontuacaoView;

    private PenaltyController penaltyController;
    private DebugController debugController;
    private Botao botaoIniciar;
    private Botao botaoComoJogar;
    private Botao botaoPontuacao;
    private float tempoAnimacao = 0;
    private float tempoMais10 = 0f;
    private float tempoMensagem = 0f;
    private String mensagemJogo = "";

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        font = new BitmapFont();
        layout = new GlyphLayout();
        fundoMenu = new Texture("img/menu_gol.png");
        fundosJogo = new Texture[] {
            new Texture("img/gol_facil.png"),
            new Texture("img/gol_medio.png"),
            new Texture("img/gol_dificil.png"),
            new Texture("img/gol_impossivel.png")
        };
        vidaLuva = new Texture("img/vida_luva_cinza.png");
        pontuacaoMais10 = new Texture("img/pontuacao_mais10.png");
        goleiroParado = new Texture("img/goleiro.png");
        goleiroPulando = new Texture("img/goleiro_pulando.png");
        goleiro = new Goleiro("img/goleiro.png", "img/goleiro_pulando.png", "img/goleiro_caido.png");
        float centerX = Gdx.graphics.getWidth() / 2f;
        goleiro.setDefaultPosition(centerX - goleiro.getSprite().getWidth() / 2f, 100);
        bola = new Bola("img/bola.png");
        bola.setDefaultPosition(centerX - bola.getSprite().getWidth() / 2f, 8);
        gameState = new GameState();
        penaltyController = new PenaltyController(goleiro, bola, gameState);
        penaltyController.setListener(new PenaltyController.PenaltyListener() {
            @Override public void onSave() { mostrarMensagem("DEFESA!"); }
            @Override public void onGoal() { mostrarMensagem("GOL!"); }
        });
        debugController = new DebugController(penaltyController, gameState);
        comoJogarView = new ComoJogarView();
        pontuacaoView = new PontuacaoView();

        font.getData().setScale(1.4f);

        botaoIniciar = new Botao(220, 230, 200, 45, "INICIAR", "Botao iniciar clicado");
        botaoComoJogar = new Botao(220, 170, 200, 45, "COMO JOGAR", "Botao como jogar clicado");
        botaoPontuacao = new Botao(220, 110, 200, 45, "PONTUACAO", "Botao pontuacao clicado");
    }

    @Override
    public void render() {
        verificarClique();
        float deltaTime = Gdx.graphics.getDeltaTime();
        tempoAnimacao += deltaTime;

        ScreenUtils.clear(0.1f, 0.45f, 0.2f, 1f);

        if (telaAtual == Tela.JOGO) {
            atualizarJogo(deltaTime);
            desenharJogo();
            return;
        }

        batch.begin();
        batch.draw(fundoMenu, 0, 0, 640, 480);
        batch.end();

        if (telaAtual == Tela.COMO_JOGAR) {
            comoJogarView.desenhar(shape, batch, font, layout);
        } else if (telaAtual == Tela.PONTUACAO) {
            pontuacaoView.desenhar(shape, batch, font, layout);
        } else {
            desenharMenu();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shape.dispose();
        font.dispose();
        fundoMenu.dispose();
        for (Texture fundo : fundosJogo) {
            fundo.dispose();
        }
        vidaLuva.dispose();
        pontuacaoMais10.dispose();
        goleiroParado.dispose();
        goleiroPulando.dispose();
        pararMusica();
    }

    private void verificarClique() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (telaAtual == Tela.MENU) {
                Gdx.app.exit();
            } else {
                telaAtual = Tela.MENU;
                pararMusica();
            }
            return;
        }

        if (!Gdx.input.justTouched()) {
            return;
        }

        if (telaAtual != Tela.MENU) {
            return;
        }

        float mouseX = Gdx.input.getX();
        float mouseY = 480 - Gdx.input.getY();

        if (botaoIniciar.contem(mouseX, mouseY)) {
            iniciarJogo();
            botaoIniciar.clicar();
        } else if (botaoComoJogar.contem(mouseX, mouseY)) {
            telaAtual = Tela.COMO_JOGAR;
            botaoComoJogar.clicar();
        } else if (botaoPontuacao.contem(mouseX, mouseY)) {
            telaAtual = Tela.PONTUACAO;
            botaoPontuacao.clicar();
        }
    }

    private void desenharMenu() {
        desenharTitulo("TARAFEL", 355);
        desenharGoleiro();
        botaoIniciar.desenhar(shape, batch, font, layout);
        botaoComoJogar.desenhar(shape, batch, font, layout);
        botaoPontuacao.desenhar(shape, batch, font, layout);
    }

    private void desenharJogo() {
        batch.begin();
        batch.draw(getFundoNivel(), 0, 0, 640, 480);
        goleiro.draw(batch);
        bola.draw(batch);
        desenharHud();
        batch.end();

        if (gameState.isGameOver()) {
            desenharGameOver();
        }
    }

    private void atualizarJogo(float deltaTime) {
        if (gameState.isGameOver()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                iniciarJogo();
            }
            return;
        }

        penaltyController.update(deltaTime);
        debugController.update();
        atualizarTempos(deltaTime);
        tocarMusicaNivel();
    }

    private void iniciarJogo() {
        telaAtual = Tela.JOGO;
        gameState.reset();
        tempoMais10 = 0f;
        tempoMensagem = 3.0f;
        mensagemJogo = "Defenda com as setas";
        penaltyController.start();
        tocarMusicaNivel();
    }

    private void atualizarTempos(float deltaTime) {
        if (tempoMais10 > 0) {
            tempoMais10 -= deltaTime;
        }

        if (tempoMensagem > 0) {
            tempoMensagem -= deltaTime;
        }
    }

    private void mostrarMensagem(String texto) {
        mensagemJogo = texto;
        tempoMensagem = 1.0f;
    }

    private Texture getFundoNivel() {
        int indice = Math.min(gameState.getLevel(), fundosJogo.length) - 1;
        return fundosJogo[indice];
    }

    private String getNomeNivel() {
        switch (Math.min(gameState.getLevel(), 4)) {
            case 1:
                return "FACIL";
            case 2:
                return "MEDIO";
            case 3:
                return "DIFICIL";
            default:
                return "IMPOSSIVEL";
        }
    }

    private String getSomNivel() {
        switch (Math.min(gameState.getLevel(), 4)) {
            case 1:
                return "som/som_facil.wav";
            case 2:
                return "som/som_medio.wav";
            case 3:
                return "som/som_dificil.wav";
            default:
                return "som/som_impossivel.wav";
        }
    }

    private void tocarMusicaNivel() {
        String somNivel = getSomNivel();

        if (somNivel.equals(somAtual)) {
            return;
        }

        if (musicaAtual != null && musicaAtual.isPlaying()) {
            musicaAtual.stop();
            musicaAtual.dispose();
        }

        somAtual = somNivel;
        musicaAtual = Gdx.audio.newMusic(Gdx.files.internal(somNivel));
        musicaAtual.setLooping(true);
        musicaAtual.setVolume(0.25f);
        musicaAtual.play();
    }

    private void pararMusica() {
        if (musicaAtual != null) {
            musicaAtual.stop();
            musicaAtual.dispose();
            musicaAtual = null;
            somAtual = null;
        }
    }

    private void desenharHud() {
        font.getData().setScale(1.05f);
        font.setColor(Color.WHITE);
        font.draw(batch, "PONTOS: " + gameState.getScore(), 20, 462);
        font.draw(batch, "NIVEL: " + getNomeNivel(), 260, 462);
        font.draw(batch, "DEFESAS: " + gameState.getLevelDefenses() + "/" + GameState.defensesAdvance, 450, 462);

        for (int i = 0; i < gameState.getLives(); i++) {
            batch.draw(vidaLuva, 18 + i * 34, 395, 30, 30);
        }

        if (tempoMais10 > 0) {
            batch.draw(pontuacaoMais10, 268, 320, 90, 50);
        }

        if (tempoMensagem > 0) {
            layout.setText(font, mensagemJogo);
            font.draw(batch, mensagemJogo, (640 - layout.width) / 2, 390);
        }

        font.getData().setScale(1.4f);
    }

    private void desenharGameOver() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0f, 0f, 0f, 0.65f));
        shape.rect(0, 0, 640, 480);
        shape.end();

        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.2f);
        escreverTextoCentralizadoSemBegin("FIM DE JOGO", 280);
        font.getData().setScale(1.2f);
        escreverTextoCentralizadoSemBegin("Pontuacao: " + gameState.getScore(), 230);
        escreverTextoCentralizadoSemBegin("ENTER ou ESPACO para jogar de novo", 190);
        font.getData().setScale(1.4f);
        batch.end();
    }

    private void desenharGoleiro() {
        Texture goleiroAtual = goleiroParado;
        float x = 80;
        float y = 90;
        float largura = 95;
        float altura = 195;

        if ((int)(tempoAnimacao * 2) % 2 == 1) {
            goleiroAtual = goleiroPulando;
            x = 35;
            y = 160 + (float)Math.sin(tempoAnimacao * 10) * 8;
            largura = 185;
            altura = 112;

            batch.begin();
            batch.draw(goleiroAtual, x, y, largura / 2, altura / 2,
                largura, altura, 1, 1, 25,
                0, 0, goleiroAtual.getWidth(), goleiroAtual.getHeight(), false, false);
            batch.end();
            return;
        }

        batch.begin();
        batch.draw(goleiroAtual, x, y, largura, altura);
        batch.end();
    }

    private void desenharTitulo(String texto, float y) {
        font.getData().setScale(2.2f);
        escreverTextoCentralizado(texto, y);
        font.getData().setScale(1.4f);
    }

    private void escreverTextoCentralizado(String texto, float y) {
        batch.begin();
        font.setColor(Color.WHITE);
        escreverTextoCentralizadoSemBegin(texto, y);
        batch.end();
    }

    private void escreverTextoCentralizadoSemBegin(String texto, float y) {
        layout.setText(font, texto);
        font.draw(batch, texto, (640 - layout.width) / 2, y);
    }
}
