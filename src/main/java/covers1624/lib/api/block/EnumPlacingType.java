package covers1624.lib.api.block;

import net.minecraft.util.IStringSerializable;

/**
 * Created by covers1624 on 1/21/2016.
 */
public enum EnumPlacingType implements IStringSerializable {

    HORIZONTAL,
    ALL,
    NONE;

    @Override
    public String getName() {
        return this.name().toLowerCase();
    }
}
