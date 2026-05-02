package com.github.awayallay.map;

import com.badlogic.gdx.math.Circle;
import com.github.awayallay.entity.tower.Tower;

public class BuildSpot {

    private final Circle bounds;
    private boolean hasBuilding = false;

    private Tower buildTower = null;

    public BuildSpot(Circle bounds) {
        this.bounds = bounds;
    }

    public Circle getBounds() {
        return bounds;
    }

    public boolean isHasBuilding() {
        return hasBuilding;
    }

    public void setHasBuilding(boolean hasBuilding) {
        this.hasBuilding = hasBuilding;
    }

    public Tower getBuildTower() {
        return buildTower;
    }

    public void setBuildTower(Tower buildTower) {
        this.buildTower = buildTower;
    }
}
