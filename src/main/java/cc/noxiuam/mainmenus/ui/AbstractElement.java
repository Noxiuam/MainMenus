package cc.noxiuam.mainmenus.ui;

import lombok.Getter;

/**
 * Credits: https://offlinecheatbreaker.com
 * (With permission)
 */
@Getter
public abstract class AbstractElement {

    protected float xPosition;
    protected float yPosition;
    protected float width;
    protected float height;

    public int yOffset;

    /** Checks if the mouse is inside the element. */
    public boolean isMouseInside(float x, float y) {
        return x > this.xPosition
                && x < this.xPosition + this.width
                && y > this.yPosition
                && y < this.yPosition + this.height;
    }

    /** Draws the element based on if the user is hovering on it or not. */
    public boolean drawElementHover(float mouseX, float mouseY, boolean hovering) {
        this.handleElementDraw(mouseX, mouseY, hovering);
        return this.isMouseInside(mouseX, mouseY);
    }

    /** Sets the size of the element. */
    public void setElementSize(float x, float y, float width, float height) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
    }

    /** Handles any updating for the element. */
    public void handleElementUpdate() {}

    /**
     * Handles closing of the element. (for example, I may need to close an audio stream on element
     * close.)
     */
    public void handleElementClose() {}

    /**
     * Handles key presses from a user.
     *
     * <p>
     *
     * @param c - The character a user typed.
     * @param n - The numerical value of c.
     */
    public void keyTyped(char c, int n) {}

    /** Handles mouse related things. (i.e. cursor movement, wheel movement, etc.) */
    public void handleElementMouse() {}

    /** Draws the element to the screen. */
    protected abstract void handleElementDraw(float mouseX, float mouseY, boolean hovering);

    /** Handles element mouse clicks. */
    public boolean handleElementMouseClicked(float mouseX, float mouseY, int button, boolean hovering) {
        return false;
    }

    /**
     * Handles mouse movement.
     */
    public void onMouseMoved(float mouseX, float mouseY, int button, boolean hovering) {}

    /**
     * Handles mouse clicking, it seems it is false by default, possibly for being an old method.
     */
    public boolean onMouseClick(float mouseX, float mouseY, int button) {
        return false;
    }

}

