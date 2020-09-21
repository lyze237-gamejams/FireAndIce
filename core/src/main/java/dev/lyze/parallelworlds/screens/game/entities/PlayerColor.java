package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

public enum PlayerColor {
    Red(Color.RED), Blue(Color.BLUE);

    @Getter
    private final Color renderColor;

    PlayerColor(Color renderColor) {
        this.renderColor = renderColor;
    }
}
