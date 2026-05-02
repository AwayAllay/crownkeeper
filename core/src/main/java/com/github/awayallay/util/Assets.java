package com.github.awayallay.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonValue;

/**Class for managing all the assets used throughout the game, exept for JSON-Files. Does that by making use of the
 * existing AssetManager-class.
 * @see AssetManager*/
public class Assets {

    /**The AssetManager used as base of operation for the class*/
    private final AssetManager assetManager;
    /**JSON-File containing a list of assets that all need to be loaded for a certain Screen or part of the game.
     * This file is located here: 'assets/loader-info/asset-loader-information.json'*/
    private final JsonValue loaderInformation;

    public Assets(JsonValue loaderInformation) {
        this.loaderInformation = loaderInformation;
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader()); //To be able to load TiledMaps
    }


    /**Queues all assets used in this block at once. The blockValue corresponds to the key in the
     * 'assets/loader-info/asset-loader-information.json' file, which provides a list of objects of the resources that
     * need to be loaded for this block.
     * @param blockValue key to list of Resources in the json file.*/
    public void loadAssetBlock(String blockValue) {

        JsonValue blockAssetPaths = loaderInformation.get(blockValue);

        if (blockAssetPaths == null) {
            System.out.println("No assets provided for Block " + blockValue);
            return;
        }

        for (JsonValue entry : blockAssetPaths) {
            loadAsset(entry.getString("path"), getType(entry.getString("type")));
        }
    }


    /*Transforms the given classType provided as a String to an Actual Class.*/
    private Class<?> getType(String classType) {
        switch (classType) {
            case "Texture": return Texture.class;
            case "TiledMap": return TiledMap.class;
            default: throw new IllegalArgumentException("Unknown type: " + classType);
        }
    }


    /**Queues a given asset to be loaded. THIS DOES NOT AUTOMATICALLY LOAD THE ASSET!
     * @param fileName Path to the file to load.
     * @param classType Type as which the resource should be loaded, should correspond to the actual type of the file,
     *                  e.g. Texture.class for images used as Textures.
     */
    public <T> void loadAsset(String fileName, Class<T> classType) {
        assetManager.load(fileName,  classType);
    }

    /**Gets the asset corresponding to the given Path.
     * @param filePath Path to the file that should be returned loaded.
     * @return the loaded Recourse, null otherwise*/
    public <T> T getAsset(String filePath) {

        try {
            if (assetManager.isLoaded(filePath)) {
                return assetManager.get(filePath);
            }
        } catch (GdxRuntimeException e) {
            return null;
        }

        return null;
    }

    /**Check whether the manager finished loading all the queued assets.*/
    public boolean finishedLoading() {
        return assetManager.isFinished();
    }

    /**Gets the Progress of the manager loading the queued assets.*/
    public float getLoadingProgress() {
        return assetManager.getProgress();
    }


    /**Used to update the processes in the Manager. Has to be called every frame or so to ensure proper loading of
     * all queued assets.*/
    public void update() {
        assetManager.update();
    }

    /**Check whether the resource was loaded or not.*/
    public boolean isLoaded(String filePath) {
        return assetManager.isLoaded(filePath);
    }

    /**Unloads the given resource*/
    public void unload(String fileName) {
        assetManager.unload(fileName);
    }

    /**Disposes this Manager*/
    public void dispose() {
        assetManager.dispose();
    }
}
