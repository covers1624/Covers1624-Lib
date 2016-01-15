package covers1624.lib.repack.codechicken.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import com.google.common.collect.ImmutableList;

import covers1624.lib.util.LogHelper;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.relauncher.CoreModManager;

public class ClassDiscoverer {
	public IStringMatcher matcher;
	public String[] superclasses;
	public ArrayList<Class<?>> classes;
	public ModClassLoader modClassLoader;

	public ClassDiscoverer(IStringMatcher matcher, Class<?>... superclasses) {
		this.matcher = matcher;
		this.superclasses = new String[superclasses.length];
		for (int i = 0; i < superclasses.length; i++) {
			this.superclasses[i] = superclasses[i].getName().replace('.', '/');
		}

		classes = new ArrayList<Class<?>>();
		modClassLoader = (ModClassLoader) Loader.instance().getModClassLoader();
	}

	public ClassDiscoverer(Class<?>... superclasses) {
		this(new IStringMatcher() {
			public boolean matches(String test) {
				return true;
			}
		}, superclasses);
	}

	public ArrayList<Class<?>> findClasses() {
		try {
			findClasspathMods();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return classes;
	}

	private void checkAddClass(String resource) {
		try {
			String classname = resource.replace(".class", "").replace("\\", ".").replace("/", ".");
			byte[] bytes = Launch.classLoader.getClassBytes(classname);
			if (bytes == null) {
				return;
			}

			ClassNode cnode = new ClassNode();
			ClassReader reader = new ClassReader(bytes);
			reader.accept(cnode, 0);
			for (String superclass : superclasses) {
				if (!cnode.interfaces.contains(superclass) && !cnode.superName.equals(superclass)) {
					return;
				}
			}

			addClass(classname);
		} catch (IOException e) {
			LogHelper.error("Unable to load class: " + resource, e);
		}
	}

	private void addClass(String classname) {
		try {
			Class<?> class1 = Class.forName(classname, true, modClassLoader);
			classes.add(class1);
		} catch (Throwable t) {
			LogHelper.error("Unable to load class: " + classname, t);
		}
	}

	private void findClasspathMods() {
		List<String> knownLibraries = ImmutableList.<String>builder().addAll(modClassLoader.getDefaultLibraries()).addAll(CoreModManager.getReparseableCoremods()).build();
		File[] minecraftSources = modClassLoader.getParentSources();
		HashSet<String> searchedSources = new HashSet<String>();
		for (File minecraftSource : minecraftSources) {
			if (searchedSources.contains(minecraftSource.getAbsolutePath())) {
				continue;
			}
			searchedSources.add(minecraftSource.getAbsolutePath());

			if (minecraftSource.isFile()) {
				if (!knownLibraries.contains(minecraftSource.getName())) {
					FMLLog.fine("Found a minecraft related file at %s, examining for RedThings Module classes", minecraftSource.getAbsolutePath());
					try {
						readFromZipFile(minecraftSource);
					} catch (Exception e) {
						LogHelper.error("Failed to scan " + minecraftSource.getAbsolutePath() + ", the zip file is invalid", e);
					}
				}
			} else if (minecraftSource.isDirectory()) {
				FMLLog.fine("Found a minecraft related directory at %s, examining for RedThings Module classes", minecraftSource.getAbsolutePath());
				readFromDirectory(minecraftSource, minecraftSource);
			}
		}
	}

	private void readFromZipFile(File file) throws IOException {
		FileInputStream fileinputstream = new FileInputStream(file);
		ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
		do {
			ZipEntry zipentry = zipinputstream.getNextEntry();
			if (zipentry == null) {
				break;
			}
			String fullname = zipentry.getName().replace('\\', '/');
			int pos = fullname.lastIndexOf('/');
			String name = pos == -1 ? fullname : fullname.substring(pos + 1);
			if (!zipentry.isDirectory() && matcher.matches(name)) {
				checkAddClass(fullname);
			}
		} while (true);
		fileinputstream.close();
	}

	private void readFromDirectory(File directory, File basedirectory) {
		for (File child : directory.listFiles()) {
			if (child.isDirectory()) {
				readFromDirectory(child, basedirectory);
			} else if (child.isFile() && matcher.matches(child.getName())) {
				String fullname = getRelativePath(basedirectory, child);
				checkAddClass(fullname);
			}
		}
	}

	public static String getRelativePath(File parent, File child) {
		if (parent.isFile() || !child.getPath().startsWith(parent.getPath())) {
			return null;
		}
		return child.getPath().substring(parent.getPath().length() + 1);
	}
}