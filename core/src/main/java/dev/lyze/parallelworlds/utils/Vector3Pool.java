package dev.lyze.parallelworlds.utils;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class Vector3Pool extends Pool<Vector3> {
    public static final Vector3Pool instance = new Vector3Pool();

    @Override
    protected Vector3 newObject() {
        return new Vector3();
    }

    @Override
    protected void reset(Vector3 object) {
        object.set(0, 0, 0);
    }


}
