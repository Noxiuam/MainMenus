package cc.noxiuam.mainmenus.ui.fade.impl;

import cc.noxiuam.mainmenus.ui.fade.AbstractFade;

/**
 * Reconstructed from CheatBreaker, documentation will come later.
 */
public class FloatFade extends AbstractFade {

    public FloatFade(long duration) {
        super(duration, 1.0f);
    }

    public FloatFade(long duration, float f) {
        super(duration, f);
    }

    @Override
    protected float getValue() {
        return (float) (this.duration - this.getTimeLeft()) / (float) this.duration;
    }

}
