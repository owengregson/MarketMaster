package me.lemonflux.fluxflips;

import me.lemonflux.fluxflips.utils.ColorScheme;
import org.apache.logging.log4j.*;

public class Reference
{
    public static final String MOD_ID = "mm";
    public static final String NAME = "MarketMaster";
    public static final String VERSION = "v1.8";
    public static final String DEVELOPER = "LemonFlux";
    public static final boolean PREMIUM = true;
    public static final ColorScheme colorScheme = ColorScheme.RED;
    public static final Logger logger;
    
    static {
        logger = LogManager.getLogger(NAME);
    }
}
