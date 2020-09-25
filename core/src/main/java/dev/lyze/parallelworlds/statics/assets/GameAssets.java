package dev.lyze.parallelworlds.statics.assets;

import dev.lyze.parallelworlds.statics.Statics;
import dev.lyze.parallelworlds.statics.assets.levels.LevelAssets;
import dev.lyze.parallelworlds.statics.assets.levels.TestLevelAssets;
import dev.lyze.parallelworlds.statics.assets.levels.shared.SharedLevelAssets;
import lombok.Getter;

import java.util.HashMap;

public class GameAssets {
    @Getter
    private SharedLevelAssets sharedLevelAssets;

    @Getter
    private final HashMap<String, LevelAssets> levels = new HashMap<>();

    public void load() {
        sharedLevelAssets = new SharedLevelAssets(Statics.assets.createAssMan());
        sharedLevelAssets.load();

        levels.put("Test", new TestLevelAssets(Statics.assets.createAssMan()));
        levels.put("1", new TestLevelAssets(Statics.assets.createAssMan()));
        levels.forEach((s, levelAssets) -> levelAssets.load());
    }
}
