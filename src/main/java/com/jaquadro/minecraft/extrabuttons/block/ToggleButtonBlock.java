package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class ToggleButtonBlock extends AbstractButtonBlock
{
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    private DyeColor color;

    public ToggleButtonBlock(DyeColor color, Block.Properties properties) {
        super(false, properties);
        this.setDefaultState(this.stateContainer.getBaseState()
            .with(HORIZONTAL_FACING, Direction.NORTH)
            .with(POWERED, false)
            .with(TRIGGERED, false)
            .with(FACE, AttachFace.WALL));
        this.color = color;
    }

    @Override
    public int tickRate (IWorldReader worldIn) {
        return 5;
    }

    @Override
    public VoxelShape getShape (BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return super.getShape(state.with(POWERED, state.get(TRIGGERED)), worldIn, pos, context);
    }

    @Override
    public boolean onBlockActivated (BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(TRIGGERED)) {
            return true;
        }

        worldIn.setBlockState(pos, state.with(TRIGGERED, true), 3);
        this.playSound(player, worldIn, pos, true);
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));

        return true;
    }

    @Override
    public void tick (BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote) {
            if (state.get(TRIGGERED)) {
                worldIn.setBlockState(pos, state.with(TRIGGERED, false).with(POWERED, !state.get(POWERED)), 3);
                this.updateNeighbors(state, worldIn, pos);
                this.playSound(null, worldIn, pos, false);
            }
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, POWERED, TRIGGERED, FACE);
    }

    protected SoundEvent getSoundEvent(boolean pressed) {
        return pressed ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }

    private void updateNeighbors(BlockState state, World worldIn, BlockPos pos) {
        worldIn.notifyNeighborsOfStateChange(pos, this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(getFacing(state).getOpposite()), this);
    }
}
