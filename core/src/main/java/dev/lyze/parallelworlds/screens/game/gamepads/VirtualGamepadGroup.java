package dev.lyze.parallelworlds.screens.game.gamepads;

import com.badlogic.gdx.scenes.scene2d.Stage;
import dev.lyze.parallelworlds.screens.game.entities.Player;
import lombok.Getter;

import java.util.ArrayList;

public class VirtualGamepadGroup {
    private ArrayList<VirtualGamepad> gamepads = new ArrayList<>();

    @Getter
    protected boolean leftPressed;
    @Getter
    protected boolean rightPressed;
    @Getter
    protected boolean jumpJustPressed;

    public VirtualGamepadGroup(Player player, Stage mobileUi) {
        gamepads.add(new KeyboardGamepad(player));

        var touchpadGamepad = new TouchpadGamepad(player);
        touchpadGamepad.setup(mobileUi);
        gamepads.add(touchpadGamepad);

        player.setGamepadGroup(this);
    }

    public void update(float delta) {
        leftPressed = false;
        rightPressed = false;
        jumpJustPressed = false;

        for (VirtualGamepad g : gamepads) {
            g.update(delta);

            leftPressed |= g.leftPressed;
            rightPressed |= g.rightPressed;
            jumpJustPressed |= g.jumpJustPressed;
        }
    }

    public void reset(float delta) {
        gamepads.forEach(g -> g.reset(delta));
    }
}
