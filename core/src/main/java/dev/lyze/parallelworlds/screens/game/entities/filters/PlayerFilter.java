package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.Player;
import dev.lyze.parallelworlds.screens.game.entities.PortalBlock;
import dev.lyze.parallelworlds.screens.game.entities.PortalDirectionBlock;
import dev.lyze.parallelworlds.screens.game.entities.StaticEntity;

public class PlayerFilter implements  CollisionFilter {
    public static final PlayerFilter instance = new PlayerFilter();

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

        if (other.userData instanceof StaticEntity)
            return Response.slide;

        return null;
    }
}
