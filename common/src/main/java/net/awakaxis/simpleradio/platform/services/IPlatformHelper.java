package net.awakaxis.simpleradio.platform.services;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    <E, R extends Registry<E>> Supplier<E> register(R registry, Supplier<E> entry, String identifier);

    <T> Supplier<DataComponentType<T>> registerDataComponentType(UnaryOperator<DataComponentType.Builder<T>> builder, String identifier);

    void sendServerBound(CustomPacketPayload payload);

    void sendClientBound(ServerPlayer player, CustomPacketPayload payload);

    void sendClientBoundTrackingChunk(ServerLevel level, ChunkPos pos, CustomPacketPayload payload);

    void broadcastClientBound(CustomPacketPayload payload);
}