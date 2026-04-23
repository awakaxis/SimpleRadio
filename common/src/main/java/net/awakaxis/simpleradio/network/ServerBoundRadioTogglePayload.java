package net.awakaxis.simpleradio.network;

import io.netty.buffer.ByteBuf;
import net.awakaxis.simpleradio.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record ServerBoundRadioTogglePayload(int slot) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ServerBoundRadioTogglePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "toggle_radio"));

    public static final StreamCodec<ByteBuf, ServerBoundRadioTogglePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ServerBoundRadioTogglePayload::slot,
            ServerBoundRadioTogglePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handleServer(final Player sender) {
        Constants.LOGGER.info("hii");
        sender.getInventory().items.set(this.slot, ItemStack.EMPTY);
        sender.getInventory().setChanged();
    }
}
