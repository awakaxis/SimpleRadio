package net.awakaxis.simpleradio;


import net.awakaxis.simpleradio.discord.listeners.MessageForwarderListener;
import net.awakaxis.simpleradio.network.ServerBoundRadioTogglePayload;
import net.awakaxis.simpleradio.platform.NeoForgePlatformHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Objects;

@Mod(Constants.MOD_ID)
public class SimpleRadioNeoForge {

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
            Objects.requireNonNull(SimpleRadio.BOT.getTextChannelById("1127684842176380938")).sendMessage(String.format("MINECRAFT %S > %S", serverChatEvent.getUsername(), serverChatEvent.getRawText())).queue();
        }));

        SimpleRadio.init();

        NeoForgePlatformHelper.REGISTERS.forEach((registry, deferredRegister) -> deferredRegister.register(eventBus));
    }
}