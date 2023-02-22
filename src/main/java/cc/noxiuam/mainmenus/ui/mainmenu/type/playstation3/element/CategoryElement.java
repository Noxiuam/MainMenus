package cc.noxiuam.mainmenus.ui.mainmenu.type.playstation3.element;

import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.font.CBFontRenderer;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import lombok.AllArgsConstructor;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@AllArgsConstructor
public class CategoryElement extends AbstractElement {

    private String name;
    private ResourceLocation icon;
    private int iconWidth;
    private int iconHeight;

    @Setter private boolean selected;

    private final CBFontRenderer fontRenderer =
            new CBFontRenderer(new ResourceLocation("PS3"), 15.0F);

    @Override
    protected void handleElementDraw(float mouseX, float mouseY, boolean hovering) {
        float fontW = this.fontRenderer.getStringWidth(this.name);
        float fontH = this.fontRenderer.getHeight();

        GlStateManager.pushMatrix();

        GlStateManager.color(1.0F, 1.0F, 1.0F, this.selected ? 1.0F : 0.7F);
//        if (this.selected) {
//            GlStateManager.scale(this.iconWidth, this.iconHeight, 0.0F);
//        } else {
//            GlStateManager.scale(this.iconWidth / 2F, this.iconHeight / 2F, 0.0F);
//        }
        RenderUtil.renderIcon(this.icon, this.x, this.y, this.iconWidth, this.iconHeight);

        GlStateManager.popMatrix();

        if (this.selected) {
            this.fontRenderer.drawCenteredStringWithShadow(
                    this.name, this.x + fontW / 2, this.y + this.iconHeight + fontH, -1
            );
        }

    }

}
