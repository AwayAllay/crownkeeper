//package com.github.awayallay.screen;
//
//import com.badlogic.ashley.core.Component;
//import com.badlogic.ashley.core.Entity;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.viewport.ExtendViewport;
//import com.github.awayallay.AlienTakeover;
//import com.github.awayallay.entity.component.*;
//import com.github.awayallay.entity.factory.EntityFactory;
//import com.github.awayallay.entity.player.Player;
//import com.github.awayallay.entity.system.AnimationSystem;
//import com.github.awayallay.entity.system.BuildSpotInteractionSystem;
//import com.github.awayallay.entity.system.MovementSystem;
//import com.github.awayallay.entity.tower.Tower;
//import com.github.awayallay.input.GameInputHandler;
//import com.github.awayallay.map.MapLoader;
//import com.github.awayallay.ui.TowerBuildUI;
//import com.github.awayallay.ui.TowerInteractionUI;
//import com.github.awayallay.util.AnimationExtractor;
//import com.github.awayallay.util.Assets;
//
//public class TestScreen extends GameScreen {
//
//    private SpriteBatch batch;
//    private OrthographicCamera camera;
//    private ExtendViewport viewport;
//
//    private TiledMap map;
//    OrthogonalTiledMapRenderer renderer;
//
//    private EntityFactory factory;
//    private Entity entity = null;
//    private MapLoader mapLoader;
//
//    private GameInputHandler inputHandler;
//
//
//    //=======================[UI]====================
//    private TowerBuildUI towerBuildUI;
//
//
//    public TestScreen(AlienTakeover game, Assets assets, String assetBlock) {
//        super(game, assets, assetBlock);
//        factory = new EntityFactory();
//    }
//
//    @Override
//    public void show() {
//
//        assets.loadAssetBlock("field-test-assets");
//
//        camera = new OrthographicCamera();
//        viewport = new ExtendViewport(26, 16, camera);
//
//        batch = new SpriteBatch();
//        factory.registerFactorySystem(new AnimationSystem(batch));
//    }
//
//    @Override
//    public void render(float delta) {
//        assets.update();
//
//        if (!assets.finishedLoading()) return;
//
//        if (map == null) {
//            //map/testmap/testmap.tmx
//            map = assets.getAsset("map/fields/map/fieldTest.tmx");
//            renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
//            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
//            camera.position.set(layer.getWidth() / 2f, layer.getHeight() / 2f, 0);
//
//            mapLoader = new MapLoader(assets, "field-test-assets");
//            mapLoader.loadPaths();
//            mapLoader.loadBuildSpots();
//
//            factory.registerFactorySystem(new MovementSystem());
//            BuildSpotInteractionSystem b = new BuildSpotInteractionSystem(mapLoader, new TowerInteractionUI(assets, batch));
//            factory.registerFactorySystem(b);
//
//            TextureRegion[][] animations = new AnimationExtractor(assets).extractAnimations("entity/textures/barbarian-spritesheet.png", 32, 32);
//            AnimationComponent ani = new AnimationComponent(
//                null,
//                new Animation<>(0.05f, animations[1]),
//                null,
//                new Animation<>(0.05f, animations[0]),
//                null,
//                null,
//                null,
//                1f,
//                1f
//            );
//            AnimationComponent ani2 = new AnimationComponent(
//                null,
//                new Animation<>(0.05f, animations[1]),
//                null,
//                new Animation<>(0.05f, animations[0]),
//                null,
//                null,
//                null,
//                1f,
//                1f
//            );
//
//            Entity playerEntity = factory.createEntity(new Component[]{new HealthComponent(100), new PlayerComponent(), new VelocityComponent(8f, 8f), new FacingComponent(Facing.NORTH), ani2, new PositionComponent(camera.position.x, camera.position.y)});
//            Player player = new Player(playerEntity, factory);
//            factory.addEntity(player.getPlayerEntity());
//            towerBuildUI = new TowerBuildUI(player, assets);
//
//            inputHandler = new GameInputHandler(player, camera, viewport, map, b, towerBuildUI);
//            Gdx.input.setInputProcessor(inputHandler);
//            PathComponent pc = new PathComponent(mapLoader.getPath(0));
//            Vector2 v = pc.getPath()[pc.getCurrentVertex()];
//            PositionComponent posc = new PositionComponent(v.x, v.y);
//            VelocityComponent fv = new VelocityComponent(2f, 2f);
//
//            FacingComponent facingComponent = new FacingComponent(Facing.EAST);
//
//            entity = factory.createEntity(new Component[]{
//                pc,
//                fv,
//                ani,
//                posc,
//                facingComponent,
//                new HealthComponent(20)
//            });
//            factory.addEntity(entity);
//
//            player.setSelectedTowers(new Tower[]{new Tower(assets.getAsset("ui/tower-menu/tower/towerTest1.png"), factory, "entity/textures/tower/tower1Test.png"), null, null, null, null, null});
//        }
//
//        inputHandler.update(delta);
//        viewport.apply();
//        camera.update();
//
//        renderer.setView(camera);
//        renderer.render();
//
//        //TODO: render the random placed objects manually by establishing a MapRenderer class
//
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        factory.update(delta);
//        towerBuildUI.render(batch);
//        batch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height);
//        camera.update();
//    }
//
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//
//    }
//}
