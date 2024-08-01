package com.jaquadro.minecraft.extrabuttons;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExtraButtons.MOD_ID)
public class ExtraButtons
{
    public static final String MOD_ID = "extrabuttons";

    public ExtraButtons() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(bus);
        ModItems.register(bus);

        bus.addListener(ModItems::creativeTabBuilding);
    }
}
