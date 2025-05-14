package com.example.bomberman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BombermanGame extends Game {
    public SpriteBatch batch;
    private AssetManager assets;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Iniciamos en LoadingScreen, no en MainMenu (ya vino del Splash/Menu)
        this.setScreen(new LoadingScreen(this));
    }

    public AssetManager getAssetManager() {
        return assets;
    }

    public void setAssetManager(AssetManager assets) {
        this.assets = assets;
    }

    @Override
    public void render() {
        super.render(); // delega el render a la pantalla activa
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
