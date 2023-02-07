package cc.noxiuam.mainmenus.ui.font;

/**
 * CheatBreaker's char data for the font renderer.
 */
public class CharData {
    public int width;
    public int height;
    public int storedX;
    public int storedY;
    final CBFont font;

    protected CharData(CBFont sFont) {
        this.font = sFont;
    }
}
