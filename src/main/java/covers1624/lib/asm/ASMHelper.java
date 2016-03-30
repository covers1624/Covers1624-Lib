package covers1624.lib.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * Created by covers1624 on 3/30/2016.
 */
public class ASMHelper {

    public static ClassNode createClassNode(byte[] bytes) {
        return createClassNode(bytes, 0);
    }

    public static ClassNode createClassNode(byte[] bytes, int flags) {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(cnode, flags);
        return cnode;
    }
}
