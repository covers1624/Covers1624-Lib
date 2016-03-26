package covers1624.lib.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Deprecated //Use CCL
public class RaytraceHelper {

    @Deprecated
    public static RayTraceResult retraceBlock(World world, EntityLivingBase player, BlockPosition blockPos) {
        Vec3d vec3 = new Vec3d(player.posX, player.posY + 1.62D, player.posZ);
        Vec3d vec32 = player.getLook(1.0F);
        Vec3d vec33 = vec3.addVector(vec32.xCoord * 5.0D, vec32.yCoord * 5.0D, vec32.zCoord * 5.0D);
        IBlockState state = blockPos.getBlockState(world);
        if (state.getBlock() != null) {
            return state.getBlock().collisionRayTrace(state, world, blockPos.toBlockPos(), vec3, vec33);
        }
        return null;
    }

    @Deprecated
    public static RayTraceResult traceBlock(EntityPlayer var0) {
        Vec3d var1 = new Vec3d(var0.posX, var0.posY + 1.62D, var0.posZ);
        Vec3d var2 = var0.getLook(1.0F);
        Vec3d var3 = var1.addVector(var2.xCoord * 5.0D, var2.yCoord * 5.0D, var2.zCoord * 5.0D);
        return var0.worldObj.rayTraceBlocks(var1, var3);
    }
}
