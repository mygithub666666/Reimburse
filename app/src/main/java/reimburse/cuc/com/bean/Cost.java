package reimburse.cuc.com.bean;


/**
 * Created by hp1 on 2017/8/19.
 */
public class Cost {
    private String title;
    private String date;
    private String count;

    public Cost(){

    }
    public Cost(String title,String date,String count) {
        this.title = title;
        this.date = date;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
