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
     * 交通厅
     */
    public static class JTT {
        public static final String APPID = "{68AD88CF-233E-E3E0-5035-7DF03742600A}";//应用编码（固定不变）
        public static final String DEVID = "292E-B46D-62AB-1018";//机器码（固定不变）
        public static final String RESTCLIENTURL = "http://59.219.195.5:6080/DATACENTERPro/api";
        public static final String REQUESTPATH = "/dataShare/getZWResource";


        public static final String SJTSYT_WLYYCZQCJYXKZXX = "0407F4C69AA74F4F8A16AD16B7F25DF1";// //1.网路预约出租汽车经营序号证信息
        public static final String SJTSYT_YYCL = "B34D94FE604C41C2A7D4CAE3A1A015C1"; //2.营运车辆道路运输证信息
        public static final String SJTSYT_LYKY = "22F36AF0303848C5B53BC4EC83B39F38"; //3.旅游客运车辆动态信息
        public static final String SJTSYT_GJZDGLL = "ABFB831DF044403C974E8430424FF0B7";  //4.国家重点公路建设项目初步设计审批信息（缺少 技术标准、里程）
        public static final String SJTSYT_LYKYCLJC = "0E7BEE8267D7411E816C572D2F078219"; //5.旅游客运车辆基础信息
        public static final String SJTSYT_JSRZC= "FFE8A3ADD59D44F9AF45E02EC6DA30BE";  //6.驾驶员从业资格证信息
        public static final String SJTSYT_YSCB= "ED1E53F51F9F49ED95872D5ADDB1536D";  //7. 运输船舶所有权登记证书信息
        public static final String SJTSYT_YSCBSYQ= "E41A0C389E2E48B0B64B4F128D1D28DE";  //8..航道通航条件影响评价审核信息
        public static final String SJTSYT_GJZDSYGC= "B8BCFF1F09A84045B360DBAC8D5695B1";  //9. 国家重点水运工程建设项目初步设计审批信息


        public static final String URL= "http://59.219.195.5:6080/DATACENTERPro/elssp/v1/GetRestResource.action";
        public static final String QUERYTYPE = "1601";// 请求类型
        public static final String SOURCE = "zw";//政务专用接口 固定值不用修改
        public static final String USER = "sjsq_dzzwb";//用户名
        public static final String PWD = "JQ100jqsjsqdzzwb#$";//密码

    }


}
