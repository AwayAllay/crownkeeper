package com.github.awayallay.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.awayallay.entity.player.Player;
import com.github.awayallay.entity.tower.Tower;
import com.github.awayallay.map.BuildSpot;
import com.github.awayallay.util.Assets;

public class TowerBuildUI {

    private boolean isOpen = false;
    private BuildSpot currentSpot;
    private final Player player;

    private int currentIndex = 0;
    private final Assets assets;

    public TowerBuildUI(Player player, Assets assets) {
        this.player = player;
        this.assets = assets;
        currentSpot = null;
    }


    public void open(BuildSpot buildSpot) {
        isOpen = true;
        currentSpot = buildSpot;
    }

    public void close() {
        currentSpot = null;
        isOpen = false;
    }

    public void scroll(float amount) {

        if (amount == 0) return;

        if (amount > 0) {
            currentIndex++;
        } else {
            currentIndex--;
        }

        if (currentIndex < 0) currentIndex = player.getSelectedTowers().length - 1;
        if (currentIndex >= player.getSelectedTowers().length) currentIndex = 0;
    }


    public void selectTower() {

        Tower selected = player.getSelectedTowers()[currentIndex];
        if (selected == null) return;

        selected.build(currentSpot, assets);
        close();
    }


    public void render(SpriteBatch batch) {
        if (!isOpen || currentSpot == null) return;
        Texture uiFrame = assets.getAsset("ui/tower-menu/ui-frame.png");
        Texture noTower = assets.getAsset("ui/tower-menu/tower/noTowerTest.png");
        Tower[] selectedTowers = player.getSelectedTowers();


        int pInd = getPreviousIndex(selectedTowers);
        int nInd = getNextIndex(selectedTowers);

        Texture pTower = selectedTowers[pInd] == null ? noTower : selectedTowers[pInd].getInGameBuildMenuTexture();
        Texture nTower = selectedTowers[nInd] == null ? noTower : selectedTowers[nInd].getInGameBuildMenuTexture();
        Texture currentTower = selectedTowers[currentIndex] == null ? noTower : selectedTowers[currentIndex].getInGameBuildMenuTexture();

        float currentX = currentSpot.getBounds().x - currentSpot.getBounds().radius / 2;
        float currentY = currentSpot.getBounds().y - currentSpot.getBounds().radius * 2;
        float spacing = 0.1f; //in units
        float tileSize = 1f;

        batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
        drawUITexture(
            batch,
            pTower,
            currentX - tileSize - spacing,
            currentY,
            tileSize,
            tileSize
        );
        drawUITexture(
            batch,
            nTower,
            currentX + tileSize + spacing,
            currentY,
            tileSize,
            tileSize
        );


        batch.setColor(Color.WHITE);
        drawUITexture(
            batch,
            currentTower,
            currentX,
            currentY,
            tileSize,
            tileSize
        );
        drawUITexture(
            batch,
            uiFrame,
            currentX,
            currentY,
            tileSize,
            tileSize
        );
    }

    private int getNextIndex(Tower[] selectedTowers) {
        return (currentIndex + 1) >= selectedTowers.length ? 0 : currentIndex + 1;
    }

    private int getPreviousIndex(Tower[] selectedTowers) {
        return (currentIndex - 1) < 0 ? selectedTowers.length - 1 : currentIndex - 1;
    }

    private void drawUITexture(SpriteBatch batch, Texture texture, float x, float y, float width, float height) {
        batch.draw(
            texture,
            x,
            y,
            width,
            height
        );
    }

    public boolean isOpen() {
        return isOpen;
    }
}
