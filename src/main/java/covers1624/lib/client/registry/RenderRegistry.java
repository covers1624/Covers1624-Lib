package covers1624.lib.client.registry;

import covers1624.lib.api.client.render.ISimpleBlockRenderingHandler;
import covers1624.lib.util.LogHelper;
import net.minecraft.util.EnumBlockRenderType;

import java.util.HashMap;

/**
 * Created by covers1624 on 3/18/2016.
 */
public class RenderRegistry {

    private static HashMap<EnumBlockRenderType, ISimpleBlockRenderingHandler> handlerMap = new HashMap<EnumBlockRenderType, ISimpleBlockRenderingHandler>();

    public static boolean canHandleRenderType(EnumBlockRenderType renderType) {
        return handlerMap.containsKey(renderType);
    }

    public static ISimpleBlockRenderingHandler getISBRHFromRenderType(EnumBlockRenderType renderType) {
        if (canHandleRenderType(renderType)) {
            ISimpleBlockRenderingHandler handler = handlerMap.get(renderType);
            if (handler != null) {
                return handler;
            }
        } throw new RuntimeException("Unable to handle render type! Call canHandleRenderType first!");
    }

    public static void registerRenderingHandler(EnumBlockRenderType renderType, ISimpleBlockRenderingHandler renderingHandler){
        if (handlerMap.containsKey(renderType)){
            LogHelper.bigError("RenderType already registered. [%s]", renderingHandler.toString());
            return;
        }
        handlerMap.put(renderType, renderingHandler);
    }

}
