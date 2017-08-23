package com.xiaoyetan.common.mq.domain;

/**
 * Created by xiaoyetan on 2017/8/18.
 * 调crm退订接口传输类
 */
public class UnsubCrmParam {
    private String mobile;
    private String serviceCode;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
