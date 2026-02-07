package kz.aitu.endtermapi.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtils {

    private ReflectionUtils() {}

    public static void printClassInfo(Class<?> cls) {
        System.out.println("Class: " + cls.getName());

        System.out.println("-- Fields --");
        for (Field f : cls.getDeclaredFields()) {
            System.out.println(f.getType().getSimpleName() + " " + f.getName());
        }

        System.out.println("-- Methods --");
        for (Method m : cls.getDeclaredMethods()) {
            System.out.println(m.getReturnType().getSimpleName() + " " + m.getName() + "()");
        }
    }
}