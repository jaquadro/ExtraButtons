package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class CapacitiveTouchBlock extends Block
{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public CapacitiveTouchBlock (Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(POWERED,false));
    }

    @Override
    public int tickRate (IWorldReader worldIn) {
        return 20;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(POWERED)) {
            return true;
        } else {
            worldIn.setBlockState(pos, state.with(POWERED, true), 3);
            updateNeighbors(worldIn, pos);
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
            return true;
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            if (state.get(POWERED)) {
                updateNeighbors(worldIn, pos);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWERED) ? 15 : 0;
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote && state.get(POWERED)) {
            worldIn.setBlockState(pos, state.with(POWERED, false), 3);
            updateNeighbors(worldIn, pos);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    private void updateNeighbors(World worldIn, BlockPos pos) {
        worldIn.notifyNeighborsOfStateChange(pos, this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(Direction.NORTH), this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(Direction.SOUTH), this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(Direction.EAST), this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(Direction.WEST), this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(Direction.UP), this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(Direction.DOWN), this);
    }
}
