package com.jaquadro.minecraft.extrabuttons;

import com.jaquadro.minecraft.extrabuttons.block.CapacitiveTouchBlock;
import com.jaquadro.minecraft.extrabuttons.block.StonePanelButtonBlock;
import com.jaquadro.minecraft.extrabuttons.block.ToggleButtonBlock;
import com.jaquadro.minecraft.extrabuttons.block.WoodPanelButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

@ObjectHolder(ExtraButtons.MOD_ID)
public class ModBlocks
{
    public static final Block
        WHITE_TOGGLE_BUTTON = null,
        ORANGE_TOGGLE_BUTTON = null,
        MAGENTA_TOGGLE_BUTTON = null,
        LIGHT_BLUE_TOGGLE_BUTTON = null,
        YELLOW_TOGGLE_BUTTON = null,
        LIME_TOGGLE_BUTTON = null,
        PINK_TOGGLE_BUTTON = null,
        GRAY_TOGGLE_BUTTON = null,
        LIGHT_GRAY_TOGGLE_BUTTON = null,
        CYAN_TOGGLE_BUTTON = null,
        PURPLE_TOGGLE_BUTTON = null,
        BLUE_TOGGLE_BUTTON = null,
        BROWN_TOGGLE_BUTTON = null,
        GREEN_TOGGLE_BUTTON = null,
        RED_TOGGLE_BUTTON = null,
        BLACK_TOGGLE_BUTTON = null,
        CAPACITIVE_TOUCH_BLOCK = null,
        STONE_PANEL_BUTTON = null,
        OAK_PANEL_BUTTON = null,
        SPRUCE_PANEL_BUTTON = null,
        BIRCH_PANEL_BUTTON = null,
        JUNGLE_PANEL_BUTTON = null,
        ACACIA_PANEL_BUTTON = null,
        DARK_OAK_PANEL_BUTTON = null;

    public static List<Block> blockList = new ArrayList<Block>();

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        registerToggleButtonBlock(event, "white_toggle_button", DyeColor.WHITE);
        registerToggleButtonBlock(event, "orange_toggle_button", DyeColor.ORANGE);
        registerToggleButtonBlock(event, "magenta_toggle_button", DyeColor.MAGENTA);
        registerToggleButtonBlock(event, "light_blue_toggle_button", DyeColor.LIGHT_BLUE);
        registerToggleButtonBlock(event, "yellow_toggle_button", DyeColor.YELLOW);
        registerToggleButtonBlock(event, "lime_toggle_button", DyeColor.LIME);
        registerToggleButtonBlock(event, "pink_toggle_button", DyeColor.PINK);
        registerToggleButtonBlock(event, "gray_toggle_button", DyeColor.GRAY);
        registerToggleButtonBlock(event, "light_gray_toggle_button", DyeColor.LIGHT_GRAY);
        registerToggleButtonBlock(event, "cyan_toggle_button", DyeColor.CYAN);
        registerToggleButtonBlock(event, "purple_toggle_button", DyeColor.PURPLE);
        registerToggleButtonBlock(event, "blue_toggle_button", DyeColor.BLUE);
        registerToggleButtonBlock(event, "brown_toggle_button", DyeColor.BROWN);
        registerToggleButtonBlock(event, "green_toggle_button", DyeColor.GREEN);
        registerToggleButtonBlock(event, "red_toggle_button", DyeColor.RED);
        registerToggleButtonBlock(event, "black_toggle_button", DyeColor.BLACK);

        registerBlock(event, "capacitive_touch_block", new CapacitiveTouchBlock(Block.Properties.create(Material.MISCELLANEOUS)));
        registerBlock(event, "stone_panel_button", new StonePanelButtonBlock(Block.Properties.create(Material.MISCELLANEOUS)
            .doesNotBlockMovement().hardnessAndResistance(0.5f)));
        registerWoodPanelButtonBlock(event, "oak_panel_button");
        registerWoodPanelButtonBlock(event, "spruce_panel_button");
        registerWoodPanelButtonBlock(event, "birch_panel_button");
        registerWoodPanelButtonBlock(event, "jungle_panel_button");
        registerWoodPanelButtonBlock(event, "acacia_panel_button");
        registerWoodPanelButtonBlock(event, "dark_oak_panel_button");
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        for (Block block : blockList) {
            BlockItem itemBlock = new BlockItem(block, new Item.Properties().group(ItemGroup.REDSTONE));
            itemBlock.setRegistryName(block.getRegistryName());
            event.getRegistry().register(itemBlock);
        }
    }

    private static Block registerToggleButtonBlock(RegistryEvent.Register<Block> event, String name, DyeColor color) {
        return registerBlock(event, name, new ToggleButtonBlock(color, Block.Properties.create(Material.MISCELLANEOUS)
            .doesNotBlockMovement().hardnessAndResistance(0.5F)));
    }

    private static Block registerWoodPanelButtonBlock(RegistryEvent.Register<Block> event, String name) {
        return registerBlock(event, name, new WoodPanelButtonBlock(Block.Properties.create(Material.MISCELLANEOUS)
            .doesNotBlockMovement().hardnessAndResistance(0.5f)));
    }

    private static Block registerBlock(RegistryEvent.Register<Block> event, String name, Block block) {
        block.setRegistryName(name);
        event.getRegistry().register(block);
        blockList.add(block);

        return block;
    }
}
