package covers1624.lib.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RaytraceHelper {

	public static MovingObjectPosition retraceBlock(World world, EntityLivingBase player, BlockPosition blockPos) {
		Vec3 vec3 = new Vec3(player.posX, player.posY + 1.62D, player.posZ);
		Vec3 vec32 = player.getLook(1.0F);
		Vec3 vec33 = vec3.addVector(vec32.xCoord * 5.0D, vec32.yCoord * 5.0D, vec32.zCoord * 5.0D);
		Block block = blockPos.getBlock(world);
		if (block != null) {
			return block.collisionRayTrace(world, blockPos.convertToBlockPos(), vec3, vec33);
		}
		return null;
	}

	public static MovingObjectPosition traceBlock(EntityPlayer var0) {
		Vec3 var1 = new Vec3(var0.posX, var0.posY + 1.62D, var0.posZ);
		Vec3 var2 = var0.getLook(1.0F);
		Vec3 var3 = var1.addVector(var2.xCoord * 5.0D, var2.yCoord * 5.0D, var2.zCoord * 5.0D);
		return var0.worldObj.rayTraceBlocks(var1, var3);
	}
}
