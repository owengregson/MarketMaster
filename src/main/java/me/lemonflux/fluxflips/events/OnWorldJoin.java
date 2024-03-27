package me.lemonflux.fluxflips.events;

import net.minecraftforge.fml.common.network.*;
import me.lemonflux.fluxflips.client.*;
import java.io.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class OnWorldJoin
{
    @SubscribeEvent
    public void onWorldJoin(final FMLNetworkEvent.ClientConnectedToServerEvent event) throws IOException {
        Client.start();
    }
}
