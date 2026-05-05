package com.github.awayallay.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.github.awayallay.Crownkeeper;
import com.github.awayallay.entity.EnemyManager;
import com.github.awayallay.entity.factory.EntityFactory;
import com.github.awayallay.entity.player.Player;
import com.github.awayallay.entity.system.AnimationSystem;
import com.github.awayallay.entity.system.MovementSystem;
import com.github.awayallay.input.GameInputHandler;
import com.github.awayallay.map.MapLoader;
import com.github.awayallay.map.MapManager;
import com.github.awayallay.ui.UIManager;
import com.github.awayallay.util.Assets;
import com.github.awayallay.util.JSONLoader;

public class LevelScreen extends GameScreen{

    private final String mapInformation;
    private final float unitScale, minWorldWidth, minWorldHeight;
    private SpriteBatch batch;
    private OrthographicCamera levelCamera;
    private ExtendViewport levelViewport;
    private MapManager mapManager;
    private JsonValue levelInformation;
    private GameInputHandler inputHandler;
    private UIManager uiManager;
    private EnemyManager enemyManager;
    private EntityFactory entityFactory;
    private boolean finishedSetup = false;


    public LevelScreen(Crownkeeper game,
                       Assets assets,
                       String assetBlock,
                       float unitScale,
                       float minWorldWidth,
                       float minWorldHeight,
                       String mapInformation) {
        super(game, assets, assetBlock);
        this.unitScale = unitScale;
        this.minWorldWidth = minWorldWidth;
        this.minWorldHeight = minWorldHeight;
        this.mapInformation = mapInformation;

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        assets.loadAssetBlock(assetBlock);
        levelCamera = new OrthographicCamera();
        levelViewport = new ExtendViewport(minWorldWidth, minWorldHeight, levelCamera);
    }

    @Override
    public void render(float delta) {
        assets.update();
        if (!assets.finishedLoading()) return; //TODO: show loading Screen

        if (!finishedSetup) finishSetup();


        levelViewport.apply();
        levelCamera.update();

        //uiManager.update(delta);

        batch.setProjectionMatrix(levelCamera.combined);
        batch.begin();
        //everything that is manually drawn, render here
        entityFactory.update(delta);
        mapManager.render(batch, levelCamera);
        //uiManager.render(batch);
        batch.end();

    }

    private void finishSetup() {
        levelInformation = new JSONLoader().loadJSON(mapInformation);
        mapManager = new MapManager(new MapLoader(assets, levelInformation), unitScale);
        levelCamera.position.set(mapManager.getMapWidth() / 2f, mapManager.getMapHeight() / 2f, 0);
        entityFactory = new EntityFactory();
        //uiManager = new UIManager(assets, new Player(entityFactory, assets), mapManager); //TODO: add player entity
        entityFactory.registerFactorySystem(new AnimationSystem(batch));
        entityFactory.registerFactorySystem(new MovementSystem());
        enemyManager = new EnemyManager(assets, entityFactory, mapManager);
        enemyManager.spawnEnemy("small-barbarian");
        finishedSetup = true;
    }

    @Override
    public void resize(int width, int height) {
        levelViewport.update(width, height);
        levelCamera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
