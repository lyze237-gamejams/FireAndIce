package dev.lyze.parallelworlds.statics.assets;

import dev.lyze.parallelworlds.statics.Statics;
import dev.lyze.parallelworlds.statics.assets.levels.LevelAssets;
import dev.lyze.parallelworlds.statics.assets.levels.TestLevelAssets;
import dev.lyze.parallelworlds.statics.assets.levels.shared.SharedLevelAssets;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameAssets {
    @Getter
    private SharedLevelAssets sharedLevelAssets;

    @Getter
    private final List<LevelAssets> levels = new ArrayList<>();

    public void load() {
        sharedLevelAssets = new SharedLevelAssets(Statics.assets.createAssMan());
        sharedLevelAssets.load();

        levels.add(new TestLevelAssets(Statics.assets.createAssMan()));
        levels.forEach(DynamicAssets::load);
    }
}
