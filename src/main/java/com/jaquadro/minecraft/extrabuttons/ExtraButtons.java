package com.jaquadro.minecraft.extrabuttons;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(ExtraButtons.MOD_ID)
public class ExtraButtons
{
    public static final String MOD_ID = "extrabuttons";

    public ExtraButtons(ModContainer modContainer, IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        modEventBus.addListener(ModItems::creativeTabBuilding);
    }
}
