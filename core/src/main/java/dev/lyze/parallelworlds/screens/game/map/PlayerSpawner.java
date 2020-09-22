package dev.lyze.parallelworlds.screens.game.map;

import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.PlayerSpawnerMapProperties;
import dev.lyze.parallelworlds.utils.Point;

import java.util.HashMap;

public class PlayerSpawner extends MapSpawner<PlayerSpawnerMapProperties> {
    private static final Logger<PlayerSpawner> logger = new Logger<>(PlayerSpawner.class);

    public PlayerSpawner(Level level, Map map) {
        super(level, map, PlayerSpawnerMapProperties.class);
    }

    @Override
    public void spawnInternal(int x, int y, PlayerSpawnerMapProperties properties, HashMap<Point, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning player " + properties.getPlayer() + " at " + x + "/" + y);

        Player player = level.getPlayers().getPlayer(properties.getPlayer());
        player.getPosition().set(x, y);
        level.getWorld().update(player.getItem(), x, y);
    }
}
