package dev.lyze.parallelworlds.screens.game.gamepads;

import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import lombok.Getter;

public abstract class VirtualGamepad {
    @Getter
    protected final Player player;

    @Getter
    protected boolean leftPressed;
    @Getter
    protected boolean rightPressed;
    @Getter
    protected boolean jumpJustPressed;

    public VirtualGamepad(Player player) {
        this.player = player;
    }

    public abstract void update(float delta);

    public abstract void reset(float delta);
}
