package com.github.awayallay.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.awayallay.entity.component.AnimationComponent;
import com.github.awayallay.entity.component.ComponentMappers;
import com.github.awayallay.entity.component.PositionComponent;

public class AnimationSystem extends IteratingSystem {

    private final SpriteBatch batch;

    public AnimationSystem(SpriteBatch batch) {
        super(Family.all(AnimationComponent.class, PositionComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = ComponentMappers.animation.get(entity);
        animationComponent.setStateTime(animationComponent.getStateTime() + deltaTime);

        TextureRegion currentFrame = animationComponent.getCurrent().getKeyFrame(animationComponent.getStateTime(), true);
        PositionComponent pos = ComponentMappers.position.get(entity);

        batch.draw(
            currentFrame,
            (float) pos.getX() - (animationComponent.getWidth() / 2f),
            (float) pos.getY() - (animationComponent.getHeight() / 2f),
            animationComponent.getWidth(),
            animationComponent.getHeight()
        );
    }
}
