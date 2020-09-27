package dev.lyze.parallelworlds.statics.assets.sfx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;
import lombok.Getter;

public class MusicAssets extends DynamicAssets {
    @Getter @LoadAssetFromFile("music/BoosterLevel_1.mp3")
    private Music booster;

    @Getter @LoadAssetFromFile("music/8-Bit-Puzzler.mp3")
    private Music eightBitPuzzler;

    @Getter @LoadAssetFromFile("music/Arcade-Puzzler_v001.mp3")
    private Music arcadePuzzler;

    @Getter @LoadAssetFromFile("music/Caffeine-Crazed-Coin-Op-Kids.mp3")
    private Music caffeine;

    @Getter @LoadAssetFromFile("music/Cute-8-Bit-Monsters.mp3")
    private Music monsters;

    @Getter @LoadAssetFromFile("music/Pixel-Island.mp3")
    private Music pixelIsland;

    @Getter @LoadAssetFromFile("music/Up-the-Ladder.mp3")
    private Music upTheLadder;

    public MusicAssets(AssetManager assMan) {
        super(assMan);
    }

    public Music get(String name) {
        return getAssMan().get("music/" + name + ".mp3", Music.class);
    }
}
