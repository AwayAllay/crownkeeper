package com.github.awayallay.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.github.awayallay.entity.component.*;

public class MovementSystem extends IteratingSystem {


    public MovementSystem() {
        super(Family.all(PositionComponent.class, PathComponent.class, VelocityComponent.class, FacingComponent.class, AnimationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        PositionComponent pos = ComponentMappers.position.get(entity);
        PathComponent path = ComponentMappers.path.get(entity);
        VelocityComponent velocity = ComponentMappers.velocity.get(entity);
        FacingComponent facing = ComponentMappers.facing.get(entity);
        AnimationComponent anim = ComponentMappers.animation.get(entity);

        if ((path.getCurrentVertex() + 1) >= path.getPath().length) {
            //TODO: Enemy has reached the end of its path without dying so pls remove and score
            entity.remove(VelocityComponent.class);
            return;
        }

        Vector2 targetPos = path.getPath()[path.getCurrentVertex() + 1];
        Vector2 direction = new Vector2((float) (targetPos.x - pos.getX()), (float) (targetPos.y - pos.getY()));

        setFacing(direction, facing);
        setAnimation(facing.getFacing(), anim);

        float distanceToTarget = direction.len();
        if (distanceToTarget < 0.1f) {
            path.setCurrentVertex(path.getCurrentVertex() + 1);
        }

        direction.nor();

        pos.setX(pos.getX() + direction.x * velocity.getX() * deltaTime);
        pos.setY(pos.getY() + direction.y * velocity.getY() * deltaTime);
    }

    //TODO: set the different animations
    private void setAnimation(Facing facing, AnimationComponent anim) {
        switch (facing) {
            case NORTH -> anim.setCurrent(anim.getWalkWest());
            case EAST -> anim.setCurrent(anim.getWalkEast());
            case SOUTH -> anim.setCurrent(anim.getWalkWest());
            case WEST -> anim.setCurrent(anim.getWalkWest());
        }
    }

    private void setFacing(Vector2 direction, FacingComponent facing) {
        //x+ -> east, x- -> west, y+ -> north, y- -> south

        float x = direction.x;
        float y = direction.y;

        if (Math.abs(x) > Math.abs(y)) {
            if (x > 0) {
                facing.setFacing(Facing.EAST);
            } else {
                facing.setFacing(Facing.WEST);
            }
        } else {
            if (y > 0) {
                facing.setFacing(Facing.NORTH);
            } else {
                facing.setFacing(Facing.SOUTH);
            }
        }

    }
}
