package covers1624.lib.proxy;

import covers1624.lib.client.render.TempISBRH;
import covers1624.lib.block.TempBlock;
import covers1624.lib.client.RenderDispatcher;
import covers1624.lib.client.registry.RenderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by covers1624 on 1/14/2016.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        //replaceRendererDispatcher();
        //RenderRegistry.registerRenderingHandler(TempBlock.type, new TempISBRH());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    private void replaceRendererDispatcher() {
        if (Minecraft.getMinecraft().blockRenderDispatcher != null) {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.blockRenderDispatcher = new RenderDispatcher(minecraft.blockRenderDispatcher, minecraft.modelManager.getBlockModelShapes(), Minecraft.getMinecraft().blockColors);
        }
    }

}
