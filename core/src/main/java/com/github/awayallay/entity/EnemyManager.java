package com.github.awayallay.entity;

import com.badlogic.gdx.utils.JsonValue;
import com.github.awayallay.entity.factory.EntityFactory;
import com.github.awayallay.map.MapManager;
import com.github.awayallay.util.Assets;
import com.github.awayallay.util.JSONLoader;

public class EnemyManager {

    private final Assets assets;
    private final EntityFactory entityFactory;
    private final MapManager mapManager;
    private final JsonValue factorySettings;

    public EnemyManager(Assets assets, EntityFactory entityFactory, MapManager mapManager) {
        this.assets = assets;
        this.entityFactory = entityFactory;
        this.mapManager = mapManager;
        factorySettings = new JSONLoader().loadJSON("entity/factory-settings/settings.json");
    }


    public Enemy spawnEnemy(String enemy) {
        JsonValue enemySettings = factorySettings.get(enemy);
        return new Enemy(entityFactory, assets, enemySettings, mapManager);
    }

}
