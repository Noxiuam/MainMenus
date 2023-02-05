package cc.noxiuam.mainmenus.ui.mainmenu.type.lunar;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.fade.impl.ColorFade;
import cc.noxiuam.mainmenus.ui.mainmenu.shared.CommonCheatBreakerBase;
import cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.element.AnimatedLogoElement;
import cc.noxiuam.mainmenus.ui.mainmenu.util.PanoramaRegistry;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class LunarClientMainMenuBase extends CommonCheatBreakerBase {

    private AnimatedLogoElement lunarLogo;

    private ColorFade titleColorFade;

    @Override
    public void initGui() {
        super.initGui();

        this.titlePanoramaPaths = PanoramaRegistry.LUNAR;
        this.titleColorFade = new ColorFade(0xF000000, -16777216);
        this.lunarLogo = new AnimatedLogoElement();

        this.singleplayerButton.setElementSize(
                this.getScaledWidth() / 2.0f - 50.0f,
                this.getScaledHeight() / 2.0f + 5.0f,
                100.0f,
                (float) FontRegistry.robotoLight18px.getHeight() + 9.5f
        );

        this.multiplayerButton.setElementSize(
                this.getScaledWidth() / 2.0f - 50.0f,
                this.getScaledHeight() / 2.0f + 24.0f,
                100.0f,
                (float) FontRegistry.robotoLight18px.getHeight() + 9.5f
        );

        this.updateSizes();
    }

    @Override
    public void drawMenu(float x, float y) {

        super.drawMenu(x, y);

    }

    @Override
    public void drawCenter() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawLogo(this.getScaledWidth(), this.getScaledHeight());
    }

    @Override
    public void drawWaterMarks(float x, float y) {

        boolean hoveringOnTitle = x < this.optionsButton.getXPosition() && y < 30.0f;
        Color color = this.titleColorFade.getColor(hoveringOnTitle);

        FontRegistry.robotoRegular24px.drawString("Lunar Client", 27.0f, 13.0f, color.getRGB());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        FontRegistry.robotoRegular24px.drawString("Lunar Client", 26.0f, 12.0f, -1);

        String commitText = "Lunar Client Alpha (" + MainMenus.VERSION + "/" + MainMenus.BRANCH + ")";
        FontRegistry.robotoLight18px.drawStringWithShadow(
                commitText,
                5.0,
                this.getScaledHeight() - 14.0f,
                -1879048193
        );

        String copyrightText = "Copyright Mojang AB. Do not distribute!";
        FontRegistry.robotoLight18px.drawStringWithShadow(
                copyrightText,
                this.getScaledWidth() - (float) FontRegistry.robotoLight18px.getStringWidth(copyrightText) - 5.0f,
                this.getScaledHeight() - 14.0f,
                -1879048193
        );

    }

    @Override
    protected void mouseClicked(float x, float y, int button) {
        super.mouseClicked(x, y, button);

        boolean hoveringOnTitle = x < this.optionsButton.getXPosition() && y < 30.0f;

        if (hoveringOnTitle) {
            this.playClick();
            this.mc.displayGuiScreen(new LunarClientMainMenuBase());
        }

    }

    @Override
    public void mouseReleased(float x, float y, int button) {
    }

    private void drawLogo(double width, double height) {
        float size = 64.0f;
        float x = (float) (width / 2.0 - (double) (size / 2.0f));
        float y = (float) (height / 2.0 - size - 12.0);

        if (this.lunarLogo == null) {
            this.lunarLogo = new AnimatedLogoElement();
        }

        this.lunarLogo.setElementSize(x, y, 64.0f, 58.5f);
        this.lunarLogo.drawElementHover(0.0f, 0.0f, true);
    }

}
