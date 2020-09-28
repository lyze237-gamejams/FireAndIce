package dev.lyze.parallelworlds.screens.game.gamepads;

import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import lombok.Getter;

public abstract class VirtualGamepad {
    @Getter
    protected final Player player;
    @Getter
    protected final int playerNumber;

    @Getter
    protected float leftPressed;
    @Getter
    protected float rightPressed;
    @Getter
    protected boolean jumpJustPressed;

    public VirtualGamepad(Player player, int playerNumber) {
        this.player = player;
        this.playerNumber = playerNumber;
    }

    public abstract void update(float delta);

    public abstract void reset(float delta);

    public abstract void vibrate(int durationInMs, float strength);

    public abstract void dispose();
}
