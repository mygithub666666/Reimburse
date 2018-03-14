package reimburse.cuc.com.bean;

/**
 * daily_cost_uuid int primary key auto_increment, daily_cost_type VARCHAR(60)
 * NOT NULL default '', daily_cost_date VARCHAR(160) NOT null default '',
 * daily_cost_unit_price VARCHAR(30) not null default '',
 * daily_cost_ticket_number VARCHAR(30) not null default '',
 * daily_cost_total_amount varchar(30) not null default '', daily_cost_desc
 * varchar(300) not null default '', daily_cost_invoice_pic_urls varchar(2048)
 * not null default '', daily_cost_user_id int not null
 * 
 * @author hp1
 * 
 */
public class DailyCost {

	private Integer daily_cost_uuid;
	private String daily_cost_type;
	private String daily_cost_date;
	private String daily_cost_unit_price;
	private String daily_cost_ticket_number;
	private String daily_cost_total_amount;
	private String daily_cost_desc;
	private String daily_cost_invoice_pic_urls;
	private Integer daily_cost_user_id;
	private String daily_cost_tag;
	private String daily_cost_comments;
	private String daily_cost_status;
	
	public DailyCost(){
		
	}

	
	
	public DailyCost(String daily_cost_type, String daily_cost_date,
			String daily_cost_unit_price, String daily_cost_ticket_number,
			String daily_cost_total_amount, String daily_cost_desc,
			String daily_cost_invoice_pic_urls, Integer daily_cost_user_id,String daily_cost_tag) {
		this.daily_cost_type = daily_cost_type;
		this.daily_cost_date = daily_cost_date;
		this.daily_cost_unit_price = daily_cost_unit_price;
		this.daily_cost_ticket_number = daily_cost_ticket_number;
		this.daily_cost_total_amount = daily_cost_total_amount;
		this.daily_cost_desc = daily_cost_desc;
		this.daily_cost_invoice_pic_urls = daily_cost_invoice_pic_urls;
		this.daily_cost_user_id = daily_cost_user_id;
		this.daily_cost_tag = daily_cost_tag;
	}



	public Integer getDaily_cost_uuid() {
		return daily_cost_uuid;
	}

	public void setDaily_cost_uuid(Integer daily_cost_uuid) {
		this.daily_cost_uuid = daily_cost_uuid;
	}

	public String getDaily_cost_type() {
		return daily_cost_type;
	}

	public void setDaily_cost_type(String daily_cost_type) {
		this.daily_cost_type = daily_cost_type;
	}

	public String getDaily_cost_date() {
		return daily_cost_date;
	}

	public void setDaily_cost_date(String daily_cost_date) {
		this.daily_cost_date = daily_cost_date;
	}

	public String getDaily_cost_unit_price() {
		return daily_cost_unit_price;
	}

	public void setDaily_cost_unit_price(String daily_cost_unit_price) {
		this.daily_cost_unit_price = daily_cost_unit_price;
	}

	public String getDaily_cost_ticket_number() {
		return daily_cost_ticket_number;
	}

	public void setDaily_cost_ticket_number(String daily_cost_ticket_number) {
		this.daily_cost_ticket_number = daily_cost_ticket_number;
	}

	public String getDaily_cost_total_amount() {
		return daily_cost_total_amount;
	}

	public void setDaily_cost_total_amount(String daily_cost_total_amount) {
		this.daily_cost_total_amount = daily_cost_total_amount;
	}

	public String getDaily_cost_desc() {
		return daily_cost_desc;
	}

	public void setDaily_cost_desc(String daily_cost_desc) {
		this.daily_cost_desc = daily_cost_desc;
	}

	public String getDaily_cost_invoice_pic_urls() {
		return daily_cost_invoice_pic_urls;
	}

	public void setDaily_cost_invoice_pic_urls(
			String daily_cost_invoice_pic_urls) {
		this.daily_cost_invoice_pic_urls = daily_cost_invoice_pic_urls;
	}

	public Integer getDaily_cost_user_id() {
		return daily_cost_user_id;
	}

	public void setDaily_cost_user_id(Integer daily_cost_user_id) {
		this.daily_cost_user_id = daily_cost_user_id;
	}



	public String getDaily_cost_tag() {
		return daily_cost_tag;
	}



	public void setDaily_cost_tag(String daily_cost_tag) {
		this.daily_cost_tag = daily_cost_tag;
	}

	public String getDaily_cost_comments() {
		return daily_cost_comments;
	}

	public void setDaily_cost_comments(String daily_cost_comments) {
		this.daily_cost_comments = daily_cost_comments;
	}

	public String getDaily_cost_status() {
		return daily_cost_status;
	}

	public void setDaily_cost_status(String daily_cost_status) {
		this.daily_cost_status = daily_cost_status;
	}

	@Override
	public String toString() {
		return "DailyCost{" +
				"daily_cost_uuid=" + daily_cost_uuid +
				", daily_cost_type='" + daily_cost_type + '\'' +
				", daily_cost_date='" + daily_cost_date + '\'' +
				", daily_cost_unit_price='" + daily_cost_unit_price + '\'' +
				", daily_cost_ticket_number='" + daily_cost_ticket_number + '\'' +
				", daily_cost_total_amount='" + daily_cost_total_amount + '\'' +
				", daily_cost_desc='" + daily_cost_desc + '\'' +
				", daily_cost_invoice_pic_urls='" + daily_cost_invoice_pic_urls + '\'' +
				", daily_cost_user_id=" + daily_cost_user_id +
				", daily_cost_tag='" + daily_cost_tag + '\'' +
				", daily_cost_comments='" + daily_cost_comments + '\'' +
				", daily_cost_status='" + daily_cost_status + '\'' +
				'}';
	}
}
