package com.github.awayallay.entity;

import com.badlogic.ashley.core.Entity;

public abstract class GameEntity {

    public GameEntity(Entity entity) {
        this.entity = entity;
    }

    protected final Entity entity;


    public abstract void attack();
    public abstract void getDamages(float damageAmount);
    public abstract void die();


    public Entity getEntity() {
        return entity;
    }
}
