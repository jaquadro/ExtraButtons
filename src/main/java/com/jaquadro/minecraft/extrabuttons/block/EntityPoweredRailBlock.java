package com.jaquadro.minecraft.extrabuttons.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityPoweredRailBlock extends PoweredRailBlock
{
    public EntityPoweredRailBlock (Properties builder) {
        super(builder);
    }

    @Override
    public void onMinecartPass (BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
        if (!state.get(POWERED))
            return;
        if (!cart.shouldDoRailFunctions())
            return;
        if (!cart.isBeingRidden())
            return;

        RailShape railshape = getRailDirection(state, world, pos, cart);

        Vec3d motion = cart.getMotion();
        double speed = Math.sqrt(cart.func_213296_b(motion));
        if (speed > 0.01D) {
            cart.setMotion(motion.add(motion.x / speed * 0.06D, 0.0D, motion.z / speed * 0.06D));
        } else {
            double motionX = motion.x;
            double motionY = motion.z;
            if (railshape == RailShape.EAST_WEST) {
                if (normalCube(world, pos.west())) {
                    motionX = 0.02D;
                } else if (normalCube(world, pos.east())) {
                    motionX = -0.02D;
                }
            } else if (railshape == RailShape.NORTH_SOUTH) {
                if (normalCube(world, pos.north())) {
                    motionY = 0.02D;
                } else if (normalCube(world, pos.south())) {
                    motionY = -0.02D;
                }
            } else {
                return;
            }

            cart.setMotion(motionX, motion.y, motionY);
        }
    }

    private boolean normalCube (World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).isNormalCube(worldIn, pos);
    }
}
