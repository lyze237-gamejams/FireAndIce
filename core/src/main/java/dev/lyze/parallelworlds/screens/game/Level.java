package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.Player;
import dev.lyze.parallelworlds.screens.game.entities.PlayerColor;
import dev.lyze.parallelworlds.statics.Statics;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class Level {
    private static final Logger<Level> logger = new Logger<>(Level.class);
    private final Viewport viewport;
    private final GameScreen game;

    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final ShapeDrawer shapeDrawer;

    @Getter
    private final Map map;

    @Getter
    private final World<Entity> world;

    @Getter
    private final Players players;

    @Getter
    private final ArrayList<Entity> entities = new ArrayList<>();

    private final BitmapFont debugFont;

    public Level(GameScreen game, TiledMap tiledMap, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;

        world = new World<>(1);
        map = new Map(game, tiledMap);

        players = new Players(this);

        shapeDrawer = new ShapeDrawer(spriteBatch, new TextureRegion(Statics.assets.getGame().getSharedLevelAssets().getPixel()));
        shapeDrawer.setDefaultLineWidth(0.1f);

        debugFont = Statics.assets.getMainMenu().getSkin().getFont("Debug");
    }

    public void initialize() {
        map.initialize();
    }

    public void update(float delta) {
        players.update(delta);

        entities.forEach(e -> e.update(world, delta));

        updateCamera();
    }

    public void render() {
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        map.render((OrthographicCamera) viewport.getCamera());

        spriteBatch.begin();
        players.render(spriteBatch);
        entities.forEach(e -> e.render(spriteBatch));

        //DEBUG LINES
        players.debugRender(shapeDrawer);

        shapeDrawer.setColor(Color.GREEN);
        entities.forEach(e -> e.debugRender(shapeDrawer));

        shapeDrawer.setColor(Color.CYAN);
        map.debugRender(shapeDrawer);
        shapeDrawer.circle(viewport.getCamera().position.x, viewport.getCamera().position.y, 1);

        spriteBatch.end();

        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.begin();
        players.debugTextRender(debugFont, viewport.getCamera(), spriteBatch);
        spriteBatch.end();
    }

    private void updateCamera() {
        var cam = (GameCamera) viewport.getCamera();

        cam.lerpToPlayers(players.getRedPlayer().getPosition(), players.getBluePlayer().getPosition());
        cam.zoomToPlayers(players.getRedPlayer().getPosition(), players.getBluePlayer().getPosition());
        cam.keepInBoundaries(map.getBoundaries());

        cam.update();
    }

    public void spawnPlayer(String name, int x, int y) {
        logger.logInfo("Spawning player " + name + " at " + x + "/" + y);

        var playerColor = PlayerColor.valueOf(name);
        Player player = players.getPlayer(playerColor);
        player.getPosition().set(x, y);
        world.update(player.getItem(), x, y);
    }
}
