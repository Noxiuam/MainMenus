package cc.noxiuam.mainmenus.ui;

import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * CheatBreaker's scrollable element.
 * Untested if this actually works.
 */
public class ScrollableElement extends AbstractElement {

    @Getter
    protected final AbstractElement abstractElement;

    protected double internalScrollAmount;

    @Getter
    protected float position;
    @Setter
    protected float scrollAmount;
    protected float oldTranslateY;
    private float someFloaty;

    @Getter
    protected boolean hovering;
    protected boolean drawing;
    @Getter
    private boolean buttonHeld;

    public ScrollableElement(AbstractElement element) {
        this.abstractElement = element;
    }

    public boolean isMouseInside(float x, float y) {
        return x >= this.x && x <= this.x + this.width && y > this.y && y < this.y + this.height;
    }

    public void handleElementDraw(float mouseX, float mouseY, boolean hovering) {
        this.drawing = true;
        GlStateManager.popMatrix();
        boolean atBottom = this.isAtBottom();

        if (this.hovering && (!Mouse.isButtonDown(0) || !this.isMouseInside(mouseX, mouseY) || !hovering)) {
            this.hovering = false;
        }

        if (this.buttonHeld && !Mouse.isButtonDown(0)) {
            this.buttonHeld = false;
        }

        float elementHeight = this.height;
        float elementScrollAmount = this.scrollAmount;
        float correctedHeight = elementHeight / elementScrollAmount * (float) 100;
        float calculatedHeight = elementHeight / (float) 100 * correctedHeight;
        float calculatedPosition = this.position / (float) 100 * correctedHeight;
        float yPos;

        if (Mouse.isButtonDown(0) && this.buttonHeld) {
            yPos = mouseY - this.y;
            float yByHeight = yPos / this.height;
            this.position = -(this.scrollAmount * yByHeight) + calculatedHeight / 2.0F;
        }

        if (atBottom) {
            yPos = this.height;
            boolean elementHovered = mouseX >= this.x && mouseX <= this.x + this.width && mouseY > this.y - calculatedPosition && mouseY < this.y + calculatedHeight - calculatedPosition;

            if (this.hovering) {
                if (this.position != this.oldTranslateY && this.oldTranslateY != calculatedHeight / 2.0F && this.oldTranslateY != calculatedHeight / 2.0F + -this.scrollAmount + yPos) {
                    if (mouseY > this.y + calculatedHeight - calculatedHeight / (float) 4 - calculatedPosition) {
                        this.position -= elementScrollAmount / (float) 7;
                    } else if (mouseY < this.y + calculatedHeight / (float) 4 - calculatedPosition) {
                        this.position += elementScrollAmount / (float) 7;
                    }

                    this.oldTranslateY = this.position;
                } else if (mouseY > this.y + calculatedHeight - calculatedHeight / (float) 4 - calculatedPosition || mouseY < this.y + calculatedHeight / (float) 4 - calculatedPosition) {
                    this.oldTranslateY = 1.0F;
                }
            }

            if (this.position < -this.scrollAmount + yPos) {
                this.position = -this.scrollAmount + yPos;
                this.internalScrollAmount = 0.0D;
            }

            if (this.position > 0.0F) {
                this.position = 0.0F;
                this.internalScrollAmount = 0.0D;
            }

            RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -13158601);
            RenderUtil.drawRect(this.x, this.y - calculatedPosition, this.x + this.width, this.y + calculatedHeight - calculatedPosition, !elementHovered && !this.hovering ? -4180940 : -52429);
        }

        if (!atBottom && this.position != 0.0F) {
            this.position = 0.0F;
        }
    }

    public void drawScrollable(float f, float f2, boolean bl) {
        if (bl && (this.abstractElement == null || this.abstractElement.isMouseInside(f, f2)) && this.internalScrollAmount != 0.0) {
            this.position = (float) ((double) this.position + this.internalScrollAmount / (double) 8);
            this.internalScrollAmount = 0.0;
        }

        if (this.drawing) {
            if (this.position < -this.scrollAmount + this.height) {
                this.position = -this.scrollAmount + this.height;
                this.internalScrollAmount = 0.0;
            }
            if (this.position > 0.0f) {
                this.position = 0.0f;
                this.internalScrollAmount = 0.0;
            }
        }

        GlStateManager.pushMatrix();
        GL11.glTranslatef(0.0f, this.position, 0.0f);
    }

    public boolean isAtBottom() {
        return this.scrollAmount > this.height;
    }

    public void handleElementMouseClicked(float mouseX, float mouseY, int button, boolean hovering) {
        if (this.isMouseInside(mouseX, mouseY) && hovering) {
            this.someFloaty = mouseY - this.y;
            this.buttonHeld = true;
        }
    }

    public void handleElementMouse() {
        int wheelPosition = Mouse.getEventDWheel();
        if (wheelPosition != 0 && this.scrollAmount >= this.height) {
            this.internalScrollAmount += (float) wheelPosition / 1.75f;
        }
    }

}