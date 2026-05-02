package com.github.awayallay.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**Extracts the animations for an entity from a certain texture.*/
public class AnimationExtractor {

    private final Assets assets;

    public AnimationExtractor(Assets assets) {
        this.assets = assets;
    }

    /**Extracts all animations from the given file (.png) and returns them in a 2-dimensional Array.
     * @param filePath the path to the .png Sprite
     * @param tileWidthInPx the width of an animation-frame on the sprite in px, used for slicing.
     * @param tileHeightInPx the height of an animation-frame on the sprite in px, used for slicing.
     * @return all animations listed as Arrays of frames. Null otherwise
     * */
    public TextureRegion[][] extractAnimations(String filePath, int tileWidthInPx, int tileHeightInPx) {

        if (filePath == null || filePath.isEmpty() || !assets.isLoaded(filePath)) {
            return null;
        }

        Texture texture = assets.getAsset(filePath);
        TextureRegion[][] tmp = TextureRegion.split(texture, tileWidthInPx, tileHeightInPx);

        return tmp;
    }
}
