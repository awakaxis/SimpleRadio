package net.awakaxis.simpleradio;

import net.fabricmc.api.ModInitializer;

public class SimpleRadioFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        SimpleRadio.init();
    }
}
