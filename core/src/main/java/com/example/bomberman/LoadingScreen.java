package com.example.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LoadingScreen implements Screen {
    private final BombermanGame game;
    private final AssetManager assets;
    private SpriteBatch batch;
    private BitmapFont font;

    public LoadingScreen(BombermanGame game) {
        this.game = game;
        this.assets = new AssetManager();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font  = new BitmapFont(); // fuente por defecto

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assets.setLoader(TiledMap.class, new TmxMapLoader(resolver));

        // 1) Encolamos assets que usaremos en el juego
        assets.load("Fotos_Inicio/inicio.png", Texture.class);
        /*  Personaje movimiento */

        /* DE CARA */
        assets.load("Personajes/DeCara/player_D1.png", Texture.class);
        assets.load("Personajes/DeCara/player_D2.png", Texture.class);
        assets.load("Personajes/DeCara/player_D3.png", Texture.class);

        /* DE ATRAS */

        assets.load("Personajes/Atras/player_U1.png", Texture.class);
        assets.load("Personajes/Atras/player_U2.png", Texture.class);
        assets.load("Personajes/Atras/player_U3.png", Texture.class);

        /* DE DERECHA */

        assets.load("Personajes/Derecha/player_R1.png", Texture.class);
        assets.load("Personajes/Derecha/player_R2.png", Texture.class);
        assets.load("Personajes/Derecha/player_R3.png", Texture.class);


        /* DE IZQUIERDA */

        assets.load("Personajes/Izquierda/player_L1.png", Texture.class);
        assets.load("Personajes/Izquierda/player_L2.png", Texture.class);
        assets.load("Personajes/Izquierda/player_L3.png", Texture.class);


        /* Mapa y items*/
        assets.load("Items/bomb.png", Texture.class);
        assets.load("Items/map.tiles.png", Texture.class);
        assets.load("Items/obstacle.png", Texture.class);
        assets.load("Items/quantity.bonus.png", Texture.class);
        assets.load("Items/range.bonus.png", Texture.class);
        assets.load("Items/map.tmx", TiledMap.class);

        // TODO: añade aquí más, p.e. muros, bombas, ítems…
        // assets.load("Tiles/muro.png", Texture.class);

        // 2) Si quieres ver la barra de progreso, no bloquees:
        //    en render() usaremos assets.update()
    }

    @Override
    public void render(float delta) {
        // Actualiza carga
        if (assets.update()) {
            game.setAssetManager(assets);
            game.setScreen(new GameScreen(game));
            return;
        }

        float progress = assets.getProgress(); // 0.0 - 1.0

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Escala grande para texto
        font.getData().setScale(2);
        String mensaje = "Cargando: " + (int)(progress * 100) + "%";
        float textWidth = font.getRegion().getRegionWidth();
        font.draw(batch, mensaje,
            (Gdx.graphics.getWidth() / 2f) - (textWidth / 2f),
            Gdx.graphics.getHeight() / 2f + 30
        );

        // Barra de carga simple
        float barWidth = 300;
        float barHeight = 20;
        float x = (Gdx.graphics.getWidth() - barWidth) / 2f;
        float y = Gdx.graphics.getHeight() / 2f - barHeight;


        // Fondo barra
        batch.setColor(0.3f, 0.3f, 0.3f, 1);
        batch.draw(new Texture("ui/uiskin.png"), x, y, barWidth, barHeight);

        // Progreso barra
        batch.setColor(0.8f, 0.8f, 1f, 1);
        batch.draw(new Texture("ui/uiskin.png"), x, y, barWidth * progress, barHeight);

        batch.setColor(1, 1, 1, 1); // Restablece color

        batch.end();
    }


    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        // NO dispo se assets: los dispone GameScreen o la clase principal al final
    }
}
