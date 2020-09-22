package dev.lyze.parallelworlds.screens.game.gamepads;

import com.badlogic.gdx.scenes.scene2d.Stage;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import lombok.Getter;

import java.util.ArrayList;

public class VirtualGamepadGroup {
    private ArrayList<VirtualGamepad> gamepads = new ArrayList<>();

    @Getter
    protected float leftPressed;
    @Getter
    protected float rightPressed;
    @Getter
    protected boolean jumpJustPressed;

    public VirtualGamepadGroup(Player player, int playerNumber, Stage mobileUi) {
        gamepads.add(new KeyboardGamepad(player, playerNumber));

        gamepads.add(new ControllerGamepad(player, playerNumber));

        var touchpadGamepad = new TouchpadGamepad(player, playerNumber);
        touchpadGamepad.setup(mobileUi);
        gamepads.add(touchpadGamepad);

        player.setGamepadGroup(this);
    }

    public void update(float delta) {
        leftPressed = 0;
        rightPressed = 0;
        jumpJustPressed = false;

        for (VirtualGamepad g : gamepads) {
            g.update(delta);

            leftPressed = Math.max(leftPressed, g.leftPressed);
            rightPressed = Math.max(rightPressed, g.rightPressed);
            jumpJustPressed |= g.jumpJustPressed;
        }
    }

    public void reset(float delta) {
        gamepads.forEach(g -> g.reset(delta));
    }
}
