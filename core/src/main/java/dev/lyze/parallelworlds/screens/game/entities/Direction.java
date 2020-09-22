package dev.lyze.parallelworlds.screens.game.entities;

import lombok.Getter;

public enum Direction {
    Up(1), Down(-1), Left(-1), Right(1);

    @Getter
    private final int axisStep;

    Direction(int axisStep) {
        this.axisStep = axisStep;
    }
}
