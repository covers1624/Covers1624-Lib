package covers1624.lib.api.block.property;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * Created by covers1624 on 2/21/2016.
 */
public class PropertyBoolUnlisted implements IUnlistedProperty<Boolean> {
    private final String name;
    private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(true, false);

    public PropertyBoolUnlisted(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(Boolean value) {
        return value != null && allowedValues.contains(value);
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public String valueToString(Boolean value) {
        return Objects.toStringHelper(this).add("name", this.name).add("clazz", Boolean.class).add("values", allowedValues).toString();
    }
}
