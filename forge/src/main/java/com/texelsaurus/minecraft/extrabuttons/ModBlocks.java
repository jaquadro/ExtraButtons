package com.texelsaurus.minecraft.extrabuttons;

import com.texelsaurus.minecraft.extrabuttons.block.*;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public final class ModBlocks
{
    public static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, ExtraButtons.MOD_ID);

    public static final RegistryObject<Block>
        WHITE_TOGGLE_BUTTON = registerToggleButtonBlock("white_toggle_button", DyeColor.WHITE),
        ORANGE_TOGGLE_BUTTON = registerToggleButtonBlock("orange_toggle_button", DyeColor.ORANGE),
        MAGENTA_TOGGLE_BUTTON = registerToggleButtonBlock("magenta_toggle_button", DyeColor.MAGENTA),
        LIGHT_BLUE_TOGGLE_BUTTON = registerToggleButtonBlock("light_blue_toggle_button", DyeColor.LIGHT_BLUE),
        YELLOW_TOGGLE_BUTTON = registerToggleButtonBlock("yellow_toggle_button", DyeColor.YELLOW),
        LIME_TOGGLE_BUTTON = registerToggleButtonBlock("lime_toggle_button", DyeColor.LIME),
        PINK_TOGGLE_BUTTON = registerToggleButtonBlock("pink_toggle_button", DyeColor.PINK),
        GRAY_TOGGLE_BUTTON = registerToggleButtonBlock("gray_toggle_button", DyeColor.GRAY),
        LIGHT_GRAY_TOGGLE_BUTTON = registerToggleButtonBlock("light_gray_toggle_button", DyeColor.LIGHT_GRAY),
        CYAN_TOGGLE_BUTTON = registerToggleButtonBlock("cyan_toggle_button", DyeColor.CYAN),
        PURPLE_TOGGLE_BUTTON = registerToggleButtonBlock("purple_toggle_button", DyeColor.PURPLE),
        BLUE_TOGGLE_BUTTON = registerToggleButtonBlock("blue_toggle_button", DyeColor.BLUE),
        BROWN_TOGGLE_BUTTON = registerToggleButtonBlock("brown_toggle_button", DyeColor.BROWN),
        GREEN_TOGGLE_BUTTON = registerToggleButtonBlock("green_toggle_button", DyeColor.GREEN),
        RED_TOGGLE_BUTTON = registerToggleButtonBlock("red_toggle_button", DyeColor.RED),
        BLACK_TOGGLE_BUTTON = registerToggleButtonBlock("black_toggle_button", DyeColor.BLACK),
        CAPACITIVE_TOUCH_BLOCK = registerCapacitiveTouchBlock("capacitive_touch_block"),
        STONE_PANEL_BUTTON = registerStonePanelButtonBlock("stone_panel_button"),
        OAK_PANEL_BUTTON = registerWoodPanelButtonBlock("oak_panel_button"),
        SPRUCE_PANEL_BUTTON = registerWoodPanelButtonBlock("spruce_panel_button"),
        BIRCH_PANEL_BUTTON = registerWoodPanelButtonBlock("birch_panel_button"),
        JUNGLE_PANEL_BUTTON = registerWoodPanelButtonBlock("jungle_panel_button"),
        ACACIA_PANEL_BUTTON = registerWoodPanelButtonBlock("acacia_panel_button"),
        CHERRY_PANEL_BUTTON = registerWoodPanelButtonBlock("cherry_panel_button"),
        DARK_OAK_PANEL_BUTTON = registerWoodPanelButtonBlock("dark_oak_panel_button"),
        MANGROVE_PANEL_BUTTON = registerWoodPanelButtonBlock("mangrove_panel_button"),
        BAMBOO_PANEL_BUTTON = registerWoodPanelButtonBlock("bamboo_panel_button"),
        CRIMSON_PANEL_BUTTON = registerWoodPanelButtonBlock("crimson_panel_button"),
        WARPED_PANEL_BUTTON = registerWoodPanelButtonBlock("warped_panel_button"),
        DELAY_BUTTON_BLOCK = registerDelayButtonBlock("delay_button"),
        ENTITY_DETECTOR_RAIL = registerEntityDetectorRailBlock("entity_detector_rail"),
        ENTITY_POWERED_RAIL = registerEntityPoweredRailBlock("entity_powered_rail");

    public static void register(IEventBus bus) {
        BLOCK_REGISTER.register(bus);
    }

    private static RegistryObject<Block> registerToggleButtonBlock(String name, DyeColor color) {
        return BLOCK_REGISTER.register(name, () -> new ToggleButtonBlock(color, BlockBehaviour.Properties.of()
            .noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
    }

    private static RegistryObject<Block> registerWoodPanelButtonBlock(String name) {
        return BLOCK_REGISTER.register(name, () -> new PanelButtonBlock(BlockBehaviour.Properties.of()
            .noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY), BlockSetType.OAK, 30));
    }

    private static RegistryObject<Block> registerStonePanelButtonBlock(String name) {
        return BLOCK_REGISTER.register(name, () -> new PanelButtonBlock(BlockBehaviour.Properties.of()
            .noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY), BlockSetType.STONE, 20));
    }

    private static RegistryObject<Block> registerCapacitiveTouchBlock(String name) {
        return BLOCK_REGISTER.register(name, () -> new CapacitiveTouchBlock(BlockBehaviour.Properties.of()));
    }

    private static RegistryObject<Block> registerDelayButtonBlock(String name) {
        return BLOCK_REGISTER.register(name, () -> new DelayButtonBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY)));
    }

    private static RegistryObject<Block> registerEntityDetectorRailBlock(String name) {
        return BLOCK_REGISTER.register(name, () -> new EntityDetectorRailBlock(BlockBehaviour.Properties.of()
            .noCollission().strength(0.7F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY)));
    }

    private static RegistryObject<Block> registerEntityPoweredRailBlock(String name) {
        return BLOCK_REGISTER.register(name, () -> new EntityPoweredRailBlock(BlockBehaviour.Properties.of()
            .noCollission().strength(0.7F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY)));
    }

    private static <B extends Block> Stream<B> getBlocksOfType(Class<B> blockClass) {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).map(blockClass::cast);
    }

    public static Stream<ToggleButtonBlock> getToggleButtons() {
        return getBlocksOfType(ToggleButtonBlock.class);
    }

    public static Stream<PanelButtonBlock> getWoodPanelButtons() {
        return getBlocksOfType(PanelButtonBlock.class).filter(b -> b.getBlockSetType() != BlockSetType.STONE);
    }
}
