package covers1624.lib.api.tile;

import net.minecraft.util.EnumFacing;

public interface IRotatableTile extends IPlacingTypeTile {

    void doRotation(boolean shift);

    EnumFacing getRotation();

    void setRotation(EnumFacing facing);

}
