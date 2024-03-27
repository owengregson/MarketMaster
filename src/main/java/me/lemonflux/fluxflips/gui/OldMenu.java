package me.lemonflux.fluxflips.gui;

import net.minecraft.client.gui.*;
import me.lemonflux.fluxflips.utils.*;
import net.minecraft.util.*;
import me.lemonflux.fluxflips.modules.*;
import java.util.*;
import me.lemonflux.fluxflips.client.*;
import java.io.*;
import net.minecraft.client.*;

public class OldMenu extends GuiScreen
{
    private GuiButton general;
    private GuiButton confidential;
    private GuiButton premium;
    private GuiButton flipper;
    private GuiButton flipSounds;
    private GuiButton apiKeyTitle;
    private GuiButton licenseKeyTitle;
    private GuiSliderFixed minimumProfit;
    private GuiSliderFixed minimumDemand;
    private GuiTextField apiKey;
    private GuiTextField licenseKey;
    private ScaledResolution resolution;
    private int xSize;
    private int ySize;
    public int guiLeft;
    public int guiTop;
    public int xPos;
    public int page;
    public ArrayList<GuiButton> page1buttons;
    public ArrayList<GuiButton> page2buttons;
    public ArrayList<GuiButton> page3buttons;
    private ArrayList<GuiTextField> textFields;
    private GuiButton apiKeySave;
    private GuiButton licenseKeySave;
    private GuiButton oneClickBuy;
    private GuiButton autoflip;
    
    public OldMenu() {
        this.general = null;
        this.confidential = null;
        this.premium = null;
        this.flipper = null;
        this.flipSounds = null;
        this.apiKeyTitle = null;
        this.licenseKeyTitle = null;
        this.minimumProfit = null;
        this.minimumDemand = null;
        this.apiKey = null;
        this.xSize = this.width - 10;
        this.ySize = this.height - 10;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.xPos = this.guiLeft + 80;
        this.page = 1;
        this.page1buttons = new ArrayList<GuiButton>();
        this.page2buttons = new ArrayList<GuiButton>();
        this.page3buttons = new ArrayList<GuiButton>();
        this.textFields = new ArrayList<GuiTextField>();
    }
    
    public void initGui() {
    	super.initGui();
        this.page1buttons.clear();
        this.page2buttons.clear();
        this.page3buttons.clear();
        this.buttonList.clear();
        this.textFields.clear();
        this.resolution = new ScaledResolution(this.mc);
        this.general = new GuiButton(0, this.guiLeft + 18, this.resolution.getScaledHeight() / 60, 8, 6, "General");
        this.buttonList.add(this.general);
        this.confidential = new GuiButton(1, this.guiLeft + 19, this.resolution.getScaledHeight() / 60 + 20, 8, 6, "Secrets");
        this.buttonList.add(this.confidential);
        this.premium = new GuiButton(1, this.guiLeft + 19, this.resolution.getScaledHeight() / 60 + 40, 8, 6, "Extras");
        this.buttonList.add(this.premium);
        this.flipper = new GuiButton(2, this.guiLeft + 130, this.resolution.getScaledHeight() / 5 + 20, 5, 6, this.booleanFormat(Config.getConfig().get("flipper").getAsString()));
        this.buttonList.add(this.flipper);
        this.page1buttons.add(this.flipper);
        this.flipSounds = new GuiButton(3, this.guiLeft + 150, this.resolution.getScaledHeight() / 5 + 40, 5, 6, this.booleanFormat(Config.getConfig().get("flipsounds").getAsString()));
        this.buttonList.add(this.flipSounds);
        this.page1buttons.add(this.flipSounds);
        this.minimumProfit = new GuiSliderFixed(4, this.xPos - 20, this.resolution.getScaledHeight() / 5 + 60, "Minimum Profit", Config.getConfig().get("minprofit").getAsDouble() / 2500000.0, 2500000.0, 10000.0);
        this.buttonList.add(this.minimumProfit);
        this.page1buttons.add(this.minimumProfit);
        this.minimumDemand = new GuiSliderFixed(5, this.xPos - 20, this.resolution.getScaledHeight() / 5 + 90, "Minimum Demand", Config.getConfig().get("mindemand").getAsDouble() / 50.0, 50.0, 0.0);
        this.page1buttons.add(this.minimumDemand);
        this.apiKeyTitle = new GuiButton(6, this.guiLeft + 130, this.resolution.getScaledHeight() / 5 + 20, 0, 0, "API Key");
        this.buttonList.add(this.apiKeyTitle);
        this.page2buttons.add(this.apiKeyTitle);
        this.apiKeySave = new GuiButton(8, this.guiLeft + 115, this.resolution.getScaledHeight() / 5 + 50, 30, 13, "Save");
        this.buttonList.add(this.apiKeySave);
        this.page2buttons.add(this.apiKeySave);
        (this.apiKey = new GuiTextField(7, this.fontRendererObj, this.guiLeft + 62, this.resolution.getScaledHeight() / 5 + 30, 145, this.fontRendererObj.FONT_HEIGHT + 5)).setText(Config.getConfig().get("apikey").getAsString());
        this.apiKey.setVisible(true);
        this.apiKey.setFocused(true);
        this.apiKey.setMaxStringLength(100);
        this.textFields.add(this.apiKey);
        this.buttonList.add(this.apiKeyTitle);
        this.page2buttons.add(this.apiKeyTitle);
        this.oneClickBuy = new GuiButton(9, this.guiLeft + 160, this.resolution.getScaledHeight() / 5 - 20, 5, 6, this.booleanFormat(Config.getConfig().get("oneclickbuy").getAsString()));
        this.buttonList.add(this.oneClickBuy);
        this.page3buttons.add(this.oneClickBuy);
        this.autoflip = new GuiButton(10, this.guiLeft + 160, this.resolution.getScaledHeight() / 5, 5, 6, this.booleanFormat(Config.getConfig().get("autoflip").getAsString()));
        this.buttonList.add(this.autoflip);
        this.page3buttons.add(this.autoflip);
        this.buttonList.add(this.minimumDemand);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GuiScreen.drawRect(this.guiLeft - 10, this.guiTop - 10, this.guiLeft + 210, 500, -1728053248);
        GuiScreen.drawRect(this.guiLeft - 10, this.guiTop - 10, this.guiLeft + 60, 500, -16777216);
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (final GuiTextField field : this.textFields) {
            field.drawTextBox();
        }
        this.resolution = new ScaledResolution(this.mc);
        this.fontRendererObj.drawString(Utils.getPrefix("raw"), this.resolution.getScaledWidth() - 90, this.resolution.getScaledHeight() - 20, Utils.getIntegerColor());
        int increment = 0;
        int booinc = 0;
        if (this.page == 1) {
            for (final GuiButton button : this.page1buttons) {
                button.visible = true;
            }
            for (final GuiButton button : this.page2buttons) {
                button.visible = false;
            }
            for (final GuiTextField field2 : this.textFields) {
                field2.setVisible(false);
            }
            for (final GuiButton button : this.page3buttons) {
                button.visible = false;
            }
            this.general.displayString = Utils.getColors(Utils.getPrefix("colors")).get("light") + "General";
            this.confidential.displayString = EnumChatFormatting.WHITE + "Secrets";
            this.premium.displayString = EnumChatFormatting.WHITE + "Extras";
        }
        if (this.page == 2) {
            for (final GuiButton button : this.page1buttons) {
                button.visible = false;
            }
            for (final GuiButton button : this.page2buttons) {
                button.visible = true;
            }
            for (final GuiTextField field2 : this.textFields) {
                field2.setVisible(true);
            }
            for (final GuiButton button : this.page3buttons) {
                button.visible = false;
            }
            this.general.displayString = EnumChatFormatting.WHITE + "General";
            this.confidential.displayString = Utils.getColors(Utils.getPrefix("colors")).get("light") + "Secrets";
            this.premium.displayString = EnumChatFormatting.WHITE + "Extras";
        }
        if (this.page == 3) {
            for (final GuiButton button : this.page1buttons) {
                button.visible = false;
            }
            for (final GuiButton button : this.page2buttons) {
                button.visible = false;
            }
            for (final GuiButton button : this.page3buttons) {
                button.visible = true;
            }
            for (final GuiTextField field2 : this.textFields) {
                field2.setVisible(false);
            }
            this.general.displayString = EnumChatFormatting.WHITE + "General";
            this.confidential.displayString = EnumChatFormatting.WHITE + "Secrets";
            this.premium.displayString = Utils.getColors(Utils.getPrefix("colors")).get("light") + "Extras";
        }
        for (final Module m : Modules.modules) {
            ++increment;
            final int yPos = this.resolution.getScaledHeight() / 5 + increment * 20;
            if (this.page == 1 && m.getPage() == 1 && !m.getName().equals("Minimum Profit") && !m.getName().equals("Minimum Demand") && m.getCategory().equals(Category.GENERAL)) {
                this.fontRendererObj.drawString(m.getName(), this.xPos - 15, yPos, 16777215);
            }
            if (this.page == 3 && m.getPage() == 3) {
            	++booinc;
                this.fontRendererObj.drawString(m.getName(), this.xPos - 15, this.resolution.getScaledHeight() / 5 - 3 * booinc*4, 16777215);
            }
        }
        if (this.minimumProfit.dragging) {
            Config.write("minprofit", Config.gson.toJsonTree((Object)(this.minimumProfit.sliderValue * this.minimumProfit.sliderMaxValue)));
        }
        if (this.minimumDemand.dragging) {
            Config.write("mindemand", Config.gson.toJsonTree((Object)(this.minimumDemand.sliderValue * this.minimumDemand.sliderMaxValue)));
        }
    }
    
    public String booleanFormat(final String s) {
        switch (s) {
            case "true": {
                return "On";
            }
            case "false": {
                return "Off";
            }
            default: {
                return "Undefined";
            }
        }
    }
    
    public void actionPerformed(final GuiButton button) {
    	if (button == this.general) {
            this.page = 1;
        }
        if (button == this.confidential) {
            this.page = 2;
        }
        if (button == this.premium) {
            this.page = 3;
        }
        if (button == this.flipper) {
            Config.write("flipper", Config.gson.toJsonTree((Object)!Config.getConfig().get("flipper").getAsBoolean()));
            this.flipper.displayString = this.booleanFormat(Config.getConfig().get("flipper").getAsString());
            String state = "";
            if (Config.getConfig().get("flipper").getAsBoolean()) {
            	state = "enabled.";
            } else {
            	state = "disabled.";
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "Flipper has been " + state));
                Client.start();
        }
        if (button == this.flipSounds) {
            Config.write("flipsounds", Config.gson.toJsonTree((Object)!Config.getConfig().get("flipsounds").getAsBoolean()));
            this.flipSounds.displayString = this.booleanFormat(Config.getConfig().get("flipsounds").getAsString());
            String state = "";
            if (Config.getConfig().get("flipsounds").getAsBoolean()) {
            	state = "enabled.";
            } else {
            	state = "disabled.";
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "Flip Sounds have been " + state));
        }
        if (button == this.minimumDemand) {
            Config.write("mindemand", Config.gson.toJsonTree((Object)(this.minimumDemand.sliderValue * this.minimumDemand.sliderMaxValue)));
        }
        if (button == this.minimumProfit) {
            Config.write("minprofit", Config.gson.toJsonTree((Object)(this.minimumProfit.sliderValue * this.minimumProfit.sliderMaxValue)));
        }
        if (button == this.apiKeySave) {
            Config.write("apikey", Config.gson.toJsonTree((Object)this.apiKey.getText()));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "API key has been saved!"));
        }
        if (button == this.licenseKeySave) {
            Config.write("licenseKey", Config.gson.toJsonTree((Object)this.licenseKey.getText()));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "License key has been saved!"));
        }
        if (button == this.oneClickBuy) {
            Config.write("oneclickbuy", Config.gson.toJsonTree((Object)!Config.getConfig().get("oneclickbuy").getAsBoolean()));
            this.oneClickBuy.displayString = this.booleanFormat(Config.getConfig().get("oneclickbuy").getAsString());
            String state = "";
            if (Config.getConfig().get("oneclickbuy").getAsBoolean()) {
            	state = "enabled.";
            } else {
            	state = "disabled.";
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "One-Click Buy has been " + state));
        }
        if (button == this.autoflip) {
        	Config.write("autoflip", Config.gson.toJsonTree((Object)!Config.getConfig().get("autoflip").getAsBoolean()));
        	this.autoflip.displayString = this.booleanFormat(Config.getConfig().get("autoflip").getAsString());
        	String state = "";
            if (Config.getConfig().get("autoflip").getAsBoolean()) {
            	state = "enabled.";
            } else {
            	state = "disabled.";
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Utils.getPrefix("default") + "Auto-Buy has been " + state));
        }
        this.updateScreen();
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        boolean typed = false;
        for (final GuiTextField text : this.textFields) {
            typed = (typed || text.textboxKeyTyped(typedChar, keyCode));
        }
        if (!typed) {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    public void updateScreen() {
        super.updateScreen();
        for (final GuiTextField text : this.textFields) {
            text.updateCursorCounter();
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean prevFocused = false;
        boolean postFocused = false;
        for (final GuiTextField text : this.textFields) {
            prevFocused = (text.isFocused() || prevFocused);
            text.mouseClicked(mouseX, mouseY, mouseButton);
            postFocused = (text.isFocused() || postFocused);
        }
    }
}
