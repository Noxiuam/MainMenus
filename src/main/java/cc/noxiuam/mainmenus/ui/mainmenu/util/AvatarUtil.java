package cc.noxiuam.mainmenus.ui.mainmenu.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AvatarUtil {

    private final Map<String, ResourceLocation> headIconCache = new HashMap<>();

    /** Returns the ResourceLocation of the player avatar for the account switcher. */
    public ResourceLocation getHeadIcon(String username) {
        ResourceLocation headIcon =
                this.headIconCache.getOrDefault(
                        username, new ResourceLocation("client/heads/" + username + ".png"));
        ResourceLocation defaultIcon = new ResourceLocation("client/defaults/steve.png");

        if (!this.headIconCache.containsKey(username)) {
            ThreadDownloadImageData downloadedIcon =
                    new ThreadDownloadImageData(
                            null,
                            "https://minotar.net/helm/" + username + "/32.png",
                            defaultIcon,
                            null);
            Minecraft.getMinecraft().renderEngine.loadTexture(headIcon, downloadedIcon);
            this.headIconCache.put(username, headIcon);
        }

        return headIcon;
    }

}
