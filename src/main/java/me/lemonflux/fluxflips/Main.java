package me.lemonflux.fluxflips;

import com.google.gson.JsonObject;
import net.minecraftforge.fml.common.*;
import me.lemonflux.fluxflips.commands.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import me.lemonflux.fluxflips.events.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;
import me.lemonflux.fluxflips.modules.*;
import me.lemonflux.fluxflips.utils.*;
import java.io.*;

import me.lemonflux.fluxflips.commands.subcommand.*;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = "[1.8.9]")
public class Main
{
    public static FFCommand commandManager;



    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) throws IOException {
		Config.init();
		final String license = Config.getLicenseKey();
		final JsonObject allContents = Utils.checkLicense(license, true);
		if(allContents.get("status").getAsString().equals("valid")) {
			PopupManager.createPopup(Utils.getPrefix("raw"), "Your License Key is valid!\nWelcome back.", "OK");
			MinecraftForge.EVENT_BUS.register((Object) new GuiEvents());
			MinecraftForge.EVENT_BUS.register((Object) new OnWorldJoin());
			ClientCommandHandler.instance.registerCommand((ICommand) Main.commandManager);
			Modules.init();
			Config.init();
		} else {
			PopupManager.createPopup(Utils.getPrefix("raw"), "Authentication Failed: " + allContents.get("reason").getAsString().replace("-", " "), "Exit");
			FMLCommonHandler.instance().exitJava(999, true);
		}
	}
    static {
        Main.commandManager = new FFCommand(new Subcommand[] { new Toggle() });
    }
}
