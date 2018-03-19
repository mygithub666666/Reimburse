package reimburse.cuc.com.bean;

import java.util.List;

public class User {
	private Integer user_uuid;
	private String user_name;
	private String password;
	private String mobile_phone_number;
	private String department;
	private String email;
	private String reim_user_job_number;
	private String reim_user_dept_time;
	private String reim_user_gender;
	private String reim_usr_age;
	private String reim_user_id_card;
	private String reim_user_id_card_num;
	private String reim_user_education;
	private String reim_user_education_time;
	private String reim_user_title;
	private String reim_user_title_time;
	private String reim_user_positon;
	private String reim_user_position_time;
	private String reim_user_personnel_type;
	private String reim_user_personnel_type_time;
	private String reim_user_note;
	private String reim_user_pro_nums;
	private String reim_user_project_nums;
	private String bank_number;
	private String bank_name;
	private String isInnerUser;
	private String proleader_uuid;

	private List<Project> projects;

	public User() {

	}


	public User(Integer user_uuid, String user_name, String password, String mobile_phone_number, String department, String email, String reim_user_job_number, String reim_user_dept_time, String reim_user_gender, String reim_usr_age, String reim_user_id_card, String reim_user_id_card_num, String reim_user_education, String reim_user_education_time, String reim_user_title, String reim_user_title_time, String reim_user_positon, String reim_user_position_time, String reim_user_personnel_type, String reim_user_personnel_type_time, String reim_user_note, String reim_user_pro_nums, String reim_user_project_nums, String bank_number, String bank_name, String isInnerUser, String proleader_uuid) {
		this.user_uuid = user_uuid;
		this.user_name = user_name;
		this.password = password;
		this.mobile_phone_number = mobile_phone_number;
		this.department = department;
		this.email = email;
		this.reim_user_job_number = reim_user_job_number;
		this.reim_user_dept_time = reim_user_dept_time;
		this.reim_user_gender = reim_user_gender;
		this.reim_usr_age = reim_usr_age;
		this.reim_user_id_card = reim_user_id_card;
		this.reim_user_id_card_num = reim_user_id_card_num;
		this.reim_user_education = reim_user_education;
		this.reim_user_education_time = reim_user_education_time;
		this.reim_user_title = reim_user_title;
		this.reim_user_title_time = reim_user_title_time;
		this.reim_user_positon = reim_user_positon;
		this.reim_user_position_time = reim_user_position_time;
		this.reim_user_personnel_type = reim_user_personnel_type;
		this.reim_user_personnel_type_time = reim_user_personnel_type_time;
		this.reim_user_note = reim_user_note;
		this.reim_user_pro_nums = reim_user_pro_nums;
		this.reim_user_project_nums = reim_user_project_nums;
		this.bank_number = bank_number;
		this.bank_name = bank_name;
		this.isInnerUser = isInnerUser;
		this.proleader_uuid = proleader_uuid;
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

	public String getReim_user_job_number() {
		return reim_user_job_number;
	}

	public void setReim_user_job_number(String reim_user_job_number) {
		this.reim_user_job_number = reim_user_job_number;
	}

	public String getReim_user_dept_time() {
		return reim_user_dept_time;
	}

	public void setReim_user_dept_time(String reim_user_dept_time) {
		this.reim_user_dept_time = reim_user_dept_time;
	}

	public String getReim_user_gender() {
		return reim_user_gender;
	}

	public void setReim_user_gender(String reim_user_gender) {
		this.reim_user_gender = reim_user_gender;
	}

	public String getReim_usr_age() {
		return reim_usr_age;
	}

	public void setReim_usr_age(String reim_usr_age) {
		this.reim_usr_age = reim_usr_age;
	}

	public String getReim_user_id_card() {
		return reim_user_id_card;
	}

	public void setReim_user_id_card(String reim_user_id_card) {
		this.reim_user_id_card = reim_user_id_card;
	}

	public String getReim_user_id_card_num() {
		return reim_user_id_card_num;
	}

	public void setReim_user_id_card_num(String reim_user_id_card_num) {
		this.reim_user_id_card_num = reim_user_id_card_num;
	}

	public String getReim_user_education() {
		return reim_user_education;
	}

	public void setReim_user_education(String reim_user_education) {
		this.reim_user_education = reim_user_education;
	}

	public String getReim_user_education_time() {
		return reim_user_education_time;
	}

	public void setReim_user_education_time(String reim_user_education_time) {
		this.reim_user_education_time = reim_user_education_time;
	}

	public String getReim_user_title() {
		return reim_user_title;
	}

	public void setReim_user_title(String reim_user_title) {
		this.reim_user_title = reim_user_title;
	}

	public String getReim_user_title_time() {
		return reim_user_title_time;
	}

	public void setReim_user_title_time(String reim_user_title_time) {
		this.reim_user_title_time = reim_user_title_time;
	}

	public String getReim_user_positon() {
		return reim_user_positon;
	}

	public void setReim_user_positon(String reim_user_positon) {
		this.reim_user_positon = reim_user_positon;
	}

	public String getReim_user_position_time() {
		return reim_user_position_time;
	}

	public void setReim_user_position_time(String reim_user_position_time) {
		this.reim_user_position_time = reim_user_position_time;
	}

	public String getReim_user_personnel_type() {
		return reim_user_personnel_type;
	}

	public void setReim_user_personnel_type(String reim_user_personnel_type) {
		this.reim_user_personnel_type = reim_user_personnel_type;
	}

	public String getReim_user_personnel_type_time() {
		return reim_user_personnel_type_time;
	}

	public void setReim_user_personnel_type_time(String reim_user_personnel_type_time) {
		this.reim_user_personnel_type_time = reim_user_personnel_type_time;
	}

	public String getReim_user_note() {
		return reim_user_note;
	}

	public void setReim_user_note(String reim_user_note) {
		this.reim_user_note = reim_user_note;
	}

	public String getReim_user_pro_nums() {
		return reim_user_pro_nums;
	}

	public void setReim_user_pro_nums(String reim_user_pro_nums) {
		this.reim_user_pro_nums = reim_user_pro_nums;
	}

	public String getReim_user_project_nums() {
		return reim_user_project_nums;
	}

	public void setReim_user_project_nums(String reim_user_project_nums) {
		this.reim_user_project_nums = reim_user_project_nums;
	}

	public String getBank_number() {
		return bank_number;
	}

	public void setBank_number(String bank_number) {
		this.bank_number = bank_number;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getIsInnerUser() {
		return isInnerUser;
	}

	public void setIsInnerUser(String isInnerUser) {
		this.isInnerUser = isInnerUser;
	}

	public String getProleader_uuid() {
		return proleader_uuid;
	}

	public void setProleader_uuid(String proleader_uuid) {
		this.proleader_uuid = proleader_uuid;
	}

	@Override
	public String toString() {
		return "User{" +
				"user_uuid=" + user_uuid +
				", user_name='" + user_name + '\'' +
				", password='" + password + '\'' +
				", mobile_phone_number='" + mobile_phone_number + '\'' +
				", department='" + department + '\'' +
				", email='" + email + '\'' +
				", reim_user_job_number='" + reim_user_job_number + '\'' +
				", reim_user_dept_time='" + reim_user_dept_time + '\'' +
				", reim_user_gender='" + reim_user_gender + '\'' +
				", reim_usr_age='" + reim_usr_age + '\'' +
				", reim_user_id_card='" + reim_user_id_card + '\'' +
				", reim_user_id_card_num='" + reim_user_id_card_num + '\'' +
				", reim_user_education='" + reim_user_education + '\'' +
				", reim_user_education_time='" + reim_user_education_time + '\'' +
				", reim_user_title='" + reim_user_title + '\'' +
				", reim_user_title_time='" + reim_user_title_time + '\'' +
				", reim_user_positon='" + reim_user_positon + '\'' +
				", reim_user_position_time='" + reim_user_position_time + '\'' +
				", reim_user_personnel_type='" + reim_user_personnel_type + '\'' +
				", reim_user_personnel_type_time='" + reim_user_personnel_type_time + '\'' +
				", reim_user_note='" + reim_user_note + '\'' +
				", reim_user_pro_nums='" + reim_user_pro_nums + '\'' +
				", reim_user_project_nums='" + reim_user_project_nums + '\'' +
				", bank_number='" + bank_number + '\'' +
				", bank_name='" + bank_name + '\'' +
				", isInnerUser='" + isInnerUser + '\'' +
				", proleader_uuid='" + proleader_uuid  +
				'}';
	}
}
