package dev.lyze.parallelworlds.ui.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import lombok.Getter;

public class ClippedImage extends Image {
    private Vector2 sceneCoordinates = new Vector2();

    @Getter
    private final Rectangle clippingRectangle;


    public ClippedImage(Texture texture) {
        super(texture);

        clippingRectangle = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }

    public ClippedImage(TextureRegion region) {
        super(region);

        clippingRectangle = new Rectangle(0, 0, region.getRegionWidth(), region.getRegionHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        sceneCoordinates.set(0, 0);
        sceneCoordinates = localToStageCoordinates(sceneCoordinates);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.flush();
        if (clipBegin(sceneCoordinates.x + clippingRectangle.x, sceneCoordinates.y + clippingRectangle.y, clippingRectangle.width, clippingRectangle.height)) {
            super.draw(batch, parentAlpha);
            batch.flush();
            clipEnd();
        }
    }

    public void setClippingPercent(float width, float height) {
        clippingRectangle.set(0, 0, getWidth() * width, getHeight() * height);
    }
}


