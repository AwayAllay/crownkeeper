package com.github.awayallay.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Comparator;
import java.util.TreeSet;

public class RenderSystem {

    private final TreeSet<RenderObject> renderObjects = new TreeSet<>(
        Comparator
            .comparingDouble(RenderObject::y)
            .thenComparing(RenderObject::x)
    );
    private final SpriteBatch batch;

    public RenderSystem(SpriteBatch batch) {
        this.batch = batch;
    }

    public void render() {

        batch.begin();
        for (RenderObject renderObject : renderObjects) {
            batch.draw(renderObject.texture(), renderObject.x(), renderObject.y(), renderObject.width(), renderObject.height());
        }
        batch.end();
        renderObjects.clear();
    }

    public void add(RenderObject renderObject) {
        renderObjects.add(renderObject);
    }
}
