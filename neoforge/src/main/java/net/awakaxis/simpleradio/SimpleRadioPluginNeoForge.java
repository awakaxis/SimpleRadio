package net.awakaxis.simpleradio;

import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.EventRegistration;

public class SimpleRadioPluginNeoForge implements VoicechatPlugin {
    @Override
    public String getPluginId() {
        return Constants.MOD_ID;
    }

    @Override
    public void initialize(VoicechatApi api) {

    }

    @Override
    public void registerEvents(EventRegistration registration) {
    }
}
