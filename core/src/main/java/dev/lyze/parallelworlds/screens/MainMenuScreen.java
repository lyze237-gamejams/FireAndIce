package dev.lyze.parallelworlds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.impl.HorizontalSlicingTransition;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.statics.Statics;

import java.util.HashSet;
import java.util.Set;

public class MainMenuScreen extends ManagedScreen {
    private static final Logger<MainMenuScreen> logger = new Logger<>(MainMenuScreen.class);

    private final Stage backgroundUi = new Stage(new FillViewport(384, 216));
    private final Stage textUi = new Stage(new ExtendViewport(1920, 1080));

    private final Set<Action> animationActions = new HashSet<Action>();

    @Override
    protected void create() {
        Statics.assets.getMainMenu().finishAndConsume();

        addInputProcessor(textUi);

        setupUi();
    }

    private void setupUi() {
        Array<TextureAtlas.AtlasRegion> layers = Statics.assets.getMainMenu().getAtlas().getLayers();

        setupBackground(layers);
        setupTitle(layers.size * 0.5f);
        setupButtons((layers.size-1) * 0.5f);
    }

    private void setupBackground(Array<TextureAtlas.AtlasRegion> layers) {
        for (int i = 0; i < layers.size; i++) {
            var layer = layers.get(i);

            Image image = new Image(new Sprite(layer));
            backgroundUi.addActor(image);

            Actions.sequence(Actions.fadeIn(2), Actions.run(() -> System.out.println("HEY")));
            registerAction(image, Actions.sequence(Actions.fadeOut(0), Actions.delay(i * 0.5f), Actions.fadeIn(0.4f)));
        }
    }

    private void setupTitle(float initialDelay) {
        var titleTable = new Table();
        titleTable.setFillParent(true);

        titleTable.add(new Label("Fire and Ice", Statics.assets.getMainMenu().getSkin(), "title"))
                .padTop(140).padLeft(140).left().top().expand();
        textUi.addActor(titleTable);

        registerAction(titleTable, Actions.sequence(
                Actions.moveBy(-2000, 0, 0),
                Actions.delay(initialDelay), Actions.moveBy(2000, 0, 0.75f, Interpolation.exp5Out)));
    }

    private void setupButtons(float initialDelay) {
        var menuTable = new Table();
        menuTable.setFillParent(true);

        var menuSubTable = new Table();
        addButton(menuSubTable, "Start new game", initialDelay + 0.15f).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Statics.assets.getMainMenu().getMusic().stop();
                Statics.parallelWorlds.getScreenManager().pushScreen(IntroScreen.class.getName(), HorizontalSlicingTransition.class.getName(), "Nsyse_Tutorial");
            }
        });
        addButton(menuSubTable, "Exit", initialDelay + 0.15f * 3).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        menuTable.add(menuSubTable).right().padRight(100).padBottom(100).expand();
        textUi.addActor(menuTable);

        registerAction(menuTable, Actions.sequence(
                Actions.moveBy(1000, 0, 0),
                Actions.delay(1f),
                Actions.moveBy(-1000, 0, 0)));
    }

    private TextButton addButton(Table table, String name, float initialDelay) {
        var button = new TextButton(name, Statics.assets.getMainMenu().getSkin());
        table.add(button).right().row();

        registerAction(button, Actions.sequence(
                Actions.moveBy(800, 0, 1),
                Actions.delay(initialDelay), Actions.moveBy(-800, 0, 0.25f, Interpolation.exp5Out)));

        return button;
    }

    private void registerAction(Actor actor, Action action) {
        animationActions.add(action);
        actor.addAction(action);
    }

    @Override
    public void show() {
        super.show();

        Statics.assets.getMainMenu().getMusic().setLooping(true);
        Statics.assets.getMainMenu().getMusic().play();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.706f, 0.851f, 0.847f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonJustPressed(0)) {
            for (Action action : animationActions) {
                //noinspection StatementWithEmptyBody
                while (!action.act(100)) ; // finish all animations, needs multiple calls since a sequence only steps the current action
            }
        }

        backgroundUi.getViewport().apply();
        backgroundUi.act(delta);
        backgroundUi.draw();

        textUi.getViewport().apply();
        textUi.act(delta);
        textUi.draw();
    }

    @Override
    public void resize(int width, int height) {
        backgroundUi.getViewport().update(width, height, true);
        textUi.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
