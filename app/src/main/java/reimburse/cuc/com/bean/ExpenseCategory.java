package reimburse.cuc.com.bean;

/**
 * `ec_uuid` int(11) NOT NULL AUTO_INCREMENT, `ec_name` varchar(255) NOT NULL
 * DEFAULT '无', `ec_note` varchar(255) NOT NULL DEFAULT '无',
 * 
 * @author hp1
 * 
 */
public class ExpenseCategory {
	private Integer ec_uuid;
	private String ec_name;
	private String ec_note;

	public ExpenseCategory() {

	}
	
	public ExpenseCategory(String ec_name, String ec_note) {
		this.ec_name = ec_name;
		this.ec_note = ec_note;
	}



	public Integer getEc_uuid() {
		return ec_uuid;
	}

	public void setEc_uuid(Integer ec_uuid) {
		this.ec_uuid = ec_uuid;
	}

	public String getEc_name() {
		return ec_name;
	}

	public void setEc_name(String ec_name) {
		this.ec_name = ec_name;
	}

	public String getEc_note() {
		return ec_note;
	}

	public void setEc_note(String ec_note) {
		this.ec_note = ec_note;
	}

	@Override
	public String toString() {
		return "ExpenseCategory [ec_uuid=" + ec_uuid + ", ec_name=" + ec_name
				+ ", ec_note=" + ec_note + "]";
	}

	
}
