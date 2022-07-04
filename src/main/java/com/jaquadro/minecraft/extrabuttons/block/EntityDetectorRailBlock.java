package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class EntityDetectorRailBlock extends DetectorRailBlock
{
    public EntityDetectorRailBlock (Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isClientSide) {
            if (!state.getValue(POWERED)) {
                this.updatePoweredState(worldIn, pos, state);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (!worldIn.isClientSide && state.getValue(POWERED)) {
            this.updatePoweredState(worldIn, pos, state);
        }
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (oldState.getBlock() != state.getBlock()) {
            super.onPlace(state, worldIn, pos, oldState, isMoving);
            this.updatePoweredState(worldIn, pos, state);
        }
    }

    private void updatePoweredState(Level worldIn, BlockPos pos, BlockState state) {
        boolean powered = state.getValue(POWERED);
        boolean isValidTarget = false;
        List<AbstractMinecart> carts = this.getInteractingMinecartOfType(worldIn, pos, AbstractMinecart.class, (e) -> { return true; });
        if (!carts.isEmpty()) {
            for (AbstractMinecart cart : carts) {
                if (!cart.getPassengers().isEmpty())
                    isValidTarget = true;
            }
        }

        if (isValidTarget && !powered) {
            BlockState blockstate = state.setValue(POWERED, true);
            worldIn.setBlock(pos, blockstate, 3);
            this.updatePowerToConnected(worldIn, pos, blockstate, true);
            worldIn.updateNeighborsAt(pos, this);
            worldIn.updateNeighborsAt(pos.below(), this);
            worldIn.setBlocksDirty(pos, state, blockstate);
        }

        if (!isValidTarget && powered) {
            BlockState blockstate1 = state.setValue(POWERED, false);
            worldIn.setBlock(pos, blockstate1, 3);
            this.updatePowerToConnected(worldIn, pos, blockstate1, false);
            worldIn.updateNeighborsAt(pos, this);
            worldIn.updateNeighborsAt(pos.below(), this);
            worldIn.setBlocksDirty(pos, state, blockstate1);
        }

        if (isValidTarget) {
            worldIn.scheduleTick(pos, this, 20);
        }

        worldIn.updateNeighbourForOutputSignal(pos, this);
    }

    private <T extends AbstractMinecart> List<T> getInteractingMinecartOfType(Level p_52437_, BlockPos p_52438_, Class<T> p_52439_, Predicate<Entity> p_52440_) {
        return p_52437_.getEntitiesOfClass(p_52439_, this.getSearchBB(p_52438_), p_52440_);
    }

    private AABB getSearchBB(BlockPos p_52471_) {
        double d0 = 0.2D;
        return new AABB((double)p_52471_.getX() + 0.2D, (double)p_52471_.getY(), (double)p_52471_.getZ() + 0.2D, (double)(p_52471_.getX() + 1) - 0.2D, (double)(p_52471_.getY() + 1) - 0.2D, (double)(p_52471_.getZ() + 1) - 0.2D);
    }
}
