package reimburse.cuc.com.bean;

import java.util.List;

/**
 * 
 * `id` int(11) NOT NULL AUTO_INCREMENT, `p_id` varchar(11) NOT NULL, `p_leader`
 * varchar(16) NOT NULL, `p_type` varchar(16) NOT NULL, `p_startdate`
 * varchar(16) NOT NULL, `p_enddate` varchar(16) NOT NULL, `p_totalmoney`
 * varchar(11) NOT NULL, `p_money_source` varchar(11) NOT NULL,
 * `p_finished_money` varchar(11) NOT NULL, `p_new_money` varchar(11) NOT NULL
 * DEFAULT '0',
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

	private List<BudgetDetail> projectBudgetDetailList;
	
	private List<Pro_received_money> pro_received_moneys;
	
	private List<Pro_Budget> pro_Budgets;

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

	public void setPro_received_moneys(List<Pro_received_money> pro_received_moneys) {
		this.pro_received_moneys = pro_received_moneys;
	}

	
	
	public List<Pro_Budget> getPro_Budgets() {
		return pro_Budgets;
	}

	public void setPro_Budgets(List<Pro_Budget> pro_Budgets) {
		this.pro_Budgets = pro_Budgets;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", p_id=" + p_id + ", p_leader="
				+ p_leader + ", p_name=" + p_name + ", p_type=" + p_type
				+ ", p_startdate=" + p_startdate + ", p_enddate=" + p_enddate
				+ ", p_totalmoney=" + p_totalmoney + ", p_money_source="
				+ p_money_source + ", p_finished_money=" + p_finished_money
				+ ", p_new_money=" + p_new_money + ", p_reimbursable_amount="
				+ p_reimbursable_amount + "]";
	}


	
	
	

}
