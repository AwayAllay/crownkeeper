package com.github.awayallay.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.github.awayallay.util.exceptions.NoBuildSpotsException;

public class TMXBuildSpotExtractor {

    private final BuildSpot[] buildSpots;

    public TMXBuildSpotExtractor(TiledMap map) {

        MapLayer buildSpotLayer = map.getLayers().get("build-spots");
        buildSpots = extractBuildSpots(buildSpotLayer);
    }

    private BuildSpot[] extractBuildSpots(MapLayer buildSpotLayer) {

        if (buildSpotLayer == null) throw new NoBuildSpotsException("No layer with buildSpots in this map");
        MapObjects spotObjects = buildSpotLayer.getObjects();

        if (spotObjects.getCount() <= 0) throw new NoBuildSpotsException("No spots to build in this map");

        BuildSpot[] toReturn = new BuildSpot[spotObjects.getCount()];

        int index = 0;
        for (MapObject spotObject : spotObjects) {

            if (spotObject instanceof EllipseMapObject ellipseMapObject) {

                Ellipse ellipse = ellipseMapObject.getEllipse();
                Circle spot = new Circle(
                    (ellipse.x + ellipse.width / 2) / 32f,
                    (ellipse.y + ellipse.height / 2) / 32f,
                    ellipse.width / 2 / 32f);

                BuildSpot buildSpot = new BuildSpot(spot);
                toReturn[index] = buildSpot;
            }

            if (index >= toReturn.length) break;
            index++;
        }
        return toReturn;
    }


    public BuildSpot[] getBuildSpots() {
        return buildSpots;
    }
}
