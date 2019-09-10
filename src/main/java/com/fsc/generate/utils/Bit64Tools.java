package com.fsc.generate.utils;

/**
 * Created by Frank.Huang on 2016/8/16.
 */
public class Bit64Tools {

    private Bit64Tools() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isSet(long v,long flag){
        return (v & flag) == flag;

    }

    public static long setBit(long v ,long flag){
        return (v|flag);
    }

    public static long clsBit(long v ,long flag){
        return (v & (~flag));
    }

}
