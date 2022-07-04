package com.jaquadro.minecraft.extrabuttons;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems
{
    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ExtraButtons.MOD_ID);

    private ModItems() {}

    public static void register(IEventBus bus) {
        for (RegistryObject<Block> ro : ModBlocks.BLOCK_REGISTER.getEntries()) {
            ITEM_REGISTER.register(ro.getId().getPath(), () -> {
                Block block = ro.get();
                CreativeModeTab tab = CreativeModeTab.TAB_REDSTONE;
                if (block == ModBlocks.ENTITY_DETECTOR_RAIL.get() || block == ModBlocks.ENTITY_POWERED_RAIL.get())
                    tab = CreativeModeTab.TAB_TRANSPORTATION;

                return new BlockItem(block, new Item.Properties().tab(tab));
            });
        }
        ITEM_REGISTER.register(bus);
    }
}
