package com.github.awayallay.entity.tower;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.awayallay.entity.component.AnimationComponent;
import com.github.awayallay.entity.component.ComponentMappers;
import com.github.awayallay.entity.component.PositionComponent;
import com.github.awayallay.entity.factory.EntityFactory;
import com.github.awayallay.map.BuildSpot;
import com.github.awayallay.util.AnimationExtractor;
import com.github.awayallay.util.Assets;

public class Tower {

    private final Texture inGameBuildMenuTexture;
    private final EntityFactory factory;

    private final String texturePath;

    public Tower(Texture inGameBuildMenuTexture, EntityFactory factory, String texturePath) {
        this.inGameBuildMenuTexture = inGameBuildMenuTexture;
        this.factory = factory;

        this.texturePath = texturePath;
    }

    public Texture getInGameBuildMenuTexture() {
        return inGameBuildMenuTexture;
    }

    public void build(BuildSpot currentSpot, Assets assets) {

        PositionComponent pos = new PositionComponent();
        pos.setX(currentSpot.getBounds().x);
        pos.setY(currentSpot.getBounds().y + currentSpot.getBounds().radius / 2);


        TextureRegion[][] animations =  new AnimationExtractor(assets).extractAnimations(texturePath, 64, 64);
        AnimationComponent anim = new AnimationComponent(
            null,
            null,
            null,
            new Animation<>(0.1f, animations[0]),
            null,
            null,
            null,
            2f,
            2f
        );
        anim.setCurrent(anim.getWalkWest());


        Entity towerEntity = factory.createEntity(new Component[]{pos, anim});
        factory.addEntity(towerEntity);

        Tower tower = new Tower(inGameBuildMenuTexture, factory, texturePath);

        currentSpot.setBuildTower(tower);
        currentSpot.setHasBuilding(true);
    }
}
