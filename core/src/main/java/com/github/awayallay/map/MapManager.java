package com.github.awayallay.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapManager {

    private final MapLoader mapLoader;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;


    public MapManager(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }

    public void setUp(float mapUnitScale) {
        mapLoader.loadMap();
        map = mapLoader.getMap();

        mapLoader.loadPaths();
        mapLoader.loadBuildSpots();

        mapRenderer = new OrthogonalTiledMapRenderer(map, mapUnitScale);
    }



    public void render(SpriteBatch batch, OrthographicCamera levelCamera) {

        mapRenderer.setView(levelCamera);
        mapRenderer.render();

        //TODO: render all objects
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


}
