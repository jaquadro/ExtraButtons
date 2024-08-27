package com.texelsaurus.minecraft.extrabuttons;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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
                return new BlockItem(ro.get(), new Item.Properties());
            });
        }
        ITEM_REGISTER.register(bus);
    }

    public static void creativeTabBuilding(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.REDSTONE_BLOCKS)) {
            event.accept(ModBlocks.STONE_PANEL_BUTTON);
            event.accept(ModBlocks.OAK_PANEL_BUTTON);
            event.accept(ModBlocks.CAPACITIVE_TOUCH_BLOCK);
            event.accept(ModBlocks.DELAY_BUTTON_BLOCK);
        } else if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(ModBlocks.ENTITY_POWERED_RAIL);
            event.accept(ModBlocks.ENTITY_DETECTOR_RAIL);
        } else if (event.getTabKey().equals(CreativeModeTabs.COLORED_BLOCKS)) {
            ModBlocks.getToggleButtons().forEach(b -> event.accept(new ItemStack(b)));
        }
    }
}
