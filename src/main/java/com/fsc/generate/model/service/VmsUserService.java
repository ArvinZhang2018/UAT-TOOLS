package com.fsc.generate.model.service;

import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.db.ddo.BizSession;
import com.fsc.generate.model.db.ddo.BizVmsUser;
import com.fsc.generate.model.db.factory.CcSessionFactory;
import com.fsc.generate.model.db.factory.VmsUserSessionFactory;
import com.fsc.generate.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VmsUserService {

    @Autowired
    VmsUserSessionFactory sessionFactory;

    public BizVmsUser queryUserV2(String macOrUid) throws CrmException {
        List<BizVmsUser> bizVmsUsers;
        if (StringUtils.isInteger(macOrUid)) {
            bizVmsUsers = queryUserByUid(Long.valueOf(macOrUid));
        } else {
            bizVmsUsers = queryUserByMac(macOrUid);
        }
        if (Objects.nonNull(bizVmsUsers)
                && bizVmsUsers.size() == 1) {
            return bizVmsUsers.get(0);
        }
        return null;
    }

    public BizVmsUser queryUser(String macOrUid) throws CrmException {
        List<BizVmsUser> bizVmsUsers;
        if (StringUtils.isInteger(macOrUid)) {
            bizVmsUsers = queryUserByUid(Long.valueOf(macOrUid));
        } else {
            bizVmsUsers = queryUserByMac(macOrUid);
        }
        if (Objects.nonNull(bizVmsUsers)
                && bizVmsUsers.size() == 1) {
            return bizVmsUsers.get(0);
        }
        throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_VMS_USER_NOT_FOUND);
    }

    public List<BizVmsUser> queryMigratedTask(long uid) throws CrmException {
        return sessionFactory.executeQuerySql(BizVmsUser.class, BizVmsUser.QUERY_MIGRATED_TASK, "%\"targetAccountId\":" + uid + "%");
    }

    public List<BizVmsUser> queryUserByUid(long uid) throws CrmException {
        return sessionFactory.executeQuerySql(BizVmsUser.class, BizVmsUser.QUERY_BY_UID, uid);
    }

    public List<BizVmsUser> queryUserByMac(String mac) throws CrmException {
        return sessionFactory.executeQuerySql(BizVmsUser.class, BizVmsUser.QUERY_BY_MAC, mac);
    }

    public void deleteByMac(String mac) throws CrmException {
        sessionFactory.executeSql(BizVmsUser.class, BizVmsUser.DELETE_BY_MAC, mac);
    }

    public List<BizVmsUser> queryBiz(long uid) throws CrmException {
        return sessionFactory.executeQuerySql(BizVmsUser.class, BizVmsUser.QUERY_BIZ, uid);
    }

    public void updateBiz(long uid, long restTs) throws CrmException {
        sessionFactory.executeSql(BizVmsUser.class, BizVmsUser.UPDATE_BIZ, restTs, uid);
    }

    public void updateToInnerTestGroup(long uid) throws CrmException {
        sessionFactory.executeSql(BizVmsUser.class, BizVmsUser.UPDATE_TO_INNER_TEST_GROUP, uid);
    }

    public List<BizSession> querySession(long uid) throws CrmException {
        return sessionFactory.executeQuerySql(BizSession.class, BizSession.QUERY_SESSION, uid);
    }

}
