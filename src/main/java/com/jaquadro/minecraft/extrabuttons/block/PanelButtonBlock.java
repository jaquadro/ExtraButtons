package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public abstract class PanelButtonBlock extends AbstractButtonBlock
{
    protected static final VoxelShape AABB_NORTH_OFF = Block.makeCuboidShape(1.0D, 1.0D, 14.0D, 15.0D, 15.0D, 16.0D);
    protected static final VoxelShape AABB_SOUTH_OFF = Block.makeCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 2.0D);
    protected static final VoxelShape AABB_WEST_OFF = Block.makeCuboidShape(14.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D);
    protected static final VoxelShape AABB_EAST_OFF = Block.makeCuboidShape(0.0D, 1.0D, 1.0D, 2.0D, 15.0D, 15.0D);
    protected static final VoxelShape AABB_UP_OFF = Block.makeCuboidShape(1.0D, 14.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    protected static final VoxelShape AABB_DOWN_OFF = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
    protected static final VoxelShape AABB_NORTH_ON = Block.makeCuboidShape(1.0D, 1.0D, 15.0D, 15.0D, 15.0D, 16.0D);
    protected static final VoxelShape AABB_SOUTH_ON = Block.makeCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 1.0D);
    protected static final VoxelShape AABB_WEST_ON = Block.makeCuboidShape(15.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D);
    protected static final VoxelShape AABB_EAST_ON = Block.makeCuboidShape(0.0D, 1.0D, 1.0D, 1.0D, 15.0D, 15.0D);
    protected static final VoxelShape AABB_UP_ON = Block.makeCuboidShape(1.0D, 15.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    protected static final VoxelShape AABB_DOWN_ON = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);

    public PanelButtonBlock (boolean wooden, Block.Properties properties) {
        super(wooden, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(POWERED,false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(HORIZONTAL_FACING);
        boolean flag = state.get(POWERED);
        switch(state.get(FACE)) {
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
