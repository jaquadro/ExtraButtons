package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class EntityDetectorRailBlock extends DetectorRailBlock
{
    public EntityDetectorRailBlock (Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote) {
            if (!state.get(POWERED)) {
                this.updatePoweredState(worldIn, pos, state);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote && state.get(POWERED)) {
            this.updatePoweredState(worldIn, pos, state);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (oldState.getBlock() != state.getBlock()) {
            super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
            this.updatePoweredState(worldIn, pos, state);
        }
    }

    private void updatePoweredState(World worldIn, BlockPos pos, BlockState state) {
        boolean powered = state.get(POWERED);
        boolean isValidTarget = false;
        List<AbstractMinecartEntity> carts = this.findMinecarts(worldIn, pos, AbstractMinecartEntity.class, (Predicate<Entity>)null);
        if (!carts.isEmpty()) {
            for (AbstractMinecartEntity cart : carts) {
                if (cart.isBeingRidden())
                    isValidTarget = true;
            }
        }

        if (isValidTarget && !powered) {
            BlockState blockstate = state.with(POWERED, true);
            worldIn.setBlockState(pos, blockstate, 3);
            this.updateConnectedRails(worldIn, pos, blockstate, true);
            worldIn.notifyNeighborsOfStateChange(pos, this);
            worldIn.notifyNeighborsOfStateChange(pos.down(), this);
            worldIn.markBlockRangeForRenderUpdate(pos, state, blockstate);
        }

        if (!isValidTarget && powered) {
            BlockState blockstate1 = state.with(POWERED, false);
            worldIn.setBlockState(pos, blockstate1, 3);
            this.updateConnectedRails(worldIn, pos, blockstate1, false);
            worldIn.notifyNeighborsOfStateChange(pos, this);
            worldIn.notifyNeighborsOfStateChange(pos.down(), this);
            worldIn.markBlockRangeForRenderUpdate(pos, state, blockstate1);
        }

        if (isValidTarget) {
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, 20);
        }

        worldIn.updateComparatorOutputLevel(pos, this);
    }
}
