package me.lemonflux.fluxflips.commands.subcommand;

import net.minecraft.command.*;

public interface Subcommand
{
    String getCommandName();
    
    boolean isHidden();
    
    String getCommandUsage();
    
    String getCommandDescription();
    
    boolean processCommand(final ICommandSender p0, final String[] p1);
}
