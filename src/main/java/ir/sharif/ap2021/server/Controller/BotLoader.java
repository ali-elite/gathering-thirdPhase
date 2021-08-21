package ir.sharif.ap2021.server.Controller;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class BotLoader {
    public void loadBot(String gameName
            ,String jarUrl
            ,String className
    ) throws
            MalformedURLException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        URL url = new URL(jarUrl);
        URL[] urls = new URL[]{url};
        ClassLoader loader = new URLClassLoader(urls);
        Class<?> target = loader.loadClass(className);
        Method method = target.getDeclaredMethod("getGenerator");
        method.setAccessible(true);
    }
}