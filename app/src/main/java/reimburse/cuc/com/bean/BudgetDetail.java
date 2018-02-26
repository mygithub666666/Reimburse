package reimburse.cuc.com.bean;
/**
 * `bd_id` int(11) NOT NULL AUTO_INCREMENT,
  `cate_id` int(11) NOT NULL,
  `bd_money` varchar(11) NOT NULL,
  `pro_uuid` int(11) NOT NULL,
 * @author hp1
 *
 */
public class BudgetDetail {

	private Integer bd_id;
	private Integer cate_id;
	private String bd_money;
	private Integer pro_uuid;
	
	
	
	private ExpenseCategory category;
	
	

	public ExpenseCategory getCategory() {
		return category;
	}

	public void setCategory(ExpenseCategory category) {
		this.category = category;
	}

	public Integer getBd_id() {
		return bd_id;
	}

	public void setBd_id(Integer bd_id) {
		this.bd_id = bd_id;
	}

	public Integer getCate_id() {
		return cate_id;
	}

	public void setCate_id(Integer cate_id) {
		this.cate_id = cate_id;
	}

	public String getBd_money() {
		return bd_money;
	}

	public void setBd_money(String bd_money) {
		this.bd_money = bd_money;
	}

	
	

	public Integer getPro_uuid() {
		return pro_uuid;
	}

	public void setPro_uuid(Integer pro_uuid) {
		this.pro_uuid = pro_uuid;
	}

	public BudgetDetail(Integer cate_id, String bd_money, Integer pro_uuid) {
		this.cate_id = cate_id;
		this.bd_money = bd_money;
		this.pro_uuid = pro_uuid;
	}

	public BudgetDetail() {
		
	}

	
}
