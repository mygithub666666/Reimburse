package reimburse.cuc.com.bean;

/**
 * * create table tbl_traffic_fare_basis( bl_traffic_fare_basis_uuid int primary
 * key auto_increment, bl_traffic_fare_basis_name varchar(30) not null default
 * '', tra_type_uuid int not null )
 * 
 * @author hp1
 * 
 */
public class Traffic_Fare_Basis {

	private Integer bl_traffic_fare_basis_uuid;
	private String bl_traffic_fare_basis_name;
	private String tra_type_uuid;

	public Traffic_Fare_Basis() {
		
	}

	public Integer getBl_traffic_fare_basis_uuid() {
		return bl_traffic_fare_basis_uuid;
	}

	public void setBl_traffic_fare_basis_uuid(Integer bl_traffic_fare_basis_uuid) {
		this.bl_traffic_fare_basis_uuid = bl_traffic_fare_basis_uuid;
	}

	public String getBl_traffic_fare_basis_name() {
		return bl_traffic_fare_basis_name;
	}

	public void setBl_traffic_fare_basis_name(String bl_traffic_fare_basis_name) {
		this.bl_traffic_fare_basis_name = bl_traffic_fare_basis_name;
	}

	public String getTra_type_uuid() {
		return tra_type_uuid;
	}

	public void setTra_type_uuid(String tra_type_uuid) {
		this.tra_type_uuid = tra_type_uuid;
	}

}
