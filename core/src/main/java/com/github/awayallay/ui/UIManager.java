package com.github.awayallay.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.awayallay.entity.player.Player;
import com.github.awayallay.map.MapManager;
import com.github.awayallay.util.Assets;

public class UIManager {

    private final TowerInteractionUI towerInteractionUI;
    private final Player player;

    public UIManager(Assets assets, Player player, MapManager mapManager) {
        this.towerInteractionUI = new TowerInteractionUI(assets, mapManager);
        this.player = player;
    }

    public void update(float delta) {
        towerInteractionUI.update(player);
    }

    public void render(SpriteBatch batch) {
        towerInteractionUI.render(batch);
    }
}
