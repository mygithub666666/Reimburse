package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import reimburse.cuc.com.bean.BaoXiaoDan;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.util.StreamTools;

public class LineChartActivity extends Activity {
    LineData lineData;
    private LineChart lineChart;
    private Typeface mTf;//声明字体库
    private List<BaoXiaoDan> baoXiaoDanList = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_BAOXIAO) {
                generateDataLine();
                lineChart.setData(lineData);
            }
        }
    };
    protected static int SHOW_ALL_BAOXIAO = 0002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        lineChart = (LineChart)findViewById(R.id.line_chart);

        //设置当前的折线图的描述
        lineChart.setDescription("报销数据");
        //是否绘制网格背景
        lineChart.setDrawGridBackground(false);

        //获取当前的x轴对象
        XAxis xAxis = lineChart.getXAxis();
        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x轴的字体
        xAxis.setTypeface(mTf);
        //是否绘制x轴的网格线
        xAxis.setDrawGridLines(false) ;

        //是否绘制x轴的轴线
        xAxis.setDrawAxisLine(true);

        //获取左边的y轴对象
        YAxis leftAxis = lineChart.getAxisLeft();
        //设置左边y轴的字体
        leftAxis.setTypeface(mTf);
        //参数1：左边y轴提供的区间的个数。 参数2：是否均匀分布这几个区间。 false：均匀。 true：不均匀
        leftAxis.setLabelCount(5, false);

        //获取右边的y轴对象
        YAxis rightAxis = lineChart.getAxisRight();
        //将右边的y轴设置为不显式的
        rightAxis.setEnabled(true);
        // 设置x轴方向的动画。执行时间是750.
        // 不需要再执行：invalidate();
        lineChart.animateX(750);

        getAllBaoxiaoDanByUserId();


    }


    public  void  getAllBaoxiaoDanByUserId(){

        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.GetBaoxiaodanWithtXiaofeiItemByUserId;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    String data ="&user_id="+ URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID), "utf-8");

                    conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                    //post的请求是把数据以流的方式写给了服务器
                    //指定请求的输出模式
                    conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                    conn.getOutputStream().write(data.getBytes());
                    Log.e("post size", String.valueOf(data.getBytes().length));
                    int code = conn.getResponseCode();

                    if(code == 200){
                        InputStream is = conn.getInputStream();
                        final String result = StreamTools.readStream(is);
                        baoXiaoDanList = JSON.parseArray(result, BaoXiaoDan.class);
                        //generateDataLine();
                        //getMonths();
                        //lineData = generateDataLine();

                        Message message = Message.obtain();
                        message.obj = baoXiaoDanList;
                        message.what = SHOW_ALL_BAOXIAO;
                        handler.sendMessage(message);
                        Log.e("从服务端得到的报销数据",baoXiaoDanList.size()+"");
                        Log.e("返回的JSON数据",result);
                        //showToastInAnyThread(result);
                        /*if(result.equals("保存成功")) {

                        }*/
                    }else{
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("请求失败");
                }

            }
        }.start();
        //return lineData;
    }

    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private void generateDataLine() {
        //折线1：
        ArrayList<Entry> e1 = new ArrayList<Entry>();
        Log.e("服务端得到的数据个数: ",baoXiaoDanList.size()+"");
        //提供折线中点的数据
        for (int i = 0; i < baoXiaoDanList.size(); i++) {
            e1.add(new Entry(Float.parseFloat(baoXiaoDanList.get(i).getBaoxiaodan_jine()), i));
        }
/*        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }*/

        LineDataSet d1 = new LineDataSet(e1, "报销金额");
        //设置折线的宽度
        d1.setLineWidth(4.5f);
        //设置小圆圈的尺寸
        d1.setCircleSize(4.5f);
        //设置高亮的颜色
        d1.setHighLightColor(Color.rgb(244, 0, 0));
        //是否显示小圆圈对应的数值
        d1.setDrawValues(true);

        //折线2：
//        ArrayList<Entry> e2 = new ArrayList<Entry>();
//
//        for (int i = 0; i < 12; i++) {
//            e2.add(new Entry(e1.get(i).getVal() - 30, i));
//        }
//
//        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
//        d2.setLineWidth(2.5f);
//        d2.setCircleSize(4.5f);
//        d2.setHighLightColor(Color.rgb(244, 117, 117));
//        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        d2.setDrawValues(false);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);

        ArrayList<String> m = new ArrayList<String>();
        for (int i = 0; i < baoXiaoDanList.size(); i++) {
            String monthStr = baoXiaoDanList.get(i).getCommitTime().split(" ")[0].split("-")[1];
            String dateStr = baoXiaoDanList.get(i).getCommitTime().split(" ")[0].split("-")[2];
            Log.e("折线图日期: "+i+",  ",monthStr+"-"+dateStr);
            m.add(monthStr+"-"+dateStr);
        }
        Collections.sort(m);
        for (int j=0;j<m.size();j++){
            String dateString = m.get(j);
            Log.e("排序后的日期: ",dateString);
        }
        lineData = new LineData(m, sets);
        //return cd;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        for (int i = 0; i < baoXiaoDanList.size(); i++) {
            m.add(baoXiaoDanList.get(i).getCommitTime());
        }
        /*m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");*/

        return m;
    }

}
