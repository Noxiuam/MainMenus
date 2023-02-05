package cc.noxiuam.mainmenus.ui.mainmenu;

import cc.noxiuam.mainmenus.ui.AbstractGui;
import cc.noxiuam.mainmenus.ui.mainmenu.data.Account;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.LegacyCBMainMenuBase;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractMainMenuBase extends AbstractGui {

    private DynamicTexture viewportTexture;

    private ResourceLocation backgroundTexture;
    public ResourceLocation[] titlePanoramaPaths;

    private static int rotationAngle = 4100;

    public String activeAccountId = "";

    public final List<Account> accounts = new ArrayList<>();

    public final File accountsFile =
            new File(System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator + "launcher_accounts.json");

    @Override
    public void initGui() {
        super.initGui();

        this.loadAccounts();

        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture =
                this.mc
                        .getTextureManager()
                        .getDynamicTextureLocation("background", viewportTexture);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++rotationAngle;
    }

    @Override
    protected void keyTyped(char c, int n) throws IOException {
        if (n == Keyboard.KEY_ESCAPE) {
            return;
        }

        super.keyTyped(c, n);
    }

    public void playClick() {
        this.mc
                .getSoundHandler()
                .playSound(
                        PositionedSoundRecord.create(
                                new ResourceLocation("gui.button.press"), 1.0f));
    }

    public boolean login(String username) {
        try {
            Account potentialAccount = null;

            for (Account loadedAccount : this.accounts) {
                if (loadedAccount.getDisplayName().equals(username)) {
                    potentialAccount = loadedAccount;
                }
            }

            if (potentialAccount != null) {
                if (potentialAccount.getUuid()
                        .equalsIgnoreCase(Minecraft.getMinecraft().getSession().getPlayerID())) {
                    return false;
                }

                Minecraft.getMinecraft()
                        .getSoundHandler()
                        .playSound(
                                PositionedSoundRecord.create(
                                        new ResourceLocation("gui.button.press"), 1.0F));

                if (this.accountsFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(this.accountsFile))) {

                        JsonObject fileObj = new JsonParser().parse(reader).getAsJsonObject();

                        Set<Map.Entry<String, JsonElement>> accounts = fileObj.getAsJsonObject("accounts").entrySet();

                        for (Map.Entry<String, JsonElement> accountElement : accounts) {

                            JsonObject accountObj = accountElement.getValue().getAsJsonObject();

                            if (!accountObj.has("accessToken")
                                    || !accountObj.has("localId")
                                    || !accountObj.has("minecraftProfile")) {
                                continue;
                            }

                            String accessToken = accountObj.get("accessToken").getAsString();
                            JsonObject minecraftProfileObject = accountObj.getAsJsonObject("minecraftProfile");

                            if (!minecraftProfileObject.has("id") || !minecraftProfileObject.has("name")) continue;
                            String uuid = minecraftProfileObject.get("id").getAsString();
                            String displayName = minecraftProfileObject.get("name").getAsString();

                            if (!fileObj.has("activeAccountLocalId")) continue;
                            this.activeAccountId = fileObj.get("activeAccountLocalId").getAsString();

                            if (displayName == null || !displayName.equals(username)) {
                                continue;
                            }

                            Session newSession = new Session(displayName, uuid, accessToken, "mojang");

                            this.setSession(newSession);
                            return true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Credit: https://github.com/The-Fireplace-Minecraft-Mods/In-Game-Account-Switcher/blob/archive/1.8/src/main/java/com/github/mrebhan/ingameaccountswitcher/MR.java
     */
    public void setSession(Session sessionIn) {
        Class<? extends Minecraft> mc = Minecraft.getMinecraft().getClass();

        try {
            Field session = null;

            for (Field f : mc.getDeclaredFields()) {
                if (f.getType().isInstance(sessionIn)) {
                    session = f;
                    System.out.println("Found field " + f + ", injecting...");
                }
            }

            if (session == null) {
                throw new IllegalStateException("No field of type " + Session.class.getCanonicalName() + " declared.");
            }

            session.setAccessible(true);
            session.set(Minecraft.getMinecraft(), sessionIn);
            session.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadAccounts() {
        ArrayList<HashMap<String, String>> accounts = new ArrayList<>();
        if (this.accountsFile.exists()) {
            try {
                FileReader launcherProfiles = new FileReader(this.accountsFile);
                JsonElement elements = new JsonParser().parse(launcherProfiles);
                Iterator<Map.Entry<String, JsonElement>> iterator =
                        elements.getAsJsonObject().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, JsonElement> entry = iterator.next();
                    for (Map.Entry<String, JsonElement> var4 :
                            entry.getValue().getAsJsonObject().entrySet()) {
                        HashMap<String, String> var5 = new HashMap<>();
                        for (Map.Entry<String, JsonElement> var7 :
                                var4.getValue().getAsJsonObject().entrySet()) {
                            if (var7.getKey().equalsIgnoreCase("minecraftProfile")) {
                                for (Map.Entry<String, JsonElement> o1 :
                                        var7.getValue().getAsJsonObject().entrySet()) {
                                    if (o1.getKey().equals("id")) {
                                        var5.put("uuid", o1.getValue().getAsString());
                                    }
                                    if (o1.getKey().equals("name")) {
                                        var5.put("displayName", o1.getValue().getAsString());
                                    }
                                }
                            } else {
                                if (!var7.getKey().equalsIgnoreCase("username")
                                        && !var7.getKey().equalsIgnoreCase("name")
                                        && !var7.getKey().equalsIgnoreCase("id")
                                        && !var7.getKey().equalsIgnoreCase("accessToken")) {
                                    continue;
                                }
                                var5.put(var7.getKey(), var7.getValue().getAsString());
                            }
                        }
                        accounts.add(var5);
                    }
                    while (iterator.hasNext()) {
                        Object o2 = iterator.next();
                        Map.Entry var10 = (Map.Entry) o2;
                        if (var10.getKey().equals("mojangClientToken")) {
                            JsonPrimitive clientTokenPrim = (JsonPrimitive) var10.getValue();
                            this.activeAccountId = clientTokenPrim.getAsString();
                        }
                    }
                }
            } catch (Exception ignored) {
            }

            this.accounts.clear();

            for (Map<String, String> account : accounts) {

                Account var22 =
                        new Account(
                                account.get("username"),
                                this.activeAccountId,
                                account.get("accessToken"),
                                account.get("displayName"),
                                account.get("uuid"));
                this.accounts.add(var22);

            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (!(this instanceof LegacyCBMainMenuBase)) {
            GlStateManager.disableAlpha();
            this.renderSkybox(mouseX, mouseY, partialTicks);
            GlStateManager.enableAlpha();
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void renderSkybox(int var1, int var2, float var3) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GL11.glViewport(0, 0, 256, 256);
        this.drawPanorama(var1, var2, var3);
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.mc.getFramebuffer().bindFramebuffer(true);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);

        float var5 =
                this.width > this.height
                        ? 120.0f / (float) this.width
                        : 120.0f / (float) this.height;
        float var6 = (float) this.height * var5 / 256.0f;
        float var7 = (float) this.width * var5 / 256.0f;
        float var8 = this.width;
        float var9 = this.height;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

        worldrenderer
                .pos(0.0, var9, zLevel)
                .tex(0.5f - var6, 0.5f + var7)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .endVertex();
        worldrenderer
                .pos(var8, var9, zLevel)
                .tex(0.5f - var6, 0.5f - var7)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .endVertex();
        worldrenderer
                .pos(var8, 0.0, zLevel)
                .tex(0.5f + var6, 0.5f - var7)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .endVertex();
        worldrenderer
                .pos(0.0, 0.0, zLevel)
                .tex(0.5f + var6, 0.5f + var7)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .endVertex();
        tessellator.draw();
    }

    private void drawPanorama(int var1, int var2, float var3) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glMatrixMode(5889);
        GlStateManager.pushMatrix();
        GL11.glLoadIdentity();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GL11.glMatrixMode(5888);
        GlStateManager.pushMatrix();
        GL11.glLoadIdentity();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        int var5 = 8;
        for (int var6 = 0; var6 < var5 * var5; ++var6) {
            GlStateManager.pushMatrix();
            float var7 = ((float) (var6 % var5) / (float) var5 - 0.5f) / 64.0f;
            float var8 = ((float) (var6 / var5) / (float) var5 - 0.5f) / 64.0f;
            float var9 = 0.0f;
            GL11.glTranslatef(var7, var8, var9);
            GL11.glRotatef(
                    MathHelper.sin(((float) rotationAngle + var3) / 400.0f) * 25.0f + 20.0f,
                    1.0f,
                    0.0f,
                    0.0f);
            GL11.glRotatef(
                    -((float) rotationAngle + var3) * 0.39240506f * 0.2548387f, 0.0f, 1.0f, 0.0f);
            for (int var10 = 0; var10 < 6; ++var10) {
                GlStateManager.pushMatrix();
                if (var10 == 1) {
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 2) {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 3) {
                    GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 4) {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var10 == 5) {
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(this.titlePanoramaPaths[var10]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                float var11 = 0.0f;
                worldrenderer
                        .pos(-1.0, -1.0, 1.0)
                        .tex(0.0f + var11, 0.0f + var11)
                        .color(255, 255, 255, 255 / (var6 + 1))
                        .endVertex();
                worldrenderer
                        .pos(1.0, -1.0, 1.0)
                        .tex(1.0f - var11, 0.0f + var11)
                        .color(255, 255, 255, 255 / (var6 + 1))
                        .endVertex();
                worldrenderer
                        .pos(1.0, 1.0, 1.0)
                        .tex(1.0f - var11, 1.0f - var11)
                        .color(255, 255, 255, 255 / (var6 + 1))
                        .endVertex();
                worldrenderer
                        .pos(-1.0, 1.0, 1.0)
                        .tex(0.0f + var11, 1.0f - var11)
                        .color(255, 255, 255, 255 / (var6 + 1))
                        .endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GL11.glColorMask(true, true, true, false);
        }
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(5889);
        GlStateManager.popMatrix();
        GL11.glMatrixMode(5888);
        GlStateManager.popMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(2929);
    }

    private void rotateAndBlurSkybox() {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GL11.glDisable(3008);
        int var3 = 3;
        for (int var4 = 0; var4 < var3; ++var4) {
            float var5 = this.width;
            float var6 = this.height;
            float var7 = (float) (var4 - var3 / 2) / 256.0f;
            worldrenderer
                    .pos(var5, var6, zLevel)
                    .tex(0.0f + var7, 1.0)
                    .color(1.0f, 1.0f, 1.0f, 1.0f / (float) (var4 + 1))
                    .endVertex();
            worldrenderer
                    .pos(var5, 0.0, zLevel)
                    .tex(1.0f + var7, 1.0)
                    .color(1.0f, 1.0f, 1.0f, 1.0f / (float) (var4 + 1))
                    .endVertex();
            worldrenderer
                    .pos(0.0, 0.0, zLevel)
                    .tex(1.0f + var7, 0.0)
                    .color(1.0f, 1.0f, 1.0f, 1.0f / (float) (var4 + 1))
                    .endVertex();
            worldrenderer
                    .pos(0.0, var6, zLevel)
                    .tex(0.0f + var7, 0.0)
                    .color(1.0f, 1.0f, 1.0f, 1.0f / (float) (var4 + 1))
                    .endVertex();
        }
        tessellator.draw();
        GL11.glEnable(3008);
        GL11.glColorMask(true, true, true, true);
    }

}
