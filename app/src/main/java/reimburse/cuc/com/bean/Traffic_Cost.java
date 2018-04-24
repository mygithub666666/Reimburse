package reimburse.cuc.com.bean;

/**
 * traffic_cost_uuid int primary key auto_increment, traffic_cost_type
 * varchar(30) not null default '', traffic_cost_fare_basis varchar(30) not null
 * default '', traffic_cost_start_city v archar(30) not null default '',
 * traffic_cost_end_city varchar(30) not null default '',
 * traffic_cost_start_datetime varchar(30) not null default '',
 * traffic_cost_end_datetime varchar(30) not null default '',
 * traffic_cost_unit_price VARCHAR(30) not null default '',
 * traffic_cost_ticket_number VARCHAR(30) not null default '',
 * traffic_cost_total_amount VARCHAR(30) not null default '',
 * traffic_cost_invoice_pic_url varchar(2048) not null default '',
 * traffic_cost_desc VARCHAR(2048) not null default ''
 * 
 * @author hp1
 * 
 */
public class Traffic_Cost {
	private Integer traffic_cost_uuid;
	private String traffic_cost_type;
	private String traffic_cost_fare_basis;
	private String traffic_cost_start_city;
	private String traffic_cost_end_city;
	private String traffic_cost_start_datetime;
	private String traffic_cost_end_datetime;
	private String traffic_cost_unit_price;
	private String traffic_cost_ticket_number;
	private String traffic_cost_total_amount;
	private String traffic_cost_invoice_pic_url;
	private String traffic_cost_desc;
	private Integer traffic_cost_user_id;
	private String traffic_cost_tag;

	private String traffic_cost_comments;
	private String traffic_cost_status;

	private Integer traffic_cost_pro_uuid;
	private String  traffic_cost_pro_name;
	private String  traffic_cost_pro_budget_type;
	private String  traffic_cost_pro_elec_check;
	private String traffic_cost_pro_paper_check;
	private String traffic_cost_check_status;
	public Traffic_Cost() {
		
	}
	
	

	public Traffic_Cost(String traffic_cost_type,
			String traffic_cost_fare_basis, String traffic_cost_start_city,
			String traffic_cost_end_city, String traffic_cost_start_datetime,
			String traffic_cost_end_datetime, String traffic_cost_unit_price,
			String traffic_cost_ticket_number,
			String traffic_cost_total_amount,
			String traffic_cost_invoice_pic_url, String traffic_cost_desc,
			Integer traffic_cost_user_id,String traffic_cost_tag) {
		this.traffic_cost_type = traffic_cost_type;
		this.traffic_cost_fare_basis = traffic_cost_fare_basis;
		this.traffic_cost_start_city = traffic_cost_start_city;
		this.traffic_cost_end_city = traffic_cost_end_city;
		this.traffic_cost_start_datetime = traffic_cost_start_datetime;
		this.traffic_cost_end_datetime = traffic_cost_end_datetime;
		this.traffic_cost_unit_price = traffic_cost_unit_price;
		this.traffic_cost_ticket_number = traffic_cost_ticket_number;
		this.traffic_cost_total_amount = traffic_cost_total_amount;
		this.traffic_cost_invoice_pic_url = traffic_cost_invoice_pic_url;
		this.traffic_cost_desc = traffic_cost_desc;
		this.traffic_cost_user_id = traffic_cost_user_id;
		this.traffic_cost_tag = traffic_cost_tag;
	}
	public Traffic_Cost(Integer traffic_cost_pro_uuid,String traffic_cost_pro_name,String traffic_cost_pro_budget_type,
						String traffic_cost_type,
			String traffic_cost_fare_basis, String traffic_cost_start_city,
			String traffic_cost_end_city, String traffic_cost_start_datetime,
			String traffic_cost_end_datetime, String traffic_cost_unit_price,
			String traffic_cost_ticket_number,
			String traffic_cost_total_amount,
			String traffic_cost_invoice_pic_url, String traffic_cost_desc,
			Integer traffic_cost_user_id,String traffic_cost_tag) {
		this.traffic_cost_pro_uuid = traffic_cost_pro_uuid;
		this.traffic_cost_pro_name = traffic_cost_pro_name;
		this.traffic_cost_pro_budget_type = traffic_cost_pro_budget_type;
		this.traffic_cost_type = traffic_cost_type;
		this.traffic_cost_fare_basis = traffic_cost_fare_basis;
		this.traffic_cost_start_city = traffic_cost_start_city;
		this.traffic_cost_end_city = traffic_cost_end_city;
		this.traffic_cost_start_datetime = traffic_cost_start_datetime;
		this.traffic_cost_end_datetime = traffic_cost_end_datetime;
		this.traffic_cost_unit_price = traffic_cost_unit_price;
		this.traffic_cost_ticket_number = traffic_cost_ticket_number;
		this.traffic_cost_total_amount = traffic_cost_total_amount;
		this.traffic_cost_invoice_pic_url = traffic_cost_invoice_pic_url;
		this.traffic_cost_desc = traffic_cost_desc;
		this.traffic_cost_user_id = traffic_cost_user_id;
		this.traffic_cost_tag = traffic_cost_tag;
	}

	public Traffic_Cost(Integer traffic_cost_uuid, String traffic_cost_type, String traffic_cost_fare_basis, String traffic_cost_start_city, String traffic_cost_end_city, String traffic_cost_start_datetime, String traffic_cost_end_datetime, String traffic_cost_unit_price, String traffic_cost_ticket_number, String traffic_cost_total_amount, String traffic_cost_invoice_pic_url, String traffic_cost_desc, Integer traffic_cost_user_id, String traffic_cost_tag, String traffic_cost_comments, String traffic_cost_status, Integer traffic_cost_pro_uuid, String traffic_cost_pro_name, String traffic_cost_pro_budget_type, String traffic_cost_pro_elec_check, String traffic_cost_pro_paper_check, String traffic_cost_check_status) {
		this.traffic_cost_uuid = traffic_cost_uuid;
		this.traffic_cost_type = traffic_cost_type;
		this.traffic_cost_fare_basis = traffic_cost_fare_basis;
		this.traffic_cost_start_city = traffic_cost_start_city;
		this.traffic_cost_end_city = traffic_cost_end_city;
		this.traffic_cost_start_datetime = traffic_cost_start_datetime;
		this.traffic_cost_end_datetime = traffic_cost_end_datetime;
		this.traffic_cost_unit_price = traffic_cost_unit_price;
		this.traffic_cost_ticket_number = traffic_cost_ticket_number;
		this.traffic_cost_total_amount = traffic_cost_total_amount;
		this.traffic_cost_invoice_pic_url = traffic_cost_invoice_pic_url;
		this.traffic_cost_desc = traffic_cost_desc;
		this.traffic_cost_user_id = traffic_cost_user_id;
		this.traffic_cost_tag = traffic_cost_tag;
		this.traffic_cost_comments = traffic_cost_comments;
		this.traffic_cost_status = traffic_cost_status;
		this.traffic_cost_pro_uuid = traffic_cost_pro_uuid;
		this.traffic_cost_pro_name = traffic_cost_pro_name;
		this.traffic_cost_pro_budget_type = traffic_cost_pro_budget_type;
		this.traffic_cost_pro_elec_check = traffic_cost_pro_elec_check;
		this.traffic_cost_pro_paper_check = traffic_cost_pro_paper_check;
		this.traffic_cost_check_status = traffic_cost_check_status;
	}

	public Integer getTraffic_cost_uuid() {
		return traffic_cost_uuid;
	}

	public void setTraffic_cost_uuid(Integer traffic_cost_uuid) {
		this.traffic_cost_uuid = traffic_cost_uuid;
	}

	public String getTraffic_cost_type() {
		return traffic_cost_type;
	}

	public void setTraffic_cost_type(String traffic_cost_type) {
		this.traffic_cost_type = traffic_cost_type;
	}

	public String getTraffic_cost_fare_basis() {
		return traffic_cost_fare_basis;
	}

	public void setTraffic_cost_fare_basis(String traffic_cost_fare_basis) {
		this.traffic_cost_fare_basis = traffic_cost_fare_basis;
	}

	public String getTraffic_cost_start_city() {
		return traffic_cost_start_city;
	}

	public void setTraffic_cost_start_city(String traffic_cost_start_city) {
		this.traffic_cost_start_city = traffic_cost_start_city;
	}

	public String getTraffic_cost_end_city() {
		return traffic_cost_end_city;
	}

	public void setTraffic_cost_end_city(String traffic_cost_end_city) {
		this.traffic_cost_end_city = traffic_cost_end_city;
	}

	public String getTraffic_cost_start_datetime() {
		return traffic_cost_start_datetime;
	}

	public void setTraffic_cost_start_datetime(
			String traffic_cost_start_datetime) {
		this.traffic_cost_start_datetime = traffic_cost_start_datetime;
	}

	public String getTraffic_cost_end_datetime() {
		return traffic_cost_end_datetime;
	}

	public void setTraffic_cost_end_datetime(String traffic_cost_end_datetime) {
		this.traffic_cost_end_datetime = traffic_cost_end_datetime;
	}

	public String getTraffic_cost_unit_price() {
		return traffic_cost_unit_price;
	}

	public void setTraffic_cost_unit_price(String traffic_cost_unit_price) {
		this.traffic_cost_unit_price = traffic_cost_unit_price;
	}

	public String getTraffic_cost_ticket_number() {
		return traffic_cost_ticket_number;
	}

	public void setTraffic_cost_ticket_number(String traffic_cost_ticket_number) {
		this.traffic_cost_ticket_number = traffic_cost_ticket_number;
	}

	public String getTraffic_cost_total_amount() {
		return traffic_cost_total_amount;
	}

	public void setTraffic_cost_total_amount(String traffic_cost_total_amount) {
		this.traffic_cost_total_amount = traffic_cost_total_amount;
	}

	public String getTraffic_cost_invoice_pic_url() {
		return traffic_cost_invoice_pic_url;
	}

	public void setTraffic_cost_invoice_pic_url(
			String traffic_cost_invoice_pic_url) {
		this.traffic_cost_invoice_pic_url = traffic_cost_invoice_pic_url;
	}

	public String getTraffic_cost_desc() {
		return traffic_cost_desc;
	}

	public void setTraffic_cost_desc(String traffic_cost_desc) {
		this.traffic_cost_desc = traffic_cost_desc;
	}

	public Integer getTraffic_cost_user_id() {
		return traffic_cost_user_id;
	}

	public void setTraffic_cost_user_id(Integer traffic_cost_user_id) {
		this.traffic_cost_user_id = traffic_cost_user_id;
	}

	
	
	public String getTraffic_cost_tag() {
		return traffic_cost_tag;
	}



	public void setTraffic_cost_tag(String traffic_cost_tag) {
		this.traffic_cost_tag = traffic_cost_tag;
	}

	public String getTraffic_cost_comments() {
		return traffic_cost_comments;
	}

	public void setTraffic_cost_comments(String traffic_cost_comments) {
		this.traffic_cost_comments = traffic_cost_comments;
	}

	public String getTraffic_cost_status() {
		return traffic_cost_status;
	}

	public void setTraffic_cost_status(String traffic_cost_status) {
		this.traffic_cost_status = traffic_cost_status;
	}

	public Integer getTraffic_cost_pro_uuid() {
		return traffic_cost_pro_uuid;
	}

	public void setTraffic_cost_pro_uuid(Integer traffic_cost_pro_uuid) {
		this.traffic_cost_pro_uuid = traffic_cost_pro_uuid;
	}

	public String getTraffic_cost_pro_name() {
		return traffic_cost_pro_name;
	}

	public void setTraffic_cost_pro_name(String traffic_cost_pro_name) {
		this.traffic_cost_pro_name = traffic_cost_pro_name;
	}

	public String getTraffic_cost_pro_budget_type() {
		return traffic_cost_pro_budget_type;
	}

	public void setTraffic_cost_pro_budget_type(String traffic_cost_pro_budget_type) {
		this.traffic_cost_pro_budget_type = traffic_cost_pro_budget_type;
	}

	public String getTraffic_cost_pro_elec_check() {
		return traffic_cost_pro_elec_check;
	}

	public void setTraffic_cost_pro_elec_check(String traffic_cost_pro_elec_check) {
		this.traffic_cost_pro_elec_check = traffic_cost_pro_elec_check;
	}

	public String getTraffic_cost_pro_paper_check() {
		return traffic_cost_pro_paper_check;
	}

	public void setTraffic_cost_pro_paper_check(String traffic_cost_pro_paper_check) {
		this.traffic_cost_pro_paper_check = traffic_cost_pro_paper_check;
	}

	public String getTraffic_cost_check_status() {
		return traffic_cost_check_status;
	}

	public void setTraffic_cost_check_status(String traffic_cost_check_status) {
		this.traffic_cost_check_status = traffic_cost_check_status;
	}

	@Override
	public String toString() {
		return "Traffic_Cost [traffic_cost_uuid=" + traffic_cost_uuid
				+ ", traffic_cost_type=" + traffic_cost_type
				+ ", traffic_cost_fare_basis=" + traffic_cost_fare_basis
				+ ", traffic_cost_start_city=" + traffic_cost_start_city
				+ ", traffic_cost_end_city=" + traffic_cost_end_city
				+ ", traffic_cost_start_datetime="
				+ traffic_cost_start_datetime + ", traffic_cost_end_datetime="
				+ traffic_cost_end_datetime + ", traffic_cost_unit_price="
				+ traffic_cost_unit_price + ", traffic_cost_ticket_number="
				+ traffic_cost_ticket_number + ", traffic_cost_total_amount="
				+ traffic_cost_total_amount + ", traffic_cost_invoice_pic_url="
				+ traffic_cost_invoice_pic_url + ", traffic_cost_desc="
				+ traffic_cost_desc + ", traffic_cost_user_id="
				+ traffic_cost_user_id + "]";
	}
	
	

}
