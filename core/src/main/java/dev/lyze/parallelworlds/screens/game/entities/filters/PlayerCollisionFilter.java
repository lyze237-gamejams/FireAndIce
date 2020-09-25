package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.impl.EnemyBarrierTile;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalTile;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalDirectionTile;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;

public class PlayerCollisionFilter implements  CollisionFilter {
    public static final PlayerCollisionFilter instance = new PlayerCollisionFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (!(item.userData instanceof Player))
            return null;

        if (other.userData instanceof PortalDirectionTile)
            return Response.cross;

        if (other.userData instanceof PortalTile) {
            var portal = (PortalTile) other.userData;
            if (portal.getColor() == null || portal.getColor() == ((Player) item.userData).getColor())
                return Response.cross;

            return Response.slide;
        }

        if (other.userData instanceof EnemyBarrierTile)
            return null;

        if (other.userData instanceof TileEntity) {
            if (((TileEntity) other.userData).isHitbox())
                return Response.slide;

            return Response.cross;
        }

        if (other.userData instanceof Player)
            return Response.slide;

        return null;
    }
}
