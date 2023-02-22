package cc.noxiuam.mainmenus.ui;

import lombok.Getter;

/**
 * Credits: https://offlinecheatbreaker.com
 * (With permission)
 */
@Getter
public abstract class AbstractElement {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public int yOffset;

    /** Checks if the mouse is inside the element. */
    public boolean isMouseInside(float x, float y) {
        return x > this.x
                && x < this.x + this.width
                && y > this.y
                && y < this.y + this.height;
    }

    /** Draws the element based on if the user is hovering on it or not. */
    public boolean drawElementHover(float mouseX, float mouseY, boolean hovering) {
        this.handleElementDraw(mouseX, mouseY, hovering);
        return this.isMouseInside(mouseX, mouseY);
    }

    /** Sets the size of the element. */
    public void setElementSize(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /** Draws the element to the screen. */
    protected abstract void handleElementDraw(float mouseX, float mouseY, boolean hovering);

    /**
     * Handles element mouse clicks.
     */
    public void handleElementMouseClicked(float mouseX, float mouseY, int button, boolean hovering) {
    }

    /** Handles mouse related things. (i.e. cursor movement, wheel movement, etc.) */
    public void handleElementMouse() {}

}

