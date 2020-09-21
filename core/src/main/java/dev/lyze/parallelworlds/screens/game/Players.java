package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.lyze.parallelworlds.screens.game.entities.Player;
import dev.lyze.parallelworlds.screens.game.entities.PlayerColor;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class Players {

    @Getter
    private final ArrayList<Player> players = new ArrayList<>();

    @Getter
    private final Player redPlayer, bluePlayer;
    private final Level level;

    public Players(Level level) {
        this.level = level;

        players.add(redPlayer = new Player(level, PlayerColor.Red, false));
        players.add(bluePlayer = new Player(level, PlayerColor.Blue, true));

        players.forEach(p -> p.addToWorld(level.getWorld()));
    }

    public void update(float delta) {
        players.forEach(p -> p.update(level.getWorld(), delta));
    }

    public void render(SpriteBatch batch) {
        players.forEach(p -> p.render(batch));
    }

    public void debugRender(ShapeDrawer shapeRenderer) {
        players.forEach(p -> p.debugRender(shapeRenderer));
    }

    public Player getPlayer(PlayerColor playerColor) {
        return players.stream().filter(p -> p.getColor() == playerColor).findFirst().get();
    }
}
