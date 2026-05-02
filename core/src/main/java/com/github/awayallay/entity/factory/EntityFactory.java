package com.github.awayallay.entity.factory;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;

public class EntityFactory {

    private final Engine entityEngine;


    public EntityFactory() {
        entityEngine = new Engine();
    }


    public void registerFactorySystem(EntitySystem system) {
        entityEngine.addSystem(system);
    }

    public void unregisterFactorySystem(EntitySystem system) {
        entityEngine.removeSystem(system);
    }

    public void addEntity(Entity entity) {

        try {
            entityEngine.addEntity(entity);
        }
        catch (IllegalArgumentException e) {}
    }


    public Entity createEntity(Component[] components) {

        Entity entity = new Entity();

        for (Component component : components) {
            entity.add(component);
        }

        return entity;
    }


    public void update(float deltaTime) {
        entityEngine.update(deltaTime);
    }

    public ImmutableArray<Entity> getEntities(Family family) {
        return entityEngine.getEntitiesFor(family);
    }


    public void dispose() {
        entityEngine.removeAllEntities();
        entityEngine.removeAllSystems();
    }
}
