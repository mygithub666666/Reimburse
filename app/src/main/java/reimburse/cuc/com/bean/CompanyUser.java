package reimburse.cuc.com.bean;

import java.util.List;

/**
 * `com_user_uuid` int(11) NOT NULL AUTO_INCREMENT, `com_user_job_number`
 * varchar(30) NOT NULL DEFAULT '无', `com_user_dept` varchar(30) NOT NULL
 * DEFAULT '五', `com_user_dept_time` varchar(30) NOT NULL DEFAULT '无',
 * `com_user_name` varchar(30) NOT NULL DEFAULT '无', `com_user_gender`
 * varchar(30) NOT NULL DEFAULT '无', `com_usr_age` varchar(16) NOT NULL DEFAULT
 * '无', `com_user_id_card` varchar(60) NOT NULL DEFAULT '无',
 * `com_user_education` varchar(60) NOT NULL DEFAULT '无',
 * `com_user_education_time` varchar(60) NOT NULL DEFAULT '无', `com_user_title`
 * varchar(30) NOT NULL DEFAULT '无', `com_user_title_time` varchar(60) NOT NULL
 * DEFAULT '无', `com_user_positon` varchar(60) NOT NULL DEFAULT '无',
 * `com_user_position_time` varchar(60) NOT NULL DEFAULT '无',
 * `com_user_personnel_type` varchar(60) NOT NULL DEFAULT '无',
 * `com_user_personnel_type_time` varchar(60) NOT NULL DEFAULT '无',
 * `com_user_mobile_phone_num` varchar(30) NOT NULL DEFAULT '无',
 * `com_user_email` varchar(60) NOT NULL DEFAULT '无', `com_user_note`
 * varchar(30) NOT NULL DEFAULT '无',
 * 
 * @author hp1
 * 
 */
public class CompanyUser {
	private Integer com_user_uuid;
	private String com_user_job_number;
	private String com_user_dept;
	private String com_user_dept_time;
	private String com_user_name;
	private String com_user_gender;
	private String com_usr_age;
	private String com_user_id_card;
	private String com_user_id_card_num;
	private String com_user_education;
	private String com_user_education_time;
	private String com_user_title;
	private String com_user_title_time;
	private String com_user_positon;
	private String com_user_position_time;
	private String com_user_personnel_type;
	private String com_user_personnel_type_time;
	private String com_user_mobile_phone_num;
	private String com_user_email;
	private String com_user_note;
	private String bank_number;
	private String bank_name;

	private Project firstShowProject;
	private List<Project> hiddenProjectList;
	private List<Project> projects;

	public CompanyUser() {

	}

	public CompanyUser(String com_user_job_number, String com_user_name,
			String com_user_gender, String com_user_mobile_phone_num,
			String com_user_email, String com_user_title, String com_usr_age,
			String com_user_dept, String com_user_note) {
		this.com_user_job_number = com_user_job_number;
		this.com_user_name = com_user_name;
		this.com_user_gender = com_user_gender;
		this.com_user_mobile_phone_num = com_user_mobile_phone_num;
		this.com_user_email = com_user_email;
		this.com_user_title = com_user_title;
		this.com_usr_age = com_usr_age;
		this.com_user_dept = com_user_dept;
		this.com_user_note = com_user_note;
	}
	
	public CompanyUser(String com_user_job_number, String com_user_dept,
			String com_user_dept_time, String com_user_name,
			String com_user_gender, String com_usr_age,
			String com_user_id_card, String com_user_id_card_num,
			String com_user_education, String com_user_education_time,
			String com_user_title, String com_user_title_time,
			String com_user_positon, String com_user_position_time,
			String com_user_personnel_type,
			String com_user_personnel_type_time,
			String com_user_mobile_phone_num, String com_user_email,
			String com_user_note) {
		this.com_user_job_number = com_user_job_number;
		this.com_user_dept = com_user_dept;
		this.com_user_dept_time = com_user_dept_time;
		this.com_user_name = com_user_name;
		this.com_user_gender = com_user_gender;
		this.com_usr_age = com_usr_age;
		this.com_user_id_card = com_user_id_card;
		this.com_user_id_card_num = com_user_id_card_num;
		this.com_user_education = com_user_education;
		this.com_user_education_time = com_user_education_time;
		this.com_user_title = com_user_title;
		this.com_user_title_time = com_user_title_time;
		this.com_user_positon = com_user_positon;
		this.com_user_position_time = com_user_position_time;
		this.com_user_personnel_type = com_user_personnel_type;
		this.com_user_personnel_type_time = com_user_personnel_type_time;
		this.com_user_mobile_phone_num = com_user_mobile_phone_num;
		this.com_user_email = com_user_email;
		this.com_user_note = com_user_note;
	}
	public CompanyUser(String com_user_job_number, String com_user_dept,
			String com_user_dept_time, String com_user_name,
			String com_user_gender, String com_usr_age,
			String com_user_id_card, String com_user_id_card_num,
			String com_user_education, String com_user_education_time,
			String com_user_title, String com_user_title_time,
			String com_user_positon, String com_user_position_time,
			String com_user_personnel_type,
			String com_user_personnel_type_time,
			String com_user_mobile_phone_num, String com_user_email,
			String com_user_note,String bank_number,String bank_name) {
		this.com_user_job_number = com_user_job_number;
		this.com_user_dept = com_user_dept;
		this.com_user_dept_time = com_user_dept_time;
		this.com_user_name = com_user_name;
		this.com_user_gender = com_user_gender;
		this.com_usr_age = com_usr_age;
		this.com_user_id_card = com_user_id_card;
		this.com_user_id_card_num = com_user_id_card_num;
		this.com_user_education = com_user_education;
		this.com_user_education_time = com_user_education_time;
		this.com_user_title = com_user_title;
		this.com_user_title_time = com_user_title_time;
		this.com_user_positon = com_user_positon;
		this.com_user_position_time = com_user_position_time;
		this.com_user_personnel_type = com_user_personnel_type;
		this.com_user_personnel_type_time = com_user_personnel_type_time;
		this.com_user_mobile_phone_num = com_user_mobile_phone_num;
		this.com_user_email = com_user_email;
		this.com_user_note = com_user_note;
		this.bank_number = bank_number;
		this.bank_name = bank_name;
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

	public Integer getCom_user_uuid() {
		return com_user_uuid;
	}

	public void setCom_user_uuid(Integer com_user_uuid) {
		this.com_user_uuid = com_user_uuid;
	}

	public String getCom_user_job_number() {
		return com_user_job_number;
	}

	public void setCom_user_job_number(String com_user_job_number) {
		this.com_user_job_number = com_user_job_number;
	}

	public String getCom_user_name() {
		return com_user_name;
	}

	public void setCom_user_name(String com_user_name) {
		this.com_user_name = com_user_name;
	}

	public String getCom_user_gender() {
		return com_user_gender;
	}

	public void setCom_user_gender(String com_user_gender) {
		this.com_user_gender = com_user_gender;
	}

	public String getCom_user_mobile_phone_num() {
		return com_user_mobile_phone_num;
	}

	public void setCom_user_mobile_phone_num(String com_user_mobile_phone_num) {
		this.com_user_mobile_phone_num = com_user_mobile_phone_num;
	}

	public String getCom_user_email() {
		return com_user_email;
	}

	public void setCom_user_email(String com_user_email) {
		this.com_user_email = com_user_email;
	}

	public String getCom_user_title() {
		return com_user_title;
	}

	public void setCom_user_title(String com_user_title) {
		this.com_user_title = com_user_title;
	}

	public String getCom_usr_age() {
		return com_usr_age;
	}

	public void setCom_usr_age(String com_usr_age) {
		this.com_usr_age = com_usr_age;
	}

	public String getCom_user_dept() {
		return com_user_dept;
	}

	public void setCom_user_dept(String com_user_dept) {
		this.com_user_dept = com_user_dept;
	}

	public String getCom_user_note() {
		return com_user_note;
	}

	public void setCom_user_note(String com_user_note) {
		this.com_user_note = com_user_note;
	}

	public Project getFirstShowProject() {
		return firstShowProject;
	}

	public void setFirstShowProject(Project firstShowProject) {
		this.firstShowProject = firstShowProject;
	}

	public List<Project> getHiddenProjectList() {
		return hiddenProjectList;
	}

	public void setHiddenProjectList(List<Project> hiddenProjectList) {
		this.hiddenProjectList = hiddenProjectList;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String getCom_user_dept_time() {
		return com_user_dept_time;
	}

	public void setCom_user_dept_time(String com_user_dept_time) {
		this.com_user_dept_time = com_user_dept_time;
	}

	public String getCom_user_id_card() {
		return com_user_id_card;
	}

	public void setCom_user_id_card(String com_user_id_card) {
		this.com_user_id_card = com_user_id_card;
	}

	
	public String getCom_user_id_card_num() {
		return com_user_id_card_num;
	}

	public void setCom_user_id_card_num(String com_user_id_card_num) {
		this.com_user_id_card_num = com_user_id_card_num;
	}

	public String getCom_user_education() {
		return com_user_education;
	}

	public void setCom_user_education(String com_user_education) {
		this.com_user_education = com_user_education;
	}

	public String getCom_user_education_time() {
		return com_user_education_time;
	}

	public void setCom_user_education_time(String com_user_education_time) {
		this.com_user_education_time = com_user_education_time;
	}

	public String getCom_user_title_time() {
		return com_user_title_time;
	}

	public void setCom_user_title_time(String com_user_title_time) {
		this.com_user_title_time = com_user_title_time;
	}

	public String getCom_user_positon() {
		return com_user_positon;
	}

	public void setCom_user_positon(String com_user_positon) {
		this.com_user_positon = com_user_positon;
	}

	public String getCom_user_position_time() {
		return com_user_position_time;
	}

	public void setCom_user_position_time(String com_user_position_time) {
		this.com_user_position_time = com_user_position_time;
	}

	public String getCom_user_personnel_type() {
		return com_user_personnel_type;
	}

	public void setCom_user_personnel_type(String com_user_personnel_type) {
		this.com_user_personnel_type = com_user_personnel_type;
	}

	public String getCom_user_personnel_type_time() {
		return com_user_personnel_type_time;
	}

	public void setCom_user_personnel_type_time(
			String com_user_personnel_type_time) {
		this.com_user_personnel_type_time = com_user_personnel_type_time;
	}

	@Override
	public String toString() {
		return "CompanyUser [com_user_uuid=" + com_user_uuid
				+ ", com_user_job_number=" + com_user_job_number
				+ ", com_user_dept=" + com_user_dept + ", com_user_dept_time="
				+ com_user_dept_time + ", com_user_name=" + com_user_name
				+ ", com_user_gender=" + com_user_gender + ", com_usr_age="
				+ com_usr_age + ", com_user_id_card=" + com_user_id_card
				+ ", com_user_id_card_num=" + com_user_id_card_num
				+ ", com_user_education=" + com_user_education
				+ ", com_user_education_time=" + com_user_education_time
				+ ", com_user_title=" + com_user_title
				+ ", com_user_title_time=" + com_user_title_time
				+ ", com_user_positon=" + com_user_positon
				+ ", com_user_position_time=" + com_user_position_time
				+ ", com_user_personnel_type=" + com_user_personnel_type
				+ ", com_user_personnel_type_time="
				+ com_user_personnel_type_time + ", com_user_mobile_phone_num="
				+ com_user_mobile_phone_num + ", com_user_email="
				+ com_user_email + ", com_user_note=" + com_user_note + "]";
	}

    

}
