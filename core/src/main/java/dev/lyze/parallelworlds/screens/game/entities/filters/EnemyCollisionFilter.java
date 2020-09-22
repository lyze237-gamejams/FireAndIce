package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.StaticEntity;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;

public class EnemyCollisionFilter implements CollisionFilter {
    public static final EnemyCollisionFilter instance = new EnemyCollisionFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (other.userData instanceof StaticEntity)
            return Response.slide;

        if (other.userData instanceof Player)
            return Response.cross;

        return null;
    }
}
