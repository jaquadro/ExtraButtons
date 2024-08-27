package com.texelsaurus.minecraft.extrabuttons;

import net.fabricmc.api.ModInitializer;

public class ExtraButtons implements ModInitializer
{
    public static final String MOD_ID = "extrabuttons";

    @Override
    public void onInitialize () {
        ModBlocks.initialize();
    }
}
