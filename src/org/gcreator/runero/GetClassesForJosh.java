package org.gcreator.runero;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.*;
import java.util.*;

public class GetClassesForJosh {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            for (Class<?> c : getClasses("org.gcreator.runero") ) {
                System.out.println("class " + c.getName());
                for (Method m : c.getMethods()) {
                    int mod = m.getModifiers();
                      
                    if (!Modifier.isPublic(mod) || isNative(m.getName()))
                        continue;
                    System.out.print(m.getReturnType().getName());
                    System.out.print(" function " + m.getName());
                    System.out.print("(");
                    for (Class<?> param : m.getParameterTypes() ) {
                        System.out.print(" " + param.getName());
                    }
                    System.out.print(")");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
    
    private static boolean isNative(String s ) {
        if ( s.equals("wait") || s.equals("toString") || s.equals("hashCode") || 
                s.equals("getClass") || s.equals("notify") || s.equals("notifyAll")
                || s.equals("equals"))
            return true;
        return false;
    }
}
