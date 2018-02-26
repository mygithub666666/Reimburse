package reimburse.cuc.com.bean;

import java.io.Serializable;
import java.util.List;

public class BaoXiaoDan implements Serializable{

	private Integer baoxiaodan_id;
	private String baoxiaodan_shiyou;
	private String baoxiaodan_jine;
	private String baoxiaodan_project;
	private String checker_account_id;
	private String user_id;
	private String commitTime;
	private String endTime;
	private String check_status;
	private String xiaofeiids;
	private String comments;
	private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList;
	private List<Caigou> caigouList;
	private List<BaoxiaodanXiaofeiItem> baoxiaodanXiaofeiItemList;
	private Project project;
	
	public BaoXiaoDan() {
		
	}

	public BaoXiaoDan(Integer baoxiaodan_id, String baoxiaodan_shiyou,
			String baoxiaodan_jine, String baoxiaodan_project,
			String checker_account_id, String user_id, String endTime,
			String check_status, String xiaofeiids) {
		this.baoxiaodan_id = baoxiaodan_id;
		this.baoxiaodan_shiyou = baoxiaodan_shiyou;
		this.baoxiaodan_jine = baoxiaodan_jine;
		this.baoxiaodan_project = baoxiaodan_project;
		this.checker_account_id = checker_account_id;
		this.user_id = user_id;
		this.endTime = endTime;
		this.check_status = check_status;
		this.xiaofeiids = xiaofeiids;
	}
	
	

	
	public List<TongyiXiaofeiBean> getTongyiXiaofeiBeanList() {
		return tongyiXiaofeiBeanList;
	}

	public void setTongyiXiaofeiBeanList(
			List<TongyiXiaofeiBean> tongyiXiaofeiBeanList) {
		this.tongyiXiaofeiBeanList = tongyiXiaofeiBeanList;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	public List<Caigou> getCaigouList() {
		return caigouList;
	}

	public void setCaigouList(List<Caigou> caigouList) {
		this.caigouList = caigouList;
	}

	public Integer getBaoxiaodan_id() {
		return baoxiaodan_id;
	}

	public void setBaoxiaodan_id(Integer baoxiaodan_id) {
		this.baoxiaodan_id = baoxiaodan_id;
	}

	public String getBaoxiaodan_shiyou() {
		return baoxiaodan_shiyou;
	}

	public void setBaoxiaodan_shiyou(String baoxiaodan_shiyou) {
		this.baoxiaodan_shiyou = baoxiaodan_shiyou;
	}

	public String getBaoxiaodan_jine() {
		return baoxiaodan_jine;
	}

	public void setBaoxiaodan_jine(String baoxiaodan_jine) {
		this.baoxiaodan_jine = baoxiaodan_jine;
	}

	public String getBaoxiaodan_project() {
		return baoxiaodan_project;
	}

	public void setBaoxiaodan_project(String baoxiaodan_project) {
		this.baoxiaodan_project = baoxiaodan_project;
	}

	public String getChecker_account_id() {
		return checker_account_id;
	}

	public void setChecker_account_id(String checker_account_id) {
		this.checker_account_id = checker_account_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCheck_status() {
		return check_status;
	}

	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}

	public String getXiaofeiids() {
		return xiaofeiids;
	}

	public void setXiaofeiids(String xiaofeiids) {
		this.xiaofeiids = xiaofeiids;
	}

}
