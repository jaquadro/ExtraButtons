package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.block.Block;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class StonePanelButtonBlock extends PanelButtonBlock
{
    public StonePanelButtonBlock(Block.Properties properties) {
        super(false, properties);
    }

    @Override
    protected SoundEvent getSoundEvent(boolean clickOn) {
        return clickOn ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }
}
