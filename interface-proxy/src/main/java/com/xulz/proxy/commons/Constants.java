package com.xulz.proxy.commons;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-14 19:40
 **/
public class Constants {

    public static final String AUTHORIZATION = "Basic Z3hzZDAxOjY2NjY2Ng==";

    /**
     * 人口查询
     **/
    public static final String RK_JBXX = "jbxx";
    public static final String RK_SFHC = "sfhc";

    /**
     * 民政部查询
     **/
    public static final String MZB_DR = "dr";//婚姻登记信息核验（单人）接口
    public static final String MZB_SR = "sr";//婚姻登记信息核验（双人）接口
    public static final String MZB_FR = "fr";//社会团体法人登记证书接口说明
    public static final String MZB_DB = "db";//低保对象信息服务接口
    public static final String MZB_BZFW = "bzfw";//殡葬管理火化证明查询接口
    public static final String MZB_LSET = "lset";//全国留守儿童和困境儿童信息查询
    public static final String MZB_JJH_FR = "jjhfr";//基金会法人登记证书接口
    public static final String MZB_SHZZ = "shzz";//社会组织信息接口说明

    /**
     * 状态码枚举
     */
    public static enum SysCode {
        /**
         * 成功
         */
        OK("0", "成功"),
        REQ_PARAM_ERROR("1", "请求参数错误"),
        APP_NOT_EXSIT("-2", "应用不存在"),
        IP_NO_AUTH("-4", "IP没有权限"),
        SIGN_CHECK_FAIL("-5", "签名参数sig校验失败"),
        ACCESS_FREQUENCY_OVERRUN("-6", "访问频率超限"),
        RTIME_IS_EXPIRE("-19", "rtime超时，与当前时间间隔不能超过10分钟"),
        API_NOT_AUTH("-20", "应用调用的API未经用户授权"),
        REFRESH_TOKEN_IS_EXPIRE("-21", "refresh_token已失效"),
        ACCESS_TOKEN_IS_EXPIRE("-22", "access_token已失效"),
        NATION_PLAT_CONNECT_TIMEOUT("-30", "请求失败：国家共享平台鉴权获取密钥超时"),
        NATION_PLAT_SECRET_ERROR("-31", "请求失败：国家共享平台鉴权获取密钥参数校验失败"),
        NATION_PLAT_CONFIG_FAIL("-32", "请求失败：国家共享平台鉴权组件管理没有配置对应的参数"),
        NATION_PLAT_SERVICE_CONFIG_FAIL("-33", "请求失败：国家共享平台鉴权组件配置没有配置对应的参数"),
        NATION_PLAT_PARAM_XML_FORMAT_FAIL("-34", "请求失败：国家共享平台鉴权组件封装xml参数失败"),
        NATION_PLAT("-35", "请求失败：国家共享平台授权失败"),
        GATEWAY_PLAT_CONNECT_TIMEOUT("-36", "请求失败：聚合平台鉴权获取密钥超时"),
        GATEWAY_PLAT_SERVICE_CONFIG_FAIL("-37", "请求失败：聚合平台鉴权组件配置没有配置对应的参数"),
        GATEWAY_PLAT_CONFIG_FAIL("-38", "请求失败：聚合平台鉴权组件管理没有配置对应的参数"),
        GATEWAY_PLAT_SECRET_ERROR("-39", "请求失败：聚合平台鉴权获取密钥参数校验失败"),
        GATEWAY_PLAT("-40", "请求失败：聚合平台鉴权失败"),
        EXPIRED_TOKEN("-41", "请求失败：token已失效"),
        INVALID_TOKEN("-42", "请求失败：无效token"),
        AUTH_PARAM("-43", "请求失败：授权参数解析失败"),
        NOKNOW_REQ("-44", "请求失败：找不到请求路径"),
        BLACK_LIST("-45", "请求失败：请求ip为黑名单"),
        IP_BIND("-46", "请求失败：请求ip不在应用的绑定ip范围内"),
        IP_BIND_NOBANGDING("-47", "请求失败：未备案ip，请确保ip已在管理端备案"),
        SERVICE_NOT_PUBLISH("-48", "请求失败：接口尚未发布"),
        AUTH("-49", "请求失败：应用没有授权"),
        NATION_PLAT_SIGN_REFRESH("-50", "请求失败：国家共享平台鉴权密钥失效，正在刷新中，请20秒后重试"),
        APP_ENABLE("-51", "请求失败：应用不存在或被禁用，请联系管理员启用后重试"),
        CALL_FAIL_OR_TIME_OUT("2010", "调用失败或请求超时"),
        LOGIN_SIGN_TIMESTAMP_TIMEOUT("11001", "登录失败：密钥解析失败【时间戳超时】"),
        LOGIN_SIGN_IS_NULL("11002", "登录失败：密钥解析失败【密钥为空】"),
        LOGIN_SIGN_DECODE_FAIL("11003", "登录失败：密钥解析失败"),
        LOGIN_CODE_IS_NULL("11004", "登录失败：验证码不能为空"),
        LOGIN_RANDOMSTR_IS_NULL("11005", "登录失败：随机串不能为空"),
        LOGIN_CODE_IS_EXPIRE("11006", "登录失败：验证码已过期，请重新输入"),
        LOGIN_CODE_IS_ERROR("11007", "登录失败：验证码错误，请重新输入"),
        SYS_ERROR("10014", "系统内部异常"),
        GATEWAY_ERROR("10000", "核心网关内部异常"),
        REAL_SERVER_ERROR("12000", "真实服务器报错"),
        ACCESS_FREQUENCY_OVERRUN_SERVER("12100", "访问频率超限【接口熔断】"),
        ACCESS_FREQUENCY_OVERRUN_MIN("12101", "访问频率超限【每分钟】"),
        ACCESS_FREQUENCY_OVERRUN_HOUR("12102", "访问频率超限【每小时】"),
        ACCESS_FREQUENCY_OVERRUN_DAY("12103", "访问频率超限【每天】"),
        ACCESS_FREQUENCY_OVERRUN_TOTAL("12104", "访问频率超限【访问总量】");

        private String code;
        private String name;


        private SysCode(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }
    }

}
