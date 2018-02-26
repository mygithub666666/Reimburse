package reimburse.cuc.com.bean;

import java.util.List;

/**
 * create table tbl_traffic_type( tra_type_uuid int primary key auto_increment,
 * tra_type_name varchar(30) not null default '' )
 * 
 * create table tbl_traffic_fare_basis( bl_traffic_fare_basis_uuid int primary
 * key auto_increment, bl_traffic_fare_basis_name varchar(30) not null default
 * '', tra_type_uuid int not null )
 * 
 * @author hp1
 * 
 */
public class Traffic_Type {
	private Integer tra_type_uuid;
	private String tra_type_name;
	private List<Traffic_Fare_Basis> traffic_Fare_Basis_List;

	public Integer getTra_type_uuid() {
		return tra_type_uuid;
	}

	public void setTra_type_uuid(Integer tra_type_uuid) {
		this.tra_type_uuid = tra_type_uuid;
	}

	public String getTra_type_name() {
		return tra_type_name;
	}

	public void setTra_type_name(String tra_type_name) {
		this.tra_type_name = tra_type_name;
	}

	public List<Traffic_Fare_Basis> getTraffic_Fare_Basis_List() {
		return traffic_Fare_Basis_List;
	}

	public void setTraffic_Fare_Basis_List(List<Traffic_Fare_Basis> traffic_Fare_Basis_List) {
		this.traffic_Fare_Basis_List = traffic_Fare_Basis_List;
	}
}
