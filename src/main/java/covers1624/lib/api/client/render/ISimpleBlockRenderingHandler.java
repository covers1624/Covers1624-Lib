package covers1624.lib.api.client.render;

import covers1624.lib.client.render.RenderStateManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Created by covers1624 on 3/25/2016.
 */
public interface ISimpleBlockRenderingHandler {

    void renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, RenderStateManager stateManager);

}
