package br.mackenzie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private BitmapFont font;
    private GlyphLayout layout;
    private Texture fundoMenu;
    private Texture goleiroParado;
    private Texture goleiroPulando;

    private Botao botaoIniciar;
    private Botao botaoComoJogar;
    private Botao botaoPontuacao;
    private float tempoAnimacao = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        font = new BitmapFont();
        layout = new GlyphLayout();
        fundoMenu = new Texture("menu_gol.png");
        goleiroParado = new Texture("goleiro.png");
        goleiroPulando = new Texture("goleiro_pulando.png");

        font.getData().setScale(1.4f);

        botaoIniciar = new Botao(220, 230, 200, 45, "INICIAR", "Botao iniciar clicado");
        botaoComoJogar = new Botao(220, 170, 200, 45, "COMO JOGAR", "Botao como jogar clicado");
        botaoPontuacao = new Botao(220, 110, 200, 45, "PONTUACAO", "Botao pontuacao clicado");
    }

    @Override
    public void render() {
        verificarClique();
        tempoAnimacao += Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(0.1f, 0.45f, 0.2f, 1f);
        batch.begin();
        batch.draw(fundoMenu, 0, 0, 640, 480);
        batch.end();

        desenharMenu();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shape.dispose();
        font.dispose();
        fundoMenu.dispose();
        goleiroParado.dispose();
        goleiroPulando.dispose();
    }

    private void verificarClique() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (!Gdx.input.justTouched()) {
            return;
        }

        float mouseX = Gdx.input.getX();
        float mouseY = 480 - Gdx.input.getY();

        if (botaoIniciar.contem(mouseX, mouseY)) {
            botaoIniciar.clicar();
        } else if (botaoComoJogar.contem(mouseX, mouseY)) {
            botaoComoJogar.clicar();
        } else if (botaoPontuacao.contem(mouseX, mouseY)) {
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
        layout.setText(font, texto);
        font.draw(batch, texto, (640 - layout.width) / 2, y);
        batch.end();
    }
}
