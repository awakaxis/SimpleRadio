package net.awakaxis.simpleradio.discord.listeners;

import net.awakaxis.simpleradio.Constants;
import net.awakaxis.simpleradio.SimpleRadio;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageForwarderListener extends ListenerAdapter {

    @Nullable
    private static MinecraftServer server;

    public static void setServer(@Nullable MinecraftServer minecraftServer) {
        server = minecraftServer;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().equals(SimpleRadio.BOT.getSelfUser())) {
            return;
        }
        Constants.LOGGER.info("!{}! [{}] {}: {}\n", server == null ? "NO SERVER" : "SERVER", event.getChannel(), event.getAuthor(), event.getMessage().getContentDisplay());
        if (server != null) {
            server.getPlayerList().broadcastSystemMessage(
                    Component.empty()
                            .append(Component.literal("[DC] ").withColor(0x5865F2).withStyle(ChatFormatting.BOLD))
                            .append(Component.nullToEmpty(String.format("%s", event.getAuthor().getName())))
                            .append(Component.nullToEmpty(String.format(": %s", event.getMessage().getContentDisplay())))
                    , false);
        }
    }
}
