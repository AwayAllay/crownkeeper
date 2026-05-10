package com.github.awayallay.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.github.awayallay.entity.component.AnimationComponent;
import com.github.awayallay.entity.component.ComponentMappers;
import com.github.awayallay.entity.component.HealthComponent;
import com.github.awayallay.entity.component.VelocityComponent;
import com.github.awayallay.entity.factory.EntityFactory;
import com.github.awayallay.util.Assets;

public abstract class GameEntity {

    protected Entity entityContainer;
    protected final EntityFactory entityFactory;
    protected final Assets assets;

    protected GameEntity(EntityFactory entityFactory, Assets assets) {
        this.entityFactory = entityFactory;
        this.assets = assets;
    }

    protected abstract Entity createEntityContainer();
    public abstract void attack();


    public void die() {
        AnimationComponent animation = ComponentMappers.animation.get(entityContainer);

        entityContainer.remove(VelocityComponent.class);
        entityContainer.remove(HealthComponent.class);

        animation.setCurrent(animation.getDie());
    }

    public abstract Rectangle hitbox();

    public void dispose() {
        entityFactory.removeEntity(entityContainer);
    }
    public Entity getEntity() {
        return entityContainer;
    }
}
