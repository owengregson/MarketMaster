package me.lemonflux.fluxflips.commands.subcommand;

import net.minecraft.command.*;
import me.lemonflux.fluxflips.utils.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import me.lemonflux.fluxflips.client.*;
import java.io.*;
import com.google.gson.*;

public class Toggle implements Subcommand
{
    public static void updateConfig() {
    }
    
    @Override
    public String getCommandName() {
        return "toggle";
    }
    
    @Override
    public boolean isHidden() {
        return false;
    }
    
    @Override
    public String getCommandUsage() {
        return "";
    }
    
    @Override
    public String getCommandDescription() {
        return "Toggles the flipper on or off";
    }
    
    @Override
    public boolean processCommand(final ICommandSender sender, final String[] args) {
        final JsonObject config = Config.getConfig();
        if (config.get("flipper").getAsBoolean()) {
            Config.write("flipper", Config.gson.toJsonTree((Object)false));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "Flipper disabled."));
        }
        else {
            Config.write("flipper", Config.gson.toJsonTree((Object)true));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "Flipper enabled."));
        }
            Client.start();
        return true;
    }
}
