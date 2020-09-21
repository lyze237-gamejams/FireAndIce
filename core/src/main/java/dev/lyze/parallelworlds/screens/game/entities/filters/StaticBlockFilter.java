package dev.lyze.parallelworlds.screens.game.entities.filters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.parallelworlds.screens.game.entities.StaticEntity;

public class StaticBlockFilter implements  CollisionFilter {
    public static final StaticBlockFilter instance = new StaticBlockFilter();

    @Override
    public Response filter(Item item, Item other) {
        return other.userData instanceof StaticEntity ? Response.slide : Response.cross;
    }
}
