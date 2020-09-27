package dev.lyze.parallelworlds.statics.assets.sfx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;
import lombok.Getter;

public class MusicAssets extends DynamicAssets {
    @Getter @LoadAssetFromFile("music/BoosterLevel_1.ogg")
    private Music booster;

    @Getter @LoadAssetFromFile("music/8-Bit-Puzzler.ogg")
    private Music eightBitPuzzler;

    @Getter @LoadAssetFromFile("music/Arcade-Puzzler_v001.ogg")
    private Music arcadePuzzler;

    @Getter @LoadAssetFromFile("music/Caffeine-Crazed-Coin-Op-Kids.ogg")
    private Music caffeine;

    @Getter @LoadAssetFromFile("music/Cute-8-Bit-Monsters.ogg")
    private Music monsters;

    @Getter @LoadAssetFromFile("music/Pixel-Island.ogg")
    private Music pixelIsland;

    @Getter @LoadAssetFromFile("music/Up-the-Ladder.ogg")
    private Music upTheLadder;

    public MusicAssets(AssetManager assMan) {
        super(assMan);
    }

    public Music get(String name) {
        return getAssMan().get("music/" + name, Music.class);
    }
}
