package com.fsc.generate.utils;

import java.lang.reflect.Method;

public class ReflectTools {

    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static Method getMethod(String methodName, Class interfaceType) {
        Method[] methods = interfaceType.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methodName.equals(methods[i].getName())) {
                return methods[i];
            }
        }
        return null;
    }
}
