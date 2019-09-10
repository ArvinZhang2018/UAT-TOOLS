package com.fsc.generate.utils;

/**
 * File        :
 * Description :
 * Author      : Frank
 * Created     : 2015/10/4 23:58
 */
public enum EUserProps {
    //用户属性(32位,从低位往高位)
    PLAY_ALLOWED(BIT_MASK(0)), //播放基本服务(0)
    TEST(BIT_MASK(1)), //测试用户
    STANDALONE(BIT_MASK(2)), //独立部署APP
    LOCATION_UNLIMITED(BIT_MASK(3)), //不限制位置信息
    LOCATION_ILLEGAL(BIT_MASK(4)),//非法的登录位置
    CONTENTS_AUTO_ADD(BIT_MASK(5)),//自动添加网盘
    CONTENTS_ADDED(BIT_MASK(6)),//已经自动添加过网盘
    FREE_TO_MEMBERSHIP(BIT_MASK(7)),//该设备绑定的账号从free trial套餐转变为membership套餐
    MIGRATED_DATA(BIT_MASK(8)),//该设备是否已经迁移过数据
    ;


    EUserProps(long value) {
        this.value = value;
    }

    private long value;


    public long getValue() {
        return value;
    }

//    public static boolean included(Long props){
//        return UserPropImmutableTools.isPropertySet(props, )
//    }

    private static long BIT_MASK(int bit) {
        return 1L << bit;
    }
}
