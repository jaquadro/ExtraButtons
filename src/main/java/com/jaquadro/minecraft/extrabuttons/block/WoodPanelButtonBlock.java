package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.block.Block;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class WoodPanelButtonBlock extends PanelButtonBlock
{
    public WoodPanelButtonBlock(Block.Properties properties) {
        super(true, properties);
    }

    @Override
    protected SoundEvent getSoundEvent(boolean clickOn) {
        return clickOn ? SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON : SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF;
    }
}
