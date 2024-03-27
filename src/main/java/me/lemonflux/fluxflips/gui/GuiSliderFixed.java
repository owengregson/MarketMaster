package me.lemonflux.fluxflips.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public class GuiSliderFixed extends GuiButton
{
    public double sliderValue;
    public double sliderMaxValue;
    public double sliderMinValue;
    public boolean dragging;
    public String label;
    
    public GuiSliderFixed(final int id, final int x, final int y, final String label, final double startingValue, final double maxValue, final double minValue) {
        super(id, x, y, 150, 20, label);
        this.sliderValue = 1.0;
        this.sliderMaxValue = 1.0;
        this.sliderMinValue = 1.0;
        this.dragging = false;
        this.label = label;
        this.sliderValue = startingValue;
        this.sliderMaxValue = maxValue;
        this.sliderMinValue = minValue;
    }
    
    protected int getHoverState(final boolean par1) {
        return 0;
    }
    
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            final FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(GuiSliderFixed.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            final int k = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;
            if (this.packedFGColour != 0) {
                l = this.packedFGColour;
            }
            else if (!this.enabled) {
                l = 10526880;
            }
            else if (this.hovered) {
                l = 16777120;
            }
            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }
    
    protected void mouseDragged(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.enabled && this.visible && this.packedFGColour == 0) {
            if (this.dragging) {
                this.sliderValue = (par2 - (this.xPosition + 4)) / (double)(this.width - 8);
                if (this.sliderValue < 0.0) {
                    this.sliderValue = 0.0;
                }
                if (this.sliderValue > 1.0) {
                    this.sliderValue = 1.0;
                }
            }
            this.displayString = this.label + ": " + (int)(this.sliderValue * this.sliderMaxValue);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.sliderValue = (par2 - (this.xPosition + 4)) / (double)(this.width - 8);
            if (this.sliderValue < 0.0) {
                this.sliderValue = 0.0;
            }
            if (this.sliderValue > 1.0) {
                this.sliderValue = 1.0;
            }
            return this.dragging = true;
        }
        return false;
    }
    
    public void mouseReleased(final int par1, final int par2) {
        this.dragging = false;
    }
}
