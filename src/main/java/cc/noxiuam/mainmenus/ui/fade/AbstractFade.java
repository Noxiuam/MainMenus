package cc.noxiuam.mainmenus.ui.fade;

import lombok.Getter;
import lombok.Setter;

/**
 * Reconstructed from CheatBreaker, documentation will come later.
 */
@Getter
@Setter
public abstract class AbstractFade {
    protected final float color;
    protected long time;
    protected long duration;
    protected boolean running = true;
    protected float amount;
    protected long unknownLong;
    private boolean loopAnimation;
    private int runTime = 1;
    private int runLength = 1;
    private long unknownLong2;
    private boolean hovered;

    public AbstractFade(long duration, float color) {
        this.duration = duration;
        this.color = color;
    }

    protected abstract float getValue();

    public void startAnimation() {
        this.time = System.currentTimeMillis();
        this.running = true;
        this.unknownLong2 = 0L;
    }

    public void startAnimationFromStartOrEnd(float f) {
        this.time = System.currentTimeMillis();
        this.unknownLong2 = f == 0.0f ? 0L : (long) ((float) this.duration * (1.0f - f));
        this.running = true;
    }

    public void loopAnimation() {
        this.loopAnimation = true;
    }

    public boolean isTimeNotAtZero() {
        return this.time != 0L;
    }

    public boolean isOver() {
        return this.getTimeLeft() <= 0L && this.running;
    }

    public void reset() {
        this.time = 0L;
        this.runTime = 1;
    }

    public float inOutFade(boolean hovering) {
        if (hovering && !this.hovered) {
            this.hovered = true;
            this.startAnimationFromStartOrEnd(this.getFinalizedValue());
        } else if (this.hovered && !hovering) {
            this.hovered = false;
            this.startAnimationFromStartOrEnd(this.getFinalizedValue());
        }
        if (this.time == 0L) {
            return 0.0f;
        }
        float f = this.getFinalizedValue();
        return this.hovered ? f : 1.0f - f;
    }

    public boolean isZeroOrLess() {
        return this.time != 0L && this.getTimeLeft() > 0L;
    }

    private float getFinalizedValue() {
        if (this.time == 0L) {
            return 0.0f;
        }
        if (this.getTimeLeft() <= 0L) {
            return 1.0f;
        }
        return this.getValue();
    }

    public float getFadeAmount() {
        if (this.time == 0L) {
            return 0.0f;
        }
        if (this.isOver()) {
            if (this.loopAnimation || this.runLength >= 1 && this.runTime < this.runLength) {
                this.startAnimation();
                ++this.runTime;
            }
            return this.color;
        }
        if (this.running) {
            return this.getValue();
        }
        return this.amount;
    }

    public void stop() {
        this.running = false;
        this.amount = this.getValue();
        this.unknownLong = System.currentTimeMillis() - this.time;
    }

    public void run() {
        this.time = System.currentTimeMillis() - this.unknownLong;
        this.running = true;
    }

    public long getRemainingTime() {
        long l =
                this.running
                        ? this.getTimeLeft()
                        : System.currentTimeMillis()
                        - this.unknownLong
                        + this.duration
                        - System.currentTimeMillis();
        return Math.min(this.duration, Math.max(0L, l));
    }

    protected long getTimeLeft() {
        return this.time + this.duration - this.unknownLong2 - System.currentTimeMillis();
    }

    public long llIIllIlIlllllIlIllIIlIll() {
        return this.duration - this.getRemainingTime();
    }

    public long getUnknownLong() {
        return this.unknownLong;
    }
}
