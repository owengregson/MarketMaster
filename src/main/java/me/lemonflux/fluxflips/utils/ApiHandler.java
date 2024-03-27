package me.lemonflux.fluxflips.utils;

import me.lemonflux.fluxflips.client.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.*;
import net.minecraft.event.*;
import net.minecraft.client.*;
import me.lemonflux.fluxflips.events.*;
import net.minecraft.util.*;
import com.google.gson.*;
import net.minecraft.client.audio.*;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;

import java.util.*;

public class ApiHandler
{
    public static ArrayList<String> cache;
    private static double balance;
    
    public static void getFlips() {
    	
        boolean found = false;
        final JsonObject config = Config.getConfig();
        //Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "It returned url: " + Utils.getApi()));
        final JsonElement json = Utils.getJson(Config.api());
        JsonArray arr = null;
        long autoBuyCooldown = 0L;
        if (json != null) {
            arr = json.getAsJsonArray();
            //Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "Connected to API successfully!"));
        }
        else {
        	byte[] apierrorb = (Base64.getDecoder().decode("RXJyb3IgY29ubmVjdGluZyB0byBBUEkhIFJldHJ5aW5nLi4u"));
    	    String apierrors = new String(apierrorb);
        	final ChatComponentText fai = new ChatComponentText(Utils.getPrefix("default") + apierrors);
        	Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)fai);
            //System.out.println("Flipper restarting due to network failure...");
            Client.start();
        }
        for (final JsonElement flip : arr) {
            final JsonObject info = flip.getAsJsonObject();
            byte[] viewaucb = (Base64.getDecoder().decode("L3ZpZXdhdWN0aW9uIA=="));
    	    String viewauction = new String(viewaucb);
    	    byte[] uuidb = (Base64.getDecoder().decode("dXVpZA=="));
    	    String uuid = new String(uuidb);
    	    byte[] profitb = (Base64.getDecoder().decode("cHJvZml0"));
    	    String profit = new String(profitb);
    	    byte[] minb = (Base64.getDecoder().decode("bWlu"));
    	    String min = new String(minb);
    	    byte[] salesb = (Base64.getDecoder().decode("c2FsZXM="));
    	    String sales = new String(salesb);
    	    byte[] msgautobuyb = (Base64.getDecoder().decode("QXV0by1CdXkgaXMgZW5hYmxlZC4gQnV5aW5nIG5vdy4uLg=="));
    	    String msgautobuy = new String(msgautobuyb);
    	    
            if (info.get("price").getAsDouble() <= ApiHandler.balance && info.get(profit).getAsDouble() > config.get(min + profit).getAsInt() && !ApiHandler.cache.contains(info.get(uuid).getAsString()) && info.has(sales) && info.get(sales).getAsInt() >= config.get(min + "demand").getAsInt()) {

            	found = true;
                final ChatComponentText msg = new ChatComponentText(Utils.getPrefix("default") + Utils.getColors(Utils.getPrefix("colors")).get("dark") + new String(info.get("name").getAsString().getBytes(), StandardCharsets.UTF_8) + EnumChatFormatting.GRAY + " was just found for " + Utils.getColors(Utils.getPrefix("colors")).get("dark") + Utils.format(info.get("price").getAsDouble()) + EnumChatFormatting.GRAY + " coins." + EnumChatFormatting.GREEN + " +" + Utils.format(info.get("profit").getAsDouble()) + " profit! " + EnumChatFormatting.BLACK + "[" + Utils.getColors(Utils.getPrefix("colors")).get("dark") + "C" + Utils.getColors(Utils.getPrefix("colors")).get("light") + "L" + EnumChatFormatting.WHITE + "I" + Utils.getColors(Utils.getPrefix("colors")).get("light") + "C" + Utils.getColors(Utils.getPrefix("colors")).get("dark") + "K" + EnumChatFormatting.BLACK + "]");
                msg.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, viewauction + info.get(uuid).getAsString())));
                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)msg);
                if(config.get("autoflip").getAsBoolean() && System.currentTimeMillis() - autoBuyCooldown > 700L) {
                	GuiEvents.nameOfItem =  new String(info.get("name").getAsString().getBytes(), StandardCharsets.UTF_8);
                	GuiEvents.itemPrice = new String(Utils.format(info.get("price").getAsDouble()));
                	autoBuyCooldown = System.currentTimeMillis();
                	Minecraft.getMinecraft().thePlayer.sendChatMessage(viewauction + info.get(uuid).getAsString());
                	final ChatComponentText ale = new ChatComponentText(Utils.getPrefix("default") + msgautobuy);
                	Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)ale);
                }
                ApiHandler.cache.add(info.get(uuid).getAsString());
            }
        }
        if (found && Config.getConfig().get("autobuy").getAsBoolean()) {
            GuiEvents.autoBuy = true;
        }
        if (found && Config.getConfig().get("flipsounds").getAsBoolean()) {
            final SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
            if (soundHandler != null && Minecraft.getMinecraft().theWorld != null) {
                soundHandler.playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("note.pling"), (float)Minecraft.getMinecraft().thePlayer.posX, (float)Minecraft.getMinecraft().thePlayer.posY, (float)Minecraft.getMinecraft().thePlayer.posZ));
                //System.out.println("Flipper restarting... ");
                Client.start();
            }
        }
    }
    
    public static void getPurse() {
        final Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        if (scoreboard != null) {
            final List<Score> scores = new LinkedList<Score>(scoreboard.getSortedScores(scoreboard.getObjectiveInDisplaySlot(1)));
            for (final Score score : scores) {
                final ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(score.getPlayerName());
                final String line = Utils.removeColorCodes(ScorePlayerTeam.formatPlayerName((Team)scorePlayerTeam, score.getPlayerName()));
                if (line.contains("Purse: ") || line.contains("Piggy: ")) {
                    ApiHandler.balance = Double.parseDouble(line.replaceAll("\\(\\+[\\d]+\\)", "").replaceAll("[^\\d.]", ""));
                    return;
                }
                ApiHandler.balance = 0.0;
            }
        }
        else {
            ApiHandler.balance = 0.0;
        }
    }
    
    static {
        ApiHandler.cache = new ArrayList<String>();
        ApiHandler.balance = 0.0;
    }
}
