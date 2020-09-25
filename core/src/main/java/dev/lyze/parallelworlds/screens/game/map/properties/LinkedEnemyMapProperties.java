package dev.lyze.parallelworlds.screens.game.map.properties;

import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemy;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.impl.BatEnemy;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.impl.SnailEnemy;
import dev.lyze.parallelworlds.screens.game.entities.enums.Direction;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkedEnemyMapProperties extends MapProperties {
    private boolean invertedWorld;
    private Types entity;
    private int height;

    public enum Types {
        Snail(SnailEnemy.class), Bat(BatEnemy.class);

        @Getter
        private final Class<? extends LinkedEnemy> entityClass;

        Types(Class<? extends LinkedEnemy> entityClass) {
            this.entityClass = entityClass;
        }
    }
}
