package reimburse.cuc.com.bean;

public class ReimStatus {
	private String pro_name;
	private String this_time_reim_amount;
	private Integer reim_uuid;
	private String reim_submit_date;
	private String reim_end_time;
	public ReimStatus() {
		
	}
	
	

	public ReimStatus(String pro_name, String this_time_reim_amount,
			Integer reim_uuid, String reim_end_time) {
		this.pro_name = pro_name;
		this.this_time_reim_amount = this_time_reim_amount;
		this.reim_uuid = reim_uuid;
		this.reim_end_time = reim_end_time;
	}

    

	public ReimStatus(String pro_name, String this_time_reim_amount,
			Integer reim_uuid, String reim_submit_date, String reim_end_time) {
		this.pro_name = pro_name;
		this.this_time_reim_amount = this_time_reim_amount;
		this.reim_uuid = reim_uuid;
		this.reim_submit_date = reim_submit_date;
		this.reim_end_time = reim_end_time;
	}



	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getThis_time_reim_amount() {
		return this_time_reim_amount;
	}

	public void setThis_time_reim_amount(String this_time_reim_amount) {
		this.this_time_reim_amount = this_time_reim_amount;
	}

	public Integer getReim_uuid() {
		return reim_uuid;
	}

	public void setReim_uuid(Integer reim_uuid) {
		this.reim_uuid = reim_uuid;
	}

	public String getReim_end_time() {
		return reim_end_time;
	}

	public void setReim_end_time(String reim_end_time) {
		this.reim_end_time = reim_end_time;
	}



	public String getReim_submit_date() {
		return reim_submit_date;
	}



	public void setReim_submit_date(String reim_submit_date) {
		this.reim_submit_date = reim_submit_date;
	}
    
	
	
}
