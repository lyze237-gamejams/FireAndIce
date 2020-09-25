package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalTile;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;

public class CoinsColliderFilter implements CollisionFilter {
    public static final CoinsColliderFilter instance = new CoinsColliderFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (other.userData instanceof TileEntity) {
            if (other.userData instanceof PortalTile)
                return Response.slide;

            if (((TileEntity) other.userData).isHitbox())
                return Response.slide;

            return null;
        }

        if (other.userData instanceof Player)
            return Response.cross;

        return null;
    }
}
