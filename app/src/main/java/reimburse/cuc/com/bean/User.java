package reimburse.cuc.com.bean;

import java.util.List;

public class User {
	private Integer user_uuid;
	private String user_name;
	private String password;
	private String mobile_phone_number;
	private String department;
	private String email;

	private List<Project> projects;

	public User() {

	}

	public User(Integer user_uuid, String user_name, String password) {
		this.user_uuid = user_uuid;
		this.user_name = user_name;
		this.password = password;
	}
	
	public User(Integer user_uuid, String user_name, String password,
			String mobile_phone_number, String department, String email) {
		this.user_uuid = user_uuid;
		this.user_name = user_name;
		this.password = password;
		this.mobile_phone_number = mobile_phone_number;
		this.department = department;
		this.email = email;
	}

	public Integer getUser_uuid() {
		return user_uuid;
	}

	public void setUser_uuid(Integer user_uuid) {
		this.user_uuid = user_uuid;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String getMobile_phone_number() {
		return mobile_phone_number;
	}

	public void setMobile_phone_number(String mobile_phone_number) {
		this.mobile_phone_number = mobile_phone_number;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
