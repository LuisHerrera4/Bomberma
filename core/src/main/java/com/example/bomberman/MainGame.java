package com.example.bomberman;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
    @Override
    public void create() {
        this.setScreen(new MainMenuScreen(this));
    }
}
