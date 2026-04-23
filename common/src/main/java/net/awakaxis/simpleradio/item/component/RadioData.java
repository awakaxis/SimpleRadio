package net.awakaxis.simpleradio.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.maxhenkel.voicechat.api.Position;
import de.maxhenkel.voicechat.api.ServerLevel;
import de.maxhenkel.voicechat.api.audiochannel.LocationalAudioChannel;
import net.awakaxis.simpleradio.SimpleRadioPlugin;

import javax.annotation.Nullable;
import java.util.UUID;

public class RadioData {
    private boolean isOn;
    private LocationalAudioChannel channel;

    public static final Codec<RadioData> CODEC = RecordCodecBuilder.create(radioDataInstance ->
            radioDataInstance.group(
                    Codec.BOOL.fieldOf("isOn").forGetter(RadioData::getIsOn)
            ).apply(radioDataInstance, RadioData::new)
    );

    public RadioData(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean getIsOn() {
        return this.isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean toggleIsOn() {
        this.isOn = !this.isOn;
        return this.isOn;
    }

    public @Nullable LocationalAudioChannel getChannel() {
        return channel;
    }

    public void removeChannel() {
        channel = null;
    }

    public @Nullable LocationalAudioChannel createChannel(ServerLevel level, Position position) {
        if (SimpleRadioPlugin.getApi() == null) {
            return null;
        }
        return SimpleRadioPlugin.getApi().createLocationalAudioChannel(UUID.randomUUID(), level, position);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof RadioData other && (this.isOn == other.isOn && this.channel.equals(other.channel)));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
