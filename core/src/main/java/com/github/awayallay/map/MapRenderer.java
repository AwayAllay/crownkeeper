package com.github.awayallay.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

record DecorationObject(TextureRegion texture, float x, float y, float width, float height) {
    //Record to store meta-data of constantly drawn map details
}


public class MapRenderer extends OrthogonalTiledMapRenderer {


    private final List<DecorationObject> decoObjects;

    public MapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        decoObjects = loadObjects();
    }

    private List<DecorationObject> loadObjects() {

        MapLayers mapLayers = map.getLayers();
        List<DecorationObject> objects = new ArrayList<>(mapLayers.getCount() * 10);

        for (MapLayer mapLayer : mapLayers) {

            if (mapLayer.isVisible()) {

                for (MapObject mapObject: mapLayer.getObjects()) {

                    if (mapObject instanceof TextureMapObject texturedMapObj) {
                        objects.add(initialiseProperties(texturedMapObj));
                    }
                }
            }
        }

        return objects;
    }

    private DecorationObject initialiseProperties(TextureMapObject texturedMapObj) {

        TextureRegion texture = texturedMapObj.getTextureRegion();

        float x = texturedMapObj.getX() * unitScale;
        float y = texturedMapObj.getY() * unitScale;

        float width = texture.getRegionWidth() * unitScale;
        float height = texture.getRegionHeight() * unitScale;

        return new DecorationObject(texture, x, y, width, height);
    }


    public void renderMapObjects(SpriteBatch batch) {
        if (decoObjects == null || decoObjects.isEmpty()) {
            System.out.println("No constant map objects for rendering stored.");
            return;
        }

        for (DecorationObject decoObject : decoObjects) {
            batch.draw(decoObject.texture(), decoObject.x(), decoObject.y(), decoObject.width(), decoObject.height());
        }

    }

}
