package reimburse.cuc.com.bean;

import java.io.Serializable;

/**
 * Created by hp1 on 2017/10/16.
 */
public class TongyiXiaofeiBean implements Serializable{

    // xiaofei_type,sum(totalMoney) as totalMoney
    //默认选中
    private boolean isSelected = false;
    private Integer id;
    private String xiaofei_type;
    private String cost_tag;
    private String riqi;
    private String totalMoney;
    private String otherInfo;

    public TongyiXiaofeiBean() {

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
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

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getCost_tag() {
        return cost_tag;
    }

    public void setCost_tag(String cost_tag) {
        this.cost_tag = cost_tag;
    }

    @Override
    public String toString() {
        return "TongyiXiaofeiBean{" +
                "isSelected=" + isSelected +
                ", id=" + id +
                ", xiaofei_type='" + xiaofei_type + '\'' +
                ", cost_tag='" + cost_tag + '\'' +
                ", riqi='" + riqi + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", otherInfo='" + otherInfo + '\'' +
                '}';
    }
}
