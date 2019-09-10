package com.fsc.generate.utils;

import java.util.EnumSet;

/**
 * Created by Frank.Huang on 2016/7/14.
 */
public class UserPropTools {

    public static EnumSet<EUserProps> userPropSet(long userProp){
        EnumSet<EUserProps> userPropSet = EnumSet.noneOf(EUserProps.class);

        for (EUserProps prop : EUserProps.values()){
            if ((userProp & prop.getValue()) == prop.getValue()){
                userPropSet.add(prop);
            }
        }

        return userPropSet;
    }

    public static boolean isPropertySet(long property, EUserProps props){
        if ((property & props.getValue()) == props.getValue()){
            return true;
        }

        return false;
    }
}
