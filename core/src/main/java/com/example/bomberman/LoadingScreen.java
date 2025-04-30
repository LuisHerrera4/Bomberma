package com.example.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen {
    private final MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private AssetManager assets;

    public LoadingScreen(MainGame game) {
        this.game = game;
        this.assets = game.assets;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Encolamos los assets del juego
        assets.load("Fotos_Inicio/inicio.png", Texture.class);
        assets.load("Personajes/DeCara/player_D1.png", Texture.class);
        // añade aquí más (muros, sprites, sonidos…)

        assets.finishLoading(); // o si quieres ver % en render(): usa assets.update()
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float progress = assets.getProgress(); // 0 a 1
        batch.begin();
        font.draw(batch, "Cargando: " + (int)(progress * 100) + "%", 50, 50);
        batch.end();

        if (assets.update()) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override public void dispose() {
        batch.dispose();
        font.dispose();
    }
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
