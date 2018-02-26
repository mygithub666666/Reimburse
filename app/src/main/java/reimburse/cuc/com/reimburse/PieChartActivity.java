package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.bean.BaoXiaoDan;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.util.StreamTools;

public class PieChartActivity extends Activity {
    static  final String TAG = PieChartActivity.class.getSimpleName();
    private Typeface mTf;//声明字体库
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList = new ArrayList<>();
    protected static  final int SHOW_ALL_PIE_CHART = 168;
    PieChart pieChart;
    PieData pieData;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_PIE_CHART) {
                generateDataPie();
                //设置数据的显示格式：百分比
                pieData.setValueFormatter(new PercentFormatter());
                //设置字体
                pieData.setValueTypeface(mTf);
                //设置字体大小
                pieData.setValueTextSize(11f);
                //设置字体颜色
                pieData.setValueTextColor(Color.RED);
                // set data
                pieChart.setData(pieData);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        pieChart = (PieChart)findViewById(R.id.pie_chart);



        //加载本地的字体库
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        //设置图表的描述信息
        pieChart.setDescription("报销的消费类型占比");
        //设置内层圆的半径
        pieChart.setHoleRadius(52f);
        //设置包裹内层圆的圆的半径（不是最外层圆）
        pieChart.setTransparentCircleRadius(57f);

        //设置内层圆中的文本内容
        pieChart.setCenterText("消费类型占比");
        //设置文本字体
        pieChart.setCenterTextTypeface(mTf);
        //设置内层圆中的文本内容的字体大小
        pieChart.setCenterTextSize(18f);
        //是否使用百分比表示：各个部分相加的和是否是100%
        pieChart.setUsePercentValues(true);

       /* //获取数据
        PieData pieData = generateDataPie();*/




        //获取说明部分
        Legend l = pieChart.getLegend();
        //设置其显示的位置
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //设置相邻的项在y轴方向上的间距
        l.setYEntrySpace(10f);
        //设置最上面的项距离顶部的值
        l.setYOffset(30f);

        // do not forget to refresh the chart
        // pieChart.invalidate();
        pieChart.animateXY(900, 900);

        testPost();

    }


    public void testPost(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.GetAllXiaoFeiPieChart;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    String data ="&user_id="+URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID),"utf-8");

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
                        tongyiXiaofeiBeanList = JSON.parseArray(result, TongyiXiaofeiBean.class);
                        Log.e(TAG+"服务端返回的个数: ",tongyiXiaofeiBeanList.size()+"");
                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_PIE_CHART;
                        msg.obj = tongyiXiaofeiBeanList;
                        handler.sendMessage(msg);
                        Log.e("返回的JSON数据",result);
                        if(result.equals("保存成功")) {
                        }
                    }else{
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("请求失败");
                }

            };
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
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private void generateDataPie() {

        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> q = new ArrayList<String>();

        for (int i = 0; i < tongyiXiaofeiBeanList.size(); i++) {
            TongyiXiaofeiBean bean = tongyiXiaofeiBeanList.get(i);
            String type = bean.getXiaofei_type();
            float jine = Float.parseFloat(bean.getTotalMoney());
            entries.add(new Entry(jine, i));
            q.add(type);
        }

        PieDataSet d = new PieDataSet(entries, "");

        //设置不同部分之间的间隙的宽度
        d.setSliceSpace(2f);
        //设置不同部分的颜色
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);



        pieData = new PieData(q, d);
        //return cd;
    }

   /* private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        q.add("三星");
        q.add("OPPO");
        q.add("VIVO");
        q.add("华为");

        return q;
    }*/

}
