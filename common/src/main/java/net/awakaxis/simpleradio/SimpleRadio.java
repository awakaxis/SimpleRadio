package net.awakaxis.simpleradio;

import net.awakaxis.simpleradio.discord.listeners.MessageForwarderListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

public class SimpleRadio {

    // todo: move
    public static final JDA BOT = JDABuilder.create("TOKEN", EnumSet.allOf(GatewayIntent.class))
            .addEventListeners(new MessageForwarderListener()).build();

    public static void init() {
        ModRegistry.registerAll();

        Constants.LOGGER.info("Hello fans of radio!");
    }
}