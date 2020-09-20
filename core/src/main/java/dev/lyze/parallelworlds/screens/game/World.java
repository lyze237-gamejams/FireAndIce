package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.Player;
import lombok.Getter;

import java.util.ArrayList;

public class World {
    private final Viewport viewport;
    private final GameScreen game;

    private SpriteBatch spriteBatch = new SpriteBatch();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Getter
    private Map map;

    @Getter
    private Player player;

    private ArrayList<Entity> entities = new ArrayList<>();

    public World(GameScreen game, Map map, Viewport viewport) {
        this.game = game;
        this.map = map;
        this.viewport = viewport;

        player = new Player(this);
    }

    public void update(float delta) {
        player.update(delta);

        entities.forEach(e -> e.update(delta));
    }

    public void render() {
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        map.render((OrthographicCamera) viewport.getCamera());

        spriteBatch.begin();
        player.render(spriteBatch);
        entities.forEach(e -> e.render(spriteBatch));
        spriteBatch.end();

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.CYAN);
        player.debugRender(shapeRenderer);
        shapeRenderer.setColor(Color.GREEN);
        entities.forEach(e -> e.debugRender(shapeRenderer));
        shapeRenderer.end();
    }
}
