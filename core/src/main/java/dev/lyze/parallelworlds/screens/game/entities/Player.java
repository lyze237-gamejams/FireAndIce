package dev.lyze.parallelworlds.screens.game.entities;

import dev.lyze.parallelworlds.screens.game.Level;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends GravityEntity {
    @Getter
    private PlayerColor color;

    public Player(Level level, PlayerColor color) {
        super(0, 0, 1, 1, level);

        this.color = color;
    }

    @Override
    public void debugRender(ShapeDrawer shapes) {
        shapes.setColor(color.getRenderColor());
        super.debugRender(shapes);
    }
}
