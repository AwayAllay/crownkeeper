package com.github.awayallay.screen;

import com.badlogic.gdx.Screen;
import com.github.awayallay.Crownkeeper;
import com.github.awayallay.util.Assets;

public abstract class GameScreen implements Screen {

    protected final Crownkeeper game;
    protected final Assets assets;
    protected final String assetBlock;

    protected GameScreen(Crownkeeper game, Assets assets, String assetBlock) {
        this.game = game;
        this.assets = assets;
        this.assetBlock = assetBlock;
    }
}
