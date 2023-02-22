package cc.noxiuam.mainmenus.ui.mainmenu.type.playstation3;

import cc.noxiuam.mainmenus.ui.AbstractGui;
import cc.noxiuam.mainmenus.ui.fade.impl.MinMaxFade;
import cc.noxiuam.mainmenus.ui.font.CBFontRenderer;
import cc.noxiuam.mainmenus.ui.mainmenu.type.playstation3.element.CategoryElement;
import cc.noxiuam.mainmenus.ui.util.RenderUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;

public class PS3MainMenuBase extends AbstractGui {

    private final MinMaxFade introFade = new MinMaxFade(500L);

    public final CBFontRenderer fontRenderer =
            new CBFontRenderer(new ResourceLocation("PS3"), 20.0F);

    private final List<CategoryElement> categories =
            ImmutableList.of(
                    new CategoryElement(
                            "Users",
                            new ResourceLocation("client/icons/ps3/other-user.png"),
                            (94 / 2) - 5,
                            82 / 2,
                            true
                    ),
                    new CategoryElement(
                            "Settings",
                            new ResourceLocation("client/icons/ps3/settings.png"),
                            (117 / 2) - 5,
                            (82 / 2) - 2,
                            false
                    ),
                    new CategoryElement(
                            "Game",
                            new ResourceLocation("client/icons/ps3/play.png"),
                            117,
                            82,
                            false
                    ),
                    new CategoryElement(
                            "Friends",
                            new ResourceLocation("client/icons/ps3/friends.png"),
                            117,
                            82,
                            false
                    )
            );

    @Override
    public void initGui() {
        super.initGui();

        float catX = this.getScaledWidth() / 2 - 100;
        for (CategoryElement categoryElement : this.categories) {
            categoryElement.setElementSize(
                    catX,
                    this.getScaledHeight() / 2 - 50,
                    100,
                    100
            );
            catX += 100;
        }

    }

    @Override
    public void drawMenu(float x, float y) {
        RenderUtil.drawRect(0, 0, this.getScaledWidth(), this.getScaledHeight(), 0xFF0F0F0F);

        for (CategoryElement categoryElement : this.categories) {
            categoryElement.drawElementHover(x, y, true);
        }

        //this.drawFadeIn();
    }

    private void drawFadeIn() {
        RenderUtil.drawRect(
                0.0f,
                0.0f,
                this.getScaledWidth(),
                this.getScaledHeight(),
                new Color(0.15F, 0.15F, 0.15F, 1.0F - this.introFade.getFadeAmount()).getRGB()
        );
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Minecraft.getMinecraft().displayGuiScreen(new PS3MainMenuBase());
        }

        if (!this.introFade.isTimeNotAtZero()) {
            this.introFade.startAnimation();
        }
    }

    @Override
    protected void mouseClicked(float x, float y, int button) {
    }

    @Override
    public void mouseReleased(float x, float y, int button) {
    }

}
