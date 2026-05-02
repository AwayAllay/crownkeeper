package com.github.awayallay.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Circle;
import com.github.awayallay.entity.component.ComponentMappers;
import com.github.awayallay.entity.component.FacingComponent;
import com.github.awayallay.entity.component.PlayerComponent;
import com.github.awayallay.entity.component.PositionComponent;
import com.github.awayallay.map.BuildSpot;
import com.github.awayallay.map.MapLoader;
import com.github.awayallay.ui.TowerInteractionUI;

public class BuildSpotInteractionSystem extends IteratingSystem {

    private final MapLoader mapLoader;
    private BuildSpot currentHovered = null;
    private final TowerInteractionUI towerInteractionUI;

    public BuildSpotInteractionSystem(MapLoader mapLoader, TowerInteractionUI towerInteractionUI) {
        super(Family.all(PlayerComponent.class, PositionComponent.class, FacingComponent.class).get());
        this.mapLoader = mapLoader;
        this.towerInteractionUI = towerInteractionUI;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        float playerReachInUnits = 0.5f;


        PositionComponent pos = ComponentMappers.position.get(entity);

        Circle reachCircle = new Circle(
            pos.getX(),
            pos.getY(),
            playerReachInUnits
        );

        currentHovered = null;


        for (BuildSpot buildSpot : mapLoader.getBuildSpots()) {

            if (buildSpot == null) continue;

            if (buildSpot.getBounds().overlaps(reachCircle)) {
                currentHovered = buildSpot;
                break;
            }
        }

        if (currentHovered != null) {
            towerInteractionUI.showTowerInteractionUI(currentHovered);
        }
    }


    public BuildSpot getCurrentHovered() {
        return currentHovered;
    }
}
