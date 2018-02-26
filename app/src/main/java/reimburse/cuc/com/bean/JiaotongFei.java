package reimburse.cuc.com.bean;

public class JiaotongFei {
	private Integer id;
	private String xiaofei_type;
	private String startCity;
	private String endCity;
	private String chufaTime;
	private String daodaTime;
	private String jiaotongleixing;
	private String piaojia;
	private String huafei_desc;
	private String fapiaourls;
	private Integer user_id;

	public JiaotongFei() {

	}

	
	
	
	public JiaotongFei(Integer id, String xiaofei_type, String startCity,
			String endCity, String chufaTime, String daodaTime,
			String jiaotongleixing,String piaojia,
			String huafei_desc, String fapiaourls, Integer user_id) {
		this.id = id;
		this.xiaofei_type = xiaofei_type;
		this.startCity = startCity;
		this.endCity = endCity;
		this.chufaTime = chufaTime;
		this.daodaTime = daodaTime;
		this.jiaotongleixing = jiaotongleixing;
		this.piaojia = piaojia;
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

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getChufaTime() {
		return chufaTime;
	}

	public void setChufaTime(String chufaTime) {
		this.chufaTime = chufaTime;
	}

	public String getDaodaTime() {
		return daodaTime;
	}

	public void setDaodaTime(String daodaTime) {
		this.daodaTime = daodaTime;
	}

	public String getJiaotongleixing() {
		return jiaotongleixing;
	}

	public void setJiaotongleixing(String jiaotongleixing) {
		this.jiaotongleixing = jiaotongleixing;
	}


	public String getPiaojia() {
		return piaojia;
	}

	public void setPiaojia(String piaojia) {
		this.piaojia = piaojia;
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

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

}
