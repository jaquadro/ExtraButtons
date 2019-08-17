package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class DelayButtonBlock extends HorizontalFaceBlock
{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<State> STATE = EnumProperty.create("state", State.class);
    public static final EnumProperty<State> PROGRAMMED = EnumProperty.create("programmed", State.class);

    protected static final VoxelShape AABB_NORTH = Block.makeCuboidShape(4.0D, 4.0D, 15.0D, 12.0D, 12.0D, 16.0D);
    protected static final VoxelShape AABB_SOUTH = Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 1.0D);
    protected static final VoxelShape AABB_WEST = Block.makeCuboidShape(15.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    protected static final VoxelShape AABB_EAST = Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 1.0D, 12.0D, 12.0D);
    protected static final VoxelShape AABB_UP = Block.makeCuboidShape(4.0D, 15.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape AABB_DOWN = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 1.0D, 12.0D);

    public DelayButtonBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState()
            .with(HORIZONTAL_FACING, Direction.NORTH)
            .with(POWERED, false)
            .with(FACE, AttachFace.WALL)
            .with(STATE, State.S0)
            .with(PROGRAMMED, State.S0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch(state.get(FACE)) {
            case FLOOR:
                return AABB_DOWN;
            case WALL:
                switch(direction) {
                    case EAST:
                        return AABB_EAST;
                    case WEST:
                        return AABB_WEST;
                    case SOUTH:
                        return AABB_SOUTH;
                    case NORTH:
                    default:
                        return AABB_NORTH;
                }
            case CEILING:
            default:
                return AABB_UP;
        }
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
            if (player.isSneaking()) {
                State next = state.get(PROGRAMMED).cycle();
                worldIn.setBlockState(pos, state.with(STATE, next).with(PROGRAMMED, next), 3);
            } else {
                worldIn.setBlockState(pos, state.with(POWERED, true).with(STATE, state.get(PROGRAMMED)), 3);
                this.playSound(player, worldIn, pos, true);
            }

            this.updateNeighbors(state, worldIn, pos);
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
            return true;
        }
    }

    @Override
    public void tick (BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote) {
            if (!state.get(POWERED)) {
                worldIn.setBlockState(pos, state.with(STATE, State.S0), 3);
                return;
            }

            State remaining = state.get(STATE);
            if (remaining != State.S0) {
                worldIn.setBlockState(pos, state.with(STATE, remaining.reduce()), 3);
                worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
            } else {
                worldIn.setBlockState(pos, state.with(POWERED, false), 3);
                this.updateNeighbors(state, worldIn, pos);
                this.playSound(null, worldIn, pos, false);
            }
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            if (state.get(POWERED)) {
                this.updateNeighbors(state, worldIn, pos);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    private void playSound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos, boolean buttonOn) {
        worldIn.playSound(buttonOn ? player : null, pos, this.getSoundEvent(buttonOn), SoundCategory.BLOCKS, 0.3F, buttonOn ? 0.6F : 0.5F);
    }

    private SoundEvent getSoundEvent(boolean buttonOn) {
        return buttonOn ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWERED) && getFacing(blockState) == side ? 15 : 0;
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, POWERED, FACE, STATE, PROGRAMMED);
    }

    private void updateNeighbors(BlockState state, World worldIn, BlockPos pos) {
        worldIn.notifyNeighborsOfStateChange(pos, this);
        worldIn.notifyNeighborsOfStateChange(pos.offset(getFacing(state).getOpposite()), this);
    }

    public enum State implements IStringSerializable
    {
        S0("s0"),
        S1("s1"),
        S2("s2"),
        S3("s3");

        private final String name;

        State(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public State cycle() {
            switch (this) {
                case S0: return S1;
                case S1: return S2;
                case S2: return S3;
                case S3: return S0;
                default: return S0;
            }
        }

        public State reduce() {
            switch (this) {
                case S3: return S2;
                case S2: return S1;
                default: return S0;
            }
        }
    }
}
