package me.lemonflux.fluxflips.commands;

import me.lemonflux.fluxflips.commands.subcommand.*;
import net.minecraft.command.*;
import me.lemonflux.fluxflips.events.*;
import me.lemonflux.fluxflips.utils.Utils;
import net.minecraft.util.*;
import java.util.*;

public class FFCommand extends CommandBase
{
    private final Subcommand[] subcommands;
    
    public FFCommand(final Subcommand[] subcommands) {
        this.subcommands = subcommands;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public List<String> getCommandAliases() {
        return Arrays.asList(Utils.getPrefix("raw").toLowerCase(), Utils.getPrefix("firstLets"));
    }
    
    public String getCommandName() {
        return Utils.getPrefix("firstLets");
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "/" + Utils.getPrefix("firstLets") + " <subcommand> <arguments>";
    }
    
    public void sendHelp(final ICommandSender sender) {
        final List<String> commandUsages = new LinkedList<String>();
        for (final Subcommand subcommand : this.subcommands) {
            if (!subcommand.isHidden()) {
                commandUsages.add(EnumChatFormatting.GOLD + "/" + Utils.getPrefix("firstLets") + " " + subcommand.getCommandName() + " " + subcommand.getCommandUsage() + EnumChatFormatting.YELLOW + " - " + subcommand.getCommandDescription());
            }
        }
        sender.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + " " + Utils.getVersion() + "\n" + String.join("\n", commandUsages)));
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length == 0) {
            GuiEvents.guiOpen = true;
            return;
        }
        for (final Subcommand subcommand : this.subcommands) {
            if (Objects.equals(args[0], subcommand.getCommandName())) {
                if (!subcommand.processCommand(sender, Arrays.copyOfRange(args, 1, args.length))) {}
                return;
            }
        }
        this.sendHelp(sender);
    }
    
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        final List<String> possibilities = new LinkedList<String>();
        for (final Subcommand subcommand : this.subcommands) {
            possibilities.add(subcommand.getCommandName());
        }
        if (args.length == 1) {
            return (List<String>)getListOfStringsMatchingLastWord(args, (Collection)possibilities);
        }
        return null;
    }
}
