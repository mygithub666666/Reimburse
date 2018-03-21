package reimburse.cuc.com.bean;

import java.util.List;

/**
 * daily_reim_uuid int primary key auto_increment, daily_reim_cause VARCHAR(60)
 * not null DEFAULT '', daily_reim_project_name VARCHAR(60) not null DEFAULT '',
 * daily_reim_amount VARCHAR(60) not null DEFAULT '',
 * daily_reim_traffic_cost_ids varchar(30) NOT NULL DEFAULT '',
 * daily_reim_user_id int(11) NOT NULL, daily_reim_account_id int(11) NOT NULL
 * DEFAULT '0', daily_reim_check_status varchar(20) NOT NULL DEFAULT '待审核',
 * daily_reim_comments varchar(255) DEFAULT '请填写审核意见', daily_reim_checker_name
 * varchar(255) NOT NULL DEFAULT '未指定', daily_reim_submitTime VARCHAR(30) NOT
 * null DEFAULT '', daily_reim_endTime varchar(30) DEFAULT '还未完成'
 */
public class DailyReim {
    private Integer daily_reim_uuid;
    private String daily_reim_cause;
    private String daily_reim_project_name;
    private String daily_reim_amount;
    private String daily_reim_traffic_cost_ids;
    private String daily_reim_daily_cost_ids;
    private String daily_reim_submitTime;
    private String daily_reim_endTime;
    private String daily_reim_check_status;
    private String daily_reim_comments;
    private Integer daily_reim_check_account_id;
    private String daily_reim_checker_name;
    private Integer daily_reim_user_id;
    private Integer project_uuid;
    private Integer daily_reim_user_uuid;
    private String daily_reim_user_name;
    private String daily_reim_mobile_phone_number;
    private String daily_reim_bank_number;
    private String daily_reim_bank_name;
    private String is_re_check;
    private String re_check_account_uuid;
    private String re_check_account_username;
    private String re_check_comments;

    private List<TongyiXiaofeiBean> tongyiXiaofeiBeans;


    public DailyReim() {

    }


    public DailyReim(String daily_reim_cause, String daily_reim_project_name,
                     String daily_reim_amount, String daily_reim_traffic_cost_ids,
                     String daily_reim_daily_cost_ids, String daily_reim_submitTime,
                     Integer daily_reim_user_id, Integer project_uuid) {

        this.daily_reim_cause = daily_reim_cause;
        this.daily_reim_project_name = daily_reim_project_name;
        this.daily_reim_amount = daily_reim_amount;
        this.daily_reim_traffic_cost_ids = daily_reim_traffic_cost_ids;
        this.daily_reim_daily_cost_ids = daily_reim_daily_cost_ids;
        this.daily_reim_submitTime = daily_reim_submitTime;
        this.daily_reim_user_id = daily_reim_user_id;
        this.project_uuid = project_uuid;
    }

    public DailyReim(String daily_reim_cause, String daily_reim_project_name,
                     String daily_reim_amount, String daily_reim_traffic_cost_ids,
                     String daily_reim_daily_cost_ids, Integer daily_reim_user_id, Integer project_uuid) {

        this.daily_reim_cause = daily_reim_cause;
        this.daily_reim_project_name = daily_reim_project_name;
        this.daily_reim_amount = daily_reim_amount;
        this.daily_reim_traffic_cost_ids = daily_reim_traffic_cost_ids;
        this.daily_reim_daily_cost_ids = daily_reim_daily_cost_ids;
        this.daily_reim_user_id = daily_reim_user_id;
        this.project_uuid = project_uuid;
    }


    public DailyReim(String daily_reim_cause, String daily_reim_project_name, String daily_reim_amount, String daily_reim_traffic_cost_ids, String daily_reim_daily_cost_ids, Integer project_uuid, Integer daily_reim_user_uuid, String daily_reim_user_name, String daily_reim_mobile_phone_number, String daily_reim_bank_number, String daily_reim_bank_name) {
        this.daily_reim_cause = daily_reim_cause;
        this.daily_reim_project_name = daily_reim_project_name;
        this.daily_reim_amount = daily_reim_amount;
        this.daily_reim_traffic_cost_ids = daily_reim_traffic_cost_ids;
        this.daily_reim_daily_cost_ids = daily_reim_daily_cost_ids;
        this.project_uuid = project_uuid;
        this.daily_reim_user_uuid = daily_reim_user_uuid;
        this.daily_reim_user_name = daily_reim_user_name;
        this.daily_reim_mobile_phone_number = daily_reim_mobile_phone_number;
        this.daily_reim_bank_number = daily_reim_bank_number;
        this.daily_reim_bank_name = daily_reim_bank_name;
    }

    public Integer getDaily_reim_uuid() {
        return daily_reim_uuid;
    }


    public void setDaily_reim_uuid(Integer daily_reim_uuid) {
        this.daily_reim_uuid = daily_reim_uuid;
    }


    public String getDaily_reim_cause() {
        return daily_reim_cause;
    }


    public void setDaily_reim_cause(String daily_reim_cause) {
        this.daily_reim_cause = daily_reim_cause;
    }


    public String getDaily_reim_project_name() {
        return daily_reim_project_name;
    }


    public void setDaily_reim_project_name(String daily_reim_project_name) {
        this.daily_reim_project_name = daily_reim_project_name;
    }


    public String getDaily_reim_amount() {
        return daily_reim_amount;
    }


    public void setDaily_reim_amount(String daily_reim_amount) {
        this.daily_reim_amount = daily_reim_amount;
    }


    public String getDaily_reim_traffic_cost_ids() {
        return daily_reim_traffic_cost_ids;
    }


    public void setDaily_reim_traffic_cost_ids(String daily_reim_traffic_cost_ids) {
        this.daily_reim_traffic_cost_ids = daily_reim_traffic_cost_ids;
    }


    public String getDaily_reim_daily_cost_ids() {
        return daily_reim_daily_cost_ids;
    }


    public void setDaily_reim_daily_cost_ids(String daily_reim_daily_cost_ids) {
        this.daily_reim_daily_cost_ids = daily_reim_daily_cost_ids;
    }


    public String getDaily_reim_submitTime() {
        return daily_reim_submitTime;
    }


    public void setDaily_reim_submitTime(String daily_reim_submitTime) {
        this.daily_reim_submitTime = daily_reim_submitTime;
    }


    public String getDaily_reim_endTime() {
        return daily_reim_endTime;
    }


    public void setDaily_reim_endTime(String daily_reim_endTime) {
        this.daily_reim_endTime = daily_reim_endTime;
    }


    public String getDaily_reim_check_status() {
        return daily_reim_check_status;
    }


    public void setDaily_reim_check_status(String daily_reim_check_status) {
        this.daily_reim_check_status = daily_reim_check_status;
    }


    public String getDaily_reim_comments() {
        return daily_reim_comments;
    }


    public void setDaily_reim_comments(String daily_reim_comments) {
        this.daily_reim_comments = daily_reim_comments;
    }


    public Integer getDaily_reim_check_account_id() {
        return daily_reim_check_account_id;
    }


    public void setDaily_reim_check_account_id(Integer daily_reim_check_account_id) {
        this.daily_reim_check_account_id = daily_reim_check_account_id;
    }


    public String getDaily_reim_checker_name() {
        return daily_reim_checker_name;
    }


    public void setDaily_reim_checker_name(String daily_reim_checker_name) {
        this.daily_reim_checker_name = daily_reim_checker_name;
    }


    public Integer getDaily_reim_user_id() {
        return daily_reim_user_id;
    }


    public void setDaily_reim_user_id(Integer daily_reim_user_id) {
        this.daily_reim_user_id = daily_reim_user_id;
    }


    public Integer getProject_uuid() {
        return project_uuid;
    }


    public void setProject_uuid(Integer project_uuid) {
        this.project_uuid = project_uuid;
    }


    public List<TongyiXiaofeiBean> getTongyiXiaofeiBeans() {
        return tongyiXiaofeiBeans;
    }

    public void setTongyiXiaofeiBeans(List<TongyiXiaofeiBean> tongyiXiaofeiBeans) {
        this.tongyiXiaofeiBeans = tongyiXiaofeiBeans;
    }

    public Integer getDaily_reim_user_uuid() {
        return daily_reim_user_uuid;
    }

    public void setDaily_reim_user_uuid(Integer daily_reim_user_uuid) {
        this.daily_reim_user_uuid = daily_reim_user_uuid;
    }

    public String getDaily_reim_user_name() {
        return daily_reim_user_name;
    }

    public void setDaily_reim_user_name(String daily_reim_user_name) {
        this.daily_reim_user_name = daily_reim_user_name;
    }

    public String getDaily_reim_mobile_phone_number() {
        return daily_reim_mobile_phone_number;
    }

    public void setDaily_reim_mobile_phone_number(String daily_reim_mobile_phone_number) {
        this.daily_reim_mobile_phone_number = daily_reim_mobile_phone_number;
    }

    public String getDaily_reim_bank_number() {
        return daily_reim_bank_number;
    }

    public void setDaily_reim_bank_number(String daily_reim_bank_number) {
        this.daily_reim_bank_number = daily_reim_bank_number;
    }

    public String getDaily_reim_bank_name() {
        return daily_reim_bank_name;
    }

    public void setDaily_reim_bank_name(String daily_reim_bank_name) {
        this.daily_reim_bank_name = daily_reim_bank_name;
    }

    public String getIs_re_check() {
        return is_re_check;
    }

    public void setIs_re_check(String is_re_check) {
        this.is_re_check = is_re_check;
    }

    public String getRe_check_account_uuid() {
        return re_check_account_uuid;
    }

    public void setRe_check_account_uuid(String re_check_account_uuid) {
        this.re_check_account_uuid = re_check_account_uuid;
    }

    public String getRe_check_account_username() {
        return re_check_account_username;
    }

    public void setRe_check_account_username(String re_check_account_username) {
        this.re_check_account_username = re_check_account_username;
    }

    public String getRe_check_comments() {
        return re_check_comments;
    }

    public void setRe_check_comments(String re_check_comments) {
        this.re_check_comments = re_check_comments;
    }

    @Override
    public String toString() {
        return "DailyReim [daily_reim_cause=" + daily_reim_cause
                + ", daily_reim_project_name=" + daily_reim_project_name
                + ", daily_reim_amount=" + daily_reim_amount
                + ", daily_reim_traffic_cost_ids="
                + daily_reim_traffic_cost_ids + ", daily_reim_daily_cost_ids="
                + daily_reim_daily_cost_ids + ", daily_reim_submitTime="
                + daily_reim_submitTime + ", daily_reim_user_id="
                + daily_reim_user_id + "]";
    }


}
