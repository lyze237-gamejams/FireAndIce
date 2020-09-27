package dev.lyze.parallelworlds.screens.game.entities.impl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;
import dev.lyze.parallelworlds.screens.game.entities.filters.CoinsColliderFilter;
import dev.lyze.parallelworlds.screens.game.entities.particles.ParticlesExplosion;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;

public class BigCoinTile extends TileEntity {
    private final Animation<TextureAtlas.AtlasRegion> animation = new Animation<>(0.1f, Statics.assets.getGame().getCharactersAtlas().getCoins_bigCoin(), Animation.PlayMode.LOOP_PINGPONG);

    private float animationTime;
    private boolean invertedGravity;

    public BigCoinTile(float x, float y, Level level, boolean invertedGravity) {
        super(x, y, 2, 2, level);

        this.invertedGravity = invertedGravity;
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        this.animationTime += delta;

        var response = world.move(item, position.x, position.y, CoinsColliderFilter.instance);
        for (int i = 0; i < response.projectedCollisions.size(); i++)
            onCollision(response.projectedCollisions.get(i));
    }

    private void onCollision(Collision collision) {
        if (!(collision.other.userData instanceof Player))
            return;

        for (int i = 0; i < 25; i++) {
            level.addEntity(new ParticlesExplosion(position.x, position.y, level, invertedGravity));
        }

        level.removeEntity(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(animation.getKeyFrame(animationTime), position.x, position.y, getWidth(), getHeight());
    }
}
