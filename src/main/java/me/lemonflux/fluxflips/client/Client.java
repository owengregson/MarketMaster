package me.lemonflux.fluxflips.client;
import javax.swing.JOptionPane;
import java.util.concurrent.*;
import java.io.*;
import java.net.URL;

import me.lemonflux.fluxflips.utils.*;

public class Client
{
    public static ScheduledExecutorService scheduledExecutorService;
    
    public static void start() {
        Client.scheduledExecutorService.shutdownNow();
        if (Config.getConfig().get("flipper").getAsBoolean()) {
            (Client.scheduledExecutorService = Executors.newScheduledThreadPool(1)).schedule(() -> flip(), 400L, TimeUnit.MILLISECONDS);
        }
    }
    
    public static void flip() {
        Client.scheduledExecutorService.scheduleAtFixedRate(() -> ApiHandler.getPurse(), 0L, 20L, TimeUnit.SECONDS);
        Client.scheduledExecutorService.scheduleAtFixedRate(() -> ApiHandler.getFlips(), 0L, 100L, TimeUnit.MILLISECONDS);
        Client.scheduledExecutorService.scheduleAtFixedRate(() -> start(), 0L, 2L, TimeUnit.MINUTES);
    }
    
    static {
        Client.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }
}
