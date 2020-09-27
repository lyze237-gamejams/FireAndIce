package dev.lyze.parallelworlds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.impl.HorizontalSlicingTransition;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.statics.Statics;

public class IntroScreen extends ManagedScreen {
    private static final Logger<IntroScreen> logger = new Logger<>(IntroScreen.class);

    private final Stage ui = new Stage(new ExtendViewport(1920, 1080));

    private float delay = 1.2f;

    private TypingLabel label;
    private boolean sceneSwitched;

    @Override
    protected void create() {
        Statics.assets.getMainMenu().finishAndConsume();

        addInputProcessor(ui);

        setupUi();
    }

    private void setupUi() {
        var root = new Table();
        root.setFillParent(true);

        label = new TypingLabel("{EASE}{SPEED=SLOWER}Glad that you arrived!{SPEED}\n" +
                "{ENDEASE}{COLOR=#743f39}Firebread{CLEARCOLOR} and {COLOR=#0095e9}Icebread{CLEARCOLOR} need your {RAINBOW}help!{ENDRAINBOW}\n" +
                "It looks like the evil {SHAKE}snail king{ENDSHAKE}...\n" +
                "{SPEED=SLOWER}...merged{SPEED} their worlds together!\n" +
                "Only {RAINBOW}you{ENDRAINBOW} can help them out of this mess...\n\n" +
                "{SPEED=SLOW}{SLIDE}save them please!{ENDSLIDE}{SPEED}\n\n" +
                "Ps. {JUMP}Jump{ENDJUMP} onto enemy souls to destroy them!{WAIT=5} ", Statics.assets.getMainMenu().getSkin());
        root.add(label).top().left().expand().padLeft(32).padTop(32);

        ui.addActor(root);
    }

    @Override
    public void show() {
        super.show();

        Statics.assets.getMainMenu().getCaffeine().setLooping(true);
        Statics.assets.getMainMenu().getCaffeine().play();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.706f * 0.25f, 0.851f * 0.25f, 0.847f * 0.25f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        if ((delay -= delta) > 0)
            return;

        if (Gdx.input.isTouched())
            label.skipToTheEnd();

        if (label.hasEnded() && !sceneSwitched) {
            Statics.assets.getMainMenu().getCaffeine().stop();
            Statics.parallelWorlds.getScreenManager().pushScreen(LoadingScreen.class.getName(), HorizontalSlicingTransition.class.getName(), "Nsyse_Tutorial");
            sceneSwitched = true;
        }

        ui.getViewport().apply();
        ui.act(delta);
        ui.draw();
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
