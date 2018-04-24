package reimburse.cuc.com.bean;

/**
 * Created by hp1 on 2017/9/15.
 */
public interface Constants {
    String URL_PREFIX = "http://";
    //10.192.126.142   1.202.41.149
    String IPADDR = "192.168.199.139";
    String APPADDR = ":8080/ssmweb";
    String BASE_URL = URL_PREFIX+IPADDR+APPADDR;
    String FILE_BASE_DIR = URL_PREFIX+IPADDR+":8080/baoxiaodantu/";
    String OKHTTP_TEST_UPLOAD = BASE_URL+"/okhttpServlet";
    String TRAFFIC_COST_SERVLET = BASE_URL+"/androidSavetrafficCostServlet";
    String FILE_TEST_CONTROLLER_AndroidSaveDailyCostForm = BASE_URL+"/fileTestController/fileTestAndroidSaveDailyCostForm";
    String SAVE_PICURLS = BASE_URL+"/baoXiaoDan/saveBaoXiaoDan";
    String LOGIN_REQUEST_URL = BASE_URL+"/user/login";
    String SAVE_CAIGOU = BASE_URL+"/caigou/androidSaveCaigou";
    String Get_ALL_Caigou_List = BASE_URL+"/caigou/getALLCaigouList";
    String BaoXiaoDan_SaveBaoXiaoDan = BASE_URL+"/baoXiaoDan/androidSaveBaoXiaoDan";
    String SAVE_FAPIAO_IMG_URL = BASE_URL+"/file/androidSaveFapiaoImg";
    String ANDROID_USER_LOGIN_URL = BASE_URL+"/user/androidlogin";
    String SAVE_CAIGOUFEI_URL = BASE_URL+"/caigoufei/androidSaveCaigoufei";
    String SAVE_JiaoTongfei_URL = BASE_URL+"/jiaotongfei/androidSaveJiaoTongfei";
    String SAVE_Canfei_URL = BASE_URL+"/canfei/saveCanfei";
    String GET_ALLXIAOFEI_URL = BASE_URL+"/allXiaofeiController/androidGetAllXiaoFeiJson";
    String AndroidGetAll_Daily_Traffic_Cost_Json = BASE_URL+"/allXiaofeiController/androidGetAll_Daily_Traffic_Cost_Json";
    String FAPIAO_URL_PREFIX = URL_PREFIX+IPADDR+":8080"+"/baoxiaodantu/";
    String GetBaoxiaodanWithtXiaofeiItemByUserId =BASE_URL+"/baoXiaoDan/androidGetBaoxiaodanWithtXiaofeiItemByUserId";

    /*得到所有工程的URL*/
    String GET_ALL_PROJECT_URL =BASE_URL+"/project/androidGetAllProjectJson";

    /**
     * 得到用户和与用户相关联的项目信息
     */

    String AndroidGetAllProjectJson = BASE_URL+"/user/androidGetAllProjectJson";

    /**
     * 得到项目负责人所管理的项目信息
     */
    ///project/androidGetProLeaderAllProjectByJobNum
    String AndroidGetProLeaderAllProjectByJobNum = BASE_URL+"/project/androidGetProLeaderAllProjectByJobNum";

    /*安卓用户得到柱状图JSON*/
    String AndroidGetBarChart_URL = BASE_URL+"/baoXiaoDan/androidGetBarChart";
    String GetAllXiaoFeiPieChart = BASE_URL+"/allXiaofeiController/androidGetAllXiaoFeiPieChart";
    // baoXiaoDan/androidGetBarChart

    // 得到所有的交通类型
    String AndroidGetAllTrafficTypeWithFareBasisList = BASE_URL+"/traffic_Type_Controller/androidGetAllTrafficTypeWithFareBasisList";

    //保存交通费
    String AndroidSaveTraffic_Cost = BASE_URL+"/traffic_Cost/androidSaveTraffic_Cost";

    /**
     * 以JSON形式保存交通费
     * androidSaveTrafficCostForm
     */

    String ANDROID_SAVE_TRAFFIC_COST = BASE_URL+"/traffic_Cost/androidSaveTrafficCostForm";


    //从服务端获取当前用的所有交通费
    String AndroidGetAllTraffic_CostByUserId = BASE_URL+"/traffic_Cost/androidGetAllTraffic_CostByUserId";

    /**
     * 得到所有的日常花费类型
     */
    String AndroidGetAllCategoryListJson  = BASE_URL+"/expenseCategory/androidGetAllCategoryListJson";
    String DAILY_COST_FORM_UPLOAD = BASE_URL+"/dailyCost/androidSaveDailyCostForm";

    /**
     *根据移动端用户的ID得到自己的所有日常消费
     */
    String AndroidGetAllDailyCostJsonByUserId = BASE_URL+"/dailyCost/androidGetAllDailyCostJsonByUserId";

    /**
     * 保存差旅报销费用
     */
    String AndroidSaveTravel_Reimbursement = BASE_URL+"/travel_Reimbursement_Controller/androidSaveTravel_Reimbursement";

    /**
     * 保存日常报销
     */
    String AndroidSaveDailyReimbursement =BASE_URL+"/dailyReimController/androidSaveDailyReimbursement";

    String ANDROID_GET_All_DAILY_AND_TRAFFIC_COST_JSON = BASE_URL+"/allXiaofeiController/android_GET_All_DAILY_AND_TRAFFIC_COST_JSON";

    /**
     * 查询安卓用户所有的日常报销列表
     */
    String ANDROID_GET_OWN_DAILY_REIM_LIST_BY_USER_ID =  BASE_URL+"/dailyReimController/androidGetOwnDailyReimListByUserID";

    /**
     * 查询安卓用户所有的差率报销
     */
    String ANDROID_GET_OWN_TRAVEL_REIM_LIST_BY_USER_ID =  BASE_URL+"/travel_Reimbursement_Controller/androidGetOwnTravelReimListByUserID";
    String android_GET_All_DAILY_AND_TRAFFIC_COST_JSON_BY_TAG = BASE_URL+"/allXiaofeiController/android_GET_All_DAILY_AND_TRAFFIC_COST_JSON_BY_TAG";
    /**
     * 提交保存借款单路径
     */
    String AndroidSaveLoan = BASE_URL+"/loanController/androidSaveLoan";
    String ANDROID_GET_OWN_LOAN_LIST_BY_USER_ID =  BASE_URL+"/loanController/androidGetOwnLoanListByUserID";
    /**
     * 安卓根据工号查询员工信息
     */
    String AndroidSearchEmpByJobNum = BASE_URL+"/employee/androidSearchEmpByJobNum";
    String addProjectReimUser = BASE_URL+"/employee/addProjectReimUser";
    String androidGetGetAllProjectWithPro_BudgetByLeaderJonNum = BASE_URL+"/project/androidGetGetAllProjectWithPro_BudgetByLeaderJonNum";
    String ANDROID_GET_USER_HISTORY_REIMBURSEMENT_LINE = BASE_URL+"/account/ANDROID_GET_USER_HISTORY_REIMBURSEMENT_LINE";
    String ANDROID_SEARCH_COMPANY_USER_BY_JOBNUM = BASE_URL+"/companyUserController/androidSearchCompanyUserByJobNum";
    String ADD_PROJECT_REIM_USER_WITH_COMPANY_USER = BASE_URL+"/user/ADD_PROJECT_REIM_USER_WITH_COMPANY_USER";
    String ANDROID_GET_TOTAL_COST_BY_USER_ID = BASE_URL+"/project/ANDROID_GET_TOTAL_COST_BY_USER_ID";
}
