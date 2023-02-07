package cc.noxiuam.mainmenus.ui.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * RenderUtil from CheatBreaker.
 */
@UtilityClass
public class RenderUtil {

    public void renderIcon(ResourceLocation resourceLocation, float size, float x, float y) {
        float f4 = size * 2.0f;
        float f5 = size * 2.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;

        GlStateManager.enableBlend();
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(f6 / size, f7 / size);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2d(f6 / size, (f7 + size) / size);
        GL11.glVertex2d(x, y + f5);
        GL11.glTexCoord2d((f6 + size) / size, (f7 + size) / size);
        GL11.glVertex2d(x + f4, y + f5);
        GL11.glTexCoord2d((f6 + size) / size, f7 / size);
        GL11.glVertex2d(x + f4, y);
        GL11.glEnd();
        GlStateManager.disableBlend();
    }

    public void renderIcon(ResourceLocation resourceLocation, float x, float y, float width, float height) {
        float f5 = width / 2.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(f6 / f5, f7 / f5);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2d(f6 / f5, (f7 + f5) / f5);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2d((f6 + f5) / f5, (f7 + f5) / f5);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2d((f6 + f5) / f5, f7 / f5);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GlStateManager.disableBlend();
    }

    public void drawRect(float left, float top, float right, float bottom, int color) {
        float tempVar;

        if (left < right) {
            tempVar = left;
            left = right;
            right = tempVar;
        }

        if (top < bottom) {
            tempVar = top;
            top = bottom;
            bottom = tempVar;
        }

        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(left, bottom, 0.0D).color(r, g, b, a).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).color(r, g, b, a).endVertex();
        worldrenderer.pos(right, top, 0.0D).color(r, g, b, a).endVertex();
        worldrenderer.pos(left, top, 0.0D).color(r, g, b, a).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void drawHorizontalLine(float left, float right, float thickness, int color) {
        if (right < left) {
            float f4 = left;
            left = right;
            right = f4;
        }

        RenderUtil.drawRect(left, thickness, right + 1.0f, thickness + 1.0f, color);
    }

    public void drawVerticalLine(float left, float right, float thickness, int color) {
        if (thickness < right) {
            float tempThickness = right;
            right = thickness;
            thickness = tempThickness;
        }

        RenderUtil.drawRect(left, right + 1.0f, left + 1.0f, thickness, color);
    }

    public void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, top, 0.0).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, 0.0).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, bottom, 0.0).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public void drawGradientRectWithOutline(float left, float top, float right, float bottom, int outlineColor, int startColor, int endColor) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);

            RenderUtil.drawGradientRect(
                (left *= 2.0f) + 1.0f,
                (top *= 2.0f) + 1.0f,
                (right *= 2.0f) - 1.0f,
                (bottom *= 2.0f) - 1.0f,
                startColor,
                endColor);
        RenderUtil.drawVerticalLine(left, top + 1.0f, bottom - 2.0f, outlineColor);
        RenderUtil.drawVerticalLine(right - 1.0f, top + 1.0f, bottom - 2.0f, outlineColor);
        RenderUtil.drawHorizontalLine(left + 2.0f, right - 3.0f, top, outlineColor);
        RenderUtil.drawHorizontalLine(left + 2.0f, right - 3.0f, bottom - 1.0f, outlineColor);
        RenderUtil.drawHorizontalLine(left + 1.0f, left + 1.0f, top + 1.0f, outlineColor);
        RenderUtil.drawHorizontalLine(right - 2.0f, right - 2.0f, top + 1.0f, outlineColor);
        RenderUtil.drawHorizontalLine(right - 2.0f, right - 2.0f, bottom - 2.0f, outlineColor);
        RenderUtil.drawHorizontalLine(left + 1.0f, left + 1.0f, bottom - 2.0f, outlineColor);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }

    public void drawBoxWithOutLine(float left, float top, float right, float bottom, float padding, int outlineColor, int innerColor) {
        RenderUtil.drawRect(left, top, right, bottom, innerColor);
        RenderUtil.drawRect(left - padding, top - padding, left, bottom + padding, outlineColor);
        RenderUtil.drawRect(right, top - padding, right + padding, bottom + padding, outlineColor);
        RenderUtil.drawRect(left, top - padding, right, top, outlineColor);
        RenderUtil.drawRect(left, bottom, right, bottom + padding, outlineColor);
    }

    public static void startScissorBox(int x, int y, int width, int height, float scaledWidth, int scaledHeight) {
        int finalHeight1 = height - y;
        int finalWidth = width - x;
        int finalHeight2 = scaledHeight - height;

        GL11.glScissor(
                (int) ((float) x * scaledWidth), (int) ((float) finalHeight2 * scaledWidth),
                (int) ((float) finalWidth * scaledWidth), (int) ((float) finalHeight1 * scaledWidth)
        );
    }

}
