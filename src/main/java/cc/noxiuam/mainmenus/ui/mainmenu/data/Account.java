package cc.noxiuam.mainmenus.ui.mainmenu.data;

import cc.noxiuam.mainmenus.MainMenus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;

@Getter
@AllArgsConstructor
public class Account {

    private final ResourceLocation headIcon;

    private String clientToken;
    private String username;
    @Setter private String accessToken;
    private String displayName;
    private String uuid;

    public Account(
            String username,
            String clientToken,
            String accessToken,
            String displayName,
            String uuid) {
        this.username = username;
        this.clientToken = clientToken;
        this.accessToken = accessToken;
        this.displayName = displayName;
        this.uuid = uuid;
        this.headIcon = MainMenus.INSTANCE.avatarUtil.getHeadIcon(displayName);
    }

}

