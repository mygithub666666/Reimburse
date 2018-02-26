package reimburse.cuc.com.bean;

/**
 * 项目的到账金额
 * 
 * @author hp1 `prm_uuid` int(11) NOT NULL AUTO_INCREMENT, `received_money`
 *         varchar(30) NOT NULL DEFAULT '0.0', `received_date` timestamp NOT
 *         NULL DEFAULT CURRENT_TIMESTAMP, `pro_uuid` int(11) NOT NULL,
 * 
 */
public class Pro_received_money {
	private Integer prm_uuid;
	private String received_money;
	private String received_date;
	private Integer pro_uuid;
	

	public Pro_received_money() {
		
	}

	public Pro_received_money(Integer prm_uuid, String received_money,
			String received_date, Integer pro_uuid) {
		this.prm_uuid = prm_uuid;
		this.received_money = received_money;
		this.received_date = received_date;
		this.pro_uuid = pro_uuid;
	}

	public Integer getPrm_uuid() {
		return prm_uuid;
	}

	public void setPrm_uuid(Integer prm_uuid) {
		this.prm_uuid = prm_uuid;
	}

	public String getReceived_money() {
		return received_money;
	}

	public void setReceived_money(String received_money) {
		this.received_money = received_money;
	}

	public String getReceived_date() {
		return received_date;
	}

	public void setReceived_date(String received_date) {
		this.received_date = received_date;
	}

	public Integer getPro_uuid() {
		return pro_uuid;
	}

	public void setPro_uuid(Integer pro_uuid) {
		this.pro_uuid = pro_uuid;
	}

	@Override
	public String toString() {
		return "Pro_received_money [prm_uuid=" + prm_uuid + ", received_money="
				+ received_money + ", received_date=" + received_date
				+ ", pro_uuid=" + pro_uuid + "]";
	}
	
	

}
