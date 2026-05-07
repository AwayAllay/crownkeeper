package com.github.awayallay.entity.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.github.awayallay.entity.GameEntity;
import com.github.awayallay.entity.component.*;
import com.github.awayallay.entity.factory.EntityFactory;
import com.github.awayallay.entity.tower.Tower;
import com.github.awayallay.util.AnimationExtractor;
import com.github.awayallay.util.Assets;

public class Player extends GameEntity {


    private final JsonValue settings;
    private final PositionComponent pos;
    private final float reach;

    public Player(EntityFactory entityFactory, Assets assets, JsonValue factorySettings, PositionComponent pos) {
        super(entityFactory, assets);
        this.settings = factorySettings.get("player");
        this.pos = pos;
        this.reach = settings.getFloat("unit-reach");
        entityContainer = createEntityContainer();
        entityFactory.addEntity(entityContainer);
    }

    @Override
    public void attack() {
        Entity target = getTarget();
        if (target == null) return;

        //TODO do damage
    }

    private Entity getTarget() {
        PositionComponent pos = ComponentMappers.position.get(entityContainer);
        FacingComponent facingComponent = ComponentMappers.facing.get(entityContainer);
        ImmutableArray<Entity> entities = entityFactory.getEntities(Family.all(HealthComponent.class, PositionComponent.class).get());


        Entity target = null;
        float closestDistance = 100f;

        for (Entity entity : entities) {
            PositionComponent entPos = ComponentMappers.position.get(entity);
            Vector2 dir = new Vector2(pos.getX() - entPos.getX(), pos.getY() - entPos.getY());

            if (dir.len() > reach) continue;

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
    protected Entity createEntityContainer() {
        return entityFactory.createEntity(new Component[]{
            getAnimationComponent(),
            getFacingComponent(),
            getHealthComponent(),
            getVelocityComponent(),
            pos,
            new PlayerComponent()
        });
    }


    private AnimationComponent getAnimationComponent() {

        TextureRegion[][] animations = new AnimationExtractor(assets)
            .extractAnimations(
              settings.getString("texture-path"),
                settings.getInt("px-width"),
                settings.getInt("px-height")
            );

        return new AnimationComponent(
            null,
            new Animation<>(settings.getFloat("frame-duration"), animations[1]),
            null,
            new Animation<>(settings.getFloat("frame-duration"), animations[0]),
            null,
            null,
            null,
            settings.getFloat("unit-height"),
            settings.getFloat("unit-width")
        );
    }

    private FacingComponent getFacingComponent() {
        return new FacingComponent(Facing.EAST);
    }

    private HealthComponent getHealthComponent() {
        return new HealthComponent(settings.getInt("health"));
    }

    private VelocityComponent getVelocityComponent() {
        return new VelocityComponent(settings.getFloat("velocity"), settings.getFloat("velocity"));
    }


    public float getReach() {
        return reach;
    }

    public Tower[] getSelectedTowers() {
        return null;
    }
}
