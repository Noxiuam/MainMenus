package cc.noxiuam.mainmenus.ui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Credits: https://offlinecheatbreaker.com
 * (With permission)
 */
public abstract class AbstractGui extends GuiScreen {
    @Getter @Setter public ScaledResolution scaledResolution;
    @Getter protected List<AbstractElement> selectedButton;
    @Getter private float scaledWidth;
    @Getter private float scaledHeight;

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        this.mc = mc;
        this.fontRendererObj = mc.fontRendererObj;
        this.width = width;
        this.height = height;
        this.buttonList.clear();
        this.scaledResolution =
                new ScaledResolution(this.mc);
        float scaleFactor = this.getScaleFactor();
        this.scaledWidth = (float) width / scaleFactor;
        this.scaledHeight = (float) height / scaleFactor;
        this.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float scale = this.getScaleFactor();

        GlStateManager.pushMatrix();
        GL11.glScalef(scale, scale, scale);
        this.drawMenu((float) mouseX / scale, (float) mouseY / scale);
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float scale = this.getScaleFactor();
        this.mouseClicked((float) mouseX / scale, (float) mouseY / scale, mouseButton);
    }

    @Override
    protected void mouseReleased(int x, int y, int button) {
        float scale = this.getScaleFactor();
        this.mouseReleased((float) x / scale, (float) y / scale, button);
    }

    public abstract void drawMenu(float x, float y);

    protected abstract void mouseClicked(float x, float y, int button);

    public abstract void mouseReleased(float x, float y, int button);

    public float getScaleFactor() {
        float scale;
        switch (this.scaledResolution.getScaleFactor()) {
            case 1:
                scale = 0.5f;
                break;
            case 3:
                scale = 1.5f;
                break;
            case 4:
                scale = 2.0f;
                break;
            default:
                scale = 1.0f;
        }
        return 1.0f / scale;
    }

}
