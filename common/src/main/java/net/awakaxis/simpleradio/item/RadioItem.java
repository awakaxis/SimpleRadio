package net.awakaxis.simpleradio.item;

import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.audiochannel.LocationalAudioChannel;
import net.awakaxis.simpleradio.Constants;
import net.awakaxis.simpleradio.ModRegistry;
import net.awakaxis.simpleradio.SimpleRadioPlugin;
import net.awakaxis.simpleradio.item.component.RadioData;
import net.awakaxis.simpleradio.network.ServerBoundRadioTogglePayload;
import net.awakaxis.simpleradio.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RadioItem extends Item {

    public RadioItem(Properties properties) {
        super(properties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action == ClickAction.SECONDARY && other.isEmpty()) {
            Services.PLATFORM.sendServerBound(new ServerBoundRadioTogglePayload(slot.getContainerSlot()));
            return true;
            /*RadioData data = getData(stack);
            boolean value = data.toggleIsOn();
            if (value) {
                VoicechatServerApi api = SimpleRadioPlugin.getApi();
                assert api != null;
                LocationalAudioChannel channel = data.createChannel(api.fromServerLevel((ServerLevel)player.level()), api.createPosition(player.getX(), player.getY(), player.getZ()));
                try {
                    FileOutputStream fos = new FileOutputStream("./test.file");
                    fos.write(123);
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                data.removeChannel();
            }
            return true;*/
        }
        return false;
    }

    public static RadioData getData(ItemStack stack) {
        return stack.getOrDefault(ModRegistry.DataComponentTypes.RADIO_ON.get(), new RadioData(false));
    }
}
