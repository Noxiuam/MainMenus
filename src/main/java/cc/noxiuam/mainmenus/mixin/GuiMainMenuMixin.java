package cc.noxiuam.mainmenus.mixin;


import cc.noxiuam.mainmenus.MainMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class GuiMainMenuMixin {

    @Inject(method = "initGui", at = @At(value = "HEAD"))
    private void onGuiInit(CallbackInfo ci) {
        Minecraft.getMinecraft().displayGuiScreen(MainMenus.config.getMainMenu());
    }

}
