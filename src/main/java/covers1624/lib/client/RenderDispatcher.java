package covers1624.lib.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Created by covers1624 on 3/17/2016.
 */
public class RenderDispatcher extends BlockRendererDispatcher {

    private final BlockRendererDispatcher oldDispatcher;

    public RenderDispatcher(BlockRendererDispatcher oldDispatcher, BlockModelShapes blockModelShapes, GameSettings gameSettings) {
        super(blockModelShapes, gameSettings);
        this.oldDispatcher = oldDispatcher;
    }

    @Override
    public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, WorldRenderer worldRendererIn) {
        return super.renderBlock(state, pos, blockAccess, worldRendererIn);
    }
}
