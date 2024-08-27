package com.texelsaurus.minecraft.extrabuttons;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems
{
    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(Registries.ITEM, ExtraButtons.MOD_ID);

    private ModItems() {}

    public static void register(IEventBus bus) {
        for (DeferredHolder<Block, ? extends Block> ro : ModBlocks.BLOCK_REGISTER.getEntries()) {
            ITEM_REGISTER.register(ro.getId().getPath(), () -> {
                return new BlockItem(ro.get(), new Item.Properties());
            });
        }
        ITEM_REGISTER.register(bus);
    }

    public static void creativeTabBuilding(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.REDSTONE_BLOCKS)) {
            event.accept(ModBlocks.STONE_PANEL_BUTTON.get());
            event.accept(ModBlocks.OAK_PANEL_BUTTON.get());
            event.accept(ModBlocks.CAPACITIVE_TOUCH_BLOCK.get());
            event.accept(ModBlocks.DELAY_BUTTON_BLOCK.get());
        } else if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(ModBlocks.ENTITY_POWERED_RAIL.get());
            event.accept(ModBlocks.ENTITY_DETECTOR_RAIL.get());
        } else if (event.getTabKey().equals(CreativeModeTabs.COLORED_BLOCKS)) {
            ModBlocks.getToggleButtons().forEach(b -> event.accept(new ItemStack(b)));
        }
    }
}
