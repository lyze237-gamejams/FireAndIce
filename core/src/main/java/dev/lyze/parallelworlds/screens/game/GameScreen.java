package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.eskalon.commons.screen.ManagedScreen;
import dev.lyze.parallelworlds.screens.game.gamepads.VirtualGamepadGroup;
import dev.lyze.parallelworlds.statics.Statics;
import dev.lyze.parallelworlds.statics.assets.levels.LevelAssets;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;

public class GameScreen extends ManagedScreen {
    private final Stage ui = new Stage(new ExtendViewport(640, 320));
    private final Stage mobileUi = new Stage(new ExtendViewport(320 * 0.75f, 160 * 0.75f));
    private final Viewport gameViewport = new ExtendViewport(80, 40, new GameCamera());

    @Getter
    private LevelAssets levelAssets;

    @Getter
    private Level level;

    private ArrayList<VirtualGamepadGroup> gamepads = new ArrayList<>();

    @Override
    protected void create() {
        addInputProcessor(mobileUi);
    }

    @Override
    public void show() {
        super.show();

        levelAssets = (LevelAssets) pushParams[0];
        level = new Level(this, levelAssets.getMap(), gameViewport);
        level.initialize();

        level.getPlayers().getPlayers().forEach(p -> gamepads.add(new VirtualGamepadGroup(p, mobileUi)));

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private float t = 0.0f;
    private float dt = 0.01f;
    private double currentTime = System.currentTimeMillis();
    private float accumulator = 0f;

    @SneakyThrows
    @Override
    public void render(float delta) {
        var newTime = System.currentTimeMillis();
        var frameTime = (newTime - currentTime) / 1000f;
        accumulator += frameTime;
        currentTime = newTime;

        while (accumulator >= dt) {
            gamepads.forEach(g -> g.update(t));
            gameViewport.apply();
            level.update(t);
            ui.getViewport().apply();
            ui.act(t);

            if (Statics.isMobileDevice) {
                mobileUi.getViewport().apply();
                mobileUi.act(t);
            }
            gamepads.forEach(g -> g.reset(t));

            accumulator -= dt;
            t = dt;
        }

        Gdx.gl.glClearColor(0.2f, 0.1f, 0.4f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);


        gameViewport.apply();
        level.render();

        ui.getViewport().apply();
        ui.draw();

        if(Statics.isMobileDevice) {
            mobileUi.getViewport().apply();
            mobileUi.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        ui.getViewport().update(width, height, true);
        mobileUi.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}