package com.github.awayallay.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PathComponent implements Component {

    private final Vector2[] path;
    private int currentVertex;

    public PathComponent(Vector2[] path) {
        this.path = path;
        currentVertex = 0;
    }

    public Vector2[] getPath() {
        return path;
    }

    public int getCurrentVertex() {
        return currentVertex;
    }

    public void setCurrentVertex(int currentVertex) {
        this.currentVertex = currentVertex;
    }
}
