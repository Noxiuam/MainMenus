package cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.element;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.mainmenu.data.Account;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@Getter @Setter
public class LegacyAccountButton extends AbstractElement {

    private String displayName;

    private ResourceLocation headIcon;

    public LegacyAccountButton(Account account) {
        this.displayName = account.getDisplayName();
        this.headIcon = MainMenus.INSTANCE.avatarUtil.getHeadIcon(account.getDisplayName());
    }

    @Override
    protected void handleElementDraw(float mouseX, float mouseY, boolean hovering) {

        RenderUtil.drawRect(
                this.xPosition,
                this.yPosition,
                this.xPosition + this.width,
                this.yPosition + this.height,
                this.isMouseInside(mouseX, mouseY) ? -15395563 : -14540254);

        int textColor = -1;
        if (this.isMouseInside(mouseX, mouseY)) {
            textColor = -3092272;
        }

        if (this.displayName.length() > 9) {
            float x = this.getXPosition() + this.width / 2 + 12;
            float y = this.yPosition + this.height / 2 - 4;
            FontRegistry.playRegular14px.drawCenteredString(this.displayName, x, y, textColor);
        } else {
            float x = this.xPosition + this.width / 2 + 12;
            float y = this.yPosition + this.height / 2 - 5;
            FontRegistry.playRegular16px.drawCenteredString(this.displayName, x, y, textColor);
        }

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(this.headIcon, 7.0f, this.xPosition + 10, this.yPosition + 5);
    }

}
