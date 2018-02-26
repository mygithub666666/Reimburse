package reimburse.cuc.com.bean;

public class Caigoufei {

	private Integer id;
	private String xiaofei_type;
	private String wupin_name;
	private String danwei;
	private String shuliang;
	private String danjia;
	private String riqi;
	private String totalMoney;
	private String huafei_desc;
	private String fapiaourls;
	private String user_id;

	public Caigoufei() {
		
	}

	public Caigoufei(Integer id, String xiaofei_type, String wupin_name,
			String danwei, String shuliang, String danjia, String riqi,
			String totalMoney, String huafei_desc, String fapiaourls,
			String user_id) {
		this.id = id;
		this.xiaofei_type = xiaofei_type;
		this.wupin_name = wupin_name;
		this.danwei = danwei;
		this.shuliang = shuliang;
		this.danjia = danjia;
		this.riqi = riqi;
		this.totalMoney = totalMoney;
		this.huafei_desc = huafei_desc;
		this.fapiaourls = fapiaourls;
		this.user_id = user_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getXiaofei_type() {
		return xiaofei_type;
	}

	public void setXiaofei_type(String xiaofei_type) {
		this.xiaofei_type = xiaofei_type;
	}

	public String getWupin_name() {
		return wupin_name;
	}

	public void setWupin_name(String wupin_name) {
		this.wupin_name = wupin_name;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}

	public String getDanjia() {
		return danjia;
	}

	public void setDanjia(String danjia) {
		this.danjia = danjia;
	}

	public String getRiqi() {
		return riqi;
	}

	public void setRiqi(String riqi) {
		this.riqi = riqi;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getHuafei_desc() {
		return huafei_desc;
	}

	public void setHuafei_desc(String huafei_desc) {
		this.huafei_desc = huafei_desc;
	}

	public String getFapiaourls() {
		return fapiaourls;
	}

	public void setFapiaourls(String fapiaourls) {
		this.fapiaourls = fapiaourls;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
