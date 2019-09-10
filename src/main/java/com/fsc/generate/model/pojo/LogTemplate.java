package com.fsc.generate.model.pojo;

import com.fsc.generate.annotation.CrmLogTemplate;

public class LogTemplate {

    public static final String TEMPLATE_SETTING = "templateSetting";
    public static final String TEMPLATE_PROFILE = "templateProfile";

    @CrmLogTemplate(
            cnTemplate = "语言：@@@，日志：@@@，版本：V@@@",
            enTemplate = "language:@@@,log:@@@,version:V@@@")
    public void templateSetting() {
    }

    @CrmLogTemplate(
            cnTemplate = "修改昵称为：@@@",
            enTemplate = "Change nickname to @@@")
    public void templateProfile() {
    }

    public static final String TEMPLATE_RESET_TO_DEVICE_LOGIN = "templateResetToDeviceLogin";

    @CrmLogTemplate(
            cnTemplate = "设备MAC：@@@，UID：@@@，套餐：@@@，渠道：@@@",
            enTemplate = "Device MAC:@@@,uid:@@@,plan:@@@,vendorId:@@@")
    public void templateResetToDeviceLogin() {
    }

    public static final String TEMPLATE_DELETE_DEVICE = "templateDeleteDevice";

    @CrmLogTemplate(
            cnTemplate = "设备MAC：@@@，UID：@@@，已删除",
            enTemplate = "Device MAC:@@@,uid:@@@,deleted")
    public void templateDeleteDevice() {
    }

    public static final String TEMPLATE_UPDATE_DEVICE_BIZ = "templateUpdateDeviceBiz";

    @CrmLogTemplate(
            cnTemplate = "设备MAC：@@@，UID：@@@，剩余天数：@@@",
            enTemplate = "Device MAC:@@@,uid:@@@,rest days:@@@")
    public void templateUpdateDeviceBiz() {
    }

    public static final String TEMPLATE_SET_TO_INNER_TEST_GROUP = "templateSetToInnerTestGroup";

    @CrmLogTemplate(
            cnTemplate = "设备MAC：@@@，UID：@@@，已设置为内测组成员",
            enTemplate = "Device MAC:@@@,uid:@@@,Set as a member of the internal test group")
    public void templateSetToInnerTestGroup() {
    }

    public static final String TEMPLATE_ACCOUNT_CREATE_EMAIL_ACCOUNT = "templateAccountCreateEmailAccount";

    @CrmLogTemplate(
            cnTemplate = "邮箱：@@@，密码：@@@，昵称：@@@",
            enTemplate = "Email:@@@,password:@@@,nickname:@@@")
    public void templateAccountCreateEmailAccount() {
    }

    public static final String TEMPLATE_ACCOUNT_CREATE_ACCOUNT_CARD = "templateAccountCreateAccountCard";

    @CrmLogTemplate(
            cnTemplate = "数量：@@@，@@@",
            enTemplate = "Amount:@@@,@@@")
    public void templateAccountCreateAccountCard() {
    }

    public static final String TEMPLATE_ACCOUNT_UPDATE_BIZ = "templateAccountUpdateBiz";

    @CrmLogTemplate(
            cnTemplate = "账号：@@@，剩余天数：@@@",
            enTemplate = "Account:@@@,rest days:@@@")
    public void templateAccountUpdateBiz() {
    }

    public static final String TEMPLATE_ACCOUNT_UPDATE_PLAN = "templateAccountUpdatePlan";

    @CrmLogTemplate(
            cnTemplate = "账号：@@@，套餐：@@@，同时在线数：@@@",
            enTemplate = "Account:@@@,plan:@@@,online amount:@@@")
    public void templateAccountUpdatePlan() {
    }

    public static final String TEMPLATE_ACCOUNT_DELETE = "templateAccountDelete";

    @CrmLogTemplate(
            cnTemplate = "Uid：@@@，账号：@@@",
            enTemplate = "Uid:@@@,email:@@@")
    public void templateAccountDelete() {
    }

    public static final String TEMPLATE_ACCOUNT_CREATE_RECHARGE_CARD = "templateCreateRechargeCard";

    @CrmLogTemplate(
            cnTemplate = "数量：@@@，@@@",
            enTemplate = "Amount:@@@,@@@")
    public void templateCreateRechargeCard() {
    }

}
