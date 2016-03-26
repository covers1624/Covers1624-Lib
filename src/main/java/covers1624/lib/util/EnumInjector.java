package covers1624.lib.util;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.classloading.FMLForgePlugin;
import net.minecraftforge.fml.common.EnhancedRuntimeException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by covers1624 on 3/25/2016.
 * Carbon copy of net.minecraftforge.common.util.EnumHelper
 * Used to add things to enums that forge doesn't have support for yet.
 */
public class EnumInjector {

    private static Object reflectionFactory = null;
    private static Method newConstructorAccessor = null;
    private static Method newInstance = null;
    private static Method newFieldAccessor = null;
    private static Method fieldAccessorSet = null;
    private static boolean isSetup = false;

    private static Class[][] commonTypes = {//
            { EnumBlockRenderType.class }//
    };

    private static void setup() {
        if (isSetup) {
            return;
        }

        try {
            Method getReflectionFactory = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("getReflectionFactory");
            reflectionFactory = getReflectionFactory.invoke(null);
            newConstructorAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newConstructorAccessor", Constructor.class);
            newInstance = Class.forName("sun.reflect.ConstructorAccessor").getDeclaredMethod("newInstance", Object[].class);
            newFieldAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newFieldAccessor", Field.class, boolean.class);
            fieldAccessorSet = Class.forName("sun.reflect.FieldAccessor").getDeclaredMethod("set", Object.class, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        isSetup = true;
    }

    public static EnumBlockRenderType addRenderType(String name) {
        return addEnum(EnumBlockRenderType.class, name);
    }

    public static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Object... paramValues) {
        setup();
        return addEnum(commonTypes, enumType, enumName, paramValues);
    }

    public static <T extends Enum<?>> T addEnum(Class[][] map, Class<T> enumType, String enumName, Object... paramValues) {
        for (Class[] lookup : map) {
            if (lookup[0] == enumType) {
                Class<?>[] paramTypes = new Class<?>[lookup.length - 1];
                if (paramTypes.length > 0) {
                    System.arraycopy(lookup, 1, paramTypes, 0, paramTypes.length);
                }
                return addEnum(enumType, enumName, paramTypes, paramValues);
            }
        }
        return null;
    }

    public static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Class<?>[] paramTypes, Object... paramValues) {
        return addEnum(false, enumType, enumName, paramTypes, paramValues);
    }

    private static <T extends Enum<?>> T addEnum(boolean test, final Class<T> enumType, String enumName, final Class<?>[] paramTypes, Object[] paramValues) {
        if (!isSetup) {
            setup();
        }

        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            if (name.equals("$VALUES") || name.equals("ENUM$VALUES")) //Added 'ENUM$VALUES' because Eclipse's internal compiler doesn't follow standards
            {
                valuesField = field;
                break;
            }
        }

        int flags = (FMLForgePlugin.RUNTIME_DEOBF ? Modifier.PUBLIC : Modifier.PRIVATE) | Modifier.STATIC | Modifier.FINAL | 0x1000 /*SYNTHETIC*/;
        if (valuesField == null) {
            String valueType = String.format("[L%s;", enumType.getName().replace('.', '/'));

            for (Field field : fields) {
                if ((field.getModifiers() & flags) == flags && field.getType().getName().replace('.', '/').equals(valueType)) //Apparently some JVMs return .'s and some don't..
                {
                    valuesField = field;
                    break;
                }
            }
        }

        if (valuesField == null) {
            final List<String> lines = Lists.newArrayList();
            lines.add(String.format("Could not find $VALUES field for enum: %s", enumType.getName()));
            lines.add(String.format("Runtime Deobf: %s", FMLForgePlugin.RUNTIME_DEOBF));
            lines.add(String.format("Flags: %s", String.format("%16s", Integer.toBinaryString(flags)).replace(' ', '0')));
            lines.add("Fields:");
            for (Field field : fields) {
                String mods = String.format("%16s", Integer.toBinaryString(field.getModifiers())).replace(' ', '0');
                lines.add(String.format("       %s %s: %s", mods, field.getName(), field.getType().getName()));
            }

            for (String line : lines) {
                LogHelper.fatal(line);
            }

            if (test) {
                throw new EnhancedRuntimeException("Could not find $VALUES field for enum: " + enumType.getName()) {
                    @Override
                    protected void printStackTrace(WrappedPrintStream stream) {
                        for (String line : lines) {
                            stream.println(line);
                        }
                    }
                };
            }
            return null;
        }

        if (test) {
            Object ctr = null;
            Exception ex = null;
            try {
                ctr = getConstructorAccessor(enumType, paramTypes);
            } catch (Exception e) {
                ex = e;
            }
            if (ctr == null || ex != null) {
                throw new EnhancedRuntimeException(String.format("Could not find constructor for Enum %s", enumType.getName()), ex) {
                    private String toString(Class<?>[] cls) {
                        StringBuilder b = new StringBuilder();
                        for (int x = 0; x < cls.length; x++) {
                            b.append(cls[x].getName());
                            if (x != cls.length - 1) {
                                b.append(", ");
                            }
                        }
                        return b.toString();
                    }

                    @Override
                    protected void printStackTrace(WrappedPrintStream stream) {
                        stream.println("Target Arguments:");
                        stream.println("    java.lang.String, int, " + toString(paramTypes));
                        stream.println("Found Constructors:");
                        for (Constructor<?> ctr : enumType.getDeclaredConstructors()) {
                            stream.println("    " + toString(ctr.getParameterTypes()));
                        }
                    }
                };
            }
            return null;
        }

        valuesField.setAccessible(true);

        try {
            T[] previousValues = (T[]) valuesField.get(enumType);
            List<T> values = new ArrayList<T>(Arrays.asList(previousValues));
            T newValue = makeEnum(enumType, enumName, values.size(), paramTypes, paramValues);
            values.add(newValue);
            setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));
            cleanEnumCache(enumType);

            return newValue;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Object getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes) throws Exception {
        Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
        return newConstructorAccessor.invoke(reflectionFactory, enumClass.getDeclaredConstructor(parameterTypes));
    }

    private static <T extends Enum<?>> T makeEnum(Class<T> enumClass, String value, int ordinal, Class<?>[] additionalTypes, Object[] additionalValues) throws Exception {
        Object[] parms = new Object[additionalValues.length + 2];
        parms[0] = value;
        parms[1] = ordinal;
        System.arraycopy(additionalValues, 0, parms, 2, additionalValues.length);
        return enumClass.cast(newInstance.invoke(getConstructorAccessor(enumClass, additionalTypes), new Object[] { parms }));
    }

    public static void setFailsafeFieldValue(Field field, Object target, Object value) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        Object fieldAccessor = newFieldAccessor.invoke(reflectionFactory, field, false);
        fieldAccessorSet.invoke(fieldAccessor, target, value);
    }

    private static void blankField(Class<?> enumClass, String fieldName) throws Exception {
        for (Field field : Class.class.getDeclaredFields()) {
            if (field.getName().contains(fieldName)) {
                field.setAccessible(true);
                setFailsafeFieldValue(field, enumClass, null);
                break;
            }
        }
    }

    private static void cleanEnumCache(Class<?> enumClass) throws Exception {
        blankField(enumClass, "enumConstantDirectory");
        blankField(enumClass, "enumConstants");
    }
}
