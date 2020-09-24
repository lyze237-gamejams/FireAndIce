package dev.lyze.parallelworlds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.impl.SlidingOutTransition;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.GameScreen;
import dev.lyze.parallelworlds.statics.Statics;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import dev.lyze.parallelworlds.ui.utils.ClippedImage;

import java.util.ArrayList;
import java.util.Objects;

public class LoadingScreen extends ManagedScreen {
    private static final Logger<LoadingScreen> logger = new Logger<>(LoadingScreen.class);

    private final Stage ui = new Stage(new ExtendViewport(1920, 1080));

    private DynamicAssets gameAssets;
    private ArrayList<DynamicAssets> assetsToLoad = new ArrayList<>();

    private boolean sceneSwitched;

    private ClippedImage loadingScreenLogo;

    @Override
    protected void create() {
        Statics.assets.getLoadingScreen().finishAndConsume();

        var table = new Table();
        table.setFillParent(true);

        loadingScreenLogo = new ClippedImage(Statics.assets.getLoadingScreen().getLogo());
        table.add(loadingScreenLogo).padRight(64).padBottom(64).bottom().right().expand();

        loadingScreenLogo.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(0.75f, Interpolation.exp5), Actions.fadeIn(0.75f, Interpolation.exp5))));

        ui.addActor(table);
    }

    @Override
    public void show() {
        super.show();

        gameAssets = (DynamicAssets) Objects.requireNonNull(pushParams)[0];

        assetsToLoad.clear();
        assetsToLoad.add(gameAssets);
        assetsToLoad.add(Statics.assets.getGame().getSharedLevelAssets());

        sceneSwitched = false;

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        ui.getViewport().apply();
        ui.act(delta);
        ui.draw();

        loadingScreenLogo.setClippingPercent(1, (float) assetsToLoad.stream().mapToDouble(a -> a.getAssMan().getProgress() / assetsToLoad.size()).sum());

        if (assetsToLoad.stream().allMatch(a -> a.getAssMan().update()) && !sceneSwitched) {
            assetsToLoad.forEach(DynamicAssets::consume);
            Statics.parallelWorlds.getScreenManager().pushScreen(GameScreen.class.getName(), SlidingOutTransition.class.getName(), gameAssets);
            sceneSwitched = true;
        }
    }

    @Override
    public void resize(int width, int height) {
        ui.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
