package br.mackenzie;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PontuacaoView {
    private static final float PAINEL_X = 70f;
    private static final float PAINEL_Y = 50f;
    private static final float PAINEL_LARGURA = 500f;
    private static final float PAINEL_ALTURA = 380f;

    private final String[] regras = {
        "Pontos por defesa:",
        "  Nivel 1 (Facil)      = 10 pts por defesa",
        "  Nivel 2 (Medio)      = 20 pts por defesa",
        "  Nivel 3 (Dificil)    = 30 pts por defesa",
        "  Nivel 4 (Impossivel) = 40 pts por defesa",
        "Bonus de nivel perfeito:",
        "  Defender todos os 5 penaltis sem levar",
        "  gol garante +100 pontos extras!",
    };

    public void desenhar(ShapeRenderer shape, SpriteBatch batch, BitmapFont font, GlyphLayout layout) {
        desenharPainel(shape);

        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);
        desenharTextoCentralizado(batch, font, layout, "PONTUACAO", 396);

        font.getData().setScale(1.05f);
        float y = 346;

        for (String regra : regras) {
            font.draw(batch, regra, 110, y);
            y -= 31;
        }

        font.getData().setScale(0.95f);
        desenharTextoCentralizado(batch, font, layout, "ESC para voltar ao menu", 82);
        font.getData().setScale(1.4f);
        batch.end();
    }

    private void desenharPainel(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.02f, 0.12f, 0.07f, 0.84f));
        shape.rect(PAINEL_X, PAINEL_Y, PAINEL_LARGURA, PAINEL_ALTURA);
        shape.end();

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);
        shape.rect(PAINEL_X, PAINEL_Y, PAINEL_LARGURA, PAINEL_ALTURA);
        shape.end();
    }

    private void desenharTextoCentralizado(SpriteBatch batch, BitmapFont font, GlyphLayout layout, String texto, float y) {
        layout.setText(font, texto);
        font.draw(batch, texto, (640 - layout.width) / 2, y);
    }
}
