package reimburse.cuc.com.bean;

import java.io.Serializable;

/**
 * Created by hp1 on 2017/10/11.
 */
public class Caigou implements Serializable {
    private Integer id;
    private String caigou_cause;
    private String caigou_name;
    private String caigou_danwei;
    private String caigou_count;
    private String caigou_danjia;
    private String caigou_jine;
    private String caigou_riqi;
    private String caigou_fapiao_urls;
    private String type;
    //默认选中
    private boolean isSelected = true;
    public Caigou() {

    }

    public Caigou(Integer id, String caigou_cause, String caigou_name,
                  String caigou_danwei, String caigou_count, String caigou_danjia,
                  String caigou_jine, String caigou_riqi, String caigou_fapiao_urls) {
        this.id = id;
        this.caigou_cause = caigou_cause;
        this.caigou_name = caigou_name;
        this.caigou_danwei = caigou_danwei;
        this.caigou_count = caigou_count;
        this.caigou_danjia = caigou_danjia;
        this.caigou_jine = caigou_jine;
        this.caigou_riqi = caigou_riqi;
        this.caigou_fapiao_urls = caigou_fapiao_urls;
    }


    public Caigou(Integer id, String caigou_cause, String caigou_name, String caigou_danwei, String caigou_count, String caigou_danjia, String caigou_jine, String caigou_riqi, String caigou_fapiao_urls, String type) {
        this.id = id;
        this.caigou_cause = caigou_cause;
        this.caigou_name = caigou_name;
        this.caigou_danwei = caigou_danwei;
        this.caigou_count = caigou_count;
        this.caigou_danjia = caigou_danjia;
        this.caigou_jine = caigou_jine;
        this.caigou_riqi = caigou_riqi;
        this.caigou_fapiao_urls = caigou_fapiao_urls;
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Caigou(String caigou_cause, String caigou_name,
                  String caigou_danwei, String caigou_count, String caigou_danjia,
                  String caigou_jine, String caigou_riqi, String caigou_fapiao_urls) {
        this.caigou_cause = caigou_cause;
        this.caigou_name = caigou_name;
        this.caigou_danwei = caigou_danwei;
        this.caigou_count = caigou_count;
        this.caigou_danjia = caigou_danjia;
        this.caigou_jine = caigou_jine;
        this.caigou_riqi = caigou_riqi;
        this.caigou_fapiao_urls = caigou_fapiao_urls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaigou_cause() {
        return caigou_cause;
    }

    public void setCaigou_cause(String caigou_cause) {
        this.caigou_cause = caigou_cause;
    }

    public String getCaigou_name() {
        return caigou_name;
    }

    public void setCaigou_name(String caigou_name) {
        this.caigou_name = caigou_name;
    }

    public String getCaigou_danwei() {
        return caigou_danwei;
    }

    public void setCaigou_danwei(String caigou_danwei) {
        this.caigou_danwei = caigou_danwei;
    }

    public String getCaigou_count() {
        return caigou_count;
    }

    public void setCaigou_count(String caigou_count) {
        this.caigou_count = caigou_count;
    }

    public String getCaigou_danjia() {
        return caigou_danjia;
    }

    public void setCaigou_danjia(String caigou_danjia) {
        this.caigou_danjia = caigou_danjia;
    }

    public String getCaigou_jine() {
        return caigou_jine;
    }

    public void setCaigou_jine(String caigou_jine) {
        this.caigou_jine = caigou_jine;
    }

    public String getCaigou_riqi() {
        return caigou_riqi;
    }

    public void setCaigou_riqi(String caigou_riqi) {
        this.caigou_riqi = caigou_riqi;
    }

    public String getCaigou_fapiao_urls() {
        return caigou_fapiao_urls;
    }

    public void setCaigou_fapiao_urls(String caigou_fapiao_urls) {
        this.caigou_fapiao_urls = caigou_fapiao_urls;
    }

}
