package dev.lyze.parallelworlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import de.eskalon.commons.screen.transition.impl.HorizontalSlicingTransition;
import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import de.eskalon.commons.screen.transition.impl.SlidingOutTransition;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.GameScreen;
import dev.lyze.parallelworlds.screens.LoadingScreen;
import dev.lyze.parallelworlds.screens.MainMenuScreen;
import dev.lyze.parallelworlds.statics.Statics;

public class ParallelWorlds extends ManagedGame<ManagedScreen, ScreenTransition> {
    private static final Logger<ParallelWorlds> logger = new Logger<>(ParallelWorlds.class);

    private SpriteBatch batch;

    @Override
    public void create() {
        super.create();

        logger.logInfo("Hello!");

        setupStatics();
        setupScreens();

        this.screenManager.pushScreen(MainMenuScreen.class.getName(), null);
    }

    private void setupScreens() {
        batch = new SpriteBatch();

        this.screenManager.addScreen(LoadingScreen.class.getName(), new LoadingScreen());
        this.screenManager.addScreen(MainMenuScreen.class.getName(), new MainMenuScreen());
        this.screenManager.addScreen(GameScreen.class.getName(), new GameScreen());

        screenManager.addScreenTransition(BlendingTransition.class.getName(), new BlendingTransition(batch, 1F, Interpolation.pow2In));
        screenManager.addScreenTransition(SlidingOutTransition.class.getName(), new SlidingOutTransition(batch, SlidingDirection.DOWN, 0.35F));
        screenManager.addScreenTransition(HorizontalSlicingTransition.class.getName(), new HorizontalSlicingTransition(batch, 5, 1F, Interpolation.exp5In));
    }

    @Override
    public void render() {
        super.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.F9)) {
            Statics.debugging = !Statics.debugging;
            Statics.isMobileDevice = Statics.debugging;
        }
    }

    private void setupStatics() {
        Statics.parallelWorlds = this;
        Statics.assets.load();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }
}