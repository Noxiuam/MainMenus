package cc.noxiuam.mainmenus.ui.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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

    public void renderIcon(
            ResourceLocation resourceLocation, float f, float f2, float f3, float f4) {
        float f5 = f3 / 2.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(f6 / f5, f7 / f5);
        GL11.glVertex2d(f, f2);
        GL11.glTexCoord2d(f6 / f5, (f7 + f5) / f5);
        GL11.glVertex2d(f, f2 + f4);
        GL11.glTexCoord2d((f6 + f5) / f5, (f7 + f5) / f5);
        GL11.glVertex2d(f + f3, f2 + f4);
        GL11.glTexCoord2d((f6 + f5) / f5, f7 / f5);
        GL11.glVertex2d(f + f3, f2);
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

    public void drawHorizontalLine(float f, float f2, float f3, int color) {
        if (f2 < f) {
            float f4 = f;
            f = f2;
            f2 = f4;
        }

        RenderUtil.drawRect(f, f3, f2 + 1.0f, f3 + 1.0f, color);
    }

    public void drawVerticalLine(float f, float f2, float f3, int color) {
        if (f3 < f2) {
            float f4 = f2;
            f2 = f3;
            f3 = f4;
        }

        RenderUtil.drawRect(f, f2 + 1.0f, f + 1.0f, f3, color);
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

    public void drawBoxWithOutLine(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        RenderUtil.drawRect(f, f2, f3, f4, n2);
        RenderUtil.drawRect(f - f5, f2 - f5, f, f4 + f5, n);
        RenderUtil.drawRect(f3, f2 - f5, f3 + f5, f4 + f5, n);
        RenderUtil.drawRect(f, f2 - f5, f3, f2, n);
        RenderUtil.drawRect(f, f4, f3, f4 + f5, n);
    }

    public static void startScissorBox(int n, int n2, int n3, int n4, float f, int n5) {
        int n6 = n4 - n2;
        int n7 = n3 - n;
        int n8 = n5 - n4;
        GL11.glScissor(
                (int) ((float) n * f),
                (int) ((float) n8 * f),
                (int) ((float) n7 * f),
                (int) ((float) n6 * f));
    }

}
