package com.github.awayallay.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.github.awayallay.entity.component.*;
import com.github.awayallay.entity.player.Player;
import com.github.awayallay.map.MapManager;

public class InputHandler implements InputProcessor {

    private final Player player;
    private final OrthographicCamera levelCamera;
    private final MapManager mapManager;
    private final int mapUnitWidth ,mapUnitHeight;
    private final float halfViewportWidth, halfViewportHeight;

    private boolean northPressed = false, eastPressed = false, southPressed = false, westPressed = false;

    public InputHandler(Player player, OrthographicCamera levelCamera, ExtendViewport levelViewport, MapManager mapManager) {
        this.player = player;
        this.levelCamera = levelCamera;
        this.mapManager = mapManager;
        this.mapUnitWidth = mapManager.getMapUnitWidth();
        this.mapUnitHeight = mapManager.getMapUnitHeight();
        this.halfViewportWidth = levelViewport.getWorldWidth() / 2f;
        this.halfViewportHeight = levelViewport.getWorldHeight() / 2f;
    }

    //Called every frame
    public void update(float deltaTime) {

        VelocityComponent velocity = ComponentMappers.velocity.get(player.getEntity());
        PositionComponent position = ComponentMappers.position.get(player.getEntity());

        float playerY = position.getY();
        float playerX = position.getX();

        if (northPressed) playerY = position.getY() + velocity.getY() * deltaTime;
        if (eastPressed && !westPressed) playerX = position.getX() + velocity.getX() * deltaTime;
        if (southPressed) playerY = position.getY() - velocity.getY() * deltaTime;
        if (westPressed && !eastPressed) playerX = position.getX() - velocity.getX() * deltaTime;

        playerX = MathUtils.clamp(playerX, 0, mapUnitWidth);
        playerY = MathUtils.clamp(playerY, 0, mapUnitHeight);

        if (!mapManager.isSpotBlocked(playerX, playerY)) {
            position.setX(playerX);
            position.setY(playerY);
        }
        else if (!mapManager.isSpotBlocked(playerX, position.getY())){
            position.setX(playerX);
            position.setY(position.getY());
        }
        else if (!mapManager.isSpotBlocked(position.getX(), playerY)) {
            position.setX(position.getX());
            position.setY(playerY);
        }

        levelCamera.position.x = MathUtils.clamp(position.getX(), halfViewportWidth, mapUnitWidth - halfViewportWidth);
        levelCamera.position.y = MathUtils.clamp(position.getY(), halfViewportHeight, mapUnitHeight - halfViewportHeight);

        levelCamera.update();
    }

    @Override
    public boolean keyDown(int keycode) {
        FacingComponent facing = ComponentMappers.facing.get(player.getEntity());
        AnimationComponent animation = ComponentMappers.animation.get(player.getEntity());

        switch (keycode) {

            case Input.Keys.W -> {
                facing.setFacing(Facing.NORTH);
                northPressed = true;
            }
            case Input.Keys.D -> {
                facing.setFacing(Facing.EAST);
                if (!westPressed) animation.setCurrent(animation.getWalkEast());
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
//                if (buildspotInteractionSystem.getCurrentHovered() != null) {
//                    if (!towerBuildUI.isOpen()) {
//                        towerBuildUI.open(buildspotInteractionSystem.getCurrentHovered());
//                    } else {
//                        towerBuildUI.close();
//                    }
//                }
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
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
