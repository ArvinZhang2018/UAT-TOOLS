package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;

public class BizBillUser {

    @CrmColumn("uid")
    private long uid;

    public static final String UPDATE_BILL_USER_PLAN = "updateBillUserPlan";
    public static final String QUERY_LAST_ONE_INVOICE = "queryLastOneInvoice";
    public static final String QUERY_INVOICE_BY_ID = "queryInvoiceById";
    public static final String DELETE_USER_BY_EMAIL = "deleteUserByEmail";


    @CrmExecuteSql("update bill_user set current_pid = ? where uid = ?")
    public void updateBillUserPlan(){
    }
    @CrmExecuteSql("select * from bill_invoice order by iid desc limit 1")
    public void queryLastOneInvoice(){
    }
    @CrmExecuteSql("select * from bill_invoice where iid = ?")
    public void queryInvoiceById(){
    }

    @CrmExecuteSql("delete from bill_user where email = ?")
    public void deleteUserByEmail(){
    }
}
