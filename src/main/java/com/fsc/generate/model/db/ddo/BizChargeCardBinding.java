package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmExecuteSql;

public class BizChargeCardBinding {

    public static final String DELETE_BY_MAC = "deleteByMac";

    @CrmExecuteSql("delete from charge_card_binding where biz_sys_uid = ?")
    public void deleteByMac(){
    }

}
