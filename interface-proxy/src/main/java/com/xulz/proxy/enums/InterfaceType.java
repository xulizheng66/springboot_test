package com.xulz.proxy.enums;

import com.xulz.proxy.commons.Constants;

/**
 * @Description: 接口类型枚举
 * @Author: xulz
 * @Date： 2019/9/20 17:35
 * @version: 1.0
 */
public enum InterfaceType {
    /**
     * 婚姻登记信息核验（单人）接口
     */
    MZB_DR("s_X01201000200000000_4817", Constants.MZB_DR),
    MZB_SR("s_X01201000200000000_4818", Constants.MZB_SR),
    MZB_FR("s_12100000000018032A_3942", Constants.MZB_FR),
    MZB_DB("s_X01201000200000000_4353", Constants.MZB_DB),
    MZB_BZFW("s_12100000000018032A_3939", Constants.MZB_BZFW),
    MZB_LSET("s_X01201000200000000_4352", Constants.MZB_LSET),
    MZB_JJH_FR("s_12100000000018032A_3940", Constants.MZB_JJH_FR),
    MZB_SHZZ("s_111000000000131433_3737", Constants.MZB_SHZZ);

    private String sid;
    private String type;

    private InterfaceType(String sid, String type) {
        this.sid = sid;
        this.type = type;
    }

    public String getSid() {
        return sid;
    }

    public String getType() {
        return type;
    }

}
