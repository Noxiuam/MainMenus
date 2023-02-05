package cc.noxiuam.mainmenus.mixin;

import cc.noxiuam.mainmenus.MainMenus;
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
        this.parentScreen = MainMenus.config.getMainMenu();
    }

}
