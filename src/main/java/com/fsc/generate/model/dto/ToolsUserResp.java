package com.fsc.generate.model.dto;

import com.fsc.generate.model.db.ddo.ToolsLog;
import com.fsc.generate.model.db.ddo.ToolsOperation;
import com.fsc.generate.model.db.ddo.ToolsUser;
import com.fsc.generate.model.db.ddo.ToolsUserOperation;
import com.valor.mfc.vms.api.model.common.AbstractPrintable;

import java.util.List;

public class ToolsUserResp extends AbstractPrintable {

    private ToolsUser userInfo;
    private List<ToolsUserOperation> userOperations;
    private List<ToolsLog> logs;

    public ToolsUserResp(ToolsUser userInfo, List<ToolsUserOperation> userOperations, List<ToolsLog> logs) {
        this.userInfo = userInfo;
        this.userOperations = userOperations;
        this.logs = logs;
    }

    public ToolsUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ToolsUser userInfo) {
        this.userInfo = userInfo;
    }

    public List<ToolsUserOperation> getUserOperations() {
        return userOperations;
    }

    public void setUserOperations(List<ToolsUserOperation> userOperations) {
        this.userOperations = userOperations;
    }

    public List<ToolsLog> getLogs() {
        return logs;
    }

    public void setLogs(List<ToolsLog> logs) {
        this.logs = logs;
    }
}
