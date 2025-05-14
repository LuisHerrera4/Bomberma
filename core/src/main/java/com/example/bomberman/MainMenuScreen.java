package com.example.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private final BombermanGame game;
    private Stage stage;
    private Texture background;
    private Skin skin;

    public MainMenuScreen(BombermanGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // 1) Carga del fondo de menú
        background = new Texture(Gdx.files.internal("Fotos_Inicio/background.png"));

        // 2) Configuración del Stage para UI
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // 3) Carga del skin (asegúrate de tener los archivos en android/assets/ui/)
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // 4) Creación del botón "Jugar"
        TextButton playButton = new TextButton("Jugar", skin);
        playButton.setSize(300, 100);
        // Centrar: pantalla_ancho/2 - botón_ancho/2
        playButton.setPosition(
            Gdx.graphics.getWidth()  / 2f - playButton.getWidth()  / 2f,
            Gdx.graphics.getHeight() / 2f - playButton.getHeight() / 2f
        );

        // 5) Listener para cambiar a GameScreen al clic
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        // 6) Añadir al stage
        stage.addActor(playButton);
    }

    @Override
    public void render(float delta) {
        // Limpia pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibuja fondo
        game.batch.begin();
        game.batch.draw(background, 0, 0,
            Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        // Actualiza y dibuja UI
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        skin.dispose();
    }
}
