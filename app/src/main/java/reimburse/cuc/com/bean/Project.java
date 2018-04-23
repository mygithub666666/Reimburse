package reimburse.cuc.com.bean;

import java.util.List;

/**
 * 
 * `id` int(11) NOT NULL AUTO_INCREMENT, `p_id` varchar(11) NOT NULL, `p_leader`
 * varchar(16) NOT NULL, `p_type` varchar(16) NOT NULL, `p_startdate`
 * varchar(16) NOT NULL, `p_enddate` varchar(16) NOT NULL, `p_totalmoney`
 * varchar(11) NOT NULL, `p_money_source` varchar(11) NOT NULL,
 * `p_finished_money` varchar(11) NOT NULL, `p_new_money` varchar(11) NOT NULL,
 * `pro_virtual_left_reimbursable_amount` varchar(30) NOT NULL DEFAULT '0',
 * `pro_leader_job_num` varchar(30) NOT NULL, `pro_mems` varchar(1024) NOT NULL
 * DEFAULT '无', DEFAULT '0',
 * 
 * @author hp1
 * 
 */
public class Project {

	private Integer id;
	private String p_id;
	private String p_leader;
	private String p_name;
	private String p_type;
	private String p_startdate;
	private String p_enddate;
	private String p_totalmoney;
	private String p_money_source;
	private String p_finished_money;
	private String p_new_money;
	private String p_reimbursable_amount;
	private String pro_virtual_left_reimbursable_amount;
	private String pro_leader_job_num;
	private String pro_mems;
	private String pro_has_budget;
	private String pro_final_passed_money_reim;
	private String elecPassedDailyReimAmount;
	private String elecPassedTravelReimAmount;

	private List<BudgetDetail> projectBudgetDetailList;

	private List<Pro_received_money> pro_received_moneys;

	private List<Pro_Budget> pro_Budgets;

	private List<User> users;


	public Project() {

	}

	public Project(String p_id, String p_leader, String p_name, String p_type,
			String p_startdate, String p_enddate, String p_totalmoney,
			String p_money_source, String p_finished_money, String p_new_money) {
		this.p_id = p_id;
		this.p_leader = p_leader;
		this.p_name = p_name;
		this.p_type = p_type;
		this.p_startdate = p_startdate;
		this.p_enddate = p_enddate;
		this.p_totalmoney = p_totalmoney;
		this.p_money_source = p_money_source;
		this.p_finished_money = p_finished_money;
		this.p_new_money = p_new_money;
	}

	public Project(Integer id, String p_id, String p_leader, String p_name,
			String p_type, String p_startdate, String p_enddate,
			String p_totalmoney, String p_money_source,
			String p_finished_money, String p_new_money,
			List<BudgetDetail> projectBudgetDetailList) {
		this.id = id;
		this.p_id = p_id;
		this.p_leader = p_leader;
		this.p_name = p_name;
		this.p_type = p_type;
		this.p_startdate = p_startdate;
		this.p_enddate = p_enddate;
		this.p_totalmoney = p_totalmoney;
		this.p_money_source = p_money_source;
		this.p_finished_money = p_finished_money;
		this.p_new_money = p_new_money;
		this.projectBudgetDetailList = projectBudgetDetailList;
	}

	public Project(String p_id, String p_leader, String p_name, String p_type,
			String p_startdate, String p_enddate, String p_totalmoney,
			String p_money_source, String p_finished_money, String p_new_money,
			String p_reimbursable_amount,
			String pro_virtual_left_reimbursable_amount,
			String pro_leader_job_num, String pro_mems) {
		this.p_id = p_id;
		this.p_leader = p_leader;
		this.p_name = p_name;
		this.p_type = p_type;
		this.p_startdate = p_startdate;
		this.p_enddate = p_enddate;
		this.p_totalmoney = p_totalmoney;
		this.p_money_source = p_money_source;
		this.p_finished_money = p_finished_money;
		this.p_new_money = p_new_money;
		this.p_reimbursable_amount = p_reimbursable_amount;
		this.pro_virtual_left_reimbursable_amount = pro_virtual_left_reimbursable_amount;
		this.pro_leader_job_num = pro_leader_job_num;
		this.pro_mems = pro_mems;
	}

	public Project(Integer id, String p_id, String p_leader, String p_name,
			String p_type, String p_startdate, String p_enddate,
			String p_totalmoney, String p_money_source,
			String p_finished_money, String p_new_money,
			String p_reimbursable_amount) {
		this.id = id;
		this.p_id = p_id;
		this.p_leader = p_leader;
		this.p_name = p_name;
		this.p_type = p_type;
		this.p_startdate = p_startdate;
		this.p_enddate = p_enddate;
		this.p_totalmoney = p_totalmoney;
		this.p_money_source = p_money_source;
		this.p_finished_money = p_finished_money;
		this.p_new_money = p_new_money;
		this.p_reimbursable_amount = p_reimbursable_amount;
	}

	public Project(Integer id, String p_id, String p_leader, String p_name, String p_type, String p_startdate, String p_enddate, String p_totalmoney, String p_money_source, String p_finished_money, String p_new_money, String p_reimbursable_amount, String pro_virtual_left_reimbursable_amount, String pro_leader_job_num, String pro_mems, String pro_has_budget, String pro_final_passed_money_reim, String elecPassedDailyReimAmount, String elecPassedTravelReimAmount) {
		this.id = id;
		this.p_id = p_id;
		this.p_leader = p_leader;
		this.p_name = p_name;
		this.p_type = p_type;
		this.p_startdate = p_startdate;
		this.p_enddate = p_enddate;
		this.p_totalmoney = p_totalmoney;
		this.p_money_source = p_money_source;
		this.p_finished_money = p_finished_money;
		this.p_new_money = p_new_money;
		this.p_reimbursable_amount = p_reimbursable_amount;
		this.pro_virtual_left_reimbursable_amount = pro_virtual_left_reimbursable_amount;
		this.pro_leader_job_num = pro_leader_job_num;
		this.pro_mems = pro_mems;
		this.pro_has_budget = pro_has_budget;
		this.pro_final_passed_money_reim = pro_final_passed_money_reim;
		this.elecPassedDailyReimAmount = elecPassedDailyReimAmount;
		this.elecPassedTravelReimAmount = elecPassedTravelReimAmount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getP_leader() {
		return p_leader;
	}

	public void setP_leader(String p_leader) {
		this.p_leader = p_leader;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getP_type() {
		return p_type;
	}

	public void setP_type(String p_type) {
		this.p_type = p_type;
	}

	public String getP_startdate() {
		return p_startdate;
	}

	public void setP_startdate(String p_startdate) {
		this.p_startdate = p_startdate;
	}

	public String getP_enddate() {
		return p_enddate;
	}

	public void setP_enddate(String p_enddate) {
		this.p_enddate = p_enddate;
	}

	public String getP_totalmoney() {
		return p_totalmoney;
	}

	public void setP_totalmoney(String p_totalmoney) {
		this.p_totalmoney = p_totalmoney;
	}

	public String getP_money_source() {
		return p_money_source;
	}

	public void setP_money_source(String p_money_source) {
		this.p_money_source = p_money_source;
	}

	public String getP_finished_money() {
		return p_finished_money;
	}

	public void setP_finished_money(String p_finished_money) {
		this.p_finished_money = p_finished_money;
	}

	public String getP_new_money() {
		return p_new_money;
	}

	public void setP_new_money(String p_new_money) {
		this.p_new_money = p_new_money;
	}

	public String getP_reimbursable_amount() {
		return p_reimbursable_amount;
	}

	public void setP_reimbursable_amount(String p_reimbursable_amount) {
		this.p_reimbursable_amount = p_reimbursable_amount;
	}

	public String getElecPassedDailyReimAmount() {
		return elecPassedDailyReimAmount;
	}

	public void setElecPassedDailyReimAmount(String elecPassedDailyReimAmount) {
		this.elecPassedDailyReimAmount = elecPassedDailyReimAmount;
	}

	public String getElecPassedTravelReimAmount() {
		return elecPassedTravelReimAmount;
	}

	public void setElecPassedTravelReimAmount(String elecPassedTravelReimAmount) {
		this.elecPassedTravelReimAmount = elecPassedTravelReimAmount;
	}

	public List<BudgetDetail> getProjectBudgetDetailList() {
		return projectBudgetDetailList;
	}

	public void setProjectBudgetDetailList(
			List<BudgetDetail> projectBudgetDetailList) {
		this.projectBudgetDetailList = projectBudgetDetailList;
	}

	public List<Pro_received_money> getPro_received_moneys() {
		return pro_received_moneys;
	}

	public void setPro_received_moneys(
			List<Pro_received_money> pro_received_moneys) {
		this.pro_received_moneys = pro_received_moneys;
	}

	public List<Pro_Budget> getPro_Budgets() {
		return pro_Budgets;
	}

	public void setPro_Budgets(List<Pro_Budget> pro_Budgets) {
		this.pro_Budgets = pro_Budgets;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getPro_virtual_left_reimbursable_amount() {
		return pro_virtual_left_reimbursable_amount;
	}

	public void setPro_virtual_left_reimbursable_amount(
			String pro_virtual_left_reimbursable_amount) {
		this.pro_virtual_left_reimbursable_amount = pro_virtual_left_reimbursable_amount;
	}

	public String getPro_leader_job_num() {
		return pro_leader_job_num;
	}

	public void setPro_leader_job_num(String pro_leader_job_num) {
		this.pro_leader_job_num = pro_leader_job_num;
	}

	public String getPro_mems() {
		return pro_mems;
	}

	public void setPro_mems(String pro_mems) {
		this.pro_mems = pro_mems;
	}

	public String getPro_has_budget() {
		return pro_has_budget;
	}

	public void setPro_has_budget(String pro_has_budget) {
		this.pro_has_budget = pro_has_budget;
	}


	
	
	public String getPro_final_passed_money_reim() {
		return pro_final_passed_money_reim;
	}

	public void setPro_final_passed_money_reim(String pro_final_passed_money_reim) {
		this.pro_final_passed_money_reim = pro_final_passed_money_reim;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", p_id=" + p_id + ", p_leader="
				+ p_leader + ", p_name=" + p_name + ", p_type=" + p_type
				+ ", p_startdate=" + p_startdate + ", p_enddate=" + p_enddate
				+ ", p_totalmoney=" + p_totalmoney + ", p_money_source="
				+ p_money_source + ", p_finished_money=" + p_finished_money
				+ ", p_new_money=" + p_new_money + ", p_reimbursable_amount="
				+ p_reimbursable_amount
				+ ", pro_virtual_left_reimbursable_amount="
				+ pro_virtual_left_reimbursable_amount
				+ ", pro_leader_job_num=" + pro_leader_job_num + ", pro_mems="
				+ pro_mems + ", pro_has_budget=" + pro_has_budget + "]";
	}

}
