package com.github.awayallay.entity.component;

import com.badlogic.ashley.core.Component;

public class FacingComponent implements Component {

    private Facing facing;


    public FacingComponent(Facing facing) {
        this.facing = facing;
    }

    public FacingComponent() {
        this.facing = Facing.SOUTH;
    }

    public Facing getFacing() {
        return facing;
    }

    public void setFacing(Facing facing) {
        this.facing = facing;
    }
}
