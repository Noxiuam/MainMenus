package cc.noxiuam.mainmenus.ui;

import cc.noxiuam.mainmenus.MainMenus;
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

    /**
     * Draws the main menu based on the scale provided,
     * this can be a custom one or just forced Normal.
     * <p>
     * @param mouseX X position.
     * @param mouseY Y position.
     * @param partialTicks - Redundant, as we do not use ticks for everything.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float scale = this.getScaleFactor();

        GlStateManager.pushMatrix();
        GL11.glScalef(scale, scale, scale);
        this.drawMenu((float) mouseX / scale, (float) mouseY / scale);
        GlStateManager.popMatrix();
    }

    /**
     * Handles all mouse clicks based on the scale.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float scale = this.getScaleFactor();
        this.mouseClicked((float) mouseX / scale, (float) mouseY / scale, mouseButton);
    }

    /**
     * Handles all mouse releases based on the scale.
     */
    @Override
    protected void mouseReleased(int x, int y, int button) {
        float scale = this.getScaleFactor();
        this.mouseReleased((float) x / scale, (float) y / scale, button);
    }

    /**
     * Draws a main menu with given positions.
     * <p>
     * @param x X position of the menu.
     * @param y Y position of the menu.
     */
    public abstract void drawMenu(float x, float y);

    /**
     * Handles all mouse clicks.
     * <p>
     * @param x Current mouse X position.
     * @param y Current mouse Y position.
     * @param button The mouse button that was clicked.
     */
    protected abstract void mouseClicked(float x, float y, int button);

    /**
     * Handles all mouse click releases (when you lift up).
     * <p>
     * @param x Current mouse X position.
     * @param y Current mouse Y position.
     * @param button The mouse button that was released.
     */
    public abstract void mouseReleased(float x, float y, int button);

    /**
     * Returns a proper scale to avoid asset distortion.
     * <p>
     * @return The proper scale for the main menus.
     */
    public float getScaleFactor() {
        float scale;

        switch (this.scaledResolution.getScaleFactor()) {
            case 1:
                scale = 0.5F;
                break;
            case 3:
                scale = 1.5F;
                break;
            case 4:
                scale = 2.0F;
                break;
            default:
                scale = 1.0F;
        }

        // Custom scale! - Nox
        if (MainMenus.config.customScale) {
            return 1.0F / scale * MainMenus.config.mainMenuScale;
        }

        return 1.0F / scale;
    }

}
