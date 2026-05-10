package com.github.awayallay.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.github.awayallay.entity.component.*;
import com.github.awayallay.entity.factory.EntityFactory;
import com.github.awayallay.map.MapManager;
import com.github.awayallay.util.AnimationExtractor;
import com.github.awayallay.util.Assets;

public class Enemy extends GameEntity {

    private final JsonValue factorySettings;
    private final MapManager mapManager;

    public Enemy(EntityFactory entityFactory, Assets assets, JsonValue factorySettings, MapManager mapManager) {
        super(entityFactory, assets);
        this.factorySettings = factorySettings;
        this.mapManager = mapManager;
        entityContainer = createEntityContainer(); //TODO: get this back in the super constructor without Nullpointers
        entityFactory.addEntity(entityContainer);
    }

    @Override
    protected Entity createEntityContainer() {
        PathComponent pathComponent = getPathComponent();

        Entity entityContainer = entityFactory.createEntity(
            new Component[]{
                getFacingComponent(),
                getAnimationComponent(),
                getHealthComponent(),
                pathComponent,
                getPositionComponent(pathComponent),
                getVelocityComponent()
            }
        );

        return entityContainer;
    }

    private FacingComponent getFacingComponent() {
        return new FacingComponent(Facing.EAST);
    }

    private HealthComponent getHealthComponent() {
        return new HealthComponent(factorySettings.getInt("health"));
    }

    private PathComponent getPathComponent() {
        return new PathComponent(mapManager.getPaths()[0]);
    }

    private VelocityComponent getVelocityComponent() {
        return new VelocityComponent(
            factorySettings.getFloat("velocity"),
            factorySettings.getFloat("velocity")
        );
    }

    private PositionComponent getPositionComponent(PathComponent pathComponent) {
        Vector2 currentVertex = pathComponent.getPath()[pathComponent.getCurrentVertex()];
        return new PositionComponent(currentVertex.x, currentVertex.y);
    }

    private AnimationComponent getAnimationComponent() {
        TextureRegion[][] entityAnimations = new AnimationExtractor(assets)
            .extractAnimations(
                factorySettings.getString("texture-path"),
                factorySettings.getInt("px-width"),
                factorySettings.getInt("px-height")
            );

        return new AnimationComponent(
            null,
            new Animation<>(factorySettings.getFloat("frame-duration"), entityAnimations[1]),
            null,
            new Animation<>(factorySettings.getFloat("frame-duration"), entityAnimations[0]),
            null,
            null,
            null,
            factorySettings.getFloat("unit-height"),
            factorySettings.getFloat("unit-width")
        );
    }

    @Override
    public void attack() {

    }

    @Override
    public Rectangle hitbox() {
        return null;
    }
}
