package cc.noxiuam.mainmenus.ui.mainmenu.shared.element.button;

import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.fade.impl.ColorFade;
import lombok.Getter;
import lombok.Setter;

/**
 * General text button used by CheatBreaker & Lunar Client 2019
 */
@Getter
public class TextButton extends AbstractElement {

    private final String text;

    @Setter private ColorFade colorFade;

    public TextButton(String text) {
        this.text = text;
        this.colorFade = new ColorFade(-1879048193, -1);
    }

    @Override
    protected void handleElementDraw(float x, float y, boolean hovering) {
        FontRegistry.robotoBold14px.drawString(
                this.text,
                this.xPosition + 6.0f,
                this.yPosition + 6.0f,
                this.colorFade.getColor(this.isMouseInside(x, y) && hovering).getRGB()
        );
    }

}

