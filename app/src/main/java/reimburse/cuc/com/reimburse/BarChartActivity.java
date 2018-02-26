package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import reimburse.cuc.com.bean.BaoXiaoDan;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.util.StreamTools;

public class BarChartActivity extends Activity {
    static  final String TAG = BarChartActivity.class.getSimpleName();
    private BarChart barChart;
    private Typeface mTf;//声明字体库
    BarData barData;
    static  final int SHOW_ALL_BAOXIAO_BAR = 6688;
    private List<BaoXiaoDan> baoXiaoDanList = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_BAOXIAO_BAR) {
                generateDataBar();
                barChart.setData(barData);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        barChart = (BarChart)findViewById(R.id.bar_chart);
        //加载本地的字体库
        //设置图表的描述
        //barChart.setDescription("北京市雾霾指数统计表");
        //是否设置网格背景
        barChart.setDrawGridBackground(false);
        //是否绘制柱状图的阴影
        barChart.setDrawBarShadow(false);

        //获取x轴
        XAxis xAxis = barChart.getXAxis();
        //设置x轴显示的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x轴显示的字体
        xAxis.setTypeface(mTf);
        //是否绘制网格线
        xAxis.setDrawGridLines(true);
        //是否绘制x轴轴线
        xAxis.setDrawAxisLine(true);

        //获取左边的y轴
        YAxis leftAxis = barChart.getAxisLeft();
        //设置左边的y轴的字体
        leftAxis.setTypeface(mTf);
        //参数1：指明左边的y轴设置区间的个数 参数2：是否均匀显示区间
        leftAxis.setLabelCount(5, false);
        //设置最高的柱状图距离顶部的数值
        leftAxis.setSpaceTop(10f);

        YAxis rightAxis = barChart.getAxisRight();
        //不显示右边的y轴
        rightAxis.setEnabled(false);




        // set data
        //barChart.setData(barData);

        //设置y轴的动画显示
        barChart.animateY(700);
        getAllBaoxiaoDanByUserId();


    }


    public  void  getAllBaoxiaoDanByUserId(){

        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidGetBarChart_URL;
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
                        message.what = SHOW_ALL_BAOXIAO_BAR;
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
     *
     * 生成显示的随机数据
     * 这些数据在项目中，可能来自于服务器。需要联网获取数据
     * @return
     */
    private void generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        ArrayList<String> m = new ArrayList<String>();
        Log.e("服务端得到的数据个数: ", baoXiaoDanList.size() + "");
        for (int i = 0; i < baoXiaoDanList.size(); i++) {
            BaoXiaoDan bean = baoXiaoDanList.get(i);
            float jine = Float.parseFloat(bean.getBaoxiaodan_jine());
            String projectName = bean.getBaoxiaodan_project();
            entries.add(new BarEntry(jine, i));
            m.add(projectName);
            Log.e(TAG+"遍历报销柱状图数据"+i+" : ",projectName+" : "+jine);
        }

        BarDataSet d = new BarDataSet(entries, "报销金额" );
        //设置柱状图之间的间距
        d.setBarSpacePercent(40f);
        //设置柱状图显示的颜色
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //设置高亮的透明度
        d.setHighLightAlpha(255);
        //设置x轴的数据
        barData = new BarData(m, d);
        barData.setValueTypeface(mTf);
        //return cd;
    }
   /* private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
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
        m.add("Dec");

        return m;
    }*/

}
