package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalDirectionBlock;
import dev.lyze.parallelworlds.screens.game.entities.StaticEntity;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;

public class CoinsColliderFilter implements CollisionFilter {
    public static final CoinsColliderFilter instance = new CoinsColliderFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (other.userData instanceof StaticEntity) {
            if (other.userData instanceof PortalDirectionBlock)
                return null;

            return Response.slide;
        }

        if (other.userData instanceof Player)
            return Response.cross;

        return null;
    }
}
