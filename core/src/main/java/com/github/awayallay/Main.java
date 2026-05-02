//package com.github.awayallay;
//
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.ApplicationListener;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.maps.MapLayer;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.utils.viewport.ExtendViewport;
//import com.github.awayallay.util.Assets;
//
///** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
//public class Main extends ApplicationAdapter {
//
//    SpriteBatch spriteBatch;
//    OrthographicCamera camera;
//    ExtendViewport extendViewport;
//    Assets assets;
//
//    @Override
//    public void create () {
//
//        spriteBatch = new SpriteBatch();
//        camera = new OrthographicCamera(200, 100);
//        extendViewport = new ExtendViewport(200, 100, camera);
//
//        assets = new Assets();
//
//
//    }
//
//    @Override
//    public void resize (int width, int height) {
//        extendViewport.update(width, height);
//    }
//
//    @Override
//    public void render () {
//
//        assets.update();
//
//        if (assets.finishedLoading()) {
//            extendViewport.apply();
//            camera.update();
//
//            Texture t = assets.getAsset("libgdx.png");
//
//            spriteBatch.setProjectionMatrix(camera.combined);
//            spriteBatch.begin();
//            spriteBatch.draw(t, 0, 0);
//            spriteBatch.end();
//        }
//        else {
//            System.out.println(assets.getLoadingProgress());
//        }
//    }
//
//    @Override
//    public void pause () {
//    }
//
//    @Override
//    public void resume () {
//    }
//
//    @Override
//    public void dispose () {
//    }
//
//
//}
