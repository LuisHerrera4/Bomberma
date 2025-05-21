package com.example.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {
    private final BombermanGame game;
    private final AssetManager assets;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private OrthogonalTiledMapRenderer mapRenderer;
    private SpriteBatch batch;

    // Coordenadas jugador
    private float playerX = 16;
    private float playerY = 16;

    public GameScreen(BombermanGame game) {
        this.game = game;
        this.assets = game.getAssetManager();
    }

    @Override
    public void show() {
        batch = game.batch;

        // 1) Obtener y renderizar mapa
        TiledMap map = assets.get("Items/map.tmx", TiledMap.class);
        mapRenderer = new OrthogonalTiledMapRenderer(map, batch);

        // 2) Configurar cámara y viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(17 * 16, 17 * 16, camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
    }

    @Override
    public void render(float delta) {
        // 1) Actualizar cámara
        camera.update();
        mapRenderer.setView(camera);

        // 2) Limpiar pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 3) Dibujar mapa
        mapRenderer.render();

        // 4) Dibujar jugador (frame único)
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Texture playerFrame = assets.get("Personajes/Derecha/player_R1.png", Texture.class);
        batch.draw(playerFrame, playerX, playerY, 16, 16);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        mapRenderer.dispose();
        // Las texturas las maneja AssetManager
    }
}
