package net.awakaxis.simpleradio.platform;

import de.maxhenkel.voicechat.api.audiochannel.LocationalAudioChannel;
import net.awakaxis.simpleradio.Constants;
import net.awakaxis.simpleradio.item.RadioItem;
import net.awakaxis.simpleradio.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NeoForgePlatformHelper implements IPlatformHelper {

    public static final HashMap<Registry<?>, DeferredRegister<?>> REGISTERS = new HashMap<>();

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <E, R extends Registry<E>> Supplier<E> register(R registry, Supplier<E> entry, String identifier) {
        @SuppressWarnings("unchecked") final DeferredRegister<E> REGISTER = (DeferredRegister<E>) REGISTERS.computeIfAbsent(registry, (reg) -> DeferredRegister.create(registry, Constants.MOD_ID));
        return REGISTER.register(identifier, entry);
    }

    @Override
    public <T> Supplier<DataComponentType<T>> registerDataComponentType(UnaryOperator<DataComponentType.Builder<T>> builder, String identifier) {
        @SuppressWarnings("unchecked") final DeferredRegister<DataComponentType<T>> REGISTER =
                (DeferredRegister<DataComponentType<T>>) REGISTERS.computeIfAbsent(BuiltInRegistries.DATA_COMPONENT_TYPE, (reg) -> DeferredRegister.create(reg, Constants.MOD_ID));
        return REGISTER.register(identifier, () -> builder.apply(DataComponentType.builder()).build());
    }

    @Override
    public void sendServerBound(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }

    @Override
    public void sendClientBound(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    public void sendClientBoundTrackingChunk(ServerLevel level, ChunkPos pos, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersTrackingChunk(level, pos, payload);
    }

    @Override
    public void broadcastClientBound(CustomPacketPayload payload) {
        PacketDistributor.sendToAllPlayers(payload);
    }
}