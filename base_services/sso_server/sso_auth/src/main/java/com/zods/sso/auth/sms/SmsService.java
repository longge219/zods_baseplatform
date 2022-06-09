package com.zods.sso.auth.sms;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import com.zods.sso.auth.temp.SmsReportResVo;
import com.zods.sso.auth.temp.SmsSendResVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zom.zods.exception.exception.category.McloudHandlerException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SmsService {

    @Autowired
    private SmsProperties smsProperties;


    /**
     * 发送短息
     *
     * @param phones 电话号码
     * @param msg    短信内容
     * @return 发送结果
     */
    public SmsSendResVo send(String phones, String msg) {
        SmsSendResVo resVo;
        try {
            String send = "/Send.do";
            String url = smsProperties.getBaseUrl() + send;
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod(url);
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
            post.addParameter("SpCode", smsProperties.getSpCode());
            post.addParameter("LoginName", smsProperties.getLoginName());
            post.addParameter("Password", smsProperties.getPassword());
            post.addParameter("MessageContent", msg);
            post.addParameter("UserNumber", phones);
            post.addParameter("SerialNumber", "");
            post.addParameter("ScheduleTime", "");
            post.addParameter("ExtendAccessNum", "");
            post.addParameter("f", "1");
            httpclient.executeMethod(post);
            String res = new String(post.getResponseBody(), "gbk");
            log.info(res);
            String convertRes = convertRes(res);
            resVo = JSON.parseObject(convertRes, SmsSendResVo.class);
        } catch (Exception e) {
            throw new McloudHandlerException("调用短信发送异常！");
        }
        return resVo;
    }

    /**
     * 短信回执
     *
     * @return 回执结果
     */
    public SmsReportResVo report() {
        SmsReportResVo resVo;
        try {
            String send = "/report.do";
            String url = smsProperties.getBaseUrl() + send;
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod(url);
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
            post.addParameter("SpCode", smsProperties.getSpCode());
            post.addParameter("LoginName", smsProperties.getLoginName());
            post.addParameter("Password", smsProperties.getPassword());
            httpclient.executeMethod(post);
            String res = new String(post.getResponseBody(), "gbk");
            log.info(res);
            String convertRes = convertRes(res);
            resVo = JSON.parseObject(convertRes, SmsReportResVo.class);
        } catch (Exception e) {
            throw new McloudHandlerException("调用短信回执异常！");
        }
        return resVo;
    }

    /**
     * 结果转换
     *
     * @param res 结果
     * @return 转换结果
     */
    private String convertRes(String res) {
        String jsonString = "";
        if (!StringUtils.isEmpty(res)) {
            List<String> keyValues = Arrays.asList(res.split("&"));
            if (!CollectionUtils.isEmpty(keyValues)) {
                Map<String, Object> map = Maps.newHashMap();
                for (String e : keyValues) {
                    String[] keyValue = e.split("=");
                    String key = e.split("=")[0];
                    Object value = "";
                    if (keyValue.length > 1) {
                        value = e.split("=")[1];
                    }
                    map.put(key, value);
                }
                jsonString = JSON.toJSONString(map);
            }
        }
        return jsonString;
    }
}
