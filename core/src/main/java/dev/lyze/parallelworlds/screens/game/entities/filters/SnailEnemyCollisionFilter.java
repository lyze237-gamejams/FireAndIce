package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;

public class SnailEnemyCollisionFilter implements CollisionFilter {
    public static final SnailEnemyCollisionFilter instance = new SnailEnemyCollisionFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (other.userData instanceof TileEntity)
            return ((TileEntity) other.userData).isHitbox() ? Response.slide : null;

        if (other.userData instanceof Player)
            return Response.cross;

        return null;
    }
}
