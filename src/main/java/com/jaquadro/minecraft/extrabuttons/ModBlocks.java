package com.jaquadro.minecraft.extrabuttons;

import com.jaquadro.minecraft.extrabuttons.block.ToggleButtonBlock;
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
        LIME__TOGGLE_BUTTON = null,
        PINK_TOGGLE_BUTTON = null,
        GRAY_TOGGLE_BUTTON = null,
        LIGHT_GRAY_TOGGLE_BUTTON = null,
        CYAN_TOGGLE_BUTTON = null,
        PURPLE_TOGGLE_BUTTON = null,
        BLUE_TOGGLE_BUTTON = null,
        BROWN_TOGGLE_BUTTON = null,
        GREEN_TOGGLE_BUTTON = null,
        RED_TOGGLE_BUTTON = null,
        BLACK_TOGGLE_BUTTON = null;

    public static List<Block> blockList = new ArrayList<Block>();

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        registerToggleButtonBlock(event, "white_toggle_button", DyeColor.WHITE);
        registerToggleButtonBlock(event, "orange_toggle_button", DyeColor.ORANGE);
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        for (Block block : blockList) {
            BlockItem itemBlock = new BlockItem(block, new Item.Properties().group(ItemGroup.REDSTONE));
            itemBlock.setRegistryName(block.getRegistryName());
            event.getRegistry().register(itemBlock);
        }
    }

    private static Block registerToggleButtonBlock(RegistryEvent.Register<Block> event, String name, DyeColor color) {
        return registerBlock(event, name, new ToggleButtonBlock(color, Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F)));
    }

    private static Block registerBlock(RegistryEvent.Register<Block> event, String name, Block block) {
        block.setRegistryName(name);
        event.getRegistry().register(block);
        blockList.add(block);

        return block;
    }
}
