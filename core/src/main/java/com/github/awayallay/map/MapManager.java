package com.github.awayallay.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class MapManager {

    private final MapLoader mapLoader;
    private MapRenderer mapRenderer;
    private TiledMap map;


    public MapManager(MapLoader mapLoader, float mapUnitScale) {
        this.mapLoader = mapLoader;
        setUp(mapUnitScale);
    }

    public void setUp(float mapUnitScale) {
        mapLoader.loadMap();
        map = mapLoader.getMap();

        mapLoader.loadPaths();
        mapLoader.loadBuildSpots();

        mapRenderer = new MapRenderer(map, mapUnitScale);
    }


    public void render(SpriteBatch batch, OrthographicCamera levelCamera) {
        mapRenderer.setView(levelCamera);
        mapRenderer.render();
        mapRenderer.renderMapObjects(batch);
    }

    public int getMapWidth() {
        if (map == null) return -1;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getWidth();
    }

    public int getMapHeight() {
        if (map == null) return -1;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getHeight();
    }

    public BuildSpot[] getBuildSpots() {
        return mapLoader.getBuildSpots();
    }

    public Vector2[][] getPaths() {return mapLoader.getPaths();}

}
