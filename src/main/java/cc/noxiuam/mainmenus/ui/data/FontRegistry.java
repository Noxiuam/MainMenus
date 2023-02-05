package cc.noxiuam.mainmenus.ui.data;

import cc.noxiuam.mainmenus.ui.font.CBFontRenderer;
import lombok.experimental.UtilityClass;
import net.minecraft.util.ResourceLocation;

@UtilityClass
public class FontRegistry {

    public final CBFontRenderer robotoLight16px;
    public final CBFontRenderer robotoLight18px;
    public final CBFontRenderer robotoLight12px;

    public final CBFontRenderer robotoMedium10px;
    public final CBFontRenderer robotoMedium13px;
    public final CBFontRenderer robotoMedium18px;

    public final CBFontRenderer robotoBold14px;
    public final CBFontRenderer robotoBold18px;
    public final CBFontRenderer robotoBold10px;
    public final CBFontRenderer robotoBold12px;
    public final CBFontRenderer robotoBold22px;

    public final CBFontRenderer robotoLight14px;
    public final CBFontRenderer robotoLight22px;
    public final CBFontRenderer robotoLight38px;

    public final CBFontRenderer robotoRegular13px;
    public final CBFontRenderer robotoRegular24px;

    public CBFontRenderer playRegular14px;
    public CBFontRenderer playRegular16px;
    public CBFontRenderer playRegular18px;

    private final ResourceLocation robotoBold;
    private final ResourceLocation robotoRegular;
    private final ResourceLocation robotoMedium;
    private final ResourceLocation robotoLight;
    private final ResourceLocation playRegular;

    static {

        playRegular = new ResourceLocation("client/font/Play-Regular.ttf");
        robotoBold = new ResourceLocation("client/font/Roboto-Bold.ttf");
        robotoRegular = new ResourceLocation("client/font/Roboto-Regular.ttf");
        robotoMedium = new ResourceLocation("client/font/Roboto-Medium.ttf");
        robotoLight = new ResourceLocation("client/font/Roboto-Light.ttf");

        robotoBold10px = new CBFontRenderer(robotoBold, 10.0F);
        robotoBold12px = new CBFontRenderer(robotoBold, 12.0F);
        robotoBold14px = new CBFontRenderer(robotoBold, 14.0F);
        robotoBold18px = new CBFontRenderer(robotoBold, 18.0F);
        robotoBold22px = new CBFontRenderer(robotoBold, 22.0F);

        robotoRegular13px = new CBFontRenderer(robotoRegular, 13.0F);
        robotoRegular24px = new CBFontRenderer(robotoRegular, 24.0F);

        robotoLight14px = new CBFontRenderer(robotoLight, 14.0F);
        robotoLight12px = new CBFontRenderer(robotoLight, 12.0F);

        robotoMedium13px = new CBFontRenderer(robotoMedium, 13.0F);
        robotoMedium10px = new CBFontRenderer(robotoMedium, 10.0F);
        robotoMedium18px = new CBFontRenderer(robotoMedium, 18.0F);

        robotoLight16px = new CBFontRenderer(robotoLight, 16.0F);
        robotoLight18px = new CBFontRenderer(robotoLight, 18.0F);
        robotoLight22px = new CBFontRenderer(robotoLight, 22.0F);
        robotoLight38px = new CBFontRenderer(robotoLight, 38.0F);

        playRegular14px = new CBFontRenderer(playRegular, 14.0F);
        playRegular16px = new CBFontRenderer(playRegular, 16.0F);
        playRegular18px = new CBFontRenderer(playRegular, 18.0F);

    }


}
