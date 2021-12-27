package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;

public class StonePanelButtonBlock extends PanelButtonBlock
{
    public StonePanelButtonBlock(Block.Properties properties) {
        super(false, properties);
    }

    @Override
    protected SoundEvent getSound(boolean clickOn) {
        return clickOn ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }
}
