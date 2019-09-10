package com.fsc.generate.controller;

import com.fsc.generate.annotation.ProcessRequestAndResponse;
import com.fsc.generate.enums.MFCUserType;
import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.http.AdminUserApi;
import com.fsc.generate.http.CCApi;
import com.fsc.generate.http.MFCAccountApi;
import com.fsc.generate.model.db.ddo.BizAccount;
import com.fsc.generate.model.db.ddo.BizVmsUser;
import com.fsc.generate.model.dto.BasicResponse;
import com.fsc.generate.model.dto.biz.*;
import com.fsc.generate.model.pojo.AccountCard;
import com.fsc.generate.model.pojo.LogTemplate;
import com.fsc.generate.model.service.*;
import com.fsc.generate.utils.RechargeCardResp;
import com.fsc.generate.utils.StringUtils;
import common.config.tools.config.ConfigTools3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/api/crm-x/", method = {RequestMethod.GET, RequestMethod.POST})
public class MFCBusinessController extends MFCBasicController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final long ONE_DAY_SECONDS = 86400000;

    @Autowired
    private CCApi ccApi;
    @Autowired
    private MFCAccountApi accountApi;
    @Autowired
    private AdminUserApi adminUserApi;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private CrmService crmService;
    @Autowired
    private VmsUserService vmsUserService;
    @Autowired
    private CcService ccService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BillService billService;

    @RequestMapping(value = "/resetToDeviceLogin/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse resetToDeviceLogin(HttpServletRequest request, HttpServletResponse response,
            @RequestBody ResetToDeviceLoginReq resetToDeviceLoginReq) throws Exception {
        BizVmsUser bizVmsUser = vmsUserService.queryUser(resetToDeviceLoginReq.getMacOrUid());
        vmsUserService.deleteByMac(bizVmsUser.getDid());
        ccService.deleteByMac(bizVmsUser.getDid());
        ccApi.createChargeCardV2(bizVmsUser.getDid(), resetToDeviceLoginReq.getPlan());
        adminUserApi.createUserV2(bizVmsUser.getDid(), resetToDeviceLoginReq.getPlan(), resetToDeviceLoginReq.getVendorId());
        bizVmsUser = vmsUserService.queryUser(resetToDeviceLoginReq.getMacOrUid());
        crmService.saveLog(resetToDeviceLoginReq, LogTemplate.TEMPLATE_RESET_TO_DEVICE_LOGIN,
                Arrays.asList(bizVmsUser.getDid(), bizVmsUser.getUid(), getPlan(resetToDeviceLoginReq.getPlan()), bizVmsUser.getVendor()));
        return new BasicResponse();
    }

    @RequestMapping(value = "/createMFCUser/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse createMFCUser(HttpServletRequest request, HttpServletResponse response,
            @RequestBody ResetToDeviceLoginReq req) throws Exception {
        BizVmsUser bizVmsUser = vmsUserService.queryUserV2(req.getMac());
        if (Objects.nonNull(bizVmsUser)) {
            throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_VMS_USER_ALREADY_EXIST);
        }
        vmsUserService.deleteByMac(req.getMac());
        ccService.deleteByMac(req.getMac());
        ccApi.createChargeCardV2(req.getMac(), req.getPlan());
        adminUserApi.createUserV2(req.getMac(), req.getPlan(), req.getVendorId());
        bizVmsUser = vmsUserService.queryUser(req.getMac());
        crmService.saveLog(req, LogTemplate.TEMPLATE_RESET_TO_DEVICE_LOGIN,
                Arrays.asList(bizVmsUser.getDid(), bizVmsUser.getUid(), getPlan(req.getPlan()), bizVmsUser.getVendor()));
        return new BasicResponse();
    }


    @RequestMapping(value = "/queryDevice/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse queryDevice(HttpServletRequest request, HttpServletResponse response,
            @RequestBody BasicBizRequest basicBizRequest) throws Exception {
        BizVmsUser user = vmsUserService.queryUser(basicBizRequest.getMacOrUid());
        user.setPlanDesc(getPlan(user.getPlan()));
        user.setLoginTypeDesc(user.isDeviceLogin() ? "DEVICE" : "ACCOUNT");
        if (Objects.nonNull(user.getActDate())) {
            user.setActDateDesc(sdf.format(user.getActDate()));
        }
        List<BizVmsUser> bizList = vmsUserService.queryBiz(user.getUid());
        if (bizList.size() == 1) {
            user.setExpireDate(sdf.format(new Date(bizList.get(0).getExpireTs())));
        } else {
            user.setExpireDate("Empty");
        }
        user.setLoginAccount(accountService.queryAccountDevice(user.getDid()));
        List<BizVmsUser> migratedTask = vmsUserService.queryMigratedTask(user.getUid());
        if (migratedTask.size() == 1) {
            user.setMigratedStatusDesc(migratedTask.get(0).getStatus() == 0 ? "unfinished" : "done");
            user.setAccountId(migratedTask.get(0).getAccountId());
        }
        Context context = new Context();
        context.setVariable("user", user);
        String htmlContent = templateEngine.process("fragment/deviceInfo", context);
        return new BasicResponse(new BasicBizWithAlertWindowResp("User",
                htmlContent));
    }

    @RequestMapping(value = "/deleteDevice/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse deleteDevice(HttpServletRequest request, HttpServletResponse response,
            @RequestBody ResetToDeviceLoginReq resetToDeviceLoginReq) throws Exception {
        BizVmsUser bizVmsUser = vmsUserService.queryUser(resetToDeviceLoginReq.getMacOrUid());
        vmsUserService.deleteByMac(bizVmsUser.getDid());
        ccService.deleteByMac(bizVmsUser.getDid());
        crmService.saveLog(resetToDeviceLoginReq, LogTemplate.TEMPLATE_DELETE_DEVICE,
                Arrays.asList(bizVmsUser.getDid(), bizVmsUser.getUid()));
        return new BasicResponse();
    }

    @RequestMapping(value = "/updateBiz/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse updateBiz(HttpServletRequest request, HttpServletResponse response,
            @RequestBody UpdateBizLoginReq updateBizLoginReq) throws Exception {
        long serviceTime = updateBizLoginReq.getRestDays() * ONE_DAY_SECONDS;
        long resTime = System.currentTimeMillis() + serviceTime;
        if (updateBizLoginReq.getUserType() == MFCUserType.DEVICE.ordinal()) {
            BizVmsUser bizVmsUser = vmsUserService.queryUser(updateBizLoginReq.getMacOrUid());
            List<BizVmsUser> bizList = vmsUserService.queryBiz(bizVmsUser.getUid());
            if (bizList.size() != 1) {
                throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_VMS_USER_BIZ_NOT_FOUND);
            }
            vmsUserService.updateBiz(bizVmsUser.getUid(), resTime);
            crmService.saveLog(updateBizLoginReq, LogTemplate.TEMPLATE_UPDATE_DEVICE_BIZ,
                    Arrays.asList(bizVmsUser.getDid(), bizVmsUser.getUid(), updateBizLoginReq.getRestDays()));
        } else if (updateBizLoginReq.getUserType() == MFCUserType.ACCOUNT.ordinal()) {
            BizAccount account = accountService.queryAccount(updateBizLoginReq.getEmailOrAccountCardOrUid());
            if (Objects.nonNull(account)) {
                BizAccount accountBiz = accountService.queryAccountBiz(account.getUid());
                if (Objects.nonNull(accountBiz)) {
                    if (accountBiz.getAvailableServiceTime() == 0) {
                        accountService.updateAccountBiz(account.getUid(), resTime);
                    } else {
                        long restTs = serviceTime < 0 ? 0 : serviceTime;
                        accountService.updateAccountAVABiz(account.getUid(), restTs);
                    }
                } else {
                    throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_ACCOUNT_BIZ_NOT_FOUND);
                }
            } else {
                throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_ACCOUNT_NOT_FOUND);
            }
            crmService.saveLog(updateBizLoginReq, LogTemplate.TEMPLATE_ACCOUNT_UPDATE_BIZ,
                    Arrays.asList(updateBizLoginReq.getEmailOrAccountCardOrUid(), updateBizLoginReq.getRestDays()));
        }
        return new BasicResponse();
    }

    @RequestMapping(value = "/updateDeviceTo5188/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse updateDeviceTo5188(HttpServletRequest request, HttpServletResponse response,
            @RequestBody BasicBizRequest basicBizRequest) throws Exception {
        BizVmsUser bizVmsUser = vmsUserService.queryUser(basicBizRequest.getMacOrUid());
        vmsUserService.updateToInnerTestGroup(bizVmsUser.getUid());
        crmService.saveLog(basicBizRequest, LogTemplate.TEMPLATE_SET_TO_INNER_TEST_GROUP,
                Arrays.asList(bizVmsUser.getDid(), bizVmsUser.getUid()));
        return new BasicResponse();
    }

    @RequestMapping(value = "/biz/createEmailAccount/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse createEmailAccount(HttpServletRequest request, HttpServletResponse response,
            @RequestBody CreateEmailAccountReq emailAccountReq) throws Exception {
        BizAccount account = accountService.queryAccountByEmail(emailAccountReq.getEmail());
        if (Objects.nonNull(account)) {
            throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_EMAIL_ALREADY_EXIST);
        }
        accountApi.createUserV2(emailAccountReq);
        accountService.activateEmailAccount(emailAccountReq.getEmail());
        crmService.saveLog(emailAccountReq, LogTemplate.TEMPLATE_ACCOUNT_CREATE_EMAIL_ACCOUNT,
                Arrays.asList(emailAccountReq.getEmail(), emailAccountReq.getPassword(), emailAccountReq.getNickname()));
        return new BasicResponse();
    }

    @RequestMapping(value = "/biz/createAccountCard/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse createAccountCard(HttpServletRequest request, HttpServletResponse response,
            @RequestBody CreateAccountCardReq accountCardReq) throws Exception {
        List<AccountCard> accountCards = new ArrayList<>();
        for (int i = 0; i < accountCardReq.getAmount(); i++) {
            accountCards.add(ccApi.createAccountCardV2());
        }
        crmService.saveLog(accountCardReq, LogTemplate.TEMPLATE_ACCOUNT_CREATE_ACCOUNT_CARD,
                Arrays.asList(accountCardReq.getAmount(), listToString(accountCards)));
        return new BasicResponse(new BasicBizWithAlertWindowResp("Pre-paid accounts",
                buildHtmlForCreateAccountCard(accountCards)));
    }

    @RequestMapping(value = "/biz/createRechargeCard/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse createRechargeCard(HttpServletRequest request, HttpServletResponse response,
            @RequestBody CreateRechargeCardReq req) throws Exception {
        List<RechargeCardResp> rechargeCards = null;
        rechargeCards = new ArrayList<>();
        for (int i = 0; i < req.getAmount(); i++) {
            rechargeCards.add(ccApi.createRechargeCard(req));
        }
        crmService.saveLog(req, LogTemplate.TEMPLATE_ACCOUNT_CREATE_RECHARGE_CARD,
                Arrays.asList(req.getAmount(), listToString(rechargeCards)));
        return new BasicResponse(new BasicBizWithAlertWindowResp("Recharge card.",
                buildHtmlForCreateRechargeCard(rechargeCards)));
    }

    private <T> String listToString(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
        }
        return sb.toString();
    }

    private String buildHtmlForCreateAccountCard(List<AccountCard> accountCards) {
        StringBuilder sb = new StringBuilder("<div style='margin:20px 0px;'>");
        for (int i = 0; i < accountCards.size(); i++) {
            sb.append("<p style='font-size:15px;'>A:")
                    .append(accountCards.get(i).getAccountId())
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P:")
                    .append(accountCards.get(i).getPassword())
                    .append("</p>");

        }
        sb.append("</div>");
        return sb.toString();
    }

    private String buildHtmlForCreateRechargeCard(List<RechargeCardResp> accountCards) {
        StringBuilder sb = new StringBuilder("<div style='margin:20px 0px;'>");
        for (int i = 0; i < accountCards.size(); i++) {
            sb.append("<p style='font-size:15px;'>CardId:")
                    .append(accountCards.get(i).getId())
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Plan:")
                    .append(ConfigTools3.getString("crm.rc.plan.name." + accountCards.get(i).getPlan()))
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Days:")
                    .append(accountCards.get(i).getBizValue())
                    .append("</p>");

        }
        sb.append("</div>");
        return sb.toString();
    }


    @RequestMapping(value = "/biz/updateAccountPlan/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse updateAccountPlan(HttpServletRequest request, HttpServletResponse response,
            @RequestBody UpdateAccountPlanReq accountPlanReq) throws Exception {
        BizAccount account = accountService.queryAccount(accountPlanReq.getEmailOrUid());
        if (Objects.isNull(account)) {
            throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_ACCOUNT_NOT_FOUND);
        }
        if (Objects.isNull(account.getEmail())) {
            throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_ACCOUNT_NOT_EMAIL);
        }
        String planName = accountPlanReq.getPlanName();
        Integer billPlan = accountPlanReq.getPlan();
        if (accountPlanReq.getPlan() == 0) {
            planName = null;
            billPlan = null;
        }
        accountService.updateAccountPlan(account.getUid(), accountPlanReq.getPlan(),
                planName, accountPlanReq.getDeviceLimit());
        billService.updateBillUserPlan(account.getBillUid(), billPlan);
        crmService.saveLog(accountPlanReq, LogTemplate.TEMPLATE_ACCOUNT_UPDATE_PLAN,
                Arrays.asList(accountPlanReq.getEmailOrUid(), accountPlanReq.getPlanName(), accountPlanReq.getDeviceLimit()));
        return new BasicResponse();
    }

    @RequestMapping(value = "/biz/queryAccount/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse queryAccount(HttpServletRequest request, HttpServletResponse response,
            @RequestBody UpdateAccountPlanReq accountPlanReq) throws Exception {
        BizAccount account = accountService.queryAccount(accountPlanReq.getEmailOrAccountCardOrUid());
        if (Objects.nonNull(account)) {
            BizAccount accountBiz = accountService.queryAccountBiz(account.getUid());
            if (Objects.nonNull(accountBiz)) {
                if (accountBiz.getExpireTs() > 0) {
                    account.setExpireTsDesc(sdf.format(accountBiz.getExpireTs()));
                } else {
                    account.setAvailableServiceTimeDesc(accountBiz.getAvailableServiceTime() / (86400000) + " days");
                }
            }
        } else {
            throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_ACCOUNT_NOT_FOUND);
        }
        if (account.getTrial() == 1) {
            account.setProductName("Free trial");
        } else {
            account.setProductName(getProduct(account.getProductId()));
        }
        account.setSessions(vmsUserService.querySession(account.getUid()));
        Context context = new Context();
        context.setVariable("account", account);
        String htmlContent = templateEngine.process("fragment/accountInfo", context);
        return new BasicResponse(new BasicBizWithAlertWindowResp("Account",
                htmlContent));
    }

    @RequestMapping(value = "/biz/deleteAccount/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse deleteAccount(HttpServletRequest request, HttpServletResponse response,
            @RequestBody BasicBizRequest accountPlanReq) throws Exception {
        BizAccount account = accountService.queryAccount(accountPlanReq.getEmailOrUid());
        if (Objects.isNull(account)) {
            throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_ACCOUNT_NOT_FOUND);
        }
        if (Objects.isNull(account.getEmail())) {
            throw CrmException.newCrmException(CrmCode.RET_HTTP, CrmCode.ERR_HTTP_ACCOUNT_NOT_EMAIL);
        }
        accountService.deleteAccount(account.getUid());
        billService.deleteBillUserByEmail(account.getEmail());
        crmService.saveLog(accountPlanReq, LogTemplate.TEMPLATE_ACCOUNT_DELETE,
                Arrays.asList(account.getUid(), account.getEmail()));
        return new BasicResponse();
    }

}
