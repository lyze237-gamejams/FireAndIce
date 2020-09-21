package dev.lyze.parallelworlds.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Vector2Pool extends Pool<Vector2> {
    public static final Vector2Pool instance = new Vector2Pool();

    @Override
    protected Vector2 newObject() {
        return new Vector2();
    }

    @Override
    protected void reset(Vector2 object) {
        object.set(0, 0);
    }


}
