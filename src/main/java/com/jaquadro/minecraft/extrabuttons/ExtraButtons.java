package com.jaquadro.minecraft.extrabuttons;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExtraButtons.MOD_ID)
public class ExtraButtons
{
    public static final String MOD_ID = "extrabuttons";

    public static final Logger log = LogManager.getLogger();

    public ExtraButtons() {

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registry
    {
        @SubscribeEvent
        public static void registerBlocks (RegistryEvent.Register<Block> event) {
            ModBlocks.registerBlocks(event);
        }

        @SubscribeEvent
        public static void registerItems (RegistryEvent.Register<Item> event) {
            ModBlocks.registerBlockItems(event);
        }
    }
}
