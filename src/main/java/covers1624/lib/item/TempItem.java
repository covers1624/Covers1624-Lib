package covers1624.lib.item;

import covers1624.lib.api.texture.ITextureRegistry;
import covers1624.lib.api.texture.Icon;
import covers1624.lib.api.texture.provider.IItemTextureProvider;
import covers1624.lib.api.texture.transforms.ICustomTransform;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

/**
 * Created by covers1624 on 1/21/2016.
 */
public class TempItem extends ItemSword implements IItemTextureProvider, ICustomTransform {
    private Icon icon;

    public TempItem() {
        super(ToolMaterial.IRON);
        setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public void registerIcons(ITextureRegistry textureRegistry) {
        icon = textureRegistry.registerIcon("covers1624lib:tempSword");
    }

    public Icon getIcon(int meta) {
        return icon;
    }

    @Override
    public boolean isFull3D() {
        return false;
    }

    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return false;
    }
}
