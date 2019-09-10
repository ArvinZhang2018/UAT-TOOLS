package com.fsc.generate.model.service;

import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.db.ddo.BizAccount;
import com.fsc.generate.model.db.ddo.BizSession;
import com.fsc.generate.model.db.ddo.BizVmsUser;
import com.fsc.generate.model.db.factory.AccountSessionFactory;
import com.fsc.generate.model.db.factory.VmsUserSessionFactory;
import com.fsc.generate.model.dto.biz.BasicBizRequest;
import com.fsc.generate.model.dto.biz.UpdateBizLoginReq;
import com.fsc.generate.utils.StringUtils;
import common.config.tools.config.ConfigTools3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    @Autowired
    AccountSessionFactory sessionFactory;

    public BizAccount queryAccountByUid(String uid) throws CrmException {
        List<BizAccount> accounts = sessionFactory.executeQuerySql(BizAccount.class, BizAccount.QUERY_ACCOUNT_BY_UID, uid);
        if (Objects.nonNull(accounts)
                && accounts.size() == 1) {
            return accounts.get(0);
        }
        return null;
    }

    public BizAccount queryAccountByEmail(String email) throws CrmException {
        List<BizAccount> accounts = sessionFactory.executeQuerySql(BizAccount.class, BizAccount.QUERY_ACCOUNT_BY_EMAIL, email);
        if (Objects.nonNull(accounts)
                && accounts.size() == 1) {
            return accounts.get(0);
        }
        return null;
    }

    public BizAccount queryAccountByAccountCard(String accountCard) throws CrmException {
        List<BizAccount> accounts = sessionFactory.executeQuerySql(BizAccount.class,
                BizAccount.QUERY_ACCOUNT_BY_ACCOUNT_CARD, accountCard);
        if (Objects.nonNull(accounts)
                && accounts.size() == 1) {
            return accounts.get(0);
        }
        return null;
    }

    public boolean isUid(String param) {
        return StringUtils.isInteger(param) && param.length() < 10;
    }

    public boolean isAccountCard(String param) {
        return StringUtils.isInteger(param) && !isUid(param);
    }

    public boolean isEmail(String param) {
        return !StringUtils.isInteger(param);
    }

    public BizAccount queryAccount(String param) throws CrmException {
        if (isEmail(param)) {
            return queryAccountByEmail(param);
        } else if (isAccountCard(param)) {
            return queryAccountByAccountCard(param);
        } else {
            return queryAccountByUid(param);
        }
    }

    public BizAccount queryAccountBiz(long uid) throws CrmException {
        List<BizAccount> accounts = sessionFactory.executeQuerySql(BizAccount.class, BizAccount.QUERY_ACCOUNT_BIZ, uid);
        if (Objects.nonNull(accounts)
                && accounts.size() == 1) {
            return accounts.get(0);
        }
        return null;
    }

    public List<BizAccount> queryAccountDevice(String did) throws CrmException {
        return sessionFactory.executeQuerySql(BizAccount.class, BizAccount.QUERY_ACCOUNT_DEVICE, did);
    }

    public void updateAccountBiz(long uid, long restTs) throws CrmException {
        sessionFactory.executeSql(BizAccount.class, BizAccount.UPDATE_ACCOUNT_BIZ, restTs, uid);
    }

    public void updateAccountAVABiz(long uid, long avaTs) throws CrmException {
        sessionFactory.executeSql(BizAccount.class, BizAccount.UPDATE_ACCOUNT_AVA_BIZ, avaTs, uid);
    }

    public void activateEmailAccount(String email) throws CrmException {
        sessionFactory.executeSql(BizAccount.class, BizAccount.ACTIVATE_ACCOUNT_BY_EMAIL, email);
    }

    public void updateAccountPlan(long uid, int plan, String planName, int deviceLimit) throws CrmException {
        sessionFactory.executeSql(BizAccount.class, BizAccount.UPDATE_ACCOUNT_PLAN, deviceLimit, planName, plan, uid);
    }

    public void deleteAccount(long uid) throws CrmException {
        sessionFactory.executeSql(BizAccount.class, BizAccount.DELETE_ACCOUNT_BY_UID, uid);
    }


}
