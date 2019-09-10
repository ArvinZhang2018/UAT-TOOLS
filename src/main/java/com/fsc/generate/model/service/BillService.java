package com.fsc.generate.model.service;

import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.db.ddo.BizAccount;
import com.fsc.generate.model.db.ddo.BizBillUser;
import com.fsc.generate.model.db.factory.AccountSessionFactory;
import com.fsc.generate.model.db.factory.BillSessionFactory;
import com.fsc.generate.model.dto.biz.BasicBizRequest;
import common.config.tools.config.ConfigTools3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BillService {

    @Autowired
    BillSessionFactory sessionFactory;

    public void updateBillUserPlan(String uid, Integer plan) throws CrmException {
        sessionFactory.executeSql(BizBillUser.class, BizBillUser.UPDATE_BILL_USER_PLAN, plan, uid);
    }

    public void deleteBillUserByEmail(String email) throws CrmException {
        sessionFactory.executeSql(BizBillUser.class, BizBillUser.DELETE_USER_BY_EMAIL, email);
    }

    public List<BizBillUser> queryLastOneInvoice() throws CrmException {
        return sessionFactory.executeQuerySql(BizBillUser.class, BizBillUser.QUERY_LAST_ONE_INVOICE);
    }

    public List<BizBillUser> queryInvoiceById(Long iid) throws CrmException {
        return sessionFactory.executeQuerySql(BizBillUser.class, BizBillUser.QUERY_INVOICE_BY_ID, iid);
    }

}
