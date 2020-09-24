package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.eskalon.commons.screen.ManagedScreen;
import dev.lyze.parallelworlds.screens.game.gamepads.VirtualGamepadGroup;
import dev.lyze.parallelworlds.statics.Statics;
import dev.lyze.parallelworlds.statics.assets.levels.LevelAssets;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Objects;

public class GameScreen extends ManagedScreen {
    private final Stage ui = new Stage(new ExtendViewport(640, 320));
    private final Stage mobileUi = new Stage(new ExtendViewport(320 * 0.75f, 160 * 0.75f));
    private final Viewport gameViewport = new ExtendViewport(80, 40, new GameCamera());

    private Label coinLabel;

    @Getter
    private LevelAssets levelAssets;

    @Getter
    private Level level;

    private ArrayList<VirtualGamepadGroup> gamepads = new ArrayList<>();

    @Override
    protected void create() {
        var root = new Table();
        root.setFillParent(true);

        var inner = new Table();
        inner.add(new Image(Statics.assets.getGame().getSharedLevelAssets().getParticlesAtlas().getCoins_idle().first())).size(25);
        coinLabel = new Label("0", Statics.assets.getGame().getSharedLevelAssets().getSkin());
        inner.add(coinLabel).padLeft(12).padTop(6);


        root.add(inner).expand().top().left().padLeft(12).padTop(12);
        ui.addActor(root);

        addInputProcessor(mobileUi);
    }

    @Override
    public void show() {
        super.show();

        levelAssets = (LevelAssets) Objects.requireNonNull(pushParams)[0];
        level = new Level(this, levelAssets.getMap(), gameViewport);
        level.initialize();

        level.getPlayers().getPlayers().forEach(p -> gamepads.add(new VirtualGamepadGroup(p, gamepads.size(), mobileUi)));

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private float actualDeltaTime = 0.0f;
    private final float targetDeltaTime = 0.01f;
    private double currentTime = System.currentTimeMillis();
    private float accumulator = 0f;

    @Override
    public void render(float delta) {
        var newTime = System.currentTimeMillis();
        var frameTime = (newTime - currentTime) / 1000f;
        accumulator += frameTime;
        currentTime = newTime;

        while (accumulator >= targetDeltaTime) {
            update();

            accumulator -= targetDeltaTime;
            actualDeltaTime = targetDeltaTime;
        }

        render();
    }

    private void update() {
        gamepads.forEach(g -> g.update(actualDeltaTime));
        gameViewport.apply();

        level.update(actualDeltaTime);
        coinLabel.setText(level.getCoinCount());

        ui.getViewport().apply();
        ui.act(actualDeltaTime);

        if (Statics.isMobileDevice) {
            mobileUi.getViewport().apply();
            mobileUi.act(actualDeltaTime);
        }
        gamepads.forEach(g -> g.reset(actualDeltaTime));
    }

    private void render() {
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