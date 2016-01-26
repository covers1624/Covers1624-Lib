package covers1624.lib.api.block;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.properties.PropertyHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by covers1624 on 1/16/2016.
 */
public class PropertyString extends PropertyHelper<String> {

	private final HashSet<String> valuesSet = new HashSet<String>();

	public PropertyString(String name, String... values) {
		super(name, String.class);
		Collections.addAll(valuesSet, values);
	}

	@Override
	public Collection<String> getAllowedValues() {
		return ImmutableSet.copyOf(valuesSet);
	}

	@Override
	public String getName(String value) {
		return value;
	}
}
