package br.mackenzie;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Botao {
    private final Rectangle area;
    private final String texto;
    private final String mensagemClique;

    public Botao(float x, float y, float largura, float altura, String texto, String mensagemClique) {
        this.area = new Rectangle(x, y, largura, altura);
        this.texto = texto;
        this.mensagemClique = mensagemClique;
    }

    public boolean contem(float x, float y) {
        return area.contains(x, y);
    }

    public void clicar() {
        System.out.println(mensagemClique);
    }

    public void desenhar(ShapeRenderer shape, SpriteBatch batch, BitmapFont font, GlyphLayout layout) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.05f, 0.25f, 0.12f, 0.9f));
        shape.rect(area.x, area.y, area.width, area.height);
        shape.end();

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);
        shape.rect(area.x, area.y, area.width, area.height);
        shape.end();

        batch.begin();
        font.setColor(Color.WHITE);
        layout.setText(font, texto);
        font.draw(batch, texto,
            area.x + (area.width - layout.width) / 2,
            area.y + 29);
        batch.end();
    }
}
