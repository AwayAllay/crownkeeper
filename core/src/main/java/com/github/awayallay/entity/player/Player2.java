package com.github.awayallay.entity.player;

import com.badlogic.ashley.core.Entity;
import com.github.awayallay.entity.GameEntity;
import com.github.awayallay.entity.factory.EntityFactory;
import com.github.awayallay.util.Assets;

public class Player2 extends GameEntity {


    protected Player2(EntityFactory entityFactory, Assets assets) {
        super(entityFactory, assets);
    }

    @Override
    protected Entity createEntityContainer() {
        return null;
    }

    @Override
    public void attack() {

    }
}
