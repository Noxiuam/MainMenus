package cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.element;

import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Used for the EXIT button in the 2017 menu.
 */
public class ImageButtonElement extends AbstractElement {

    private final String text;

    private final boolean showBackground;

    private final ResourceLocation image;

    public ImageButtonElement(ResourceLocation image, String text, boolean showBackground) {
        this.image = image;
        this.text = text;
        this.showBackground = showBackground;
    }

    @Override
    protected void handleElementDraw(float mouseX, float mouseY, boolean hovering) {
        boolean mouseOver = this.isMouseInside(mouseX, mouseY);

        if (this.showBackground) {
            Gui.drawRect(
                    (int) this.xPosition,
                    (int) this.yPosition,
                    (int) (this.xPosition + this.getWidth()),
                    (int) (this.yPosition + this.getHeight()),
                    mouseOver ? -15395563 : -14540254);
        }

        int textColor = -3092272;
        if (mouseOver) {
            textColor = -1;
        }

        FontRegistry.playRegular16px.drawCenteredString(
                this.text,
                this.xPosition + this.getWidth() / 2,
                this.yPosition + this.getHeight() / 2 - (this.showBackground ? 6 : 5),
                textColor
        );

        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.3f);
        RenderUtil.renderIcon(
                this.image,
                7.0F,
                this.xPosition + this.getWidth() - 20,
                this.yPosition + 5);
    }

}
