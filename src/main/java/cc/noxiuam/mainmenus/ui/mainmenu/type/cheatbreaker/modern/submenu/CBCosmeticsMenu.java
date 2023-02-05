package cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.modern.submenu;

import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.mainmenu.shared.element.button.GradientTextButton;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.modern.ModernCBMainMenuBase;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;

public class CBCosmeticsMenu extends ModernCBMainMenuBase {

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

        FontRegistry.playRegular16px.drawCenteredString(
                "You don't own any cosmetics.",
                this.getScaledWidth() / 2.0f,
                this.getScaledHeight() / 2.0f + 4.0f,
                -6381922
        );

    }

    @Override
    protected void mouseClicked(float x, float y, int button) {
        super.mouseClicked(x, y, button);

        if (this.backButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new ModernCBMainMenuBase());
        }

    }

}
