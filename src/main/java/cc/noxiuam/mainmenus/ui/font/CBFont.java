package cc.noxiuam.mainmenus.ui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * CheatBreaker's font object.
 */
public class CBFont {
    private final float imgSize = 1048;
    protected CharData[] charData = new CharData[256];
    protected java.awt.Font font;
    protected boolean antiAlias;
    protected boolean fractionalMetrics;
    protected int fontHeight = -1;
    protected int charOffset = 0;
    protected DynamicTexture tex;

    public CBFont(ResourceLocation resourceLocation, float size) {
        java.awt.Font font;
        try {
            InputStream inputStream = Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getResourceManager() == null ? getResourceStreamerino(resourceLocation) : Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
            font = java.awt.Font.createFont(0, inputStream).deriveFont(size);
        } catch (Exception exception) {
            font = new java.awt.Font("Arial", 0, (int) size);
        }
        this.font = font;
        this.antiAlias = true;
        this.fractionalMetrics = true;
        this.tex = this.setupTexture(this.font, this.antiAlias, this.fractionalMetrics, this.charData);
    }

    protected DynamicTexture setupTexture(java.awt.Font font, boolean bl, boolean bl2, CharData[] arrcharData) {
        BufferedImage bufferedImage = this.generateFontImage(font, bl, bl2, arrcharData);
        try {
            return new DynamicTexture(bufferedImage);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static InputStream getResourceStreamerino(ResourceLocation location) {
        return DefaultResourcePack.class.getResourceAsStream("/assets/" + location.getResourceDomain() + "/" + location.getResourcePath());
    }

    private BufferedImage generateFontImage(java.awt.Font font, boolean bl, boolean bl2, CharData[] arrcharData) {
        int n = (int) this.imgSize;
        BufferedImage bufferedImage = new BufferedImage(n, n, 2);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, n, n);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, bl2 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, bl ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, bl ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n2 = 0;
        int n3 = 0;
        int n4 = 1;
        for (int i = 0; i < arrcharData.length; ++i) {
            char c = (char) i;
            CharData charData = new CharData(this);
            Rectangle2D rectangle2D = fontMetrics.getStringBounds(String.valueOf(c), graphics2D);
            charData.width = rectangle2D.getBounds().width + 8;
            charData.height = rectangle2D.getBounds().height;
            if (n3 + charData.width >= n) {
                n3 = 0;
                n4 += n2;
                n2 = 0;
            }
            if (charData.height > n2) {
                n2 = charData.height;
            }
            charData.storedX = n3;
            charData.storedY = n4;
            if (charData.height > this.fontHeight) {
                this.fontHeight = charData.height;
            }
            arrcharData[i] = charData;
            graphics2D.drawString(String.valueOf(c), n3 + 2, n4 + fontMetrics.getAscent());
            n3 += charData.width;
        }
        return bufferedImage;
    }

    protected void drawChar(CharData[] arrcharData, char c, float f, float f2) {
        try {
            this.drawQuad(f, f2, arrcharData[c].width, arrcharData[c].height, arrcharData[c].storedX, arrcharData[c].storedY, arrcharData[c].width, arrcharData[c].height);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void drawQuad(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float f9 = f5 / this.imgSize;
        float f10 = f6 / this.imgSize;
        float f11 = f7 / this.imgSize;
        float f12 = f8 / this.imgSize;
        GL11.glTexCoord2f(f9 + f11, f10);
        GL11.glVertex2d(f + f3, f2);
        GL11.glTexCoord2f(f9, f10);
        GL11.glVertex2d(f, f2);
        GL11.glTexCoord2f(f9, f10 + f12);
        GL11.glVertex2d(f, f2 + f4);
        GL11.glTexCoord2f(f9, f10 + f12);
        GL11.glVertex2d(f, f2 + f4);
        GL11.glTexCoord2f(f9 + f11, f10 + f12);
        GL11.glVertex2d(f + f3, f2 + f4);
        GL11.glTexCoord2f(f9 + f11, f10);
        GL11.glVertex2d(f + f3, f2);
    }

    public int getHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public int getStringWidth(String string) {
        int n = 0;
        for (char c : string.toCharArray()) {
            if (c >= this.charData.length || c < '\u0000') continue;
            n += this.charData[c].width - 8 + this.charOffset;
        }
        return n / 2;
    }

    public void setAntiAlias(boolean bl) {
        if (this.antiAlias != bl) {
            this.antiAlias = bl;
            this.tex = this.setupTexture(this.font, bl, this.fractionalMetrics, this.charData);
        }
    }

    public void setFractionalMetrics(boolean bl) {
        if (this.fractionalMetrics != bl) {
            this.fractionalMetrics = bl;
            this.tex = this.setupTexture(this.font, this.antiAlias, bl, this.charData);
        }
    }

    public java.awt.Font getFont() {
        return this.font;
    }

    public void setFont(java.awt.Font font) {
        this.font = font;
        this.tex = this.setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
    }
}
