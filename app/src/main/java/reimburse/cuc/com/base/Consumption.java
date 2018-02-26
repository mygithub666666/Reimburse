package reimburse.cuc.com.base;

import java.io.Serializable;

/**
 * Created by hp1 on 2017/8/20.
 */
public class Consumption implements Serializable{
    private String xiaofei_id;
    private String content;
    private String date;
    private String money;
    private String picUris;
    //默认选中
    private boolean isSelected = true;


    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Consumption() {
    }

    public Consumption(String content, String date, String money, String picUris) {
        this.content = content;
        this.date = date;
        this.money = money;
        this.picUris = picUris;
    }
    public Consumption(String xiaofei_id,String content, String date, String money, String picUris) {
        this.xiaofei_id = xiaofei_id;
        this.content = content;
        this.date = date;
        this.money = money;
        this.picUris = picUris;
    }

    public String getXiaofei_id() {
        return xiaofei_id;
    }

    public void setXiaofei_id(String xiaofei_id) {
        this.xiaofei_id = xiaofei_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPicUris() {
        return picUris;
    }

    public void setPicUris(String picUris) {
        this.picUris = picUris;
    }

    @Override
    public String toString() {
        return xiaofei_id+"=="+content+"=="+date+"=="+money+"=="+picUris;
    }
}
