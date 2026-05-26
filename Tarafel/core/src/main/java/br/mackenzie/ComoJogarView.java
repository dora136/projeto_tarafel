package br.mackenzie;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ComoJogarView {
    private static final float PAINEL_X = 70f;
    private static final float PAINEL_Y = 58f;
    private static final float PAINEL_LARGURA = 500f;
    private static final float PAINEL_ALTURA = 380f;

    private final String[] regras = {
        "1. Clique em INICIAR para comecar a partida.",
        "2. A bola pode ir para esquerda, direita ou cima.",
        "3. Escolha a defesa com as setas do teclado.",
        "4. Esquerda, direita e cima sao direcoes validas.",
        "5. Cada defesa vale +10 pontos.",
        "6. A cada 5 defesas o nivel aumenta.",
        "7. Se tomar gol, voce perde uma vida.",
        "8. Com 0 vidas, o jogo acaba."
    };

    public void desenhar(ShapeRenderer shape, SpriteBatch batch, BitmapFont font, GlyphLayout layout) {
        desenharPainel(shape);

        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);
        desenharTextoCentralizado(batch, font, layout, "COMO JOGAR", 396);

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
