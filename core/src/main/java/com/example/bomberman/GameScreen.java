package com.example.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final BombermanGame game;
    private final AssetManager assets;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private OrthogonalTiledMapRenderer mapRenderer;
    private SpriteBatch batch;

    // UI
    private Stage uiStage;
    private Texture btnUp, btnDown, btnLeft, btnRight, btnBomb;

    // Movimiento
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private float playerX = 16;
    private float playerY = 16;
    private float speed = 100f;
    private float animTime = 0f;

    // Animaciones por dirección
    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction currentDir = Direction.DOWN;

    public GameScreen(BombermanGame game) {
        this.game = game;
        this.assets = game.getAssetManager();
    }

    @Override
    public void show() {
        batch = game.batch;

        // Mapa
        TiledMap map = assets.get("Items/map.tmx", TiledMap.class);
        mapRenderer = new OrthogonalTiledMapRenderer(map, batch);

        // Cámara y viewport centrados en el mapa
        camera = new OrthographicCamera();
        float mapWidth = 17 * 16f;  // 272
        float mapHeight = 17 * 16f;
        viewport = new FitViewport(mapWidth, mapHeight, camera);
        viewport.apply();
        camera.position.set(mapWidth / 2f, mapHeight / 2f, 0);

        // UI: Cargar imágenes de botones
        btnUp    = new Texture("ui/btn_up.png");
        btnDown  = new Texture("ui/btn_down.png");
        btnLeft  = new Texture("ui/btn_left.png");
        btnRight = new Texture("ui/btn_right.png");
        btnBomb  = new Texture("ui/btn_bomb.png");

        // Crear Stage UI
        uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(uiStage);

        // 📱 Tamaño de pantalla real
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float btnSize = 96f;
        float padding = 32f;

        // 🕹️ Botones de movimiento a la izquierda
        ImageButton up    = crearBoton(btnUp,     padding + btnSize, screenHeight / 2f + btnSize);
        ImageButton down  = crearBoton(btnDown,   padding + btnSize, screenHeight / 2f - btnSize);
        ImageButton left  = crearBoton(btnLeft,   padding,           screenHeight / 2f);
        ImageButton right = crearBoton(btnRight,  padding + btnSize * 2, screenHeight / 2f);

        // 💣 Botón de bomba a la derecha
        ImageButton bomb = crearBoton(btnBomb, screenWidth - btnSize - padding, screenHeight / 2f - btnSize / 2f);

        // Listeners
        up.addListener(toque(() -> moveUp = true, () -> moveUp = false));
        down.addListener(toque(() -> moveDown = true, () -> moveDown = false));
        left.addListener(toque(() -> moveLeft = true, () -> moveLeft = false));
        right.addListener(toque(() -> moveRight = true, () -> moveRight = false));
        bomb.addListener(toque(this::colocarBomba, null));

        uiStage.addActor(up);
        uiStage.addActor(down);
        uiStage.addActor(left);
        uiStage.addActor(right);
        uiStage.addActor(bomb);

        // Animaciones (3 frames por dirección)
        walkDown = new Animation<>(0.15f,
            new TextureRegion(assets.get("Personajes/DeCara/player_D1.png", Texture.class)),
            new TextureRegion(assets.get("Personajes/DeCara/player_D2.png", Texture.class)),
            new TextureRegion(assets.get("Personajes/DeCara/player_D3.png", Texture.class)));

        walkUp = new Animation<>(0.15f,
            new TextureRegion(assets.get("Personajes/Atras/player_U1.png", Texture.class)),
            new TextureRegion(assets.get("Personajes/Atras/player_U2.png", Texture.class)),
            new TextureRegion(assets.get("Personajes/Atras/player_U3.png", Texture.class)));

        walkLeft = new Animation<>(0.15f,
            new TextureRegion(assets.get("Personajes/Izquierda/player_L1.png", Texture.class)),
            new TextureRegion(assets.get("Personajes/Izquierda/player_L2.png", Texture.class)),
            new TextureRegion(assets.get("Personajes/Izquierda/player_L3.png", Texture.class)));

        walkRight = new Animation<>(0.15f,
            new TextureRegion(assets.get("Personajes/Derecha/player_R1.png", Texture.class)),
            new TextureRegion(assets.get("Personajes/Derecha/player_R2.png", Texture.class)),
            new TextureRegion(assets.get("Personajes/Derecha/player_R3.png", Texture.class)));
    }


    @Override
    public void render(float delta) {
        animTime += delta;

        // Actualizar movimiento y dirección
        if (moveUp) {
            playerY += speed * delta;
            currentDir = Direction.UP;
        } else if (moveDown) {
            playerY -= speed * delta;
            currentDir = Direction.DOWN;
        } else if (moveLeft) {
            playerX -= speed * delta;
            currentDir = Direction.LEFT;
        } else if (moveRight) {
            playerX += speed * delta;
            currentDir = Direction.RIGHT;
        }

        camera.update();
        mapRenderer.setView(camera);

        // Limpiar
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar mapa
        mapRenderer.render();

        // Dibujar jugador
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        TextureRegion frame;
        switch (currentDir) {
            case UP: frame = walkUp.getKeyFrame(animTime, true); break;
            case DOWN: frame = walkDown.getKeyFrame(animTime, true); break;
            case LEFT: frame = walkLeft.getKeyFrame(animTime, true); break;
            case RIGHT: frame = walkRight.getKeyFrame(animTime, true); break;
            default: frame = walkDown.getKeyFrame(0); break;
        }
        batch.draw(frame, playerX, playerY, 16, 16);
        batch.end();

        // UI
        uiStage.act(delta);
        uiStage.draw();
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
    }

    private ImageButton crearBoton(Texture tex, float x, float y) {
        ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(tex)));
        button.setSize(64, 64);
        button.setPosition(x, y);
        return button;
    }

    private ClickListener toque(Runnable onPress, Runnable onRelease) {
        return new ClickListener() {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (onPress != null) onPress.run();
                return true;
            }
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (onRelease != null) onRelease.run();
            }
        };
    }

    private void colocarBomba() {
        System.out.println("💣 Bomba colocada!");
    }
}
