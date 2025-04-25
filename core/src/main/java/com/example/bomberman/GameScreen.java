package com.example.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

    private MainGame game;
    private SpriteBatch batch;
    private Texture background;
    private Texture player;

    private float playerX, playerY;
    private float speed = 200; // velocidad de movimiento

    public GameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("Fotos_Inicio/background.png");
        player = new Texture("Personajes/player.png");

        playerX = 100;
        playerY = 100;
    }

    @Override
    public void render(float delta) {
        // Movimiento del jugador
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerX += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerX -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) playerY += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerY -= speed * delta;

        // Dibujado
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(player, playerX, playerY);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        player.dispose();
    }
}
