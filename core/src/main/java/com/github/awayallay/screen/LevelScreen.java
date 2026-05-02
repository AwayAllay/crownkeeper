package com.github.awayallay.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.github.awayallay.AlienTakeover;
import com.github.awayallay.map.MapLoader;
import com.github.awayallay.map.MapManager;
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
    private boolean finishedSetup = false;


    public LevelScreen(AlienTakeover game,
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

        batch.setProjectionMatrix(levelCamera.combined);
        batch.begin();
        //everything that is manually drawn, render here
        mapManager.render(batch, levelCamera);
        batch.end();

    }

    private void finishSetup() {
        levelInformation = new JSONLoader().loadJSON(mapInformation);
        mapManager = new MapManager(new MapLoader(assets, levelInformation));

        mapManager.setUp(unitScale);
        levelCamera.position.set(mapManager.getMapWidth() / 2f, mapManager.getMapHeight() / 2f, 0);
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
