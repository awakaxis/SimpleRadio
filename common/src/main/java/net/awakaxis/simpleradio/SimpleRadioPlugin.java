package net.awakaxis.simpleradio;

import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;

import javax.annotation.Nullable;

public class SimpleRadioPlugin implements VoicechatPlugin {

    private static VoicechatServerApi API_INSTANCE;

    @Override
    public String getPluginId() {
        return Constants.MOD_ID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
       registration.registerEvent(VoicechatServerStartedEvent.class, (event) -> {
           API_INSTANCE = event.getVoicechat();
       });
    }

    public static @Nullable VoicechatServerApi getApi() {
        return API_INSTANCE;
    }
}
