package reimburse.cuc.com.bean;

public class BaoxiaodanXiaofeiItem {

	private String baseInfo;
	private String picUrls;
	
	
	
	public BaoxiaodanXiaofeiItem() {
		
	}


	public BaoxiaodanXiaofeiItem(String baseInfo, String picUrls) {
		this.baseInfo = baseInfo;
		this.picUrls = picUrls;
	}

	
	public String getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(String picUrls) {
		this.picUrls = picUrls;
	}

}
