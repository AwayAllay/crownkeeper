package com.github.awayallay.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.github.awayallay.util.Assets;

public class MapLoader {


    private final Assets assets;
    private final JsonValue mapInformation;
    private TiledMap map;
    private BuildSpot[] buildSpots;

    private TMXPathExtractor pathExtractor;

    public MapLoader(Assets assets, JsonValue mapInformation) {
        this.assets = assets;
        this.mapInformation = mapInformation;

        map = null;
        buildSpots = null;
        pathExtractor = null;
    }

    public void loadMap() {
        if (mapInformation.get("map") == null) return; //TODO: what to do when there is no map
        map = assets.getAsset(mapInformation.getString("map"));
    }

    public TiledMap getMap() {
        return map;
    }

    public void loadBuildSpots() {
        if (buildSpots != null) return;

        buildSpots = new TMXBuildSpotExtractor(map).getBuildSpots();
    }

    public void loadPaths() {
        if (pathExtractor != null) return;

        pathExtractor = new TMXPathExtractor(map);
    }

    public Vector2[] getPath (int pathIndex) {
        if (pathExtractor == null) return null;
        return pathExtractor.getPath(pathIndex);
    }

    public Vector2[][] getPaths() {
        return pathExtractor.getPaths();
    }

    public BuildSpot[] getBuildSpots() {
        return buildSpots;
    }
}
