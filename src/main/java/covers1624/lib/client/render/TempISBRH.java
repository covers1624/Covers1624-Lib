package covers1624.lib.client.render;

import covers1624.lib.api.client.render.ISimpleBlockRenderingHandler;
import covers1624.lib.math.Vector3;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Created by covers1624 on 3/25/2016.
 */
public class TempISBRH implements ISimpleBlockRenderingHandler {
    @Override
    public void renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, RenderStateManager stateManager) {
        stateManager.pushOffset();
        stateManager.setBufferOffset(new Vector3(pos));

        stateManager.popOffset();
    }
}
