package dev.lyze.parallelworlds.screens.game.gamepads;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.screens.game.entities.players.PlayerColor;

public class KeyboardGamepad extends VirtualGamepad {
    public KeyboardGamepad(Player player, int playerNumber) {
        super(player, playerNumber);
    }

    @Override
    public void update(float delta) {
        leftPressed = Gdx.input.isKeyPressed(player.getColor() == PlayerColor.Ice ? Input.Keys.A : Input.Keys.LEFT) ? 1 : 0;
        rightPressed = Gdx.input.isKeyPressed(player.getColor() == PlayerColor.Ice ? Input.Keys.D : Input.Keys.RIGHT) ? 1 : 0;
        jumpJustPressed = Gdx.input.isKeyJustPressed(player.getColor() == PlayerColor.Ice ? Input.Keys.W : Input.Keys.UP);
    }

    @Override
    public void reset(float delta) {

    }
}
