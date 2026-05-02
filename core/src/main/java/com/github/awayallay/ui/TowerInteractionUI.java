package com.github.awayallay.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.awayallay.map.BuildSpot;
import com.github.awayallay.util.Assets;

public class TowerInteractionUI {

    private final Assets assets;
    private final SpriteBatch batch;

    public TowerInteractionUI(Assets assets, SpriteBatch batch) {
        this.assets = assets;
        this.batch = batch;
    }


    public void showTowerInteractionUI(BuildSpot buildSpot) {

        Texture hammerTexture = assets.getAsset("ui/tower-menu/hammer.png");

        batch.draw(hammerTexture, buildSpot.getBounds().x, buildSpot.getBounds().y + buildSpot.getBounds().radius, 0.5f, 0.5f);
    }
}
