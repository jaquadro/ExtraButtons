package com.jaquadro.minecraft.extrabuttons;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.CreativeModeTabEvent;
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

    public static void creativeModeTagRegister(CreativeModeTabEvent.BuildContents event) {
        var entries = event.getEntries();
        var vis = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

        if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
            entries.put(new ItemStack(ModBlocks.WHITE_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.ORANGE_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.MAGENTA_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.LIGHT_BLUE_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.YELLOW_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.LIME_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.PINK_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.GRAY_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.LIGHT_GRAY_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.CYAN_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.PURPLE_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.BLUE_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.BROWN_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.GREEN_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.RED_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.BLACK_TOGGLE_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.CAPACITIVE_TOUCH_BLOCK.get()), vis);
            entries.put(new ItemStack(ModBlocks.STONE_PANEL_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.OAK_PANEL_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.SPRUCE_PANEL_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.BIRCH_PANEL_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.JUNGLE_PANEL_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.ACACIA_PANEL_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.DARK_OAK_PANEL_BUTTON.get()), vis);
            entries.put(new ItemStack(ModBlocks.DELAY_BUTTON_BLOCK.get()), vis);
        } else if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            entries.put(new ItemStack(ModBlocks.ENTITY_DETECTOR_RAIL.get()), vis);
            entries.put(new ItemStack(ModBlocks.ENTITY_POWERED_RAIL.get()), vis);
        }
    }
}
