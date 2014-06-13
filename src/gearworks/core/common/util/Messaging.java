package gearworks.core.common.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Messaging {

    private static final Logger logger = Logger.getLogger ("Minecraft");

    public static void log (final Level level, String message, final Object... args){
        logger.log (level, (args.length != 0 ? String.format (message, args) : message));
    }

    public static void info (final String message, final Object... args){
        log (Level.INFO, message, args);
    }

    public static void severe (final String message, final Object... args){
        log (Level.SEVERE, message, args);
    }
}
