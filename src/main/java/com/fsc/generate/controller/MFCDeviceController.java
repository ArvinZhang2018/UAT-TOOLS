package com.fsc.generate.controller;

import com.fsc.generate.http.AdminUserApi;
import com.fsc.generate.http.CCApi;
import com.fsc.generate.http.MFCAccountApi;
import com.fsc.generate.model.service.CrmService;
import com.fsc.generate.utils.AccountCardInfoDo;
import com.fsc.generate.utils.SessionFactory;
import com.valor.mfc.vms.api.common.encrypt.MD5;
import com.valor.mfc.vms.api.model.common.response.ResponseMsgSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/api/crm-x/", method = {RequestMethod.GET, RequestMethod.POST})
public class MFCDeviceController {

    private static final long ONE_DAY_SECONDS = 86400000;

    @Autowired
    private CCApi ccApi;

    @Autowired
    private MFCAccountApi mfcAccountApi;

    @Autowired
    private AdminUserApi adminUserApi;

    @Autowired
    private CrmService crmService;

    @RequestMapping(value = "/initDeviceInfo/v1", method = RequestMethod.POST)
    @ResponseBody
    public String initDeviceInfo(@RequestParam("did") String did, @RequestParam("plan") int plan) throws Exception {
        SessionFactory.executeSql("cc", "delete from charge_card_binding where biz_sys_uid = ?", did);
        Map<String, Object> chargeCard = ccApi.createChargeCard(did, plan);
        SessionFactory.executeSql("device", "delete from vms_user where did = ?", did);
        Map<String, Object> user = adminUserApi.createUser(did, plan);
        if (chargeCard != null && user != null &&
                String.valueOf(chargeCard.get("retCode")).equalsIgnoreCase("0.0") &&
                String.valueOf(chargeCard.get("errCode")).equalsIgnoreCase("0.0") &&
                String.valueOf(user.get("retCode")).equalsIgnoreCase("0.0") &&
                String.valueOf(user.get("retCode")).equalsIgnoreCase("0.0")) {
            return "Congratulations , operation is successful !";
        } else {
            return "ok";
        }
    }

    @RequestMapping(value = "/deleteDeviceInfo/v1", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDeviceInfo(@RequestParam("did") String did) {
        SessionFactory.executeSql("cc", "delete from charge_card_binding where biz_sys_uid = ?", did);
        SessionFactory.executeSql("device", "delete from vms_user where did = ?", did);
        return "ok";
    }

    @RequestMapping(value = "/clearServiceTime/v1", method = RequestMethod.POST)
    @ResponseBody
    public String clearServiceTime(@RequestParam("did") String did, @RequestParam("days") int days, @RequestParam("type") int type) {
        long serviceTime = days * ONE_DAY_SECONDS;
        long resTime = System.currentTimeMillis() + serviceTime;
        if (type == 0) {
            SessionFactory.executeSql("device",
                    "update vms_user_biz set expire_ts = ? where uid in "
                            + "( select uid from vms_user where did = ?)", resTime, did);
        } else {
            if (did.contains("@")) {
                SessionFactory.executeSql("account",
                        "update mfc_user_biz set expire_ts = ? where uid in "
                                + "( select uid from mfc_user where email = ?)", resTime, did);
            } else {
                SessionFactory.executeSql("account",
                        "update mfc_user_biz set expire_ts = ? where uid in "
                                + "( select a.uid from mfc_user a, mfc_account_link b"
                                + " where a.uid = b.uid and b.login_id = ?);", resTime, did);

            }
        }
        return "Congratulations , operation is successful !";
    }

    @RequestMapping(value = "/activeEmailAccount/v1", method = RequestMethod.POST)
    @ResponseBody
    public String activeEmailAccount(@RequestParam("email") String email) {
        SessionFactory.executeSql("account",
                "update mfc_user set property = 0 , status = 0 where email = ?", email);
        return "Congratulations , operation is successful !";
    }

    @RequestMapping(value = "/createEmailAccount/v1", method = RequestMethod.POST)
    @ResponseBody
    public String createEmailAccount(@RequestParam("email") String email, @RequestParam("password") String password,
            @RequestParam("name") String name) {
        Map<String, Object> user = mfcAccountApi.createUser(email, name, MD5.MD5(password));
        if (user != null &&
                String.valueOf(user.get("retCode")).equalsIgnoreCase("0.0") &&
                String.valueOf(user.get("retCode")).equalsIgnoreCase("0.0")) {
            SessionFactory.executeSql("account",
                    "update mfc_user set property = 0 , status = 0 where email = ?", email);
            return "Congratulations , operation is successful !";
        } else {
            return "Unfortunately, the operation failed, please try again !";
        }
    }

    @RequestMapping(value = "/createAccountCard/v1", method = RequestMethod.POST)
    @ResponseBody
    public String createAccountCard() {
        ResponseMsgSingle<AccountCardInfoDo> accountCard = ccApi.createAccountCard();
        if (accountCard != null && accountCard.isSuccess()) {
            AccountCardInfoDo result = accountCard.getResult();
            return "Congratulations , operation is successful !\naccount : " + result.getAccountId() + "\npassword : " + result.getPassword();
        } else {
            return "Unfortunately, the operation failed, please try again !";
        }
    }

    @RequestMapping("/ping")
    @ResponseBody
    public String ping() {
        return String.valueOf(new Date());
    }

}
