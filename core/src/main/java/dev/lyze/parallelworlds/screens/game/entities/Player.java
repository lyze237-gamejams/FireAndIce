package dev.lyze.parallelworlds.screens.game.entities;

import dev.lyze.parallelworlds.screens.game.World;

public class Player extends Entity {
    public Player(World world) {
        super(16 * 3, 16 * 8, 16, 16, world);
    }
}
