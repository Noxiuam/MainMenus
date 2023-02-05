package cc.noxiuam.mainmenus;

import cc.noxiuam.mainmenus.config.Settings;
import cc.noxiuam.mainmenus.ui.mainmenu.util.AvatarUtil;
import cc.polyfrost.oneconfig.events.EventManager;

@net.minecraftforge.fml.common.Mod(modid = MainMenus.MODID, name = MainMenus.NAME, version = MainMenus.VERSION)
public class MainMenus {

    @net.minecraftforge.fml.common.Mod.Instance("@ID@")
    public static MainMenus INSTANCE;
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    public static Settings config;

    public static final String BRANCH = "private";

    public AvatarUtil avatarUtil;

    public MainMenus() {
        this.avatarUtil = new AvatarUtil();
        System.out.println("[MainMenus] Created Avatar stuff");
    }

    // Register the config and commands.
    @net.minecraftforge.fml.common.Mod.EventHandler
    public void onFMLInitialization(net.minecraftforge.fml.common.event.FMLInitializationEvent event) {
        config = new Settings();
        EventManager.INSTANCE.register(this);
    }

}
