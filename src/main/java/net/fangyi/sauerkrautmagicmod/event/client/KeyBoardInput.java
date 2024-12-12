package net.fangyi.sauerkrautmagicmod.event.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class KeyBoardInput {
    public static final KeyMapping UP_KEY = new KeyMapping("key.up",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            "key.category.flyingsword");
    public static final KeyMapping DOWN_KEY = new KeyMapping("key.down",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_CONTROL,
            "key.category.flyingsword");

    public static boolean up = false;
    public static boolean down = false;
    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        if (UP_KEY.getKey().getValue() == event.getKey()) {
            if (event.getAction() == GLFW.GLFW_PRESS){
                up = true;
            }else if (event.getAction() == GLFW.GLFW_RELEASE){
                up = false;
            }
        }
        if (DOWN_KEY.getKey().getValue() == event.getKey()) {
            if (event.getAction() == GLFW.GLFW_PRESS){
                down = true;
            }else if (event.getAction() == GLFW.GLFW_RELEASE){
                down = false;
            }
        }
    }
}
