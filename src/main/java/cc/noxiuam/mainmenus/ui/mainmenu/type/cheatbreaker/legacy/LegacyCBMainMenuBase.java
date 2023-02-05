package cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.mainmenu.AbstractMainMenuBase;
import cc.noxiuam.mainmenus.ui.mainmenu.data.Account;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.element.ImageButtonElement;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.element.LegacyAccountButton;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.element.LegacyMainMenuButton;
import cc.noxiuam.mainmenus.ui.mainmenu.util.PanoramaRegistry;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import com.google.common.collect.ImmutableList;
import com.replaymod.core.ReplayMod;
import com.replaymod.replay.ReplayModReplay;
import com.replaymod.replay.gui.screen.GuiReplayViewer;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class LegacyCBMainMenuBase extends AbstractMainMenuBase {

    private final ResourceLocation logoOuter = new ResourceLocation("client/logo_outer.png");
    private final ResourceLocation logoInner = new ResourceLocation("client/logo_inner.png");

    private final LegacyMainMenuButton optionsButton = new LegacyMainMenuButton("OPTIONS", false);
    private final LegacyMainMenuButton languageButton = new LegacyMainMenuButton("LANGUAGE", false);

    // Forge specific
    private final LegacyMainMenuButton forgeButton = new LegacyMainMenuButton("MODS", false);
    private final LegacyMainMenuButton replaysButton = new LegacyMainMenuButton("REPLAYS", false);

    private final List<LegacyMainMenuButton> topLeftButtons =
            ImmutableList.of(this.optionsButton, this.languageButton, this.forgeButton);

    private final LegacyMainMenuButton singlePlayerButton = new LegacyMainMenuButton(I18n.format("menu.singleplayer"), true);
    private final LegacyMainMenuButton multiPlayerButton = new LegacyMainMenuButton(I18n.format("menu.multiplayer"), true);

    private LegacyAccountButton accountButton;

    private ImageButtonElement exitButton;

    private final List<LegacyAccountButton> accountButtons = new ArrayList<>();

    private double eventButton;
    private float lastMouseEvent = 0.0f;

    private boolean showAccountList;
    private boolean hasHoveredOverAccountButton;

    @Override
    public void initGui() {
        super.initGui();

        this.titlePanoramaPaths = PanoramaRegistry.LEGACY_CHEATBREAKER;

        this.exitButton = new ImageButtonElement(new ResourceLocation("client/icons/exit-64.png"), "EXIT", false);
        this.exitButton.setElementSize(
                this.getScaledWidth() - 65, 0, 65, 25
        );

        this.accountButton = null;
        this.accountButtons.clear();

        int n = 0;
        for (Account account : this.accounts) {
            String accountName = account.getDisplayName();

            LegacyAccountButton legacyAccountButton = new LegacyAccountButton(account);

            if (accountName.equals(this.mc.getSession().getUsername())) {
                legacyAccountButton.setElementSize(
                        this.getScaledWidth() - 200, 0, 130, 25
                );
            } else {
                legacyAccountButton.setElementSize(
                        this.getScaledWidth() - 200, n * 25, 130, 25
                );
            }


            this.accountButtons.add(legacyAccountButton);

            if (this.mc.getSession() != null && accountName.equalsIgnoreCase(this.mc.getSession().getUsername())) {
                this.accountButton = legacyAccountButton;
                if (n != 0) {
                    LegacyAccountButton entry = this.accountButtons.get(0);
                    entry.yOffset = n * 25;
                    this.accountButton.yOffset = 0;
                }
            }
            ++n;
        }

        if (this.accountButton == null && !this.accountButtons.isEmpty()) {
            this.accountButton = this.accountButtons.get(0);
        }

        float yPos = this.getScaledHeight() / 4 + 48;
        this.singlePlayerButton.setElementSize(
                this.getScaledWidth() / 2.0f - (float) 65,
                yPos + 24,
                130,
                24);
        this.multiPlayerButton.setElementSize(
                this.getScaledWidth() / 2.0f - 65,
                yPos + 52,
                130,
                24);

        float topLeftButtonsXPosition = 44;
        for (LegacyMainMenuButton button : this.topLeftButtons) {
            if (button == this.replaysButton && !this.isReplayModPresent) {
                continue;
            }

            button.setElementSize(topLeftButtonsXPosition, 0.5f, 50, 25);
            topLeftButtonsXPosition += 50;
        }
    }

    @Override
    public void drawMenu(float x, float y) {

        // Use the actual Minecraft method, if you try to make your own, this will literally nuke rendering.
        this.drawGradientRect(0, 0, (int) this.getScaledWidth(), (int) this.getScaledHeight(), 0x5FFFFFFF, 0x2FFFFFFF);
        RenderUtil.drawRect(0.0f, 0.0f, this.getScaledWidth(), 25, -819846622);

        this.drawTopLeftWaterMark();

        String gitInformation = "CheatBreaker (" + MainMenus.VERSION + "/" + MainMenus.BRANCH + ")";
        this.drawString(this.fontRendererObj, gitInformation, 2, (int) (this.getScaledHeight() - 10), -1);

        String copyright = "Copyright Mojang AB. Do not distribute!";
        this.drawString(this.fontRendererObj, copyright, (int) (this.getScaledWidth() - this.fontRendererObj.getStringWidth(copyright) - 2),
                (int) (this.getScaledHeight() - 10), -1);

        this.drawCenter();

        this.singlePlayerButton.drawElementHover(x, y, true);
        this.multiPlayerButton.drawElementHover(x, y, true);

        for (LegacyMainMenuButton button : this.topLeftButtons) {
            if (button == this.replaysButton && !this.isReplayModPresent) {
                continue;
            }

            button.drawElementHover(x, y, true);
        }

        if (this.accountButton != null) {
            this.accountButton.drawElementHover(x, y, true);

            if (this.accountButton.isMouseInside(x, y)) {
                this.hasHoveredOverAccountButton = true;
            }

            this.showAccountList = this.hasHoveredOverAccountButton && x > this.accountButton.getXPosition()
                    && x < this.accountButton.getXPosition() + this.accountButton.getWidth()
                    && y > this.accountButton.getYPosition()
                    && y < this.accountButton.getYPosition() + this.accountButton.getHeight() * this.accountButtons.size();

            if (this.showAccountList) {
                float accountButtonHeight = this.accountButton.getHeight();
                for (LegacyAccountButton legacyAccountButton : this.accountButtons) {
                    if (legacyAccountButton != this.accountButton) {
                        legacyAccountButton.drawElementHover(x, y, true);
                    }
                    accountButtonHeight += legacyAccountButton.getHeight();
                }
            } else {
                this.hasHoveredOverAccountButton = false;
            }
        }

        this.exitButton.drawElementHover(x, y, true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.eventButton += 0.06283185307179587;
        this.lastMouseEvent = (float) ((Math.sin(this.eventButton) / 2.0 + 0.5) * 180.0);
    }

    @Override
    protected void mouseClicked(float x, float y, int button) {

        if (this.singlePlayerButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        } else if (this.multiPlayerButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        } else if (this.optionsButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        } else if (this.languageButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        } else if (this.forgeButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiModList(this));
        } else if (this.replaysButton.isMouseInside(x, y) && this.isReplayModPresent) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiReplayViewer(new ReplayModReplay(ReplayMod.instance)).toMinecraft());
        } else if (this.exitButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.shutdown();
        }

        if (this.showAccountList) {
            for (LegacyAccountButton accountButton : this.accountButtons) {
                if (accountButton != this.accountButton
                        && x < accountButton.getXPosition() + accountButton.getWidth()
                        && y > accountButton.getYPosition()
                        && y < accountButton.getYPosition() + accountButton.getHeight()) {
                    if (!this.login(accountButton.getDisplayName()) || accountButton == this.accountButton) break;

                    this.accountButton.setDisplayName(accountButton.getDisplayName());
                    this.accountButton.setHeadIcon(accountButton.getHeadIcon());
                    break;
                }
            }
        }

    }

    @Override
    public void mouseReleased(float x, float y, int button) {
    }

    private void drawCenter() {

        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 20F, 0F);
        RenderUtil.drawRect(
                this.getScaledWidth() / 2F - 71,
                this.getScaledHeight() / 4F - 40,
                this.getScaledWidth() / 2F + 71,
                this.getScaledHeight() / 4F + 110,
                -1342177281);
        RenderUtil.drawRect(
                this.getScaledWidth() / 2F - 73,
                this.getScaledHeight() / 4F - 42,
                this.getScaledWidth() / 2F - 71,
                this.getScaledHeight() / 4F + 112,
                0x3FFFFFFF);
        RenderUtil.drawRect(
                this.getScaledWidth() / 2F + 71,
                this.getScaledHeight() / 4F - 42,
                this.getScaledWidth() / 2F + 73,
                this.getScaledHeight() / 4F + 112,
                0x3FFFFFFF);
        RenderUtil.drawRect(
                this.getScaledWidth() / 2F - 71,
                this.getScaledHeight() / 4F + 110,
                this.getScaledWidth() / 2F + 71,
                this.getScaledHeight() / 4F + 112,
                0x3FFFFFFF);
        RenderUtil.drawRect(
                this.getScaledWidth() / 2F - 71,
                this.getScaledHeight() / 4F - 42,
                this.getScaledWidth() / 2F + 71,
                this.getScaledHeight() / 4F - 40,
                0x3FFFFFFF);

        this.drawLogo();
        GL11.glPopMatrix();
    }

    /**
     * This uses the old method to preserve the choppy animation from 2017.
     */
    private void drawLogo() {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.9f);

        GlStateManager.pushMatrix();
//        Tessellator tessellator = Tessellator.getInstance();

        GlStateManager.pushMatrix();
        float scale = 0.65f;
        GL11.glScalef(scale, scale, scale);
        GlStateManager.pushMatrix();
        GL11.glTranslatef(
                ((this.getScaledWidth() / 2) - 40.0F * scale) / scale,
                ((this.getScaledHeight() / 4) - 40.0F * scale) / scale,
                0.0f);
        int outerScale = 40;
        GL11.glTranslatef(outerScale, outerScale, outerScale);
        GlStateManager.rotate(this.lastMouseEvent, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-outerScale, -outerScale, -outerScale);
        RenderUtil.renderIcon(this.logoOuter, (float) outerScale, 0.0f, 0.0f);
        GlStateManager.popMatrix();
        RenderUtil.renderIcon(
                this.logoInner,
                40.0F,
                ((this.getScaledWidth() / 2) - 40.0F * scale) / scale,
                ((this.getScaledHeight() / 4) - 39.0F * scale) / scale);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
//        tessellator.setColorOpaque_I(-1);
    }

    private void drawTopLeftWaterMark() {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(new ResourceLocation("client/icons/cb.png"), 10f, 5f, 30f, 15f);
        GL11.glPopMatrix();
    }

}
