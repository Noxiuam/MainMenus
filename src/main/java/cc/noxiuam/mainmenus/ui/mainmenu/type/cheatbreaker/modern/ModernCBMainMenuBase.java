package cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.modern;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.fade.impl.ColorFade;
import cc.noxiuam.mainmenus.ui.fade.impl.CosineFade;
import cc.noxiuam.mainmenus.ui.mainmenu.shared.CommonCheatBreakerBase;
import cc.noxiuam.mainmenus.ui.mainmenu.util.PanoramaRegistry;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModernCBMainMenuBase extends CommonCheatBreakerBase {

    private final ResourceLocation outerLogo = new ResourceLocation("client/logo_255_outer.png");
    private final ResourceLocation innerLogo = new ResourceLocation("client/logo_108_inner.png");
    private final ResourceLocation logo = new ResourceLocation("client/logo_42.png");

    private final ColorFade overlayGradient = new ColorFade(0xF000000, -16777216);

    private final CosineFade outerLogoRotationTime = new CosineFade(4000L);

    @Override
    public void initGui() {
        super.initGui();

        this.closeButton.setIconScale(4.0F);

        this.titlePanoramaPaths = PanoramaRegistry.CHEATBREAKER;

        this.closeButton.setShowBackground(false);
        this.closeButton.setElementSize(this.getScaledWidth() - 30.0f, 7.0f, 23.0f, 17.0f);
        this.languageButton.setShowBackground(false);
        this.forgeButton.setShowBackground(false);
        this.replaysButton.setShowBackground(false);

        this.singleplayerButton.setTextYOffset(2.0F);
        this.multiplayerButton.setTextYOffset(2.0F);
        this.singleplayerButton.setFontRenderer(FontRegistry.robotoRegular13px);
        this.multiplayerButton.setFontRenderer(FontRegistry.robotoRegular13px);

        this.singleplayerButton.setElementSize(
                this.getScaledWidth() / 2.0f - (float) 50,
                this.getScaledHeight() / 2.0f + 5.0F,
                100.0F,
                12);

        this.multiplayerButton.setElementSize(
                this.getScaledWidth() / 2.0f - 50.0F,
                this.getScaledHeight() / 2.0f + 24.0F,
                100.0F,
                12);

        this.optionsButton.setElementSize(124.0f, 6.0f, 42.0f, 20.0f);
        this.cosmeticsButton.setElementSize(167.0f, 6.0f, 48.0f, 20.0f);

        this.accountButton.setElementSize(
                this.getScaledWidth()
                        - 35.0f
                        - this.accountButton.getNewAccountButtonWidth(this.accountButtonWidth),
                7.0f,
                this.accountButton.getNewAccountButtonWidth(this.accountButtonWidth),
                17.0f);
    }

    @Override
    public void drawMenu(float x, float y) {

        RenderUtil.drawGradientRect(
                0.0f, 0.0f, this.getScaledWidth(), this.getScaledHeight(), 0x5FFFFFFF, 0x2FFFFFFF);
        RenderUtil.drawGradientRect(0.0f, 0.0f, this.getScaledWidth(), 160.0f, -553648128, 0);

        super.drawMenu(x, y);

        FontRegistry.playRegular18px.drawStringWithShadow(
                "CheatBreaker Dev (" + MainMenus.VERSION + "/" + MainMenus.BRANCH + ")",
                5.0f,
                this.getScaledHeight() - 14.0f,
                -1879048193
        );

        String copyright = "Copyright Mojang AB. Do not distribute!";

        FontRegistry.playRegular18px.drawStringWithShadow(
                copyright,
                this.getScaledWidth() - (float) FontRegistry.playRegular18px.getStringWidth(copyright) - 5.0f,
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
            this.mc.displayGuiScreen(new ModernCBMainMenuBase());
        }
    }

    @Override
    public void drawCenter() {
        RenderUtil.drawRect(
                this.singleplayerButton.getXPosition() - (float) 20, this.getScaledHeight() / 2.0f - (float) 80,
                this.singleplayerButton.getXPosition() + this.singleplayerButton.getWidth() + (float) 20,
                this.multiplayerButton.getYPosition() + this.multiplayerButton.getHeight() + (float) 14,
                0x2F000000
        );

        this.drawLogo(this.getScaledWidth(), this.getScaledHeight());
    }

    @Override
    public void drawWaterMarks(float x, float y) {
        boolean hoveringOverTitle = x < this.optionsButton.getXPosition() && y < 30.0f;
        Color titleTextColor = this.overlayGradient.getColor(hoveringOverTitle);

        FontRegistry.robotoRegular24px.drawString("CheatBreaker", 37.0f, 9.0f, titleTextColor.getRGB());
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(this.logo, 10.0f, 8.0f, 6.0f);
        FontRegistry.robotoRegular24px.drawString("CheatBreaker", 36.0f, 8.0f, -1);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (!this.outerLogoRotationTime.isTimeNotAtZero()) {
            this.outerLogoRotationTime.startAnimation();
            this.outerLogoRotationTime.loopAnimation();
        }
    }

    private void drawLogo(float x, float y) {
        float size = 27;
        double x2 = x / 2.0 - (double) size;
        double y2 = y / 2.0 - (double) size - (double) (35.0F);

        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        GL11.glTranslatef((float) x2, (float) y2, 1.0f);
        GL11.glTranslatef(size, size, size);

        GlStateManager.rotate(180.0F * this.outerLogoRotationTime.getFadeAmount(), 0.0f, 0.0f, 1.0f);

        GL11.glTranslatef(-size, -size, -size);

        RenderUtil.renderIcon(this.outerLogo, size, 0.0f, 0.0f);
        GlStateManager.popMatrix();
        RenderUtil.renderIcon(this.innerLogo, size, (float) x2, (float) y2);
    }

}
