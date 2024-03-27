package me.lemonflux.fluxflips.events;

import me.lemonflux.fluxflips.gui.ConfigMenu;
import me.lemonflux.fluxflips.gui.OldMenu;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import me.lemonflux.fluxflips.utils.*;
import net.minecraft.client.gui.inventory.*;
import me.lemonflux.fluxflips.client.*;
import net.minecraft.entity.player.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.util.concurrent.*;

import net.minecraft.inventory.*;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class GuiEvents
{
    public static boolean guiOpen;
    public static boolean autoBuy;
    public static boolean isFlipping;
	public static String nameOfItem;
	public static String itemPrice;
    private long timeLast1;
    private long timeLast2;
    private long timeLast3;

    public GuiEvents() {
        this.timeLast1 = 0L;
        this.timeLast2 = 0L;
        this.timeLast3 = 0L;
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (GuiEvents.guiOpen) {
            // show Elementa GUI
			Minecraft.getMinecraft().displayGuiScreen(new ConfigMenu());
            GuiEvents.guiOpen = false;
        }
    }

    @SubscribeEvent
    public void onTick(final GuiScreenEvent.BackgroundDrawnEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (Config.getConfig().get("oneclickbuy").getAsBoolean() && mc.currentScreen instanceof GuiChest && !GuiEvents.isFlipping) {
        	final Container containerChest = mc.thePlayer.openContainer;
            final String displayName = ((ContainerChest)containerChest).getLowerChestInventory().getDisplayName().getUnformattedText().trim();
            if (!(displayName.contains("Auction House"))) {
	            if (displayName.contains("BIN Auction View") && System.currentTimeMillis() - this.timeLast1 > 70L) {
	                this.timeLast1 = System.currentTimeMillis();
	                final Minecraft minecraft = Minecraft.getMinecraft();
	                if(containerChest.getSlot(31).getStack() != null) {
		                    if (containerChest.getSlot(31).getStack().getDisplayName().contains("Buy Item Right Now") && minecraft.thePlayer.openContainer.getSlot(31).getStack().isStackable()) {
		                    	final String nameOfSlot = containerChest.getSlot(31).getStack().getDisplayName();
		                        Client.scheduledExecutorService.schedule(() -> minecraft.playerController.windowClick(minecraft.thePlayer.openContainer.windowId, 31, 0, 0, (EntityPlayer)minecraft.thePlayer), 19L, TimeUnit.MILLISECONDS);
		                        Client.scheduledExecutorService.schedule(() -> minecraft.thePlayer.closeScreen(), 1600L, TimeUnit.MILLISECONDS);
		                        Client.scheduledExecutorService.schedule(() -> flip(), 2000L, TimeUnit.MILLISECONDS);
		                    }
	                	}
	            }
	            if (displayName.contains("Confirm Purchase") && System.currentTimeMillis() - this.timeLast2 > 70L) {
	                this.timeLast2 = System.currentTimeMillis();
	                final Minecraft minecraft2 = Minecraft.getMinecraft();
	                Client.scheduledExecutorService.schedule(() -> minecraft2.playerController.windowClick(minecraft2.thePlayer.openContainer.windowId, 11, 0, 0, (EntityPlayer)minecraft2.thePlayer), 19L, TimeUnit.MILLISECONDS);
	                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "Purchased item!"));
	            }
	        }
        }
    }
    private void flip() {
    	if((ContainerChest)Minecraft.getMinecraft().thePlayer.openContainer == null) {
    		GuiEvents.isFlipping = true;
    		Minecraft.getMinecraft().thePlayer.sendChatMessage("/ah");
    		// open "View bids"
    		Client.scheduledExecutorService.schedule(() -> viewBids(), 700L, TimeUnit.MILLISECONDS);
    	} else {
    		Client.scheduledExecutorService.schedule(() -> flip(), 300L, TimeUnit.MILLISECONDS);
    	}
	}
    private void viewBids() {
    	Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, 13, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
    	for(int i = 0; i < Minecraft.getMinecraft().thePlayer.openContainer.inventorySlots.size(); i++) {
    		if(Minecraft.getMinecraft().thePlayer.openContainer.getSlot(i).getStack().getDisplayName().contains(GuiEvents.nameOfItem)) {
    			final int slot = i;
    			Client.scheduledExecutorService.schedule(() -> Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, slot, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer), 700L, TimeUnit.MILLISECONDS);
    			Client.scheduledExecutorService.schedule(() -> lookForItem(), 800L, TimeUnit.MILLISECONDS);
    		}
    	}
    	//Client.scheduledExecutorService.schedule(() -> claimItem(), 700L, TimeUnit.MILLISECONDS); // 26
    }
    void sendKeys(Robot robot, String keys) {
        for (char c : keys.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                throw new RuntimeException(
                    "Key code not found for character '" + c + "'");
            }
            robot.keyPress(keyCode);
            robot.delay(100);
            robot.keyRelease(keyCode);
            robot.delay(100);
        }
        robot.delay(400);
        robot.keyPress(KeyEvent.VK_ESCAPE);
    }
    private void lookForItem() {
    	Minecraft.getMinecraft().thePlayer.sendChatMessage("/ah");
    	for(int i = 0; i < Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory(); i++) {
    		if(Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getDisplayName().contains(GuiEvents.nameOfItem)) {
    			final int slot = i;
    			Client.scheduledExecutorService.schedule(() -> Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, 15, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer), 400L, TimeUnit.MILLISECONDS);
    			Client.scheduledExecutorService.schedule(() -> Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, 24, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer), 1200L, TimeUnit.MILLISECONDS);
    			// click item
    			Client.scheduledExecutorService.schedule(() -> Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, 31, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer), 2300L, TimeUnit.MILLISECONDS);
    			//Minecraft.getMinecraft().thePlayer;
    			try{
    				
					Robot robot = new Robot();
					System.setProperty("java.awt.headless", "false");
					Client.scheduledExecutorService.schedule(() -> sendKeys(robot, GuiEvents.itemPrice), 2800L, TimeUnit.MILLISECONDS);
				}catch(AWTException e){e.printStackTrace();}
    			
    			Client.scheduledExecutorService.schedule(() -> Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, 33, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer), 5000L, TimeUnit.MILLISECONDS);
    			Client.scheduledExecutorService.schedule(() -> Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, 14, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer), 5600, TimeUnit.MILLISECONDS);
    			Client.scheduledExecutorService.schedule(() -> Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, 28, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer), 6400, TimeUnit.MILLISECONDS);
    			Client.scheduledExecutorService.schedule(() -> GuiEvents.isFlipping = false, 7100, TimeUnit.MILLISECONDS);
    			
    			//Client.scheduledExecutorService.schedule(() -> lookForItem(), 800L, TimeUnit.MILLISECONDS);
    		}
    	}
    	//Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, 10, 0, 0, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
    	//Client.scheduledExecutorService.schedule(() -> , delay, unit)
    }
	static {
        GuiEvents.guiOpen = false;
        GuiEvents.autoBuy = false;
        GuiEvents.isFlipping = false;
    }
}
