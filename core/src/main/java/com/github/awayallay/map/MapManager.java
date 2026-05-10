package com.github.awayallay.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class MapManager {

    private final MapLoader mapLoader;
    private final float mapUnitScale;
    private MapRenderer mapRenderer;
    private TiledMap map;
    private List<Polygon> mapCollisionBoxes;


    public MapManager(MapLoader mapLoader, float mapUnitScale) {
        this.mapLoader = mapLoader;
        this.mapUnitScale = mapUnitScale;
        setUp(mapUnitScale);
        mapCollisionBoxes = getCollisionBoxes();
    }

    private List<Polygon> getCollisionBoxes() {

        MapLayer collisionLayer = map.getLayers().get("collision");
        List<Polygon> collisionBoxes = new ArrayList<>();

        for (MapObject mapObject : collisionLayer.getObjects()) {

            if (mapObject instanceof PolygonMapObject polyObj) {
                collisionBoxes.add(rescalePolygon(polyObj.getPolygon()));
            }
        }
        return collisionBoxes;
    }

    private Polygon rescalePolygon(Polygon polygon) {
        float[] originalVertices = polygon.getVertices();
        float[] scaledVertices = new float[originalVertices.length];

        for (int i = 0; i < originalVertices.length; i++) {
            scaledVertices[i] = originalVertices[i] * mapUnitScale;
        }

        Polygon scaled = new Polygon(scaledVertices);
        scaled.setPosition(polygon.getX() * mapUnitScale, polygon.getY() * mapUnitScale);

        return scaled;
    }


    public void setUp(float mapUnitScale) {
        mapLoader.loadMap();
        map = mapLoader.getMap();

        mapLoader.loadPaths();
        mapLoader.loadBuildSpots();

        mapRenderer = new MapRenderer(map, mapUnitScale);
    }


    public boolean collides(float x, float y) {
        for (Polygon mapCollisionBox : mapCollisionBoxes) {
            if (mapCollisionBox.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public void render(SpriteBatch batch, OrthographicCamera levelCamera) {
        mapRenderer.setView(levelCamera);
        mapRenderer.render();
        mapRenderer.renderMapObjects(batch);
    }

    public int getMapUnitWidth() {
        if (map == null) return -1;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getWidth();
    }

    public int getMapUnitHeight() {
        if (map == null) return -1;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getHeight();
    }

    public BuildSpot[] getBuildSpots() {
        return mapLoader.getBuildSpots();
    }

    public Vector2[][] getPaths() {return mapLoader.getPaths();}

}
