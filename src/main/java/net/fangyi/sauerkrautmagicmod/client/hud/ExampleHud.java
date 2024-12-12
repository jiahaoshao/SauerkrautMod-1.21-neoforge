package net.fangyi.sauerkrautmagicmod.client.hud;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;

public class ExampleHud implements LayeredDraw.Layer {
    private static final ExampleHud hud = new ExampleHud();
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = SauerkrautMagicMod.prefix("textures/gui/hud.png");

    public ExampleHud() {
        this.width = Minecraft.getInstance().getWindow().getScreenWidth();
        this.height = Minecraft.getInstance().getWindow().getScreenHeight();
        this.minecraft = Minecraft.getInstance();
    }


    public static ExampleHud getInstance() {
        return hud;
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (minecraft.player.getMainHandItem().getItem()!= ModItems.RUBY_APPLE.get())
            return;
        //SauerkrautMagicMod.LOGGER.info("Rendering HUD " + width + " " + height);
        guiGraphics.setColor(1,1,1,1);
        guiGraphics.blit(HUD,width/2,height/2,0,0,0,32,32,32,32);
    }
}
