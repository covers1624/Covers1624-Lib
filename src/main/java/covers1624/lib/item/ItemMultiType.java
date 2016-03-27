package covers1624.lib.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by covers1624 on 3/27/2016.
 */
public class ItemMultiType extends Item {

    private HashMap<Integer, String> names = new HashMap<Integer, String>();
    public final String name;
    private int nextVariant = 0;
    @SideOnly(Side.CLIENT)
    private boolean hasRegistered = false;

    /**
     * @param name Must be prefixed with "<MODID>:"
     */
    public ItemMultiType(CreativeTabs tab, String name) {
        this.name = name;
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(tab);
        setUnlocalizedName(name);
        setMaxStackSize(64);
    }

    public ItemStack registerSubItem(String name) {

        names.put(nextVariant, name);
        ItemStack stack = new ItemStack(this, 1, nextVariant);
        nextVariant++;
        return stack;
    }

    @SideOnly(Side.CLIENT)
    public void registerModelVariants() {
        if (!hasRegistered) {
            for (Map.Entry<Integer, String> entry : names.entrySet()) {
                ModelResourceLocation location = new ModelResourceLocation(name, "type=" + entry.getValue().toLowerCase());
                ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), location);
            }
            hasRegistered = true;
        }
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (Map.Entry<Integer, String> entry : names.entrySet()) {
            subItems.add(new ItemStack(itemIn, 1, entry.getKey()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        return "item." + names.get(meta);
    }
}
