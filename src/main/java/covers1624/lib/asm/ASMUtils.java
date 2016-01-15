package covers1624.lib.asm;

import covers1624.lib.repack.codechicken.lib.asm.InsnListSection;
import covers1624.lib.util.LogHelper;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by covers1624 on 10/24/2015.
 * TODO Move to lib.
 */
public class ASMUtils {

	public static final boolean obfuscated;

	static {
		boolean obf = true;
		try {
			obf = Launch.classLoader.getClassBytes("net.minecraft.world.World") == null;
		} catch (IOException ignored) {
		}
		obfuscated = obf;
	}

	public static void printMethods(ClassNode classNode) {
		for (MethodNode node : classNode.methods) {
			String parmString = buildParmString(node);
			LogHelper.info("ACC: %s, Name: %s, Desc: %s, Parms: %s", node.access, node.name, node.desc, parmString);
		}
	}

	private static String buildParmString(MethodNode node) {
		String parms = null;
		if (node.parameters != null && !node.parameters.isEmpty()) {
			parms = "";
			for (ParameterNode parmNode : node.parameters) {
				parms = parms + parmNode.name + " ";
			}
		}
		return parms != null ? parms : "None";
	}

	public static void printMethodNodeInstructions(MethodNode methodNode) {
		printInsnList(methodNode.instructions);
	}

	public static void printInsnList(InsnList list) {
		for (int i = 0; i < list.size(); i++) {
			AbstractInsnNode node = list.get(i);
			if (node instanceof LabelNode) {
				LabelNode labelNode = (LabelNode) node;
				LogHelper.info("LabelNode: Opcode: %s Type: %s", labelNode.getOpcode(), labelNode.getType());
				continue;
			}
			if (node instanceof LineNumberNode) {
				LineNumberNode numberNode = (LineNumberNode) node;
				LogHelper.info("LineNumberNode: Opcode: %s Line: %s", numberNode.getOpcode(), numberNode.line);
				continue;
			}
			if (node instanceof VarInsnNode) {
				VarInsnNode insnNode = (VarInsnNode) node;
				LogHelper.info("VarInsnNode: Opcode: %s Var: ", insnNode.getOpcode(), insnNode.var);
				continue;
			}
			if (node instanceof FieldInsnNode) {
				FieldInsnNode fieldInsnNode = (FieldInsnNode) node;
				LogHelper.info("FieldInsnNode: Opcode: Name: %s Desc: %s", fieldInsnNode.getOpcode(), fieldInsnNode.name, fieldInsnNode.desc);
				continue;
			}
			if (node instanceof MethodInsnNode) {
				MethodInsnNode methodInsnNode = (MethodInsnNode) node;
				LogHelper.info("MethodInsnNode: Opcode: %s Name: %s", methodInsnNode.getOpcode(), methodInsnNode.name);
				continue;
			}
			if (node instanceof InsnNode) {
				InsnNode insnNode = (InsnNode) node;
				LogHelper.info("InsnNode: Opcode: %s", insnNode.getOpcode());
				continue;
			}
			LogHelper.info(node);
		}
	}

	public static String toString(InsnList list) {
		return new InsnListSection(list).toString();
	}

	public static MethodNode findMethodNodeOfClass(ClassNode classNode, String methodName, String methodDesc) {
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(methodName) && (methodDesc == null || method.desc.equals(methodDesc))) {
				return method;
			}
		}
		return null;
	}

	public static void writeClassToFile(ClassWriter classWriter, String name) {
		try {
			File classFolder = new File(".", "tempClasses");
			if (!classFolder.exists()) {
				classFolder.mkdir();
			}
			File classFile = new File(classFolder, name + ".class");
			FileOutputStream outputStream = new FileOutputStream(classFile);
			outputStream.write(classWriter.toByteArray());
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
