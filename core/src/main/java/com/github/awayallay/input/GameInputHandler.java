package com.github.awayallay.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.github.awayallay.entity.component.*;
import com.github.awayallay.entity.player.Player;
import com.github.awayallay.entity.system.BuildSpotInteractionSystem;
import com.github.awayallay.ui.TowerBuildUI;

public class GameInputHandler implements InputProcessor {

    private final Player player;
    private final OrthographicCamera camera;
    private boolean northPressed = false, eastPressed = false, southPressed = false, westPressed = false;

    private final BuildSpotInteractionSystem buildspotInteractionSystem;
    private final TowerBuildUI towerBuildUI;

    private final int mapHeightInUnits, mapWidthInUnits;
    private final float halfMapHeightInUnits, halfMapWidthInUnits;

    public GameInputHandler(Player player, OrthographicCamera camera, ExtendViewport viewport, TiledMap map, BuildSpotInteractionSystem buildspotInteractionSystem, TowerBuildUI towerBuildUI) {
        this.player = player;
        this.camera = camera;
        this.buildspotInteractionSystem = buildspotInteractionSystem;

        mapHeightInUnits = map.getProperties().get("height", Integer.class);
        halfMapHeightInUnits = viewport.getWorldHeight() / 2f;

        mapWidthInUnits = map.getProperties().get("width", Integer.class);
        halfMapWidthInUnits = viewport.getWorldWidth() / 2f;
        this.towerBuildUI = towerBuildUI;
    }

    //Called every frame
    public void update(float deltaTime) {

        VelocityComponent velocity = ComponentMappers.velocity.get(player.getPlayerEntity());
        PositionComponent position = ComponentMappers.position.get(player.getPlayerEntity());

        float playerY = position.getY();
        float playerX = position.getX();

        if (northPressed) playerY = position.getY() + velocity.getY() * deltaTime;
        if (eastPressed) playerX = position.getX() + velocity.getX() * deltaTime;
        if (southPressed) playerY = position.getY() - velocity.getY() * deltaTime;
        if (westPressed) playerX = position.getX() - velocity.getX() * deltaTime;

        playerX = MathUtils.clamp(playerX, 0, mapWidthInUnits);
        playerY = MathUtils.clamp(playerY, 0, mapHeightInUnits);

        position.setX(playerX);
        position.setY(playerY);

        camera.position.x = MathUtils.clamp(position.getX(), halfMapWidthInUnits, mapWidthInUnits - halfMapWidthInUnits);
        camera.position.y = MathUtils.clamp(position.getY(), halfMapHeightInUnits, mapHeightInUnits - halfMapHeightInUnits);

        camera.update();
    }


    @Override
    public boolean keyDown(int keycode) {

        FacingComponent facing = ComponentMappers.facing.get(player.getPlayerEntity());
        AnimationComponent animation = ComponentMappers.animation.get(player.getPlayerEntity());

        switch (keycode) {

            case Input.Keys.W -> {
                facing.setFacing(Facing.NORTH);
                northPressed = true;
            }
            case Input.Keys.D -> {
                facing.setFacing(Facing.EAST);
                animation.setCurrent(animation.getWalkEast());
                eastPressed = true;
            }
            case Input.Keys.S -> {
                facing.setFacing(Facing.SOUTH);
                southPressed = true;
            }
            case Input.Keys.A -> {
                facing.setFacing(Facing.WEST);
                animation.setCurrent(animation.getWalkWest());
                westPressed = true;
            }
            case Input.Keys.E -> {
                if (buildspotInteractionSystem.getCurrentHovered() != null) {
                    if (!towerBuildUI.isOpen()) {
                        towerBuildUI.open(buildspotInteractionSystem.getCurrentHovered());
                    } else {
                        towerBuildUI.close();
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {

            case Input.Keys.W -> {
                northPressed = false;
            }
            case Input.Keys.D -> {
                eastPressed = false;
            }
            case Input.Keys.S -> {
                southPressed = false;
            }
            case Input.Keys.A -> {
                westPressed = false;
            }
        }

        if (!(northPressed || eastPressed || southPressed || westPressed)) {
            //TODO: set currentAnimation to idle
        }

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        towerBuildUI.scroll(amountY);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (towerBuildUI.isOpen()) {
            towerBuildUI.selectTower();
        }
        else {
            player.attack();
        }


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
