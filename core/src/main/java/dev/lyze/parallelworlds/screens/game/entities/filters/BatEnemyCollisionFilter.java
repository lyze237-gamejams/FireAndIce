package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalDirectionTile;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;

public class BatEnemyCollisionFilter implements CollisionFilter {
    public static final BatEnemyCollisionFilter instance = new BatEnemyCollisionFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (other.userData instanceof TileEntity) {
            var tileEntity = (TileEntity) other.userData;
            if (tileEntity.isHitbox())
                return Response.slide;

            if (tileEntity instanceof PortalDirectionTile)
                return Response.slide;

            return null;
        }

        if (other.userData instanceof Player)
            return Response.cross;

        return null;
    }
}
