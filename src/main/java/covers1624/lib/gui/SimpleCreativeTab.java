package covers1624.lib.gui;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by covers1624 on 2/23/2016.
 */
public class SimpleCreativeTab extends CreativeTabs {

    private ItemStack tabIcon;
    //Uses GameRegistry.findItem / block to craft tab icon.
    private final String name;
    private final int meta;
    private final boolean isBlock;

    public SimpleCreativeTab(String tabLabel, String tabIcon, boolean isBlock) {
        this(tabLabel, tabIcon, 0, isBlock);
    }

    public SimpleCreativeTab(String tabLabel, String name, int meta, boolean isBlock) {
        super(tabLabel);
        this.name = name;
        this.meta = meta;
        this.isBlock = isBlock;
    }

    public void craftIcon() {
        ResourceLocation location = new ResourceLocation(name);
        if (isBlock) {
            Block block = GameRegistry.findBlock(location.getResourceDomain(), location.getResourcePath());
            if (block == null) {
                throw new RuntimeException(String.format("Unable to find [%s] in GameRegistry. Unable to craft tab icon!", name));
            }
            tabIcon = new ItemStack(block, 1, meta);
        } else {
            Item item = GameRegistry.findItem(location.getResourceDomain(), location.getResourcePath());
            if (item == null) {
                throw new RuntimeException(String.format("Unable to find [%s] in GameRegistry. Unable to craft tab icon!", name));
            }
            tabIcon = new ItemStack(item, 1, meta);
        }
    }

    @Override
    public Item getTabIconItem() {
        return Items.redstone;
    }

    @Override
    public ItemStack getIconItemStack() {
        return tabIcon;
    }
}
