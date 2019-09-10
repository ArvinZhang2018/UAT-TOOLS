package com.fsc.generate.model.service;

import com.fsc.generate.annotation.CrmLogTemplate;
import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.db.ddo.ToolsLog;
import com.fsc.generate.model.db.ddo.ToolsOperation;
import com.fsc.generate.model.db.ddo.ToolsUser;
import com.fsc.generate.model.db.ddo.ToolsUserOperation;
import com.fsc.generate.model.db.factory.CrmSessionFactory;
import com.fsc.generate.model.dto.BasicRequest;
import com.fsc.generate.model.pojo.LogTemplate;
import com.fsc.generate.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Service
public class CrmService {

    @Autowired
    CrmSessionFactory sessionFactory;

    public List<ToolsOperation> queryOperations() throws CrmException {
        return sessionFactory.executeQuerySql(ToolsOperation.class, ToolsOperation.QUERY_ALL_OPERATIONS);
    }

    public ToolsUser queryUserInfo(String fingerprint) throws CrmException {
        List<ToolsUser> userList = sessionFactory.executeQuerySql(ToolsUser.class,
                ToolsUser.QUERY_USER_INFO, fingerprint);
        if (userList.size() == 1) {
            return userList.get(0);
        } else if (userList.isEmpty()) {
            sessionFactory.executeSql(ToolsUser.class, ToolsUser.INSERT_USER, fingerprint, new Date());
            List<ToolsUser> toolsUsers = sessionFactory.executeQuerySql(ToolsUser.class,
                    ToolsUser.QUERY_USER_INFO, fingerprint);
            if (toolsUsers.size() == 1) {
                return toolsUsers.get(0);
            }
        }
        throw CrmException.newCrmException(CrmCode.RET_DB, CrmCode.ERR_DB_QUERY_USER_FAILURE);
    }

    public List<ToolsUserOperation> queryUserOperations(String fingerprint) throws CrmException {
        return sessionFactory.executeQuerySql(ToolsUserOperation.class,
                ToolsUserOperation.QUERY_RECORD_BY_FINGERPRINT, fingerprint);
    }

    public List<ToolsLog> queryLog(String fingerprint) throws CrmException {
        ToolsUser userInfo = queryUserInfo(fingerprint);
        if (userInfo.getShowLog() != 2) {
            return sessionFactory.executeQuerySql(ToolsLog.class, ToolsLog.QUERY_LOG);
        } else {
            return sessionFactory.executeQuerySql(ToolsLog.class, ToolsLog.QUERY_LOG_BY_FINGERPRINT, fingerprint);
        }
    }

    public void recordClickEvent(String fingerprint, int operationId) throws CrmException {
        sessionFactory.executeSql(ToolsOperation.class, ToolsOperation.UPDATE_OPERATIONS_ADD_CLICK_AMOUNT, operationId);
        List<ToolsUserOperation> userOperations = sessionFactory.executeQuerySql(ToolsUserOperation.class,
                ToolsUserOperation.QUERY_RECORD_BY_OPID_FINGERPRINT, operationId, fingerprint);
        if (userOperations.isEmpty()) {
            sessionFactory.executeSql(ToolsUserOperation.class,
                    ToolsUserOperation.INSERT_USER_OPERATION, operationId, fingerprint, new Date());
        }
        sessionFactory.executeSql(ToolsUserOperation.class, ToolsUserOperation.UPDATE_USER_OPERATION_ADD_CLICK_AMOUNT,
                operationId, fingerprint);
        sessionFactory.executeSql(ToolsUser.class, ToolsUser.UPDATE_USER_ADD_SCORE, fingerprint);
    }

    public void saveSettings(String fingerprint, String language, int showLog, int toolsVersion)
            throws CrmException {
        sessionFactory.executeSql(ToolsUser.class, ToolsUser.UPDATE_USER_CONFIG,
                language, showLog, toolsVersion, fingerprint);
    }

    public void saveLog(BasicRequest request,String executedMethod,
            List<Object> cnParams,List<Object> enParams) throws Exception{
        Method method = LogTemplate.class.getDeclaredMethod(executedMethod);
        sessionFactory.executeSql(ToolsLog.class, ToolsLog.INSERT_LOG,
                request.getFingerprint(), request.getOperation(), new Date(),
                StringUtils.fillTemplate(method.getAnnotation(CrmLogTemplate.class).cnTemplate(),cnParams),
                StringUtils.fillTemplate(method.getAnnotation(CrmLogTemplate.class).enTemplate(),enParams));
    }
    public void saveLog(BasicRequest request,String executedMethod,
            List<Object> params) throws Exception{
        saveLog(request,executedMethod,params,params);
    }

    public void changeVersion(String fingerprint, int version) throws CrmException {
        sessionFactory.executeSql(ToolsUser.class, ToolsUser.UPDATE_USER_VERSION,
                version, fingerprint);
    }

    public void saveProfile(String fingerprint, String nickName) throws CrmException {
        sessionFactory.executeSql(ToolsUser.class, ToolsUser.UPDATE_USER_NICKNAME,
                nickName, fingerprint);
    }

}
