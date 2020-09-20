package dev.lyze.parallelworlds.logger;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class Logger<TClass> {
    private String prefix;

    public Logger(Class<TClass> clazz) {
        prefix = clazz.getSimpleName();
    }
    
    public void logFatal(String message, Exception exception) {
        log(Level.Fatal, message, exception);
    }

    public void logFatal(String message) {
        log(Level.Fatal, message);
    }
    
    public void logError(String message, Exception exception) {
        log(Level.Error, message, exception);
    }

    public void logError(String message) {
        log(Level.Error, message);
    }
    
    public void logWarn(String message, Exception exception) {
        log(Level.Warn, message, exception);
    }

    public void logWarn(String message) {
        log(Level.Warn, message);
    }

    public void logInfo(String message, Exception exception) {
        log(Level.Info, message, exception);
    }

    public void logInfo(String message) {
        log(Level.Info, message);
    }

    public void logDebug(String message, Exception exception) {
        log(Level.Debug, message, exception);
    }

    public void logDebug(String message) {
        log(Level.Debug, message);
    }

    private void log(Level level, String message, Exception exception) {
        var time = " (" + Gdx.graphics.getFrameId() + ") ";

        if (level == Level.Debug)
            Gdx.app.debug(level.toString(), prefix + time + message, exception);
        else
            Gdx.app.log(level.toString(), prefix + time + message, exception);
    }

    private void log(Level level, String message) {
        var time = " (" + Gdx.graphics.getFrameId() + ") ";

        if (level == Level.Debug)
            Gdx.app.debug(level.toString(), prefix + time + message);
        else
            Gdx.app.log(level.toString(), prefix + time + message);
    }

    enum Level {
        Debug, Info, Warn, Error, Fatal
    }
}
