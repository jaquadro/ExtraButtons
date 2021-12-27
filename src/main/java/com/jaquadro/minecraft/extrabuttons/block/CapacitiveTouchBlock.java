package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class CapacitiveTouchBlock extends Block
{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private int tickRate = 20;

    public CapacitiveTouchBlock (Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED,false));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(POWERED)) {
            return InteractionResult.CONSUME;
        } else {
            worldIn.setBlock(pos, state.setValue(POWERED, true), 3);
            updateNeighbors(worldIn, pos);
            worldIn.getBlockTicks().scheduleTick(pos, this, this.tickRate);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            if (state.getValue(POWERED)) {
                updateNeighbors(worldIn, pos);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!worldIn.isClientSide && state.getValue(POWERED)) {
            worldIn.setBlock(pos, state.setValue(POWERED, false), 3);
            updateNeighbors(worldIn, pos);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    private void updateNeighbors(Level worldIn, BlockPos pos) {
        worldIn.updateNeighborsAt(pos, this);
        worldIn.updateNeighborsAt(pos.relative(Direction.NORTH), this);
        worldIn.updateNeighborsAt(pos.relative(Direction.SOUTH), this);
        worldIn.updateNeighborsAt(pos.relative(Direction.EAST), this);
        worldIn.updateNeighborsAt(pos.relative(Direction.WEST), this);
        worldIn.updateNeighborsAt(pos.relative(Direction.UP), this);
        worldIn.updateNeighborsAt(pos.relative(Direction.DOWN), this);
    }
}
