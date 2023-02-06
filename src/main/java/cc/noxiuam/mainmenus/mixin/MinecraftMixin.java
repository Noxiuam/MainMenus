package cc.noxiuam.mainmenus.mixin;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.LegacyCBMainMenuBase;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.modern.ModernCBMainMenuBase;
import cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.LunarClientMainMenuBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Inject(method = "displayGuiScreen", at = @At(value="HEAD"), cancellable=true)
    private void onGuiDisplay(GuiScreen guiScreenIn, CallbackInfo ci) {

        if (MainMenus.config.enabled) {
            if (guiScreenIn instanceof GuiMainMenu
                    || (guiScreenIn == null && Minecraft.getMinecraft().theWorld == null)) {
                switch (MainMenus.config.mainMenu) {
                    case 1:
                        ci.cancel();
                        Minecraft.getMinecraft().displayGuiScreen(new LunarClientMainMenuBase());
                        break;

                    case 2:
                        ci.cancel();
                        Minecraft.getMinecraft().displayGuiScreen(new ModernCBMainMenuBase());
                        break;

                    case 3:
                        ci.cancel();
                        Minecraft.getMinecraft().displayGuiScreen(new LegacyCBMainMenuBase());
                        break;
                }
            }
        }

    }

}
