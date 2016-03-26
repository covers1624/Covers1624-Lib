package covers1624.lib.api.block.property;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import covers1624.lib.api.block.EnumPlacingType;
import net.minecraft.block.properties.PropertyEnum;

import java.util.Collection;

/**
 * Created by covers1624 on 1/21/2016.
 */
public class PropertyEnumPlacing extends PropertyEnum<EnumPlacingType> {

    protected PropertyEnumPlacing(String name, Collection<EnumPlacingType> allowedValues) {
        super(name, EnumPlacingType.class, allowedValues);
    }

    public static PropertyEnumPlacing create(String name) {
        return new PropertyEnumPlacing(name, Collections2.filter(Lists.newArrayList(EnumPlacingType.values()), Predicates.<EnumPlacingType>alwaysTrue()));
    }

}
