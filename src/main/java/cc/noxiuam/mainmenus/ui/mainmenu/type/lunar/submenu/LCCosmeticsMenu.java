package cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.submenu;

import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.mainmenu.shared.element.button.GradientTextButton;
import cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.LunarClientMainMenuBase;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import org.lwjgl.input.Keyboard;

/**
 * Recreation of the cosmetics menu, when you do not have cosmetics.
 */
public class LCCosmeticsMenu extends LunarClientMainMenuBase {

    private final GradientTextButton backButton = new GradientTextButton("BACK", FontRegistry.robotoMedium13px);

    @Override
    public void drawMenu(float x, float y) {
        super.drawMenu(x, y);

        RenderUtil.drawRect(
                this.getScaledWidth() / 2.0f - 80.0f,
                this.getScaledHeight() / 2.0f - 78.0f,
                this.getScaledWidth() / 2.0f + 80.0f,
                this.getScaledHeight() / 2.0f + 100.0f,
                0x2F000000
        );

        this.backButton.setTextYOffset(2.0F);
        this.backButton.setElementSize(this.getScaledWidth() / 2.0f - 30.0f, this.getScaledHeight() / 2.0f + 105.0f, 60.0f, 12.0f);
        this.backButton.drawElementHover(x, y, true);

        FontRegistry.robotoLight16px.drawCenteredString(
                "You don't own any cosmetics.",
                this.getScaledWidth() / 2.0f,
                this.getScaledHeight() / 2.0f + 4.0f,
                -6381922
        );

    }

    @Override
    protected void keyTyped(char c, int n) {
        if (n == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(new LunarClientMainMenuBase());
        }
    }

    @Override
    protected void mouseClicked(float x, float y, int button) {
        super.mouseClicked(x, y, button);

        if (this.backButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new LunarClientMainMenuBase());
        }

    }

}
