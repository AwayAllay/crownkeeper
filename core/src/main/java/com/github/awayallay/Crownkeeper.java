package com.github.awayallay;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.github.awayallay.screen.LevelScreen;
//import com.github.awayallay.screen.TestScreen;
import com.github.awayallay.util.Assets;
import com.github.awayallay.util.JSONLoader;

public class Crownkeeper extends Game {

    @Override
    public void create() {

        Assets assets = new Assets(new JSONLoader().loadJSON("loader-info/asset-loader-information.json"));
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        //setScreen(new TestScreen(this, assets, "foobar"));
        setScreen(new LevelScreen(this, assets, "field-test-assets", 1f / 32f, 26, 16, "loader-info/map-information/lvl1.json"));

    }
}
