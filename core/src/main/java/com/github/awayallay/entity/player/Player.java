package com.github.awayallay.entity.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.github.awayallay.entity.GameEntity;
import com.github.awayallay.entity.component.*;
import com.github.awayallay.entity.factory.EntityFactory;
import com.github.awayallay.entity.tower.Tower;
import com.github.awayallay.util.Assets;

public class Player extends GameEntity {

    private Tower[] selectedTowers;
    private final EntityFactory factory;
    private final float range = 1.5f;


    public Player(EntityFactory factory, Assets assets) {
        super(factory, assets);
        this.factory = factory;
        selectedTowers = new Tower[6];
    }

    @Override
    protected Entity createEntityContainer() {
        return null;
    }

    @Override
    public void attack() {

        Entity target = getTarget();
        if (target == null) return;

        //TODO do damage
    }

    private Entity getTarget() {
        PositionComponent pos = ComponentMappers.position.get(getPlayerEntity());
        FacingComponent facingComponent = ComponentMappers.facing.get(getPlayerEntity());
        ImmutableArray<Entity> entities = factory.getEntities(Family.all(HealthComponent.class, PositionComponent.class).get());


        Entity target = null;
        float closestDistance = 100f;

        for (Entity entity : entities) {
            PositionComponent entPos = ComponentMappers.position.get(entity);
            Vector2 dir = new Vector2(pos.getX() - entPos.getX(), pos.getY() - entPos.getY());

            if (dir.len() > 0.5f * 32f) continue;

            if (getDirection(dir).equals(facingComponent.getFacing())) {

                if (target == null || dir.len() < closestDistance) {
                    target = entity;
                }

            }

        }

        return target;
    }

    private  Facing getDirection(Vector2 dir) {

        if (Math.abs(dir.x) > Math.abs(dir.y)) {
            if (dir.x > 0) {
                return Facing.EAST;
            } else {
                return Facing.WEST;
            }
        } else {
            if (dir.y > 0) {
                return Facing.NORTH;
            } else {
                return Facing.SOUTH;
            }
        }
    }


    @Override
    public void die() {

    }


    public Entity getPlayerEntity() {
        return getEntity();
    }

    public Tower[] getSelectedTowers() {
        return selectedTowers;
    }

    public void setSelectedTowers(Tower[] selectedTowers) {
        this.selectedTowers = selectedTowers;
    }

    public float getRange() {
        return range;
    }
}
