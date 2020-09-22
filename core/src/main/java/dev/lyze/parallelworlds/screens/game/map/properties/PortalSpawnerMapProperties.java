package dev.lyze.parallelworlds.screens.game.map.properties;

import dev.lyze.parallelworlds.screens.game.entities.enums.PlayerColor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortalSpawnerMapProperties extends MapProperties {
    private PlayerColor color;
}
