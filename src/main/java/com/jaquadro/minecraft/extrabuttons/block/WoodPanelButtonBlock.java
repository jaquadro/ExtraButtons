package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;

public class WoodPanelButtonBlock extends PanelButtonBlock
{
    public WoodPanelButtonBlock(Block.Properties properties) {
        super(true, properties);
    }

    @Override
    protected SoundEvent getSound(boolean clickOn) {
        return clickOn ? SoundEvents.WOODEN_BUTTON_CLICK_ON : SoundEvents.WOODEN_BUTTON_CLICK_OFF;
    }
}
