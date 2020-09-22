package dev.lyze.parallelworlds.screens.game.map.properties;

import dev.lyze.parallelworlds.screens.game.entities.enums.Direction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkedEnemyMapProperties extends MapProperties {
    private Direction direction;
}
