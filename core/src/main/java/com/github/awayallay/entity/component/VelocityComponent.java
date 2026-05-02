package com.github.awayallay.entity.component;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {

    private float x, y;

    public VelocityComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public VelocityComponent() {
        this.x = 0;
        this.y = 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
