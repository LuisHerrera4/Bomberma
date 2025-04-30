package com.example.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private final MainGame game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture bgMenu;

    public MainMenuScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        bgMenu = new Texture("Fotos_Inicio/inicio.png");  // fondo de menú si quieres

        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        TextButton play = new TextButton("Jugar", skin);
        play.addListener(evt -> {
            game.setScreen(new LoadingScreen(game));
            return true;
        });

        Table t = new Table(skin);
        t.setFillParent(true);
        t.center();
        t.add(play).width(200).height(60).pad(10);
        stage.addActor(t);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bgMenu, 0, 0,
            Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void dispose() {
        batch.dispose();
        stage.dispose();
        bgMenu.dispose();
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
