package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

public class DelayButtonBlock extends FaceAttachedHorizontalDirectionalBlock
{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<State> STATE = EnumProperty.create("state", State.class);
    public static final EnumProperty<State> PROGRAMMED = EnumProperty.create("programmed", State.class);

    protected static final VoxelShape AABB_NORTH = Block.box(4.0D, 4.0D, 15.0D, 12.0D, 12.0D, 16.0D);
    protected static final VoxelShape AABB_SOUTH = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 1.0D);
    protected static final VoxelShape AABB_WEST = Block.box(15.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    protected static final VoxelShape AABB_EAST = Block.box(0.0D, 4.0D, 4.0D, 1.0D, 12.0D, 12.0D);
    protected static final VoxelShape AABB_UP = Block.box(4.0D, 15.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape AABB_DOWN = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 1.0D, 12.0D);

    private int tickRate = 20;

    public DelayButtonBlock(Block.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(FACE, AttachFace.WALL)
            .setValue(STATE, State.S0)
            .setValue(PROGRAMMED, State.S0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch(state.getValue(FACE)) {
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
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(POWERED)) {
            return InteractionResult.CONSUME;
        } else {
            if (player.isShiftKeyDown()) {
                State next = state.getValue(PROGRAMMED).cycle();
                worldIn.setBlock(pos, state.setValue(STATE, next).setValue(PROGRAMMED, next), 3);
            } else {
                worldIn.setBlock(pos, state.setValue(POWERED, true).setValue(STATE, state.getValue(PROGRAMMED)), 3);
                this.playSound(player, worldIn, pos, true);
            }

            this.updateNeighbors(state, worldIn, pos);
            worldIn.getBlockTicks().scheduleTick(pos, this, this.tickRate);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void tick (BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!worldIn.isClientSide) {
            if (!state.getValue(POWERED)) {
                worldIn.setBlock(pos, state.setValue(STATE, State.S0), 3);
                return;
            }

            State remaining = state.getValue(STATE);
            if (remaining != State.S0) {
                worldIn.setBlock(pos, state.setValue(STATE, remaining.reduce()), 3);
                worldIn.getBlockTicks().scheduleTick(pos, this, this.tickRate);
            } else {
                worldIn.setBlock(pos, state.setValue(POWERED, false), 3);
                this.updateNeighbors(state, worldIn, pos);
                this.playSound(null, worldIn, pos, false);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            if (state.getValue(POWERED)) {
                this.updateNeighbors(state, worldIn, pos);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    private void playSound(@Nullable Player player, Level worldIn, BlockPos pos, boolean buttonOn) {
        worldIn.playSound(buttonOn ? player : null, pos, this.getSoundEvent(buttonOn), SoundSource.BLOCKS, 0.3F, buttonOn ? 0.6F : 0.5F);
    }

    private SoundEvent getSoundEvent(boolean buttonOn) {
        return buttonOn ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) && getConnectedDirection(blockState) == side ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, FACE, STATE, PROGRAMMED);
    }

    private void updateNeighbors(BlockState state, Level worldIn, BlockPos pos) {
        worldIn.updateNeighborsAt(pos, this);
        worldIn.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), this);
    }

    public enum State implements StringRepresentable
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
        public String getSerializedName () {
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
