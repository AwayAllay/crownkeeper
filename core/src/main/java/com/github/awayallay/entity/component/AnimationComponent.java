package com.github.awayallay.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {

    private Animation<TextureRegion> walkNorth;
    private Animation<TextureRegion> walkEast;
    private Animation<TextureRegion> walkSouth;
    private Animation<TextureRegion> walkWest;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> attack;
    private Animation<TextureRegion> die;

    private Animation<TextureRegion> current;
    private float stateTime = 0f;

    private float height, width;

    public AnimationComponent(Animation<TextureRegion> walkNorth,
                              Animation<TextureRegion> walkEast,
                              Animation<TextureRegion> walkSouth,
                              Animation<TextureRegion> walkWest,
                              Animation<TextureRegion> idle,
                              Animation<TextureRegion> attack,
                              Animation<TextureRegion> die,
                              float height,
                              float width) {
        this.walkNorth = walkNorth;
        this.walkEast = walkEast;
        this.walkSouth = walkSouth;
        this.walkWest = walkWest;
        this.idle = idle;
        this.attack = attack;
        this.die = die;

        this.height = height;
        this.width = width;

        current = walkEast;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }


    public Animation<TextureRegion> getWalkNorth() {
        return walkNorth;
    }

    public Animation<TextureRegion> getWalkEast() {
        return walkEast;
    }

    public Animation<TextureRegion> getWalkSouth() {
        return walkSouth;
    }

    public Animation<TextureRegion> getWalkWest() {
        return walkWest;
    }

    public Animation<TextureRegion> getIdle() {
        return idle;
    }

    public Animation<TextureRegion> getAttack() {
        return attack;
    }

    public Animation<TextureRegion> getDie() {
        return die;
    }

    public Animation<TextureRegion> getCurrent() {
        return current;
    }

    public void setCurrent(Animation<TextureRegion> current) {
        this.current = current;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
