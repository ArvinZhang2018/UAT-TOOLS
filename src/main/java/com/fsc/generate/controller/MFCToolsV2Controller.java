package com.fsc.generate.controller;

import com.fsc.generate.annotation.ProcessRequestAndResponse;
import com.fsc.generate.enums.ToolsLanguage;
import com.fsc.generate.enums.ToolsVersion;
import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.http.AdminUserApi;
import com.fsc.generate.http.CCApi;
import com.fsc.generate.http.MFCAccountApi;
import com.fsc.generate.model.db.ddo.ToolsLog;
import com.fsc.generate.model.db.ddo.ToolsOperation;
import com.fsc.generate.model.db.ddo.ToolsUser;
import com.fsc.generate.model.db.ddo.ToolsUserOperation;
import com.fsc.generate.model.dto.*;
import com.fsc.generate.model.pojo.LogTemplate;
import com.fsc.generate.model.service.CrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/api/crm-x/", method = {RequestMethod.GET, RequestMethod.POST})
public class MFCToolsV2Controller extends MFCBasicController {

    @Autowired
    private CrmService crmService;

    @RequestMapping("/")
    @ProcessRequestAndResponse
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer toolsVersion = getCookieAsInt(request, "toolsVersion");
        ModelAndView mv;
        if (Objects.nonNull(toolsVersion)
                && toolsVersion == ToolsVersion.V1.getVersion()) {
            mv = new ModelAndView("index");
        } else {
            mv = new ModelAndView("index-1");
            mv.addObject("ops", crmService.queryOperations());
        }
        return mv;
    }

    @RequestMapping(value = "/getUserInfo/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse getUserInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestBody BasicRequest basicRequest) throws Exception {
        ToolsUser userInfo = crmService.queryUserInfo(basicRequest.getFingerprint());
        List<ToolsUserOperation> userOperations = crmService.queryUserOperations(basicRequest.getFingerprint());
        List<ToolsLog> logs = crmService.queryLog(basicRequest.getFingerprint());
        writeCookie(response, "toolsVersion", userInfo.getVersion());
        return new BasicResponse(new ToolsUserResp(userInfo, userOperations, logs));
    }

    @RequestMapping(value = "/getUser/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse getUser(HttpServletRequest request, HttpServletResponse response,
            @RequestBody BasicRequest basicRequest) throws Exception {
        return new BasicResponse(crmService.queryUserInfo(basicRequest.getFingerprint()));
    }

    @RequestMapping(value = "/getLogs/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse getLogs(HttpServletRequest request, HttpServletResponse response,
            @RequestBody BasicRequest basicRequest) throws Exception {
        return new BasicResponse(crmService.queryLog(basicRequest.getFingerprint()));
    }

    @RequestMapping(value = "/recordClickEven/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse recordClickEven(HttpServletRequest request, HttpServletResponse response,
            @RequestBody BasicRequest basicRequest) throws Exception {
        crmService.recordClickEvent(basicRequest.getFingerprint(), basicRequest.getOperation());
        return new BasicResponse();
    }

    @RequestMapping(value = "/saveSettings/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse saveSettings(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SettingsRequest settingsRequest) throws Exception {
        writeCookie(response, "toolsVersion", settingsRequest.getToolsVersion());
        crmService.saveSettings(settingsRequest.getFingerprint(), settingsRequest.getLanguage(),
                settingsRequest.getShowLog(), settingsRequest.getToolsVersion());
        crmService.saveLog(settingsRequest, LogTemplate.TEMPLATE_SETTING,
                Arrays.asList(settingsRequest.getLanguage(),
                        (settingsRequest.getShowLog() == 0 ? "ON" : "OFF"),settingsRequest.getToolsVersion()));
        return new BasicResponse();
    }

    @RequestMapping(value = "/changeVersion/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse changeVersion(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute SettingsRequest settingsRequest) throws Exception {
        writeCookie(response, "toolsVersion", settingsRequest.getToolsVersion());
        crmService.changeVersion(settingsRequest.getFingerprint(), settingsRequest.getToolsVersion());
        return new BasicResponse();
    }

    @RequestMapping(value = "/saveProfile/v1", method = RequestMethod.POST)
    @ResponseBody
    @ProcessRequestAndResponse
    public BasicResponse saveProfile(HttpServletRequest request, HttpServletResponse response,
            @RequestBody ProfileRequest profileRequest) throws Exception {
        crmService.saveProfile(profileRequest.getFingerprint(), profileRequest.getNickName());
        crmService.saveLog(profileRequest, LogTemplate.TEMPLATE_PROFILE,
                Arrays.asList(profileRequest.getNickName()));
        return new BasicResponse();
    }
}
