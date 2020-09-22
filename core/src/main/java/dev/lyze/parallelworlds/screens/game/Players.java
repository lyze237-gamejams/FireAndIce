package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lyze.parallelworlds.screens.game.entities.players.FirePlayer;
import dev.lyze.parallelworlds.screens.game.entities.players.FrozenPlayer;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.screens.game.entities.players.PlayerColor;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class Players {

    @Getter
    private final ArrayList<Player> players = new ArrayList<>();

    @Getter
    private final Player firePlayer, icePlayer;
    private final Level level;

    public Players(Level level) {
        this.level = level;

        players.add(firePlayer = new FirePlayer(level));
        players.add(icePlayer = new FrozenPlayer(level));

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

    public void debugTextRender(BitmapFont font, Camera camera, SpriteBatch screenBatch) {
        players.forEach(p -> p.debugTextRender(font, camera, screenBatch));
    }
}
