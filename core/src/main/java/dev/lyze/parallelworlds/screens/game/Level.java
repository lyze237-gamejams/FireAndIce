package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.Player;
import dev.lyze.parallelworlds.screens.game.entities.PlayerColor;
import dev.lyze.parallelworlds.statics.Statics;
import dev.lyze.parallelworlds.utils.Vector3Pool;
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
    private final ArrayList<Player> players = new ArrayList<>();

    @Getter
    private final ArrayList<Entity> entities = new ArrayList<>();

    public Level(GameScreen game, TiledMap tiledMap, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;

        world = new World<>();
        map = new Map(game, tiledMap);

        players.add(new Player(this, PlayerColor.Red));
        players.add(new Player(this, PlayerColor.Blue));

        shapeDrawer = new ShapeDrawer(spriteBatch, new TextureRegion(Statics.assets.getGame().getSharedLevelAssets().getPixel()));
        shapeDrawer.setDefaultLineWidth(0.1f);
    }

    public void initialize() {
        map.initialize();
    }

    public void update(float delta) {
        players.forEach(p -> p.update(delta));
        entities.forEach(e -> e.update(delta));

        updateCamera();
    }

    public void render() {
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        map.render((OrthographicCamera) viewport.getCamera());

        spriteBatch.begin();
        players.forEach(p -> p.render(spriteBatch));
        entities.forEach(e -> e.render(spriteBatch));

        //DEBUG LINES
        players.forEach(p -> p.debugRender(shapeDrawer));

        shapeDrawer.setColor(Color.GREEN);
        entities.forEach(e -> e.debugRender(shapeDrawer));

        shapeDrawer.setColor(Color.CYAN);
        map.debugRender(shapeDrawer);
        shapeDrawer.circle(viewport.getCamera().position.x, viewport.getCamera().position.y, 1);

        spriteBatch.end();
    }

    private void updateCamera() {
        var cam = (OrthographicCamera) viewport.getCamera();

        lerpToPlayers(cam);
        zoomToPlayers(cam);
        keepInBoundaries(cam);

        cam.update();
    }

    private void zoomToPlayers(OrthographicCamera cam) {
        var redPlayerViewport = Vector3Pool.instance.obtain();
        var bluePlayerViewport = Vector3Pool.instance.obtain();

        redPlayerViewport.set(getRedPlayer().getPosition().x / Gdx.graphics.getWidth(), getRedPlayer().getPosition().y / Gdx.graphics.getHeight(), 0);
        bluePlayerViewport.set(getBluePlayer().getPosition().x / Gdx.graphics.getWidth(), getBluePlayer().getPosition().y / Gdx.graphics.getHeight(), 0);

        cam.project(redPlayerViewport);
        cam.project(bluePlayerViewport);

        var viewportDist = redPlayerViewport.dst(bluePlayerViewport);
        if (viewportDist > 0.8f) {
            cam.zoom = cam.zoom + 0.01f;
        }
        if (cam.zoom < 0.5f) {
            cam.zoom = 1f;
        }
    }

    private void lerpToPlayers(OrthographicCamera cam) {
        var avgX = (getRedPlayer().getPosition().x + getBluePlayer().getPosition().x) / 2f;
        var avgY = (getRedPlayer().getPosition().y + getBluePlayer().getPosition().y) / 2f;

        cam.position.x = cam.position.x + (avgX - cam.position.x) * 0.1f;
        cam.position.y = cam.position.y + (avgY - cam.position.y) * 0.1f;
    }

    private void keepInBoundaries(OrthographicCamera cam) {
        var boundaries = map.getBoundaries();

        var halfViewportWidth = (cam.viewportWidth * cam.zoom) / 2f;
        var halfViewportHeight = (cam.viewportHeight * cam.zoom) / 2f;

        cam.position.x = MathUtils.clamp(cam.position.x, boundaries.getX() + halfViewportWidth, boundaries.getX() + boundaries.getWidth() - halfViewportWidth);
        cam.position.y = MathUtils.clamp(cam.position.y, boundaries.getY() + halfViewportHeight, boundaries.getY() + boundaries.getHeight() - halfViewportHeight);
    }

    public void spawnPlayer(String name, int x, int y) {
        logger.logInfo("Spawning player " + name + " at " + x + "/" + y);

        var playerColor = PlayerColor.valueOf(name);
        getPlayer(playerColor).getPosition().set(x, y);
    }

    public Player getPlayer(PlayerColor playerColor) {
        return players.get(0).getColor() == playerColor ? players.get(0) : players.get(1);
    }

    public Player getRedPlayer() {
        return getPlayer(PlayerColor.Red);
    }

    public Player getBluePlayer() {
        return getPlayer(PlayerColor.Blue);
    }
}
