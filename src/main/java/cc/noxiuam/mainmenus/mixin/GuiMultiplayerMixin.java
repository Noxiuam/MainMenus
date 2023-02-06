package cc.noxiuam.mainmenus.mixin;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.LegacyCBMainMenuBase;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.modern.ModernCBMainMenuBase;
import cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.LunarClientMainMenuBase;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public class GuiMultiplayerMixin {

    @Shadow private GuiScreen parentScreen;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void onInit(GuiScreen parentScreen, CallbackInfo ci) {

        if (MainMenus.config.enabled) {
            switch (MainMenus.config.mainMenu) {
                case 1:
                    this.parentScreen = new LunarClientMainMenuBase();
                    break;

                case 2:
                    this.parentScreen = new ModernCBMainMenuBase();
                    break;

                case 3:
                    this.parentScreen = new LegacyCBMainMenuBase();
                    break;
            }
        }

    }

}
