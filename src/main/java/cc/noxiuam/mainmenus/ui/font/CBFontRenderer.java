package cc.noxiuam.mainmenus.ui.font;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CBFontRenderer extends CBFont {
    private final char COLOR_CODE_START = (char) 167;

    protected CharData[] boldItalicChars = new CharData[256];
    protected CharData[] italicChars = new CharData[256];
    protected CharData[] boldChars = new CharData[256];

    private final int[] colorCode = new int[32];

    private final String validColorCodes = "0123456789abcdefklmnor";

    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    private static final Pattern formattingCodePattern = Pattern.compile("(?i)" + "§" + "[0-9A-FR]");

    public CBFontRenderer(ResourceLocation resourceLocation, float size) {
        super(resourceLocation, size);
        this.setupMinecraftColorCodes();
        this.setupBoldItalicIDs();
    }

    public float drawCenteredStringWithShadow(String var1, double var2, double var4, int var6, int color) {
        float shadowWidth = this.drawString(var1, var2 + 1.0, var4 + 1.0, color, false);
        return Math.max(shadowWidth, this.drawString(var1, var2, var4, var6, false));
    }

    public float drawStringWithShadow(String var1, double var2, double var4, int var6) {
        float shadowWidth = this.drawString(var1, var2 + 1.0, var4 + 1.0, var6, true);
        return Math.max(shadowWidth, this.drawString(var1, var2, var4, var6, false));
    }

    public float drawString(String var1, float var2, float var3, int color) {
        return this.drawString(var1, var2, var3, color, false);
    }

    public float drawCenteredString(String var1, float var2, float var3, int color) {
        return this.drawString(var1, var2 - (float) (this.getStringWidth(var1) / 2), var3, color);
    }

    public float drawCenteredStringWithShadow(String var1, float var2, float var3, int var4) {
        this.drawString(var1, (double) (var2 - (float) (this.getStringWidth(var1) / 2)) + 1.0, (double) var3 + 1.0, var4, true);
        return this.drawString(var1, var2 - (float) (this.getStringWidth(var1) / 2), var3, var4);
    }

    public float drawString(String var1, double var2, double var4, int color, boolean var7) {
        var2 -= 1.0;
        if (var1 == null) {
            return 0.0f;
        }

        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (var7) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }

        CharData[] var8 = this.charData;
        float var9 = (float) (color >> 24 & 0xFF) / 255.0f;
        boolean var10 = false;
        boolean var11 = false;
        boolean var12 = false;
        boolean var13 = false;
        boolean var14 = false;
        boolean var15 = true;
        var2 *= 2.0;
        var4 = (var4 - 3.0) * 2.0;
        if (var15) {
            GL11.glPushMatrix();
            GL11.glScaled(0.5, 0.5, 0.5);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f((float) (color >> 16 & 0xFF) / 255.0f, (float) (color >> 8 & 0xFF) / 255.0f, (float) (color & 0xFF) / 255.0f, var9);
            int var16 = var1.length();
            GL11.glEnable(3553);
//            System.out.println(this.tex.getGlTextureId());
            int oldTex = GL11.glGetInteger(32873);
            GL11.glBindTexture(3553, this.tex.getGlTextureId());
            for (int var17 = 0; var17 < var16; ++var17) {
                char var18 = var1.charAt(var17);
                if (var18 == this.COLOR_CODE_START && var17 < var16) {
                    int var19 = 21;
                    try {
                        var19 = "0123456789abcdefklmnor".indexOf(var1.charAt(var17 + 1));
                    } catch (Exception var21) {
                        var21.printStackTrace();
                    }
                    if (var19 < 16) {
                        var11 = false;
                        var12 = false;
                        var10 = false;
                        var14 = false;
                        var13 = false;
                        GL11.glBindTexture(3553, this.tex.getGlTextureId());
                        var8 = this.charData;
                        if (var19 < 0 || var19 > 15) {
                            var19 = 15;
                        }
                        if (var7) {
                            var19 += 16;
                        }
                        int var20 = this.colorCode[var19];
                        GL11.glColor4f((float) (var20 >> 16 & 0xFF) / 255.0f, (float) (var20 >> 8 & 0xFF) / 255.0f, (float) (var20 & 0xFF) / 255.0f, var9);
                    } else if (var19 == 16) {
                        var10 = true;
                    } else if (var19 == 17) {
                        var11 = true;
                        if (var12) {
                            GL11.glBindTexture(3553, this.texItalicBold.getGlTextureId());
                            var8 = this.boldChars;
                        } else {
                            GL11.glBindTexture(3553, this.texBold.getGlTextureId());
                            var8 = this.boldItalicChars;
                        }
                    } else if (var19 == 18) {
                        var13 = true;
                    } else if (var19 == 19) {
                        var14 = true;
                    } else if (var19 == 20) {
                        var12 = true;
                        if (var11) {
                            GL11.glBindTexture(3553, this.texItalicBold.getGlTextureId());
                            var8 = this.boldChars;
                        } else {
                            GL11.glBindTexture(3553, this.texItalic.getGlTextureId());
                            var8 = this.italicChars;
                        }
                    } else if (var19 == 21) {
                        var11 = false;
                        var12 = false;
                        var10 = false;
                        var14 = false;
                        var13 = false;
                        GL11.glColor4f((float) (color >> 16 & 0xFF) / 255.0f, (float) (color >> 8 & 0xFF) / 255.0f, (float) (color & 0xFF) / 255.0f, var9);
                        GL11.glBindTexture(3553, this.tex.getGlTextureId());
                        var8 = this.charData;
                    }
                    ++var17;
                    continue;
                }
                if (var18 >= var8.length || var18 < '\u0000') continue;
                GL11.glBegin(4);
                this.drawChar(var8, var18, (float) var2, (float) var4 + 6.0f);
                GL11.glEnd();
                if (var13) {
                    this.drawLine(var2, var4 + (double) (var8[var18].height / 2),
                            var2 + (double) var8[var18].width - 8.0, var4 +
                                    (double) (var8[var18].height / 2), 1.0f);
                }
                if (var14) {
                    this.drawLine(var2, var4 + (double) var8[var18].height - 2.0,
                            var2 + (double) var8[var18].width - 8.0, var4 +
                                    (double) var8[var18].height - 2.0, 1.0f);
                }
                var2 += var8[var18].width - 8 + this.charOffset;
            }
            GL11.glDisable(3042);
            GL11.glBindTexture(3553, oldTex);
            GL11.glHint(3155, 4352);
            GL11.glPopMatrix();
        }
        return (float) var2 / 2.0f;
    }

    public String setWrapWords(String string, double width) {
        return this.wrapWords(string, width, false);
    }

    public String wrapWords(String var1, double width, boolean var4) {
        StringBuilder var5 = new StringBuilder();
        float var6 = 0.0f;
        int var7 = var4 ? var1.length() - 1 : 0;
        int var8 = var4 ? -1 : 1;
        boolean var9 = false;
        boolean var10 = false;
        for (int var11 = var7; var11 >= 0 && var11 < var1.length() && var6 < (float) width; var11 += var8) {
            char var12 = var1.charAt(var11);
            double var13 = this.getStringWidth(String.valueOf(var12));
            if (var9) {
                var9 = false;
                if (var12 != 'l' && var12 != 'L') {
                    if (var12 == 'r' || var12 == 'R') {
                        var10 = false;
                    }
                } else {
                    var10 = true;
                }
            } else if (var13 < 0.0) {
                var9 = true;
            } else {
                var6 = (float) ((double) var6 + var13);
                if (var10) {
                    var6 += 1.0f;
                }
            }
            if (var6 > (float) width) break;
            if (var4) {
                var5.insert(0, var12);
                continue;
            }
            var5.append(var12);
        }
        return var5.toString();
    }

    @Override
    public int getStringWidth(String var1) {
        if (var1 == null) {
            return 0;
        }
        int var2 = 0;
        CharData[] var3 = this.charData;
        boolean var4 = false;
        boolean var5 = false;
        int var6 = var1.length();
        for (int var7 = 0; var7 < var6; ++var7) {
            char var8 = var1.charAt(var7);
            if (var8 == this.COLOR_CODE_START && var7 < var6) {
                int var9 = "0123456789abcdefklmnor".indexOf(var8);
                if (var9 < 16) {
                    var4 = false;
                    var5 = false;
                } else if (var9 == 17) {
                    var4 = true;
                    var3 = var5 ? this.boldChars : this.boldItalicChars;
                } else if (var9 == 20) {
                    var5 = true;
                    var3 = var4 ? this.boldChars : this.italicChars;
                } else if (var9 == 21) {
                    var4 = false;
                    var5 = false;
                    var3 = this.charData;
                }
                ++var7;
                continue;
            }
            if (var8 >= var3.length || var8 < '\u0000') continue;
            var2 += var3[var8].width - 8 + this.charOffset;
        }
        return var2 / 2;
    }

    @Override
    public void setAntiAlias(boolean var1) {
        super.setAntiAlias(var1);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean var1) {
        super.setFractionalMetrics(var1);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldChars);
    }

    private void drawLine(double var1, double var3, double var5, double var7, float var9) {
        GL11.glDisable(3553);
        GL11.glLineWidth(var9);
        GL11.glBegin(1);
        GL11.glVertex2d(var1, var3);
        GL11.glVertex2d(var5, var7);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    private String formatColor(String input) {
        Matcher matcher = formattingCodePattern.matcher(input);
        String group = "";

        while (matcher.find()) {
            group = matcher.group();
        }

        return group;
    }

    public List<String> wrapWords(String text, double width) {
        ArrayList<String> finalWords = new ArrayList<>();
        if ((double) this.getStringWidth(text) > width) {
            String[] words = text.split(" ");
            String currentWord = "";
            int lastColorCode = 65535;
            for (int i = 0; i < words.length; ++i) {
                String word = words[i];
                for (int var12 = 0; var12 < word.toCharArray().length; ++var12) {
                    char var13 = word.toCharArray()[var12];
                    if (var13 != this.COLOR_CODE_START || var12 >= word.toCharArray().length - 1) continue;
                    lastColorCode = word.toCharArray()[var12 + 1];
                }
                StringBuilder stringBuilder = new StringBuilder();
                if ((double) this.getStringWidth(stringBuilder.append(currentWord).append(word).append(" ").toString()) < width) {
                    currentWord = currentWord + word + " ";
                    continue;
                }
                finalWords.add(currentWord);
                currentWord = this.COLOR_CODE_START + lastColorCode + word + " ";
            }
            if (currentWord.length() > 0) {
                if ((double) this.getStringWidth(currentWord) < width) {
                    finalWords.add(this.COLOR_CODE_START + lastColorCode + currentWord + " ");
                    currentWord = "";
                } else {
                    for (String s : this.formatString(currentWord, width)) {
                        finalWords.add(s);
                    }
                }
            }
        } else {
            finalWords.add(text);
        }
        return finalWords;
    }

    public List<String> formatString(String var1, double var2) {
        ArrayList<String> finalWords = new ArrayList<String>();
        String currentWord = "";
        int lastColorCode = 65535;
        char[] chars = var1.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if (c == this.COLOR_CODE_START && i < chars.length - 1) {
                lastColorCode = chars[i + 1];
            }
            StringBuilder stringBuilder = new StringBuilder();
            if ((double) this.getStringWidth(stringBuilder.append(currentWord).append(c).toString()) < var2) {
                currentWord = currentWord + c;
                continue;
            }
            finalWords.add(currentWord);
            currentWord = this.COLOR_CODE_START + lastColorCode + String.valueOf(c);
        }
        if (currentWord.length() > 0) {
            finalWords.add(currentWord);
        }
        return finalWords;
    }

    private void setupMinecraftColorCodes() {
        for (int var1 = 0; var1 < 32; ++var1) {
            int var2 = (var1 >> 3 & 1) * 85;
            int var3 = (var1 >> 2 & 1) * 170 + var2;
            int var4 = (var1 >> 1 & 1) * 170 + var2;
            int var5 = (var1 & 1) * 170 + var2;
            if (var1 == 6) {
                var3 += 85;
            }
            if (var1 >= 16) {
                var3 /= 4;
                var4 /= 4;
                var5 /= 4;
            }
            this.colorCode[var1] = (var3 & 0xFF) << 16 | (var4 & 0xFF) << 8 | var5 & 0xFF;
        }
    }
}
