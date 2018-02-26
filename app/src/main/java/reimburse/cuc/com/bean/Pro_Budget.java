package reimburse.cuc.com.bean;

/**
 * pro_budget_uuid int primary key auto_increment, pro_budget_name VARCHAR(60)
 * NOT NULL default '', pro_budget_percentage VARCHAR(30) not null default '',
 * pro_budget_amount varchar(30) NOT null default '', pro_uuid int not null
 * 
 * @author hp1
 * 
 */
public class Pro_Budget {
	private Integer pro_budget_uuid;
	private String pro_budget_name;
	private String pro_budget_percentage;
	private String pro_budget_amount;
	private Integer pro_uuid;

	public Pro_Budget() {

	}

	public Pro_Budget(String pro_budget_name, String pro_budget_percentage,
			String pro_budget_amount, Integer pro_uuid) {
		this.pro_budget_name = pro_budget_name;
		this.pro_budget_percentage = pro_budget_percentage;
		this.pro_budget_amount = pro_budget_amount;
		this.pro_uuid = pro_uuid;
	}

	public Integer getPro_budget_uuid() {
		return pro_budget_uuid;
	}

	public void setPro_budget_uuid(Integer pro_budget_uuid) {
		this.pro_budget_uuid = pro_budget_uuid;
	}

	public String getPro_budget_name() {
		return pro_budget_name;
	}

	public void setPro_budget_name(String pro_budget_name) {
		this.pro_budget_name = pro_budget_name;
	}

	public String getPro_budget_percentage() {
		return pro_budget_percentage;
	}

	public void setPro_budget_percentage(String pro_budget_percentage) {
		this.pro_budget_percentage = pro_budget_percentage;
	}

	public String getPro_budget_amount() {
		return pro_budget_amount;
	}

	public void setPro_budget_amount(String pro_budget_amount) {
		this.pro_budget_amount = pro_budget_amount;
	}

	public Integer getPro_uuid() {
		return pro_uuid;
	}

	public void setPro_uuid(Integer pro_uuid) {
		this.pro_uuid = pro_uuid;
	}

}
