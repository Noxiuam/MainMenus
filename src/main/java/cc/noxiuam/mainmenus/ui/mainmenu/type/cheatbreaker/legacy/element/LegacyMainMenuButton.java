package cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.element;

import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

/**
 * This is the main center buttons in the legacy 2017 menu.
 */
@AllArgsConstructor
public class LegacyMainMenuButton extends AbstractElement {

    @Getter private String text;
    private boolean showBackground;

    @Override
    protected void handleElementDraw(float mouseX, float mouseY, boolean hovering) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        boolean mouseOver = this.isMouseInside(mouseX, mouseY);

        if (this.showBackground) {
            Gui.drawRect(
                    (int) this.x,
                    (int) this.y,
                    (int) (this.x + this.width),
                    (int) (this.y + this.height),
                    mouseOver ? -15395563 : -14540254);
        }

        int textColor = -3092272;
        if (mouseOver) {
            textColor = -1;
        }

        FontRegistry.playRegular16px.drawCenteredString(
                        this.text,
                        this.x + this.width / 2,
                        this.y + this.height / 2 - 5,
                        textColor
                );
    }

}
