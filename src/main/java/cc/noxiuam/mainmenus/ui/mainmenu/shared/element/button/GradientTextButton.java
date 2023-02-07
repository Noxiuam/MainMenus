package cc.noxiuam.mainmenus.ui.mainmenu.shared.element.button;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.fade.impl.ColorFade;
import cc.noxiuam.mainmenus.ui.font.CBFontRenderer;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Text button used by CheatBreaker & Lunar Client 2019
 * <p>
 * Credits: Moonsworth, LLC for combining two elements into one, was very smart.
 */
public class GradientTextButton extends AbstractElement {

    @Setter private ColorFade outlineColor;
    @Getter @Setter private ColorFade topGradientColor;
    @Setter private ColorFade bottomGradientColor;

    @Setter private ResourceLocation icon;

    @Setter private CBFontRenderer fontRenderer;

    private String text;

    @Setter private float iconScale;

    @Setter private float textYOffset = 3.0F;

    private boolean showText;
    @Setter private boolean showBackground = true;

    // used to save on some performance
    private boolean hasSet = false;

    public GradientTextButton(float iconScale, ResourceLocation resourceLocation) {
        this.fontRenderer = FontRegistry.robotoMedium13px;
        this.icon = resourceLocation;
        this.iconScale = iconScale;

        if (MainMenus.config.mainMenu == 1) {
            this.outlineColor = new ColorFade(0x4FFFFFFF, -1);
            this.topGradientColor = new ColorFade(0x40000000, 0x1A858585);
            this.bottomGradientColor = new ColorFade(0x40000000, 0x1A858585);
        } else {
            this.outlineColor = new ColorFade(0xFF262626, -11493284);
            this.topGradientColor = new ColorFade(0xFF323232, -10176146);
            this.bottomGradientColor = new ColorFade(0xFF2A2A2A, -11164318);
        }
    }

    public GradientTextButton(String string, CBFontRenderer fontRenderer) {
        this.iconScale = 4.0F;
        this.text = string;
        this.showText = true;
        this.fontRenderer = fontRenderer;

        if (MainMenus.config.mainMenu == 1) {
            this.outlineColor = new ColorFade(0x4FFFFFFF, -1);
            this.topGradientColor = new ColorFade(0x40000000, 0x1A858585);
            this.bottomGradientColor = new ColorFade(0x40000000, 0x1A858585);
        } else {
            this.outlineColor = new ColorFade(0xFF262626, 0xFF50A05C);
            this.topGradientColor = new ColorFade(0xFF323232, 0xFF64B96E);
            this.bottomGradientColor = new ColorFade(0xFF2A2A2A, 0xFF55A562);
        }
    }

    @Override
    protected void handleElementDraw(float x, float y, boolean hovering) {
        boolean mouseOver = hovering && this.isMouseInside(x, y);

        if (!this.showBackground && MainMenus.config.mainMenu == 2 && !this.hasSet) {
            this.outlineColor = new ColorFade(0x4FFFFFFF, 0xAF50A05C);
            this.topGradientColor = new ColorFade(0x1A858585, 0x3F64B96E);
            this.bottomGradientColor = new ColorFade(0x1A858585, 0x3F55A562);
            this.hasSet = true;
        }

        RenderUtil.drawGradientRectWithOutline(
                this.xPosition, this.yPosition,
                this.xPosition + this.width,
                this.yPosition + this.height,
                this.outlineColor.getColor(mouseOver).getRGB(),
                this.topGradientColor.getColor(mouseOver).getRGB(),
                this.bottomGradientColor.getColor(mouseOver).getRGB()
        );

        if (this.showText) {

            this.fontRenderer.drawCenteredString(
                    this.text, this.xPosition + this.width / 2.0F,
                    this.yPosition + this.textYOffset, -1
            );
        } else {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
            RenderUtil.renderIcon(
                    this.icon, this.iconScale,
                    this.xPosition + this.width / 2.0F - this.iconScale, this.yPosition + this.height / 2.0F - this.iconScale
            );
        }
    }

}

