package com.github.awayallay.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.github.awayallay.entity.component.ComponentMappers;
import com.github.awayallay.entity.component.PositionComponent;
import com.github.awayallay.entity.player.Player;
import com.github.awayallay.map.BuildSpot;
import com.github.awayallay.map.MapManager;
import com.github.awayallay.util.Assets;

public class TowerInteractionUI {

    private final Assets assets;
    private final BuildSpot[] buildSpots;
    private boolean isVisible = false;
    private BuildSpot currentHoveredSpot = null;

    public TowerInteractionUI(Assets assets, MapManager mapManager) {
        this.assets = assets;
        buildSpots = mapManager.getBuildSpots();
    }

    public void update(Player player) {

        PositionComponent pos = ComponentMappers.position.get(player.getPlayerEntity());

        Circle reachCircle = new Circle(
            pos.getX(),
            pos.getY(),
            player.getRange()
        );

        for (BuildSpot buildSpot : buildSpots) {

            if (buildSpot == null) continue;

            if (buildSpot.getBounds().overlaps(reachCircle)) {
                isVisible = true;
                currentHoveredSpot = buildSpot;
                break;
            }
            else {
                isVisible = false;
                currentHoveredSpot = null;
            }
        }

    }


    public void render(SpriteBatch batch) {
        if (!isVisible || currentHoveredSpot == null) return;

        Texture hammerTexture = assets.getAsset("ui/tower-menu/hammer.png");

        batch.draw(hammerTexture,
            currentHoveredSpot.getBounds().x,
            currentHoveredSpot.getBounds().y + currentHoveredSpot.getBounds().radius,
            0.5f, 0.5f);
    }

}
