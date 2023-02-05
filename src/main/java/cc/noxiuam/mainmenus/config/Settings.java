package cc.noxiuam.mainmenus.config;

import cc.noxiuam.mainmenus.MainMenus;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.legacy.LegacyCBMainMenuBase;
import cc.noxiuam.mainmenus.ui.mainmenu.type.cheatbreaker.modern.ModernCBMainMenuBase;
import cc.noxiuam.mainmenus.ui.mainmenu.type.lunar.LunarClientMainMenuBase;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class Settings extends Config {

    @Dropdown(name = "Main Menu", subcategory = "General", options = {
            "Vanilla", // 0
            "Lunar 2019", // 1
            "CheatBreaker 2018", // 2
            "CheatBreaker 2017" // 3
    })
    public int mainMenu = 0;

    public Settings() {
        super(new Mod(MainMenus.NAME, ModType.UTIL_QOL), MainMenus.MODID + ".json");
        initialize();
    }

    public GuiScreen getMainMenu() {

        if (MainMenus.config.enabled) {
            switch (this.mainMenu) {

                case 1:
                    return new LunarClientMainMenuBase();
                case 2:
                    return new ModernCBMainMenuBase();
                case 3:
                    return new LegacyCBMainMenuBase();

            }
        }

        return new GuiMainMenu();
    }

}

