package cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.element;

import cc.noxiuam.mainmenus.ui.AbstractElement;
import cc.noxiuam.mainmenus.ui.fade.impl.FloatFade;
import cc.noxiuam.mainmenus.ui.fade.impl.MinMaxFade;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Lunar Client's animated logo element.
 * All credits go to Moonsworth, LLC.
 */
public class AnimatedLogoElement extends AbstractElement {

    private static final ResourceLocation[] starLocations = new ResourceLocation[8];
    private final ResourceLocation origLunarLogo = new ResourceLocation("client/animatedlogo/128/logo_128_no_stars.png");

    private final TwinklingStar[] twinklingStars = new TwinklingStar[8];
    private final float[] twinkleCache = new float[8];

    private final boolean shadow;
    private boolean animating = true;

    public AnimatedLogoElement() {
        this(true);
    }

    public AnimatedLogoElement(boolean shadow) {
        for (int frame = 1; frame <= 8; ++frame) {
            if (starLocations[frame - 1] != null) continue;
            AnimatedLogoElement.starLocations[frame - 1] = new ResourceLocation("client/animatedlogo/128/logo_128_star_" + frame + ".png");
        }
        this.updateLogo();
        this.shadow = shadow;
    }

    /**
     * Update all the twinkling stars currently animating.
     */
    private void updateLogo() {
        for (int i = 1; i <= 8; ++i) {

            if (this.twinklingStars[i - 1] != null && !this.twinklingStars[i - 1].isZeroOrLess()) 
                this.animating = false;

            if (this.twinklingStars[i - 1] != null && this.twinklingStars[i - 1].isZeroOrLess()) continue;

            long l = ThreadLocalRandom.current().nextLong(4000L, 12000L);
            
            if (this.animating) 
                this.twinkleCache[i - 1] = Math.max(ThreadLocalRandom.current().nextFloat(), 0.8F);
            
            this.twinklingStars[i - 1] = new TwinklingStar(this, l);
        }
    }

    @Override
    protected void handleElementDraw(float x, float y, boolean hovering) {
        GlStateManager.pushMatrix();

        if (this.shadow) {
            GlStateManager.color(0.0F, 0.0F, 0.0F, 0.2f);
            RenderUtil.renderIcon(this.origLunarLogo, this.xPosition + 1.0F, this.yPosition + 1.0F, this.width, this.height);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtil.renderIcon(this.origLunarLogo, this.xPosition, this.yPosition, this.width, this.height);

        for (int i = 0; i < 8; ++i) {
            TwinklingStar currentStar = this.twinklingStars[i];
            if (!currentStar.isTimeNotAtZero()) {
                currentStar.startAnimation();
            }

            GlStateManager.pushMatrix();
            if (!currentStar.isZeroOrLess()) {
                this.updateLogo();
            }

            float starOpacity = currentStar.getRunTimeFadeLength();
            if (currentStar.isRunTimeOver() && this.animating) {
                this.animating = false;
            }

            if (this.animating) {
                starOpacity = Math.max(starOpacity, this.twinkleCache[i]);
            }

            // draw the animated shadow, if allowed of course.
            if (this.shadow) {
                GlStateManager.color(0.0F, 0.0F, 0.0F, starOpacity / 5.0F);
                RenderUtil.renderIcon(starLocations[i], this.xPosition + 1.0F, this.yPosition + 1.0F, this.width, this.height);
            }

            // draw the star with the correct opacity
            GlStateManager.color(1.0F, 1.0F, 1.0F, starOpacity);
            RenderUtil.renderIcon(starLocations[i], this.xPosition, this.yPosition, this.width, this.height);

            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
    }

    /**
     * The one of eight logos that twinkle on the main menu's center logo.
     */
    static class TwinklingStar extends FloatFade {
        
        public final AnimatedLogoElement logo;
        
        private final MinMaxFade runTime;
        private final MinMaxFade runLength;

        private TwinklingStar(AnimatedLogoElement logo, long l) {
            super((long) ((float) l));
            this.logo = logo;
            this.runTime = new MinMaxFade(Math.min((long) Math.min((float) l * 0.2F, 3000.0F), 1500L));
            this.runLength = new MinMaxFade(Math.min((long) ((float) l * 0.4F), 5000L));
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
                return Math.max(this.runTime.getFadeAmount(), 0.15F);
            }
            
            if (this.getRemainingTime() <= this.runLength.getDuration()) {
                if (!this.runLength.isTimeNotAtZero()) {
                    this.runLength.startAnimation();
                }
                return 1.0F - 0.85F * this.runLength.getFadeAmount();
            }
            
            return 1.0F;
        }
    }

}