package dev.lyze.parallelworlds.screens.game.entities.enums;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

public enum PlayerColor {
    Fire(new Color(228 / 256f, 59 / 256f, 68 / 256f, 1f)),
    Ice(new Color(0f, 149 / 256f, 233 / 256f, 1f));

    @Getter
    private final Color renderColor;

    PlayerColor(Color renderColor) {
        this.renderColor = renderColor;
    }
}
