package covers1624.lib.classutils;

import covers1624.lib.asm.ASMHelper;
import covers1624.lib.util.LogHelper;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.ModContainer;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        modClassLoader = Loader.instance().getModClassLoader();
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

            ClassNode cnode = ASMHelper.createClassNode(bytes);
            for (String superclass : superclasses) {
                if (!cnode.interfaces.contains(superclass) && !cnode.superName.equals(superclass)) {
                    return;
                }
            }

            addClass(classname);
        } catch (IOException e) {
            LogHelper.error("Unable to load class: " + resource);
            e.printStackTrace();
        }
    }

    private void addClass(String classname) {
        try {
            Class<?> class1 = Class.forName(classname, true, modClassLoader);
            classes.add(class1);
        } catch (Throwable t) {
            LogHelper.error("Unable to load class: " + classname);
            t.printStackTrace();
        }
    }

    private void findClasspathMods() {
        List<ModContainer> mods = Loader.instance().getActiveModList();
        HashSet<String> searched = new HashSet<String>();
        for (ModContainer mod : mods) {
            File source = mod.getSource();
            if (source == null || searched.contains(source.getAbsolutePath())) {
                continue;
            }
            searched.add(source.getAbsolutePath());

            if (source.isFile()) {
                LogHelper.debug("Found a mod container %s, examining for covers1624 classes", source.getAbsolutePath());
                try {
                    readFromZipFile(source);
                } catch (Exception e) {
                    LogHelper.error("Failed to scan " + source.getAbsolutePath() + ", the zip file is invalid");
                    e.printStackTrace();
                }
            } else if (source.isDirectory()) {
                LogHelper.debug("Found a minecraft related directory at %s, examining for covers1624 classes", source.getAbsolutePath());
                readFromDirectory(source, source);
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
