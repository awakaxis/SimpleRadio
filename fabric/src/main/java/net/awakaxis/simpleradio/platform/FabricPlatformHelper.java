package net.awakaxis.simpleradio.platform;

import net.awakaxis.simpleradio.Constants;
import net.awakaxis.simpleradio.item.RadioItem;
import net.awakaxis.simpleradio.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <E, R extends Registry<E>> Supplier<E> register(R registry, Supplier<E> entry, String identifier) {
        Registry.register(registry, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, identifier), entry.get());
        return entry;
    }

    @Override
    public <T> Supplier<DataComponentType<T>> registerDataComponentType(UnaryOperator<DataComponentType.Builder<T>> builder, String identifier) {
        DataComponentType<T> dataComponentType = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, identifier), builder.apply(DataComponentType.builder()).build());
        return () -> dataComponentType;
    }

    @Override
    public void sendServerBound(CustomPacketPayload payload) {

    }

    @Override
    public void sendClientBound(ServerPlayer player, CustomPacketPayload payload) {

    }

    @Override
    public void sendClientBoundTrackingChunk(ServerLevel level, ChunkPos pos, CustomPacketPayload payload) {

    }

    @Override
    public void broadcastClientBound(CustomPacketPayload payload) {

    }
}
