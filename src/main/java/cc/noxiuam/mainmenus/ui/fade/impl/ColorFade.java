package cc.noxiuam.mainmenus.ui.fade.impl;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * Reconstructed from CheatBreaker, documentation will come later.
 */
public class ColorFade extends ExponentialFade {
    @Getter @Setter private int startColor;
    @Getter @Setter private int endColor;
    private boolean started;
    private Color endColorObj;
    private Color startColorObj;

    public ColorFade(long fadeDuration, int startColor, int endColor) {
        super(fadeDuration);
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public ColorFade(int startColor, int endColor) {
        this(175L, startColor, endColor);
    }

    public Color getColor(boolean criteria) {
        Color color = new Color(criteria ? this.endColor : this.startColor, true);
        if (criteria && !this.started) {
            this.started = true;
            this.endColorObj = new Color(this.startColor, true);
            this.startColorObj = new Color(this.endColor, true);
            this.startAnimation();
        } else if (this.started && !criteria) {
            this.started = false;
            this.endColorObj = new Color(this.endColor, true);
            this.startColorObj = new Color(this.startColor, true);
            this.startAnimation();
        }
        if (this.isZeroOrLess()) {
            float f = super.getFadeAmount();
            int n =
                    (int)
                            Math.abs(
                                    f * (float) this.startColorObj.getRed()
                                            + (1.0f - f) * (float) this.endColorObj.getRed());
            int n2 =
                    (int)
                            Math.abs(
                                    f * (float) this.startColorObj.getGreen()
                                            + (1.0f - f) * (float) this.endColorObj.getGreen());
            int n3 =
                    (int)
                            Math.abs(
                                    f * (float) this.startColorObj.getBlue()
                                            + (1.0f - f) * (float) this.endColorObj.getBlue());
            int n4 =
                    (int)
                            Math.abs(
                                    f * (float) this.startColorObj.getAlpha()
                                            + (1.0f - f) * (float) this.endColorObj.getAlpha());
            color = new Color(n, n2, n3, n4);
        }
        return color;
    }
}