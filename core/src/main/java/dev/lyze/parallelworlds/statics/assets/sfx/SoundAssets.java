package dev.lyze.parallelworlds.statics.assets.sfx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;
import lombok.Getter;

import java.util.Random;

public class SoundAssets extends DynamicAssets {
    @Getter @LoadAssetFromFile("sounds/Action Misc 8.ogg")
    private Sound actionMisc8;

    @Getter @LoadAssetFromFile("sounds/Coin 1.ogg")
    private Sound coin1;

    @Getter @LoadAssetFromFile("sounds/Coin 2.ogg")
    private Sound coin2;

    @Getter @LoadAssetFromFile("sounds/Coin Total Win 2.ogg")
    private Sound coinTotalWin2;

    @Getter @LoadAssetFromFile("sounds/Coin Total Win.ogg")
    private Sound coinTotalWin1;

    @Getter @LoadAssetFromFile("sounds/Extra Life.ogg")
    private Sound extraLife;

    @Getter @LoadAssetFromFile("sounds/Magic Dark.ogg")
    private Sound magicDark;

    @Getter @LoadAssetFromFile("sounds/Power Up 1.ogg")
    private Sound powerUp1;

    @Getter @LoadAssetFromFile("sounds/Power Up 2.ogg")
    private Sound powerUp2;

    @Getter @LoadAssetFromFile("sounds/Power Up 4.ogg")
    private Sound powerUp4;

    @Getter @LoadAssetFromFile("sounds/Power Up 5.ogg")
    private Sound powerUp5;

    @Getter @LoadAssetFromFile("sounds/Sword 1.ogg")
    private Sound sword1;

    @Getter @LoadAssetFromFile("sounds/Sword 2.ogg")
    private Sound sword2;

    @Getter @LoadAssetFromFile("sounds/Jump 1.ogg")
    private Sound jump1;

    @Getter @LoadAssetFromFile("sounds/Jump 3.ogg")
    private Sound jump3;

    @Getter @LoadAssetFromFile("sounds/Fall.ogg")
    private Sound fall;

    private final Random random = new Random();

    public SoundAssets(AssetManager assMan) {
        super(assMan);
    }

    public void play(float volume, Sound... sounds) {
        sounds[random.nextInt(sounds.length)].play(volume);
    }

    public void playSmallPitch(float volume, Sound... sounds) {
        sounds[random.nextInt(sounds.length)].play(volume, (random.nextFloat() / 3f) + 0.9f, 0);
    }

    public void playBigPitch(float volume, Sound... sounds) {
        sounds[random.nextInt(sounds.length)].play(volume, random.nextFloat() + 0.8f, 0);
    }
}
