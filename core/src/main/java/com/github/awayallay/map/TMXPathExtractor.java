package com.github.awayallay.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.github.awayallay.util.exceptions.NoPathsException;

import java.util.Random;



/**Extracts all paths of a given map that can be walked by npcs as Vertor2[].Note that paths should be saved on
 * an individual layer called "path-layer" and should be named after their index starting from 0.
 * @see Vector2
 * @see TiledMap
 * @see TiledMapTileLayer*/
public class TMXPathExtractor { //TODO: implement security for wrong or no named paths

    private Vector2[][] paths;
    private final Random random;

    public TMXPathExtractor(TiledMap map) throws NoPathsException {

        random = new Random();

        MapLayer pathLayer =  map.getLayers().get("path-layer");
        paths = extractPathsFromLayer(pathLayer);

    }


    /**Returns an Array of vertices representing a path on the map.
     * @param pathIndex the index of the path, represented by the name of the path as it should be named after its index.
     * @return Verctor2[] of vertices, null otherwise*/
    public Vector2[] getPath(int pathIndex) {
        if (pathIndex >= paths.length) {
            return null;
        }

        return paths[pathIndex];
    }


    public Vector2[][] getPaths() {
        return paths;
    }


    /**Returns a random path on the map.*/
    public Vector2[] getRandomPath() {
        return getPath(random.nextInt(paths.length));
    }



    /**Extracts all the paths from the path-layer of the map and saves them as readable coordinate Arrays in paths. The
     * paths are saved according to their name, that should also represent their index, so path with name "0" is at index
     * 0 and so on.
     * @return 2-dimensional Array representing the vertices of all the paths in the path-layer
     * @param pathLayer the path-layer the paths should be extracted from
     * @throws NoPathsException if the path layer does not contain any paths*/
    private Vector2[][] extractPathsFromLayer(MapLayer pathLayer) throws NoPathsException {

        MapObjects pathObjects = pathLayer.getObjects();

        if (pathObjects.getCount() <= 0) {
            throw new NoPathsException("path-layer of the map does not contain any paths."); //TODO: display the name of the map
        }

        Vector2[][] pathCoordinates = new Vector2[pathObjects.getCount()][];

        for (int i = 0; i < pathCoordinates.length; i++) {
            Vector2[] coords = transformPolylineToCoords(extractPath(i, pathObjects));
            if (coords == null) break;
            pathCoordinates[i] = coords;
        }

        return pathCoordinates;
    }


    /**Extracts the path with the given name as a Polyline from the MapObjects. Note that paths should be named their index starting
     * at 0 because the id of the tiled object can vary.
     * @return Polyline representing the desired path. Null otherwise.
     * @param pathIndex the index representing the path name
     * @param pathObjects the MapObjects to extract the path from*/

    private Polyline extractPath(int pathIndex, MapObjects pathObjects) {

        MapObject lineObject = pathObjects.get(Integer.toString(pathIndex));

        if (lineObject == null) return null;

        return ((PolylineMapObject) lineObject).getPolyline();
    }


    /**Transforms the vertices from a Polyline into a more readable Vector2[].
     * @return Array of vertices coordinates, null otherwise.
     * @param line 2-dimensional line of vertices.
     * */
    private Vector2[] transformPolylineToCoords(Polyline line) {

        if (line == null) return null;

        float[] verticesArr = line.getTransformedVertices(); //saved in format {x,y,x1,y1,x2,y2}

        if (verticesArr.length <= 1) return null;

        Vector2[] vertices = new Vector2[verticesArr.length / 2];

        int verticesIndex = 0;
        for (int i = 0; i < verticesArr.length; i+=2) {

            if ((i + 1) >= verticesArr.length) break;
            float x = verticesArr[i];
            float y = verticesArr[i + 1];

            x /= 32f;
            y /= 32f;

            vertices[verticesIndex] = new Vector2(x, y);
            verticesIndex++;
        }

        return vertices;
    }


}
