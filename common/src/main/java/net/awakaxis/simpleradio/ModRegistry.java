package net.awakaxis.simpleradio;

import com.mojang.serialization.Codec;
import net.awakaxis.simpleradio.item.RadioItem;
import net.awakaxis.simpleradio.item.component.RadioData;
import net.awakaxis.simpleradio.platform.Services;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModRegistry {
    public static final class Items {
        public static final Supplier<Item> RADIO = Services.PLATFORM.register(BuiltInRegistries.ITEM, () -> new RadioItem(new Item.Properties().stacksTo(1).component(DataComponentTypes.RADIO_ON.get(), new RadioData(false))), "radio");

        private static void register() {
            Constants.LOGGER.info("registered items");
        }
    }
    public static final class DataComponentTypes {
        public static final Supplier<DataComponentType<RadioData>> RADIO_ON = Services.PLATFORM.registerDataComponentType(builder -> builder.persistent(RadioData.CODEC), "radio");

        private static void register() {
            Constants.LOGGER.info("registered data component types");
        }
    }

    public static void registerAll() {
        Items.register();
        DataComponentTypes.register();
    }
}
