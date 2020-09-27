package dev.lyze.parallelworlds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import de.eskalon.commons.screen.ManagedScreen;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.statics.Statics;

import java.util.Objects;

public class EndScene extends ManagedScreen {
    private static final Logger<EndScene> logger = new Logger<>(EndScene.class);

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

        label = new TypingLabel("{EASE}{SPEED=SLOWER}Thanks for playing!{SPEED}\nYou earned a total of {VAR=COINS} coins\nand died {VAR=deaths} times.\n\nNot bad!\n\nThanks! - Made by {RAINBOW}@lyze237{ENDRAINBOW}, @ZeNsyse, @Borazilla", Statics.assets.getMainMenu().getSkin());
        root.add(label).top().left().expand().padLeft(32).padTop(32);

        ui.addActor(root);
    }

    @Override
    public void show() {
        super.show();

        Statics.assets.getMainMenu().getCaffeine().setLooping(true);
        Statics.assets.getMainMenu().getCaffeine().play();

        var coins = (int) Objects.requireNonNull(pushParams)[0];
        var deaths = (int) Objects.requireNonNull(pushParams)[1];

        label.setVariable("COINS", "" + coins);
        label.setVariable("DEATHS", "" + deaths);

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
