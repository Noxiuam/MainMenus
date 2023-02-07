package cc.noxiuam.mainmenus.config;

import cc.noxiuam.mainmenus.MainMenus;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class Settings extends Config {

    @Dropdown(name = "Main Menu", subcategory = "General", options = {
            "Vanilla", // 0
            "Lunar 2019", // 1
            "CheatBreaker 2018", // 2
            "CheatBreaker 2017" // 3
    })
    public int mainMenu = 0;

    @Switch(
            name = "Custom Scale",
            subcategory = "General"
    )
    public boolean customScale = false;

    @Slider(
            name = "Main Menu Scale",
            subcategory = "General",
            min = 0.5F,
            max = 2.0F
    )
    public float mainMenuScale = 1.0F;

    public Settings() {
        super(new Mod(MainMenus.NAME, ModType.UTIL_QOL), MainMenus.MODID + ".json");
        initialize();
        addDependency("mainMenuScale", "customScale");
    }

}

