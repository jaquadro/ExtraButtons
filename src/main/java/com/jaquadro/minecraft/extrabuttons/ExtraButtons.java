package com.jaquadro.minecraft.extrabuttons;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExtraButtons.MOD_ID)
public class ExtraButtons
{
    public static final String MOD_ID = "extrabuttons";

    public static final Logger log = LogManager.getLogger();

    public ExtraButtons() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(bus);
        ModItems.register(bus);

        bus.addListener(ModItems::creativeModeTagRegister);
    }
}
