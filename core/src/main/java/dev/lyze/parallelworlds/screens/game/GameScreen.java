package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.eskalon.commons.screen.ManagedScreen;
import dev.lyze.parallelworlds.statics.Statics;
import dev.lyze.parallelworlds.statics.assets.levels.LevelAssets;
import lombok.Getter;

public class GameScreen extends ManagedScreen {
    private final Stage ui = new Stage(new ExtendViewport(640, 320));
    private final Viewport gameViewport = new ExtendViewport(640, 320);

    @Getter
    private LevelAssets levelAssets;

    @Getter
    private World world;

    public GameScreen() {
    }

    @Override
    protected void create() {
        var uiAtlas = Statics.assets.getGame().getSharedLevelAssets().getUiAtlas();
    }

    @Override
    public void show() {
        super.show();

        levelAssets = (LevelAssets) pushParams[0];
        world = new World(this, new Map(this, levelAssets.getMap()), gameViewport);

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.1f, 0.4f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        gameViewport.apply();
        world.update(delta);
        world.render();

        ui.getViewport().apply();
        ui.act(delta);
        ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
        ui.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}