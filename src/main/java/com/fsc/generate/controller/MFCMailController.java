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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/api/crm-x/", method = {RequestMethod.GET, RequestMethod.POST})
public class MFCMailController extends MFCBasicController {


//    @RequestMapping(value = "/biz/queryAccount/v1", method = RequestMethod.POST)
//    @ResponseBody
//    @ProcessRequestAndResponse
//    public BasicResponse queryAccount(HttpServletRequest request, HttpServletResponse response,
//            @RequestBody UpdateAccountPlanReq accountPlanReq) throws Exception {
//
//    }

}
