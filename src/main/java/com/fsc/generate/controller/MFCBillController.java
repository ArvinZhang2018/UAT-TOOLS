package com.fsc.generate.controller;

import com.fsc.generate.annotation.ProcessRequestAndResponse;
import com.fsc.generate.http.*;
import com.fsc.generate.model.db.ddo.BizBillUser;
import com.fsc.generate.model.dto.biz.BillNotificationRequest;
import com.fsc.generate.model.dto.biz.UpdateAccountPlanReq;
import com.fsc.generate.model.service.BillService;
import com.fsc.generate.utils.AccountCardInfoDo;
import com.fsc.generate.utils.ConfigService;
import com.fsc.generate.utils.SessionFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.valor.mfc.vms.api.common.encrypt.MD5;
import com.valor.mfc.vms.api.model.common.response.ResponseMsgSingle;
import common.config.tools.config.ConfigTools3;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/api/crm-x/", method = {RequestMethod.GET, RequestMethod.POST})
public class MFCBillController {


    private Random random = new Random(System.currentTimeMillis());
    @Autowired
    private BillApi billApi;
    @Autowired
    private BillService billService;

    @RequestMapping(value = "/bill/getRenewalInfo/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getRenewalInfo(@RequestParam("invoiceIdForEditRenewal") Long invoiceIdForEditRenewal,
            @RequestParam("originOrderIdForEditRenewal") Long originOrderIdForEditRenewal) throws Exception {
        List<Map<String, Object>> bills = SessionFactory.executeQuerySql("bill", "select * from bill_renewal where current_iid = ? or origin_iid = ? or"
                + " order_id = ?", invoiceIdForEditRenewal, invoiceIdForEditRenewal, originOrderIdForEditRenewal + "-1");
        if (Objects.isNull(bills) || bills.size() != 1) {
            return null;
        }
        Map<String, Object> bill = bills.get(0);
        List<Map<String, Object>> invoices = SessionFactory.executeQuerySql("bill", "select * from bill_invoice where iid = ?",
                bill.get("origin_iid"));
        if (Objects.isNull(invoices) || invoices.size() != 1) {
            return null;
        }
        bill.put("origin_order_id", invoices.get(0).get("order_id"));
        return bill;
    }

    @RequestMapping(value = "/bill/editRenewalInfo/v1", method = RequestMethod.POST)
    @ResponseBody
    public String editRenewalInfo(
            @RequestParam("renewalId") Long renewalId,
            @RequestParam("originOrderID") String originOrderID,
            @RequestParam("subscriptionId") String subscriptionId,
            @RequestParam("nextPaymentDate") String nextPaymentDate,
            @RequestParam("invoiceExpireDate") String invoiceExpireDate,
            @RequestParam("renewalStatus") int renewalStatus) throws Exception {
        if (Objects.nonNull(renewalId) && Objects.nonNull(subscriptionId)) {
            SessionFactory.executeSql("bill", "update bill_renewal set order_id = ?,next_payment_date = ?"
                            + ",invoice_expire_date = ?, status = ? where id = ?",
                    subscriptionId, nextPaymentDate,
                    StringUtils.isEmpty(invoiceExpireDate) ? null : invoiceExpireDate, renewalStatus, renewalId);
            SessionFactory.executeSql("bill", "update bill_invoice set order_id = ROUND(RAND() * 100000000 + 600000000),origin_order_id = ?"
                            + " where rid = ? and order_id is not null ",
                    subscriptionId, renewalId);
            SessionFactory.executeSql("bill", "update bill_invoice set origin_order_id = ? "
                            + " where rid = ? ",
                    subscriptionId, renewalId);
            return "Save successfully.";
        }
        return null;
    }


    @RequestMapping(value = "/bill/sendNotification/v1", method = RequestMethod.POST)
    @ResponseBody
    public String sendNotification(@RequestParam("notification") String notification) throws Exception {
        okhttp3.ResponseBody stringObjectMap = billApi.sendNotification(notification);
        return stringObjectMap.string();
    }

    @RequestMapping(value = "/bill/getTemplate/v1", method = RequestMethod.POST)
    @ResponseBody
    public String getTemplate(@RequestParam("templateIndex") int templateIndex,
            @RequestParam(value = "invoiceId", required = false) Long invoiceId) throws Exception {
        List<Map<String, Object>> invoices;
        if (Objects.isNull(invoiceId) || invoiceId <= 0) {
            invoices = SessionFactory.executeQuerySql("bill",
                    "select * from bill_invoice order by iid desc limit 1");
        } else {
            invoices = SessionFactory.executeQuerySql("bill",
                    "select * from bill_invoice where iid = ? ", invoiceId);
        }
        String template = getTemplateByIndex(templateIndex, invoices);
        if (Objects.nonNull(template) && !template.isEmpty()) {
            String s = replaceTemplateByInvoiceId(template, invoices);
            if (Objects.nonNull(s) && !s.isEmpty()) {
                return s;
            }
        }
        return "Template not found!";
    }

    private long getRandomNumber(int digit) {
        double v = random.nextDouble() + random.nextInt(9) + 1;
        return (long) (v * Math.pow(10, digit));
    }

    private long getRandomNumberBasicPrefix(int firstDigit, int digit) {
        double v = random.nextDouble() + firstDigit;
        return (long) (v * Math.pow(10, digit));
    }


    @RequestMapping(value = "/bill/getInvoiceIdAndSign/v1", method = RequestMethod.POST)
    @ResponseBody
    public String getInvoiceIdAndSign(@RequestParam("invoiceId") Long invoiceId,
            @RequestParam("templateIndex") int templateIndex) throws Exception {
        List<Map<String, Object>> invoices;
        if (Objects.isNull(invoiceId) || invoiceId <= 0) {
            invoices = SessionFactory.executeQuerySql("bill",
                    "select * from bill_invoice order by iid desc limit 1");
        } else {
            invoices = SessionFactory.executeQuerySql("bill",
                    "select * from bill_invoice where iid = ? ", invoiceId);
        }
        String template = getTemplateByIndex(templateIndex, invoices);
        String s = replaceTemplateByInvoiceId(template, invoices);
        if (Objects.nonNull(s) && !s.isEmpty()) {
            return s;
        }
        return "Invoice[" + invoiceId + "] not found in DB!";
    }


    private String replaceTemplateByInvoiceId(String template, List<Map<String, Object>> invoices) {
        String sign;
        if (Objects.nonNull(invoices) && invoices.size() == 1) {
            sign = getSignByInovice(invoices.get(0));
            if (Objects.nonNull(sign) && Objects.nonNull(template)) {
                return replaceTemplateByParams(template,
                        ImmutableMap.of(
                                "\"$ID\"", String.valueOf(getRandomNumber(8)),
                                "$mfcOrderId", String.valueOf(invoices.get(0).get("iid")),
                                "$sign", sign,
                                "\"$orderId\"", String.valueOf(invoices.get(0).get("order_id")),
                                "$originOrderId", String.valueOf(invoices.get(0).get("origin_order_id"))));
            }
        }
        return null;
    }

    private String replaceTemplateByParams(String template, Map<String, String> params) {
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        String templateReplaced = template;
        long randomNumberBasicPrefix = getRandomNumberBasicPrefix(6, 8);
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            if (next.getKey().equals("\"$orderId\"")
                    && (Objects.isNull(next.getValue()) || next.getValue().isEmpty()
                    || next.getValue().equals("null"))) {
                templateReplaced = templateReplaced.replace(next.getKey(),
                        String.valueOf(randomNumberBasicPrefix));
            } else if (next.getKey().equals("\"$orderId\"")) {
                templateReplaced = templateReplaced.replace(next.getKey(), next.getValue());
            } else if (next.getKey().equals("$originOrderId")
                    && (Objects.isNull(next.getValue()) || next.getValue().isEmpty()
                    || next.getValue().equals("null"))) {
                if (params.get("\"$orderId\"").equals("null")) {
                    templateReplaced = templateReplaced.replace(next.getKey(), randomNumberBasicPrefix + "-1");
                } else {
                    templateReplaced = templateReplaced.replace(next.getKey(), params.get("\"$orderId\"") + "-1");
                }
            } else {
                templateReplaced = templateReplaced.replace(next.getKey(), next.getValue());
            }
        }
        return templateReplaced;
    }

    private String getSignByInovice(Map<String, Object> ddo) {
        StringBuilder sb = new StringBuilder();
        sb.append(ddo.get("iid")).append("@").append("@").append(ddo.get("invoice_date"))
                .append("@").append(ddo.get("amount"))
                .append("@").append(ddo.get("currency")).append("@").append(ddo.get("pid")).append("@")
                .append(ddo.get("product_name")).append("@").append(ddo.get("service_days")).append("@")
                .append(ddo.get("service_days")).append("@").append(ddo.get("uid")).append("@")
                .append(ddo.get("payment_source"));
        return MD5.MD5(sb.toString());
    }

    private String getTemplateByIndex(int templateIndex, List<Map<String, Object>> invoices) {
        if (String.valueOf(invoices.get(0).get("pid")).equals("1") && templateIndex == 1) {
            templateIndex = 2;
        }
        if (!String.valueOf(invoices.get(0).get("pid")).equals("1") && templateIndex == 2) {
            templateIndex = 1;
        }
        if (!String.valueOf(invoices.get(0).get("pid")).equals("1") && templateIndex == 4) {
            templateIndex = 14;
        }
        if (!String.valueOf(invoices.get(0).get("pid")).equals("1") && templateIndex == 5) {
            templateIndex = 15;
        }
        String fileName = ConfigTools3.getString("mfc.bill.notification.template." + templateIndex);
        try {
            String s = FileUtils.readFileToString(
                    new File(ConfigTools3.getString("mfc.bill.notification.template.base.dir") + fileName),
                    StandardCharsets.UTF_8);
            if (!s.isEmpty()) {
                return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
