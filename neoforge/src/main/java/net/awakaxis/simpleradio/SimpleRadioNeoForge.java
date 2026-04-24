package net.awakaxis.simpleradio;


import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.awakaxis.simpleradio.discord.WebhookHelper;
import net.awakaxis.simpleradio.discord.listeners.MessageForwarderListener;
import net.awakaxis.simpleradio.network.ServerBoundRadioTogglePayload;
import net.awakaxis.simpleradio.platform.NeoForgePlatformHelper;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Webhook;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

@Mod(Constants.MOD_ID)
public class SimpleRadioNeoForge {

    //todo: remove
    private static final String URL = "https://discord.com/api/webhooks/1496991330801549456/fOGiJmJBV423GahyJMLP6C-26MK-ycYzgD2HUWKV8cJrQS71cYiOhMcTc_eQZpl3e15f";

    public SimpleRadioNeoForge(IEventBus eventBus) {

        eventBus.addListener(RegisterPayloadHandlersEvent.class, (registerPayloadHandlersEvent -> {
            final PayloadRegistrar registrar = registerPayloadHandlersEvent.registrar("1");
            registrar.playToServer(
                    ServerBoundRadioTogglePayload.TYPE,
                    ServerBoundRadioTogglePayload.STREAM_CODEC,
                    ((serverBoundRadioTogglePayload, iPayloadContext) -> {
                        iPayloadContext.enqueueWork(() -> serverBoundRadioTogglePayload.handleServer(iPayloadContext.player()));
                    })
            );
        }));

        NeoForge.EVENT_BUS.addListener(ServerStartedEvent.class, (serverStartedEvent -> {
            MessageForwarderListener.setServer(serverStartedEvent.getServer());
        }));

        NeoForge.EVENT_BUS.addListener(ServerChatEvent.class, (serverChatEvent -> {
            WebhookHelper.sendPlayerMessage(serverChatEvent.getUsername(), serverChatEvent.getRawText(), URL);
        }));

        NeoForge.EVENT_BUS.addListener(LivingDeathEvent.class, (livingDeathEvent -> {
            if (livingDeathEvent.getEntity() instanceof ServerPlayer player) {
                WebhookHelper.sendServerMessage(livingDeathEvent.getSource().getLocalizedDeathMessage(player).getString(), URL);
            }
        }));

        NeoForge.EVENT_BUS.addListener(CommandEvent.class, (commandEvent -> {
            if ((commandEvent.getParseResults().getContext().getSource().getEntity() instanceof ServerPlayer serverPlayer)) {
                WebhookHelper.sendServerMessage(String.format("%s executed `%s`", serverPlayer.getName().getString(), commandEvent.getParseResults().getReader().getString()), URL);
            }
        }));

        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, (playerLoggedInEvent -> {
            if (playerLoggedInEvent.getEntity() instanceof ServerPlayer serverPlayer) {
                WebhookHelper.sendServerMessage(String.format("%s joined the game", serverPlayer.getName().getString()), URL);
            }
        }));

        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedOutEvent.class, (playerLoggedOutEvent -> {
            if (playerLoggedOutEvent.getEntity() instanceof ServerPlayer serverPlayer) {
                WebhookHelper.sendServerMessage(String.format("%s left the game", serverPlayer.getName().getString()), URL);
            }
        }));

        NeoForge.EVENT_BUS.addListener(AdvancementEvent.AdvancementEarnEvent.class, (advancementEarnEvent -> {
            if (advancementEarnEvent.getEntity() instanceof ServerPlayer serverPlayer) {
                Optional<DisplayInfo> display = advancementEarnEvent.getAdvancement().value().display();
                if (display.isEmpty() || !display.get().shouldAnnounceChat()) return;

                WebhookHelper.sendServerMessage(String.format("%s has made the advancement **%S**", serverPlayer.getName().getString(), Advancement.name(advancementEarnEvent.getAdvancement()).getString()), URL);
            }
        }));

        SimpleRadio.init();

        NeoForgePlatformHelper.REGISTERS.forEach((registry, deferredRegister) -> deferredRegister.register(eventBus));
    }
}