package reimburse.cuc.com.bean;

import java.util.List;

/**
 * 
 * `travel_reimbursement_uuid` int(11) NOT NULL AUTO_INCREMENT,
 * `travel_reimbursement_start_city` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_end_city` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_start_date` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_end_date` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_traffic_allowance` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_board_allowance` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_name` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_job_title` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_cause` varchar(3000) NOT NULL DEFAULT '',
 * `travel_reimbursement_total_amount` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_submit_date_time` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_project_name` varchar(60) NOT NULL DEFAULT '',
 * `travel_reimbursement_daily_cost_ids` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_traffic_cost_ids` varchar(30) NOT NULL DEFAULT '',
 * `travel_reimbursement_user_id` int(11) NOT NULL,
 * 
 * @author hp1
 * 
 */
public class Travel_Reimbursement {
	private Integer travel_reimbursement_uuid;
	private String travel_reimbursement_start_city;
	private String travel_reimbursement_end_city;
	private String travel_reimbursement_start_date;
	private String travel_reimbursement_end_date;
	private String travel_reimbursement_traffic_allowance;
	private String travel_reimbursement_board_allowance;
	private String travel_reimbursement_name;
	private String travel_reimbursement_job_title;
	private String travel_reimbursement_cause;
	private String travel_reimbursement_total_amount;
	private String travel_reimbursement_submit_date_time;
	private String travel_reimbursement_project_name;
	private String travel_reimbursement_daily_cost_ids;
	private String travel_reimbursement_traffic_cost_ids;
	private Integer travel_reimbursement_user_id;
	private Integer checker_account_id;
	private String endTime;
	private String check_status;
	private String comments;
	private String checker_name;
	private Integer project_uuid;
	
	private List<Traffic_Cost> traffic_Costs;
	
	private List<DailyCost> dailyCosts;

	private List<TongyiXiaofeiBean> tongyiXiaofeiBeans;

	public Travel_Reimbursement() {
		
	}

	public Travel_Reimbursement(
			String travel_reimbursement_start_city,
			String travel_reimbursement_end_city,
			String travel_reimbursement_start_date,
			String travel_reimbursement_end_date,
			String travel_reimbursement_traffic_allowance,
			String travel_reimbursement_board_allowance,
			String travel_reimbursement_name,
			String travel_reimbursement_job_title,
			String travel_reimbursement_cause,
			String travel_reimbursement_total_amount,
			String travel_reimbursement_project_name,
			String travel_reimbursement_traffic_cost_ids,
			String travel_reimbursement_daily_cost_ids,
			Integer travel_reimbursement_user_id,
			Integer project_uuid,
			String travel_reimbursement_submit_date_time) {
		this.travel_reimbursement_start_city = travel_reimbursement_start_city;
		this.travel_reimbursement_end_city = travel_reimbursement_end_city;
		this.travel_reimbursement_start_date = travel_reimbursement_start_date;
		this.travel_reimbursement_end_date = travel_reimbursement_end_date;
		this.travel_reimbursement_traffic_allowance = travel_reimbursement_traffic_allowance;
		this.travel_reimbursement_board_allowance = travel_reimbursement_board_allowance;
		this.travel_reimbursement_name = travel_reimbursement_name;
		this.travel_reimbursement_job_title = travel_reimbursement_job_title;
		this.travel_reimbursement_cause = travel_reimbursement_cause;
		this.travel_reimbursement_total_amount = travel_reimbursement_total_amount;
		this.travel_reimbursement_submit_date_time = travel_reimbursement_submit_date_time;
		this.travel_reimbursement_project_name = travel_reimbursement_project_name;
		this.travel_reimbursement_daily_cost_ids = travel_reimbursement_daily_cost_ids;
		this.travel_reimbursement_traffic_cost_ids = travel_reimbursement_traffic_cost_ids;
		this.travel_reimbursement_user_id = travel_reimbursement_user_id;
		this.project_uuid = project_uuid;
	}

	
	


	public Integer getTravel_reimbursement_uuid() {
		return travel_reimbursement_uuid;
	}

	public void setTravel_reimbursement_uuid(Integer travel_reimbursement_uuid) {
		this.travel_reimbursement_uuid = travel_reimbursement_uuid;
	}

	public String getTravel_reimbursement_start_city() {
		return travel_reimbursement_start_city;
	}

	public void setTravel_reimbursement_start_city(
			String travel_reimbursement_start_city) {
		this.travel_reimbursement_start_city = travel_reimbursement_start_city;
	}

	public String getTravel_reimbursement_end_city() {
		return travel_reimbursement_end_city;
	}

	public void setTravel_reimbursement_end_city(
			String travel_reimbursement_end_city) {
		this.travel_reimbursement_end_city = travel_reimbursement_end_city;
	}

	public String getTravel_reimbursement_start_date() {
		return travel_reimbursement_start_date;
	}

	public void setTravel_reimbursement_start_date(
			String travel_reimbursement_start_date) {
		this.travel_reimbursement_start_date = travel_reimbursement_start_date;
	}

	public String getTravel_reimbursement_end_date() {
		return travel_reimbursement_end_date;
	}

	public void setTravel_reimbursement_end_date(
			String travel_reimbursement_end_date) {
		this.travel_reimbursement_end_date = travel_reimbursement_end_date;
	}

	public String getTravel_reimbursement_traffic_allowance() {
		return travel_reimbursement_traffic_allowance;
	}

	public void setTravel_reimbursement_traffic_allowance(
			String travel_reimbursement_traffic_allowance) {
		this.travel_reimbursement_traffic_allowance = travel_reimbursement_traffic_allowance;
	}

	public String getTravel_reimbursement_board_allowance() {
		return travel_reimbursement_board_allowance;
	}

	public void setTravel_reimbursement_board_allowance(
			String travel_reimbursement_board_allowance) {
		this.travel_reimbursement_board_allowance = travel_reimbursement_board_allowance;
	}

	public String getTravel_reimbursement_name() {
		return travel_reimbursement_name;
	}

	public void setTravel_reimbursement_name(String travel_reimbursement_name) {
		this.travel_reimbursement_name = travel_reimbursement_name;
	}

	public String getTravel_reimbursement_job_title() {
		return travel_reimbursement_job_title;
	}

	public void setTravel_reimbursement_job_title(
			String travel_reimbursement_job_title) {
		this.travel_reimbursement_job_title = travel_reimbursement_job_title;
	}

	public String getTravel_reimbursement_cause() {
		return travel_reimbursement_cause;
	}

	public void setTravel_reimbursement_cause(String travel_reimbursement_cause) {
		this.travel_reimbursement_cause = travel_reimbursement_cause;
	}

	public String getTravel_reimbursement_total_amount() {
		return travel_reimbursement_total_amount;
	}

	public void setTravel_reimbursement_total_amount(
			String travel_reimbursement_total_amount) {
		this.travel_reimbursement_total_amount = travel_reimbursement_total_amount;
	}

	public String getTravel_reimbursement_submit_date_time() {
		return travel_reimbursement_submit_date_time;
	}

	public void setTravel_reimbursement_submit_date_time(
			String travel_reimbursement_submit_date_time) {
		this.travel_reimbursement_submit_date_time = travel_reimbursement_submit_date_time;
	}

	public String getTravel_reimbursement_project_name() {
		return travel_reimbursement_project_name;
	}

	public void setTravel_reimbursement_project_name(
			String travel_reimbursement_project_name) {
		this.travel_reimbursement_project_name = travel_reimbursement_project_name;
	}

	public String getTravel_reimbursement_daily_cost_ids() {
		return travel_reimbursement_daily_cost_ids;
	}

	public void setTravel_reimbursement_daily_cost_ids(
			String travel_reimbursement_daily_cost_ids) {
		this.travel_reimbursement_daily_cost_ids = travel_reimbursement_daily_cost_ids;
	}

	public String getTravel_reimbursement_traffic_cost_ids() {
		return travel_reimbursement_traffic_cost_ids;
	}

	public Integer getTravel_reimbursement_user_id() {
		return travel_reimbursement_user_id;
	}

	public void setTravel_reimbursement_user_id(Integer travel_reimbursement_user_id) {
		this.travel_reimbursement_user_id = travel_reimbursement_user_id;
	}

	public void setTravel_reimbursement_traffic_cost_ids(
			String travel_reimbursement_traffic_cost_ids) {
		this.travel_reimbursement_traffic_cost_ids = travel_reimbursement_traffic_cost_ids;
	}

    	

	public Integer getChecker_account_id() {
		return checker_account_id;
	}

	public void setChecker_account_id(Integer checker_account_id) {
		this.checker_account_id = checker_account_id;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCheck_status() {
		return check_status;
	}

	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getChecker_name() {
		return checker_name;
	}

	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}

	
	
	
	
	public Integer getProject_uuid() {
		return project_uuid;
	}

	public void setProject_uuid(Integer project_uuid) {
		this.project_uuid = project_uuid;
	}

	public List<Traffic_Cost> getTraffic_Costs() {
		return traffic_Costs;
	}

	public void setTraffic_Costs(List<Traffic_Cost> traffic_Costs) {
		this.traffic_Costs = traffic_Costs;
	}

	public List<DailyCost> getDailyCosts() {
		return dailyCosts;
	}

	public void setDailyCosts(List<DailyCost> dailyCosts) {
		this.dailyCosts = dailyCosts;
	}

	public List<TongyiXiaofeiBean> getTongyiXiaofeiBeans() {
		return tongyiXiaofeiBeans;
	}

	public void setTongyiXiaofeiBeans(List<TongyiXiaofeiBean> tongyiXiaofeiBeans) {
		this.tongyiXiaofeiBeans = tongyiXiaofeiBeans;
	}

	@Override
	public String toString() {
		return "Travel_Reimbursement [travel_reimbursement_start_city="
				+ travel_reimbursement_start_city
				+ ", travel_reimbursement_end_city="
				+ travel_reimbursement_end_city
				+ ", travel_reimbursement_start_date="
				+ travel_reimbursement_start_date
				+ ", travel_reimbursement_end_date="
				+ travel_reimbursement_end_date
				+ ", travel_reimbursement_traffic_allowance="
				+ travel_reimbursement_traffic_allowance
				+ ", travel_reimbursement_board_allowance="
				+ travel_reimbursement_board_allowance
				+ ", travel_reimbursement_name=" + travel_reimbursement_name
				+ ", travel_reimbursement_job_title="
				+ travel_reimbursement_job_title
				+ ", travel_reimbursement_cause=" + travel_reimbursement_cause
				+ ", travel_reimbursement_total_amount="
				+ travel_reimbursement_total_amount
				+ ", travel_reimbursement_submit_date_time="
				+ travel_reimbursement_submit_date_time
				+ ", travel_reimbursement_project_name="
				+ travel_reimbursement_project_name
				+ ", travel_reimbursement_daily_cost_ids="
				+ travel_reimbursement_daily_cost_ids
				+ ", travel_reimbursement_traffic_cost_ids="
				+ travel_reimbursement_traffic_cost_ids
				+ ", travel_reimbursement_user_id="
				+ travel_reimbursement_user_id + "]";
	}

	
	
    
	  
	 
}
