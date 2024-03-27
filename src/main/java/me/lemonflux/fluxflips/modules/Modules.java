package me.lemonflux.fluxflips.modules;

import java.util.*;
import me.lemonflux.fluxflips.utils.*;

public class Modules
{
    public static ArrayList<Module> modules;
    
    public static void init() {
        Modules.modules.add(new Module("Flipper", "Turn the flipper on and off", Category.GENERAL, Type.TOGGLE, "flipper", Config.gson.toJsonTree((Object)false), 1));
        Modules.modules.add(new Module("Flip Sounds", "Turn flip sounds on and off", Category.GENERAL, Type.TOGGLE, "flipsounds", Config.gson.toJsonTree((Object)false), 1));
        Modules.modules.add(new Module("Minimum Profit", "Turn the flipper on and off", Category.GENERAL, Type.INTEGER, "minprofit", Config.gson.toJsonTree((Object)100000), 1));
        Modules.modules.add(new Module("Minimum Demand", "Set the minimum sales per day for flip items", Category.GENERAL, Type.INTEGER, "mindemand", Config.gson.toJsonTree((Object)5), 1));
        Modules.modules.add(new Module("Auto-Buy", "Automatically purchase flips", Category.PREMIUM, Type.TOGGLE, "autoflip", Config.gson.toJsonTree((Object)false), 3));
        Modules.modules.add(new Module("One-Click Buy", "Buy flips with one click", Category.PREMIUM, Type.TOGGLE, "oneclickbuy", Config.gson.toJsonTree((Object)false), 3));
    }
    
    static {
        Modules.modules = new ArrayList<Module>();
    }
}
