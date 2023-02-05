package cc.noxiuam.mainmenus.ui.mainmenu.shared;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.data.FontRegistry;
import cc.noxiuam.mainmenus.ui.font.CBFontRenderer;
import cc.noxiuam.mainmenus.ui.mainmenu.AbstractMainMenuBase;
import cc.noxiuam.mainmenus.ui.mainmenu.data.Account;
import cc.noxiuam.mainmenus.ui.mainmenu.shared.element.account.AccountButton;
import cc.noxiuam.mainmenus.ui.mainmenu.shared.element.button.GradientTextButton;
import cc.noxiuam.mainmenus.ui.mainmenu.shared.element.button.TextButton;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.modern.submenu.CBCosmeticsMenu;
import cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.submenu.LCCosmeticsMenu;
import cc.noxiuam.mainmenus.ui.mainmenu.util.PanoramaRegistry;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import com.google.common.collect.ImmutableList;
import com.replaymod.core.ReplayMod;
import com.replaymod.replay.ReplayModReplay;
import com.replaymod.replay.gui.screen.GuiReplayViewer;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonCheatBreakerBase extends AbstractMainMenuBase {

    protected final TextButton optionsButton = new TextButton("OPTIONS");
    protected final TextButton cosmeticsButton = new TextButton("COSMETICS");

    public final GradientTextButton singleplayerButton = new GradientTextButton("SINGLEPLAYER", FontRegistry.robotoLight18px);
    public final GradientTextButton multiplayerButton = new GradientTextButton("MULTIPLAYER", FontRegistry.robotoLight18px);

    public final GradientTextButton closeButton = new GradientTextButton(2.0f, new ResourceLocation("client/icons/delete-64.png"));
    public final GradientTextButton languageButton = new GradientTextButton(6.0f, new ResourceLocation("client/icons/globe-24.png"));

    public final GradientTextButton forgeButton = new GradientTextButton(6.0f, new ResourceLocation("client/icons/forge-24.png"));
    public GradientTextButton replaysButton = new GradientTextButton(6.0f, new ResourceLocation("client/icons/forge-24.png"));

    protected final List<GradientTextButton> buttons = ImmutableList.of(
            this.singleplayerButton, this.multiplayerButton, this.closeButton,
            this.languageButton, this.forgeButton, this.replaysButton
    );

    protected final List<GradientTextButton> bottomButtons = new ArrayList<>();

    public AccountButton accountButton;

    @Override
    public void initGui() {
        super.initGui();

        this.bottomButtons.clear();
        this.bottomButtons.add(this.forgeButton);
        this.bottomButtons.add(this.languageButton);
        this.bottomButtons.add(this.replaysButton);

        this.titlePanoramaPaths = PanoramaRegistry.LUNAR;

        CBFontRenderer fontRenderer;
        if (MainMenus.config.mainMenu == 2) {
            fontRenderer = FontRegistry.robotoRegular13px;
        } else {
            fontRenderer = FontRegistry.robotoMedium13px;
        }

        this.accountButtonWidth = (float) fontRenderer.getStringWidth(Minecraft.getMinecraft().getSession().getUsername());

        String username = Minecraft.getMinecraft().getSession().getUsername();

        for (Account account : this.accounts) {
            String accountUsername = account.getUsername();
            this.accountButtonWidth = Math.max(this.accountButtonWidth, (float) fontRenderer.getStringWidth(accountUsername));
        }

        this.accountButton = new AccountButton(
                this,
                username,
                MainMenus.INSTANCE.avatarUtil.getHeadIcon(username)
        );

        this.updateSizes();
    }

    @Override
    public void drawMenu(float x, float y) {

        // required since minecraft wants to throw a fit with CheatBreaker based menus lmao
        RenderUtil.drawGradientRect(0, 0, 0, 0, 0x40292929, 0x40292929);

        this.drawWaterMarks(x, y);

        if (!(this.mc.currentScreen instanceof LCCosmeticsMenu || this.mc.currentScreen instanceof CBCosmeticsMenu)) {
            this.drawCenter();
        }

        this.optionsButton.drawElementHover(x, y, true);
        this.cosmeticsButton.drawElementHover(x, y, true);
        this.accountButton.drawElementHover(x, y, true);

        for (GradientTextButton button : this.buttons) {
            if ((this instanceof LCCosmeticsMenu || this instanceof CBCosmeticsMenu)
                    && (button == this.singleplayerButton || button == this.multiplayerButton)) {
                continue;
            }

            if (button == this.replaysButton && !this.isReplayModPresent) {
                continue;
            }

            button.drawElementHover(x, y, true);
        }
    }

    @Override
    protected void mouseClicked(float x, float y, int button) {

        this.accountButton.handleElementMouseClicked(x, y, button, true);

        if (this.singleplayerButton.isMouseInside(x, y) && !(this instanceof LCCosmeticsMenu)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        } else if (this.multiplayerButton.isMouseInside(x, y) && !(this instanceof LCCosmeticsMenu)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        } else if (this.languageButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        } else if (this.optionsButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        } else if (this.cosmeticsButton.isMouseInside(x, y)) {
            this.playClick();
            if (MainMenus.config.mainMenu == 2) {
                this.mc.displayGuiScreen(new CBCosmeticsMenu());
            } else {
                this.mc.displayGuiScreen(new LCCosmeticsMenu());
            }
        } else if (this.closeButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.shutdown();
        } else if (this.forgeButton.isMouseInside(x, y)) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiModList(this));
        } else if (this.replaysButton.isMouseInside(x, y) && this.isReplayModPresent) {
            this.playClick();
            this.mc.displayGuiScreen(new GuiReplayViewer(new ReplayModReplay(ReplayMod.instance)).toMinecraft());
        }
    }

    @Override
    public void mouseReleased(float x, float y, int button) {}

    public void drawWaterMarks(float x, float y) {}

    public void drawCenter() {}

    @SneakyThrows
    protected void updateSizes() {

        if (!this.isReplayModPresent) {
            this.bottomButtons.remove(this.replaysButton);
        }

        this.closeButton.setElementSize(this.getScaledWidth() - 30.0f, 11.0f, 23.0f, 17.0f);

        float bottomHorizontalPosition = -this.bottomButtons.size() * 31.0f / 2.0f + 2.5f;
        for (GradientTextButton button : this.bottomButtons) {
            if (!this.isReplayModPresent && button == this.replaysButton) {
                continue;
            }
            button.setElementSize(this.getScaledWidth() / 2.0f + bottomHorizontalPosition, this.getScaledHeight() - 17.0f, 26.0f, 18.0f);
            bottomHorizontalPosition += 30;
        }

        this.forgeButton.setShowBackground(false);

        if (this.isReplayModPresent) {
            this.replaysButton.setIcon(new ResourceLocation("replaymod", "logo_button.png"));
            this.replaysButton.setElementSize(this.getScaledWidth() / 2.0f + 15.0f, this.getScaledHeight() - 17.0f, 26.0f, 18.0f);
            this.replaysButton.setShowBackground(false);
        }

        this.optionsButton.setElementSize(114.0f, 10.0f, 42.0f, 20.0f);
        this.cosmeticsButton.setElementSize(157.0f, 10.0f, 48.0f, 20.0f);

        this.accountButton.setElementSize(
                this.getScaledWidth() - 35.0f - this.accountButton.getNewAccountButtonWidth(this.accountButtonWidth),
                11.0f, this.accountButton.getNewAccountButtonWidth(this.accountButtonWidth), 17.0f
        );
    }

}
