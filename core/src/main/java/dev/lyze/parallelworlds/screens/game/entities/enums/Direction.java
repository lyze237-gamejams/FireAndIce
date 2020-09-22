package dev.lyze.parallelworlds.screens.game.entities.enums;

import lombok.Getter;

public enum Direction {
    Up(1), Down(-1), Left(-1), Right(1);

    @Getter
    private final int axisStep;

    Direction(int axisStep) {
        this.axisStep = axisStep;
    }
}
