package cc.noxiuam.mainmenus.ui.fade.impl;

public class MinMaxFade extends FloatFade {

    public MinMaxFade(long l) {
        super(l);
    }

    @Override
    protected float getValue() {
        float value = super.getValue();
        return (double) value < 0.5 ? 2.0f * value * value : -1.0f + (4.0f - 2.0f * value) * value;
    }

}