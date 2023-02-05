package cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.element;

import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.fade.impl.FloatFade;
import cc.noxiuam.mainmenus.ui.fade.impl.MinMaxFade;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.ThreadLocalRandom;

public class AnimatedLogoElement extends AbstractElement {

    private static final ResourceLocation[] logoLocations = new ResourceLocation[8];
    private final ResourceLocation origLunarLogo =
            new ResourceLocation("client/animatedlogo/128/logo_128_no_stars.png");
    private final TwinklingLunarLogo[] logos = new TwinklingLunarLogo[8];
    private final float[] twinkle = new float[8];
    private final boolean shadow;
    private boolean animating = true;

    public AnimatedLogoElement() {
        this(true);
    }

    public AnimatedLogoElement(boolean shadow) {
        for (int frame = 1; frame <= 8; ++frame) {
            if (logoLocations[frame - 1] != null) continue;
            AnimatedLogoElement.logoLocations[frame - 1] = new ResourceLocation("client/animatedlogo/128/logo_128_star_" + frame + ".png");
        }
        this.updateLogo();
        this.shadow = shadow;
    }

    private void updateLogo() {
        for (int i = 1; i <= 8; ++i) {
            if (this.logos[i - 1] != null && !this.logos[i - 1].isZeroOrLess()) {
                this.animating = false;
            }
            if (this.logos[i - 1] != null && this.logos[i - 1].isZeroOrLess()) continue;
            long l = ThreadLocalRandom.current().nextLong(4000L, 12000L);
            if (this.animating) {
                this.twinkle[i - 1] = Math.max(ThreadLocalRandom.current().nextFloat(), 0.8f);
            }
            this.logos[i - 1] = new TwinklingLunarLogo(this, l);
        }
    }

    @Override
    public void setElementSize(float f, float f2, float f3, float f4) {
        super.setElementSize(f, f2, f3, f4);
    }

    @Override
    public void handleElementUpdate() {
        this.updateLogo();
    }

    @Override
    protected void handleElementDraw(float x, float y, boolean hovering) {
        GlStateManager.pushMatrix();
        if (this.shadow) {
            GlStateManager.color(0.0f, 0.0f, 0.0f, 0.2f);
            RenderUtil.renderIcon(
                    this.origLunarLogo,
                    this.xPosition + 1.0f,
                    this.yPosition + 1.0f,
                    this.width,
                    this.height);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(
                this.origLunarLogo, this.xPosition, this.yPosition, this.width, this.height);
        for (int i = 0; i < 8; ++i) {
            TwinklingLunarLogo logo = this.logos[i];
            if (!logo.isTimeNotAtZero()) {
                logo.startAnimation();
            }
            GlStateManager.pushMatrix();
            if (!logo.isZeroOrLess()) {
                this.updateLogo();
            }
            float f3 = logo.getRunTimeFadeLength();
            if (logo.isRunTimeOver() && this.animating) {
                this.animating = false;
            }
            if (this.animating) {
                f3 = Math.max(f3, this.twinkle[i]);
            }
            if (this.shadow) {
                GlStateManager.color(0.0f, 0.0f, 0.0f, f3 / 5.0f);
                RenderUtil.renderIcon(
                        logoLocations[i],
                        this.xPosition + 1.0f,
                        this.yPosition + 1.0f,
                        this.width,
                        this.height);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, f3);
            RenderUtil.renderIcon(
                    logoLocations[i], this.xPosition, this.yPosition, this.width, this.height);
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }

    static class TwinklingLunarLogo extends FloatFade {
        public final AnimatedLogoElement loopAnimation;
        private final MinMaxFade runTime;
        private final MinMaxFade runLength;

        private TwinklingLunarLogo(AnimatedLogoElement lunarLogoElement, long l) {
            super((long) ((float) l));
            this.loopAnimation = lunarLogoElement;
            this.runTime =
                    new MinMaxFade(Math.min((long) Math.min((float) l * 0.2f, 3000.0f), 1500L));
            this.runLength = new MinMaxFade(Math.min((long) ((float) l * 0.4f), 5000L));
        }

        @Override
        public void startAnimation() {
            super.startAnimation();
            if (!this.runTime.isTimeNotAtZero()) {
                this.runTime.startAnimation();
            }
        }

        boolean isRunTimeOver() {
            return this.runTime.isOver();
        }

        float getRunTimeFadeLength() {
            if (!this.runTime.isTimeNotAtZero()) {
                this.runTime.startAnimation();
            }
            if (this.runTime.isZeroOrLess()) {
                return Math.max(this.runTime.getFadeAmount(), 0.15f);
            }
            if (this.getRemainingTime() <= this.runLength.getDuration()) {
                if (!this.runLength.isTimeNotAtZero()) {
                    this.runLength.startAnimation();
                }
                return 1.0f - 0.85f * this.runLength.getFadeAmount();
            }
            return 1.0f;
        }
    }

}