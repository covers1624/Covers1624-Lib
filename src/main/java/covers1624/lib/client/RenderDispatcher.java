package covers1624.lib.client;

import covers1624.lib.api.client.render.ISimpleBlockRenderingHandler;
import covers1624.lib.client.registry.RenderRegistry;
import covers1624.lib.client.render.RenderStateManager;
import covers1624.lib.util.EnumInjector;
import covers1624.lib.util.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Created by covers1624 on 3/17/2016.
 */
public class RenderDispatcher extends BlockRendererDispatcher {

    private final BlockRendererDispatcher oldDispatcher;

    public RenderDispatcher(BlockRendererDispatcher oldDispatcher, BlockModelShapes blockModelShapes, BlockColors blockColors) {
        super(blockModelShapes, blockColors);
        this.oldDispatcher = oldDispatcher;
    }

    @Override
    public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, VertexBuffer vertexBuffer) {
        if (RenderRegistry.canHandleRenderType(state.getRenderType())){
            ISimpleBlockRenderingHandler renderingHandler = RenderRegistry.getISBRHFromRenderType(state.getRenderType());
            renderingHandler.renderBlock(state, pos, blockAccess, new RenderStateManager(vertexBuffer));
            return true;
        }
        return oldDispatcher.renderBlock(state, pos, blockAccess, vertexBuffer);
    }

    public static EnumBlockRenderType createNewRenderType(String name) {
        return EnumInjector.addRenderType(name);
    }

}
