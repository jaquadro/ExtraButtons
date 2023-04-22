package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PanelButtonBlock extends ButtonBlock
{
    protected static final VoxelShape AABB_NORTH_OFF = Block.box(1.0D, 1.0D, 14.0D, 15.0D, 15.0D, 16.0D);
    protected static final VoxelShape AABB_SOUTH_OFF = Block.box(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 2.0D);
    protected static final VoxelShape AABB_WEST_OFF = Block.box(14.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D);
    protected static final VoxelShape AABB_EAST_OFF = Block.box(0.0D, 1.0D, 1.0D, 2.0D, 15.0D, 15.0D);
    protected static final VoxelShape AABB_UP_OFF = Block.box(1.0D, 14.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    protected static final VoxelShape AABB_DOWN_OFF = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
    protected static final VoxelShape AABB_NORTH_ON = Block.box(1.0D, 1.0D, 15.0D, 15.0D, 15.0D, 16.0D);
    protected static final VoxelShape AABB_SOUTH_ON = Block.box(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 1.0D);
    protected static final VoxelShape AABB_WEST_ON = Block.box(15.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D);
    protected static final VoxelShape AABB_EAST_ON = Block.box(0.0D, 1.0D, 1.0D, 1.0D, 15.0D, 15.0D);
    protected static final VoxelShape AABB_UP_ON = Block.box(1.0D, 15.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    protected static final VoxelShape AABB_DOWN_ON = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);

    public PanelButtonBlock (Block.Properties properties, int ticksToStayPressed, boolean arrowsCanPress, SoundEvent soundOff, SoundEvent soundOn) {
        super(properties, ticksToStayPressed, arrowsCanPress, soundOff, soundOn);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED,false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        boolean flag = state.getValue(POWERED);
        switch(state.getValue(FACE)) {
            case FLOOR:
                return flag ? AABB_DOWN_ON : AABB_DOWN_OFF;
            case WALL:
                switch(direction) {
                    case EAST:
                        return flag ? AABB_EAST_ON : AABB_EAST_OFF;
                    case WEST:
                        return flag ? AABB_WEST_ON : AABB_WEST_OFF;
                    case SOUTH:
                        return flag ? AABB_SOUTH_ON : AABB_SOUTH_OFF;
                    case NORTH:
                    default:
                        return flag ? AABB_NORTH_ON : AABB_NORTH_OFF;
                }
            case CEILING:
            default:
                return flag ? AABB_UP_ON : AABB_UP_OFF;
        }
    }
}
