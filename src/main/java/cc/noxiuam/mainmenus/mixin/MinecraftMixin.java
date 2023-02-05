package cc.noxiuam.mainmenus.mixin;

import cc.noxiuam.mainmenus.MainMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Inject(method = "displayGuiScreen", at = @At(value = "HEAD"), cancellable = true)
    private void onGuiDisplay(GuiScreen guiScreenIn, CallbackInfo ci) {
        if (guiScreenIn instanceof GuiMainMenu
                || (guiScreenIn == null && Minecraft.getMinecraft().theWorld == null)) {
            ci.cancel();
            Minecraft.getMinecraft().displayGuiScreen(MainMenus.config.getMainMenu());
        }
    }

}
