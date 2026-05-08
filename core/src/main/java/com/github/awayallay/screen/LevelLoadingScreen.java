package com.github.awayallay.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.awayallay.Crownkeeper;
import com.github.awayallay.util.Assets;
import com.github.awayallay.util.JSONLoader;

public class LevelLoadingScreen implements Screen {

    private final Crownkeeper gameInstance;
    private final Assets assets;
    private final String assetBlock;
    private final float unitScale, minWorldUnitWidth, minWorldUnitHeight;
    private final String levelInformationPath;

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final float progressBarWidth, progressBarHeight, barX, barY;

    private Texture backgroundImage;
    private BitmapFont font;

    public LevelLoadingScreen(Crownkeeper gameInstance, Assets assets, String assetBlock, float unitScale, float minWorldUnitWidth, float minWorldUnitHeight, String levelInformationPath) {
        this.gameInstance = gameInstance;
        this.assets = assets;
        this.assetBlock = assetBlock;
        this.unitScale = unitScale;
        this.minWorldUnitWidth = minWorldUnitWidth;
        this.minWorldUnitHeight = minWorldUnitHeight;
        this.levelInformationPath = levelInformationPath;

        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        progressBarWidth = viewport.getWorldWidth() * 0.3f;
        progressBarHeight = viewport.getWorldHeight() * 0.03f;
        barX = (viewport.getWorldWidth() - progressBarWidth) / 2f;
        barY = (progressBarHeight * 3);
    }

    @Override
    public void show() {
        backgroundImage = new Texture("loadingScreen-assets/loadingBackground.png");
        font = new BitmapFont();
        assets.loadAssetBlock(assetBlock);
    }

    @Override
    public void render(float delta) {

        assets.update();
        viewport.apply();
        camera.update();

        if (assets.finishedLoading()) {
            JSONLoader jsonLoader = new JSONLoader();
            JsonValue levelInformation = jsonLoader.loadJSON(levelInformationPath);
            JsonValue factorySettings = jsonLoader.loadJSON("entity/factory-settings/settings.json");
            gameInstance.setScreen(new LevelScreen(gameInstance, assets, unitScale, minWorldUnitWidth, minWorldUnitHeight, levelInformation, factorySettings));
        }
        else {

            float progress = assets.getLoadingProgress();

            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(backgroundImage, 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());
            batch.end();

            renderProgressbar(progress);

            batch.begin();
            font.draw(batch, (int)(progress * 100) + "%", (barX + progressBarWidth / 2f), barY + progressBarHeight);
            font.draw(batch, "loading...", (barX + progressBarWidth / 2f), (barY - barY / 2f));
            batch.end();
        }
    }

    private void renderProgressbar(float loadingProgress) {
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(barX, barY, progressBarWidth, progressBarHeight);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(barX, barY, progressBarWidth * loadingProgress, progressBarHeight);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
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
        batch.dispose();
        font.dispose();
    }
}
