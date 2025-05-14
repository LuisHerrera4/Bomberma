package com.example.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {
    private final BombermanGame game;
    private SpriteBatch batch;
    private Texture splash;
    private float elapsed = 0;

    public SplashScreen(BombermanGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        splash = new Texture("Fotos_Inicio/inicio.png");  // tu logo o imagen de arranque
    }

    @Override
    public void render(float delta) {
        elapsed += delta;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(splash,
            0, 0,
            Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
        );
        batch.end();

        // tras 2 segundos, pasamos al menú
        if (elapsed > 2f) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    // el resto igual: resize(), pause(), resume(), hide() sin nada, y dispose():
    @Override public void dispose() {
        batch.dispose();
        splash.dispose();
    }
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
