package reimburse.cuc.com.bean;

import com.bumptech.glide.load.engine.Resource;

import java.util.List;

/**
 * `id` int(11) NOT NULL AUTO_INCREMENT, `user_name` varchar(30) NOT NULL,
 * `user_password` varchar(1000) NOT NULL, `e_phone` varchar(30) NOT NULL,
 * `email` varchar(60) NOT NULL, `role_id` int(11) NOT NULL, `dept` varchar(30)
 * NOT NULL DEFAULT '无', `user_title` varchar(30) NOT NULL DEFAULT '无', `gender`
 * varchar(16) NOT NULL DEFAULT '未填写', `age` varchar(16) NOT NULL DEFAULT '未填写',
 * `note` varchar(30) NOT NULL DEFAULT '无',
 * 
 * private Integer com_user_uuid; private String com_user_job_number; private
 * String com_user_name; private String com_user_gender; private String
 * com_user_mobile_phone_num; private String com_user_email; private String
 * com_user_title; private String com_usr_age; private String com_user_dept;
 * private String com_user_note; emp_pro_nums VARCHAR(11) NOT NULL DEFAULT '0',
 * emp_reim_nums VARCHAR(11) NOT NULL DEFAULT '0' emp_reim_success_nums
 * VARCHAR(11) NOT NULL DEFAULT '0', emp_reim_fail_nums VARCHAR(11) NOT NULL
 * DEFAULT '0'
 * 
 * @author hp1
 * 
 */
public class Employee {
	private Integer emp_uuid;
	private String user_password;
	private String com_user_job_number;//工号
	private String user_name;//姓名
	private String e_phone;//手机号
	private String email;//手机号
	private String dept;//部门
	private String user_title;//职称
	private String gender;//性别
	private String age;//出生日期
	private String note;//备注
	private Integer role_id;//角色ID
	private String emp_pro_nums;//项目数
	private String emp_reim_nums;//报销总数
	private String emp_reim_success_nums;//审核通过数
	private String emp_reim_fail_nums;//审核未通过数
	private String emp_pro_totalmoney;//所负责的项目总经费

	private String emp_bank_number;
	private String emp_bank_name;

	private List<Resource> resources;
	private Project firstShowProject;
	private List<Project> hiddenProjectList;
	private List<Project> projects;

	public Employee() {

	}

	
	
	public Employee(String com_user_job_number, String user_name,
			String e_phone, String email, String dept, String user_title,
			String gender, String age, String note, Integer role_id) {
		this.com_user_job_number = com_user_job_number;
		this.user_name = user_name;
		this.e_phone = e_phone;
		this.email = email;
		this.dept = dept;
		this.user_title = user_title;
		this.gender = gender;
		this.age = age;
		this.note = note;
		this.role_id = role_id;
	}



	public Employee(String user_password, String com_user_job_number,
			String user_name, String e_phone, String email, String dept,
			String user_title, String gender, String age, String note) {
		this.user_password = user_password;
		this.com_user_job_number = com_user_job_number;
		this.user_name = user_name;
		this.e_phone = e_phone;
		this.email = email;
		this.dept = dept;
		this.user_title = user_title;
		this.gender = gender;
		this.age = age;
		this.note = note;
	}

	public Employee(String user_password, String com_user_job_number,
			String user_name, String e_phone, String email, String dept,
			String user_title, String gender, String age, String note,
			Integer role_id) {
		this.user_password = user_password;
		this.com_user_job_number = com_user_job_number;
		this.user_name = user_name;
		this.e_phone = e_phone;
		this.email = email;
		this.dept = dept;
		this.user_title = user_title;
		this.gender = gender;
		this.age = age;
		this.note = note;
		this.role_id = role_id;
	}

	//user_name&com_user_job_number&email=&user_title&e_phone&age&dept&note&role_id
	
	
	
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}


	public Integer getEmp_uuid() {
		return emp_uuid;
	}

	public void setEmp_uuid(Integer emp_uuid) {
		this.emp_uuid = emp_uuid;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getE_phone() {
		return e_phone;
	}

	public void setE_phone(String e_phone) {
		this.e_phone = e_phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getUser_title() {
		return user_title;
	}

	public void setUser_title(String user_title) {
		this.user_title = user_title;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCom_user_job_number() {
		return com_user_job_number;
	}

	public void setCom_user_job_number(String com_user_job_number) {
		this.com_user_job_number = com_user_job_number;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getEmp_pro_nums() {
		return emp_pro_nums;
	}

	public void setEmp_pro_nums(String emp_pro_nums) {
		this.emp_pro_nums = emp_pro_nums;
	}

	public String getEmp_reim_nums() {
		return emp_reim_nums;
	}

	public void setEmp_reim_nums(String emp_reim_nums) {
		this.emp_reim_nums = emp_reim_nums;
	}

	public String getEmp_reim_success_nums() {
		return emp_reim_success_nums;
	}

	public void setEmp_reim_success_nums(String emp_reim_success_nums) {
		this.emp_reim_success_nums = emp_reim_success_nums;
	}

	public String getEmp_reim_fail_nums() {
		return emp_reim_fail_nums;
	}

	public void setEmp_reim_fail_nums(String emp_reim_fail_nums) {
		this.emp_reim_fail_nums = emp_reim_fail_nums;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
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

	
    


	public String getEmp_pro_totalmoney() {
		return emp_pro_totalmoney;
	}



	public void setEmp_pro_totalmoney(String emp_pro_totalmoney) {
		this.emp_pro_totalmoney = emp_pro_totalmoney;
	}

	public String getEmp_bank_number() {
		return emp_bank_number;
	}

	public void setEmp_bank_number(String emp_bank_number) {
		this.emp_bank_number = emp_bank_number;
	}

	public String getEmp_bank_name() {
		return emp_bank_name;
	}

	public void setEmp_bank_name(String emp_bank_name) {
		this.emp_bank_name = emp_bank_name;
	}

	public Employee(String com_user_job_number, String user_name,
					String e_phone, String email, String dept, String user_title,
					String gender, String age, String note, Integer role_id,
					String emp_bank_number, String emp_bank_name) {
		this.com_user_job_number = com_user_job_number;
		this.user_name = user_name;
		this.e_phone = e_phone;
		this.email = email;
		this.dept = dept;
		this.user_title = user_title;
		this.gender = gender;
		this.age = age;
		this.note = note;
		this.role_id = role_id;
		this.emp_bank_number = emp_bank_number;
		this.emp_bank_name = emp_bank_name;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"emp_pro_totalmoney='" + emp_pro_totalmoney + '\'' +
				", emp_uuid=" + emp_uuid +
				", user_password='" + user_password + '\'' +
				", com_user_job_number='" + com_user_job_number + '\'' +
				", user_name='" + user_name + '\'' +
				", e_phone='" + e_phone + '\'' +
				", email='" + email + '\'' +
				", dept='" + dept + '\'' +
				", user_title='" + user_title + '\'' +
				", gender='" + gender + '\'' +
				", age='" + age + '\'' +
				", note='" + note + '\'' +
				", role_id=" + role_id +
				", emp_pro_nums='" + emp_pro_nums + '\'' +
				", emp_reim_nums='" + emp_reim_nums + '\'' +
				", emp_reim_success_nums='" + emp_reim_success_nums + '\'' +
				", emp_reim_fail_nums='" + emp_reim_fail_nums + '\'' +
				'}';
	}
}
