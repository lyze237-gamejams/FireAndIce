package dev.lyze.parallelworlds.screens.game.gamepads;

import com.badlogic.gdx.controllers.AdvancedController;
import com.badlogic.gdx.controllers.Controllers;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;

public class ControllerGamepad extends VirtualGamepad {
    private boolean jumpHeld;

    public ControllerGamepad(Player player, int playerNumber) {
        super(player, playerNumber);
    }

    @Override
    public void update(float delta) {
        if (Controllers.getControllers().size <= playerNumber)
            return;

        var basic = Controllers.getControllers().get(playerNumber);
        if (!(basic instanceof AdvancedController))
            return;

        var controller = (AdvancedController) basic;
        var mapping = controller.getMapping();

        if (!jumpHeld)
            if (jumpJustPressed = controller.getButton(mapping.buttonA))
                jumpHeld = true;

        if (!controller.getButton(mapping.buttonA))
            jumpHeld = false;

        leftPressed = controller.getAxis(mapping.axisLeftX) < 0 ? Math.abs(controller.getAxis(mapping.axisLeftX)) : 0;
        rightPressed = controller.getAxis(mapping.axisLeftX) > 0 ? Math.abs(controller.getAxis(mapping.axisLeftX)) : 0;
    }

    @Override
    public void reset(float delta) {
        jumpJustPressed = false;
    }

    @Override
    public void vibrate(int durationInMs, float strength) {
        if (Controllers.getControllers().size <= playerNumber)
            return;

        var basic = Controllers.getControllers().get(playerNumber);
        if (!(basic instanceof AdvancedController))
            return;

        var controller = (AdvancedController) basic;

        if (!controller.canVibrate())
            return;

        controller.cancelVibration();
        controller.startVibration(durationInMs, strength);
    }
}
