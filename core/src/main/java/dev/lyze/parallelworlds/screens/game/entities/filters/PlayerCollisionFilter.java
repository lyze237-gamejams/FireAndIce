package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalBlock;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalDirectionBlock;
import dev.lyze.parallelworlds.screens.game.entities.StaticEntity;

public class PlayerCollisionFilter implements  CollisionFilter {
    public static final PlayerCollisionFilter instance = new PlayerCollisionFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (!(item.userData instanceof Player))
            return null;

        var player = (Player) item.userData;

        if (other.userData instanceof PortalDirectionBlock)
            return Response.cross;

        if (other.userData instanceof PortalBlock) {
            var portal = (PortalBlock) other.userData;
            if (portal.getColor() == null || portal.getColor() == player.getColor())
                return Response.cross;

            return Response.slide;
        }

        if (other.userData instanceof StaticEntity) {
            if (((StaticEntity) other.userData).isCollidable())
                return Response.slide;
            return Response.cross;
        }

        if (other.userData instanceof Player)
            return Response.slide;

        return null;
    }
}
