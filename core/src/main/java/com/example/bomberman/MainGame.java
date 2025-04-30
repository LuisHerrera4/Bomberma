package com.example.bomberman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class MainGame extends Game {
    public AssetManager assets;  // gestor de assets compartido

    @Override
    public void create() {
        assets = new AssetManager();
        setScreen(new SplashScreen(this));
    }
}
