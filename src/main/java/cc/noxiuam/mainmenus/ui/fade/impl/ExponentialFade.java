package cc.noxiuam.mainmenus.ui.fade.impl;

/**
 * Reconstructed from CheatBreaker, documentation will come later.
 */
public class ExponentialFade extends FloatFade {

    public ExponentialFade(long duration) {
        super(duration);
    }

    @Override
    public float getValue() {
        float value = super.getValue();
        return (float) Math.pow(value * (2.0f - value), 1.0);
    }

}
