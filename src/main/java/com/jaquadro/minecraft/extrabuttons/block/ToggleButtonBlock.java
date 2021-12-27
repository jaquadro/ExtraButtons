package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ToggleButtonBlock extends ButtonBlock
{
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    private DyeColor color;

    public ToggleButtonBlock(DyeColor color, Block.Properties properties) {
        super(false, properties);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(TRIGGERED, false)
            .setValue(FACE, AttachFace.WALL));
        this.color = color;
    }

    @Override
    public VoxelShape getShape (BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return super.getShape(state.setValue(POWERED, state.getValue(TRIGGERED)), worldIn, pos, context);
    }

    @Override
    public InteractionResult use (BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(TRIGGERED)) {
            return InteractionResult.CONSUME;
        }

        worldIn.setBlock(pos, state.setValue(TRIGGERED, true), 3);
        this.playSound(player, worldIn, pos, true);
        worldIn.getBlockTicks().scheduleTick(pos, this, 5);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick (BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!worldIn.isClientSide) {
            if (state.getValue(TRIGGERED)) {
                worldIn.setBlock(pos, state.setValue(TRIGGERED, false).setValue(POWERED, !state.getValue(POWERED)), 3);
                this.updateNeighbors(state, worldIn, pos);
                this.playSound(null, worldIn, pos, false);
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, POWERED, TRIGGERED, FACE);
    }

    protected SoundEvent getSound(boolean pressed) {
        return pressed ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }

    private void updateNeighbors(BlockState state, Level worldIn, BlockPos pos) {
        worldIn.updateNeighborsAt(pos, this);
        worldIn.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), this);
    }
}
