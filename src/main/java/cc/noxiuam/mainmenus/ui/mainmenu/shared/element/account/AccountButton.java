package cc.noxiuam.mainmenus.ui.mainmenu.shared.element.account;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.ScrollableElement;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.fade.impl.ColorFade;
import cc.noxiuam.mainmenus.ui.fade.impl.MinMaxFade;
import cc.noxiuam.mainmenus.ui.font.CBFontRenderer;
import cc.noxiuam.mainmenus.ui.mainmenu.data.Account;
import cc.noxiuam.mainmenus.ui.mainmenu.shared.CommonCheatBreakerBase;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * Account button element utilized by CheatBreaker & Lunar Client 2019
 */
public class AccountButton extends AbstractElement {

    @Setter private ResourceLocation avatarIcon;
    @Setter private String displayName;

    private final ColorFade outline;
    private final ColorFade topGradient;
    private final ColorFade bottomGradient;
    private final MinMaxFade dropFade = new MinMaxFade(300L);

    private float modifiedHeight;

    private boolean interacting;

    private final CommonCheatBreakerBase mainMenuBase;

    private final ScrollableElement scrollbar;

    private float accountSelectionBoxHeight;

    public AccountButton(CommonCheatBreakerBase mainMenuBase, String displayName, ResourceLocation resourceLocation) {
        this.mainMenuBase = mainMenuBase;
        this.avatarIcon = resourceLocation;
        this.displayName = displayName;

        this.scrollbar = new ScrollableElement(this);

        if (MainMenus.config.mainMenu == 2) {
            this.outline = new ColorFade(0x4FFFFFFF, 0xAF50A05C);
            this.topGradient = new ColorFade(0x1A858585, 0x3F64B96E);
            this.bottomGradient = new ColorFade(0x1A858585, 0x3F55A562);
        } else {
            this.outline = new ColorFade(0x4FFFFFFF, -1);
            this.topGradient = new ColorFade(0x40000000, 0x1A858585);
            this.bottomGradient = new ColorFade(0x40000000, 0x1A858585);
        }

    }

    @Override
    protected void handleElementDraw(float x, float y, boolean hovering) {

        boolean mouseInside = hovering && this.isMouseInside(x, y);

        RenderUtil.drawGradientRectWithOutline(
                this.xPosition, this.yPosition,
                this.xPosition + this.width,
                this.yPosition + this.modifiedHeight,
                this.outline.getColor(mouseInside).getRGB(),
                this.topGradient.getColor(mouseInside).getRGB(),
                this.bottomGradient.getColor(mouseInside).getRGB()
        );

        CBFontRenderer fontRenderer;

        if (MainMenus.config.mainMenu == 2) {
            fontRenderer = FontRegistry.robotoRegular13px;
        } else {
            fontRenderer = FontRegistry.robotoMedium13px;
        }

        float xOffset = 6.0f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(this.avatarIcon, xOffset, this.xPosition + 4.0f, this.yPosition + this.modifiedHeight / 2.0f - xOffset);
        fontRenderer.drawString(this.displayName, this.xPosition + 22.0f, this.yPosition + 4.5f, -1342177281);

        float heightFadeAmount = this.dropFade.inOutFade(this.isMouseInside(x, y) && hovering);
        if (this.dropFade.isZeroOrLess()) {
            this.setElementSize(this.xPosition, this.yPosition, this.width, this.modifiedHeight + this.accountSelectionBoxHeight * heightFadeAmount);
            this.interacting = true;
        } else if (!this.dropFade.isZeroOrLess() && !this.isMouseInside(x, y)) {
            this.interacting = false;
        }

        if (this.interacting) {
            boolean flag;

            float halfOfOne = 0.5f;
            float initialHeight = this.yPosition + this.height + halfOfOne;
            float currentHeight = this.yPosition + 5.0f + this.modifiedHeight;

            // The outline of the whole account switcher
            if (initialHeight > currentHeight) {
                RenderUtil.drawBoxWithOutLine(
                        this.xPosition + 1.0f, currentHeight,
                        this.xPosition + this.width - 1.0f,
                        initialHeight,
                        halfOfOne,
                        0x4FFFFFFF,
                        444958085
                );
            }

            GL11.glPushMatrix();
            GL11.glEnable(3089);
            RenderUtil.startScissorBox(
                    (int) this.xPosition,
                    (int) (this.yPosition + this.modifiedHeight),
                    (int) (this.xPosition + this.width),
                    (int) (this.yPosition + this.modifiedHeight + 7.0f + (this.height - this.modifiedHeight - 6.0f) * heightFadeAmount),
                    this.mainMenuBase.getScaledResolution().getScaleFactor() * this.mainMenuBase.getScaleFactor(),
                    (int) this.mainMenuBase.getScaledHeight()
            );

            this.scrollbar.drawScrollable(x, y, hovering);

            int index = 1;
            for (Account account : this.mainMenuBase.accounts) {
                //if (account.getDisplayName().equals(this.mainMenuBase.mc.getSession().getUsername())) continue;

                flag = account.getAccessToken() == null;

                float initialAccountX = this.xPosition;
                float accountWidth = this.xPosition + this.width;
                float accountHeight = this.yPosition + this.modifiedHeight + (float)(index * 16) - 8.0f;
                float padding = accountHeight + 16.0f;

                boolean hoveringOverAccount =
                        x > initialAccountX
                                && x < accountWidth
                                && y - this.scrollbar.getPosition() > accountHeight
                                && y - this.scrollbar.getPosition() < padding
                                && hovering && !this.scrollbar.isMouseInside(x, y)
                                && !this.scrollbar.isButtonHeld();

                // account avatar
                GL11.glColor4f(flag ? 0.8f : 1.0f, flag ? 0.15f : 1.0f, flag ? 0.15f : 1.0f, hoveringOverAccount ? 0.5f : 0.3f);
                RenderUtil.renderIcon(account.getHeadIcon(), xOffset, this.xPosition + 4.0f, accountHeight + 8.0f - xOffset);

                // account username

                fontRenderer.drawString(
                        account.getDisplayName(),
                        this.xPosition + 22.0f,
                        accountHeight + 4.0f,
                        hoveringOverAccount ? (flag ? -2130771968 : -1) : (flag ? 452919296 : -1342177281)
                );

                ++index;
            }

            // add account button, should look at how this is done elsewhere.
            if (MainMenus.config.mainMenu == 1) {
                float addAccountY = this.yPosition + this.modifiedHeight + (float)(index * 16) - 8.0f;
                float addAccountPadding = addAccountY + 16.0f;
                flag = x > this.xPosition + this.width / 2.0f - 3.0f && x < this.xPosition + this.width / 2.0f + 3.0f && y - this.scrollbar.getPosition() > addAccountY && y - this.scrollbar.getPosition() < addAccountPadding && hovering && !this.scrollbar.isMouseInside(x, y) && !this.scrollbar.isButtonHeld();

                GL11.glColor4f(1.0f, 1.0f, 1.0f, flag ? 1.0f : 0.7f);
                RenderUtil.renderIcon(new ResourceLocation("client/icons/plus-64.png"), 4.0f, this.xPosition + this.width / 2.0f - 3.0f, addAccountY + xOffset / 2.0f);
            }

            this.scrollbar.handleElementDraw(x, y, hovering);

            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }

        this.setElementSize(this.xPosition, this.yPosition, this.width, this.height);
    }

    @Override
    public boolean handleElementMouseClicked(float mouseX, float mouseY, int button, boolean hovering) {
        if (!hovering) {
            return false;
        }

        if (this.dropFade.isHovered()) {
            this.scrollbar.handleElementMouseClicked(mouseX, mouseY, button, hovering);

            int index = 1;
            for (Account account : this.mainMenuBase.accounts) {

                if (account.getDisplayName().equals(this.mainMenuBase.mc.getSession().getUsername())) {
                    continue;
                }

                float initialAccountX = this.xPosition;
                float accountWidth = this.xPosition + this.width;
                float accountHeight = this.yPosition + this.modifiedHeight + (float) (index * 16) - 8.0f;
                float padding = accountHeight + 16.0f;

                boolean hoveringOverAccount = mouseX > initialAccountX && mouseX < accountWidth && mouseY - this.scrollbar.getPosition() > accountHeight && mouseY - this.scrollbar.getPosition() < padding && hovering && !this.scrollbar.isMouseInside(mouseX, mouseY) && !this.scrollbar.isButtonHeld();
                if (hoveringOverAccount) {
                    this.mainMenuBase.playClick();

                    if (!this.mainMenuBase.login(account.getDisplayName())) break;

                    this.setDisplayName(account.getDisplayName());
                    this.setAvatarIcon(account.getHeadIcon());

                    CBFontRenderer fontRenderer;
                    if (MainMenus.config.mainMenu == 2) {
                        fontRenderer = FontRegistry.robotoRegular13px;
                    } else {
                        fontRenderer = FontRegistry.robotoMedium13px;
                    }

                    this.mainMenuBase.accountButtonWidth = (float) fontRenderer.getStringWidth(Minecraft.getMinecraft().getSession().getUsername());
                }

                ++index;
            }
        }
        return false;
    }

    @Override
    public void setElementSize(float x, float y, float width, float height) {
        super.setElementSize(x, y, width, height);
        Keyboard.enableRepeatEvents(true);

        if (this.modifiedHeight == 0.0f) {
            this.modifiedHeight = height;
        }

        boolean showAddIcon = MainMenus.config.mainMenu == 1;

        this.accountSelectionBoxHeight = Math.min((this.mainMenuBase.accounts.size() + (showAddIcon ? 1 : 0)) * 16 + 12, 120);
        this.scrollbar.setElementSize(x + width - 5.0f, y + this.modifiedHeight + 6.0f, 4.0f, this.accountSelectionBoxHeight - 7.0f);
        this.scrollbar.setScrollAmount((this.mainMenuBase.accounts.size() + (showAddIcon ? 1 : 0)) * 16 + 4);
    }

    @Override
    public void handleElementMouse() {
        this.scrollbar.handleElementMouse();
    }

    public float getNewAccountButtonWidth(float currentWidth) {
        return 22.0f + currentWidth + 10.0f;
    }

}
