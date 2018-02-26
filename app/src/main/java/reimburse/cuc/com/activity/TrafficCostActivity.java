package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/12/25.
 */
public class TrafficCostActivity extends Activity {


    private static final int SHOW_ALL_TRAFFIC_COST = 01;
    private static final String TAG = TrafficCostActivity.class.getSimpleName();
    private ListView lv_traffic_cost_list;

    List<Traffic_Cost> trafficCostList;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_TRAFFIC_COST) {
                lv_traffic_cost_list.setAdapter(new TrafficCostAdapter());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_cost_list);
        lv_traffic_cost_list = (ListView) findViewById(R.id.lv_traffic_cost_list);
        trafficCostList = new ArrayList<>();
        testPost();

        lv_traffic_cost_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG,"position: "+position+",id: "+id);
                Intent intent = new Intent(TrafficCostActivity.this,TrafficCostDisplayActivity.class);
                Traffic_Cost traffic_cost = trafficCostList.get(position);
                // 用户组对象转JSON串
                String jsonString = JSON.toJSONString(traffic_cost);
                Log.e(TAG, ", jsonString:" + jsonString);

                intent.putExtra("TrafficCostJsonString",jsonString);
                startActivity(intent);
            }
        });

    }

    public void testPost(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidGetAllTraffic_CostByUserId;
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
                        trafficCostList = JSON.parseArray(result, Traffic_Cost.class);

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_TRAFFIC_COST;
                        msg.obj = trafficCostList;
                        handler.sendMessage(msg);
                        Log.e("返回的JSON数据",result);
                        //showToastInAnyThread(result);
                        if(result.equals("保存成功")) {
                            //finish();
                          /* Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                           startActivity(intent);*/
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

    class TrafficCostAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return trafficCostList.size();
        }

        @Override
        public Object getItem(int position) {
            return trafficCostList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_traffic_cost,null);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.tv_lv_item_traffic_cost_type);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_lv_item_traffic_cost_start_date);
                viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_lv_item_traffic_cost_amount);
                //viewHolder.tvOhterInfo = (TextView) convertView.findViewById(R.id.tv_lv_item_traffic_cost_other_info);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }
            Traffic_Cost consumption = trafficCostList.get(position);
            viewHolder.tvType.setText(consumption.getTraffic_cost_type());
            viewHolder.tvDate.setText(consumption.getTraffic_cost_start_datetime().split(" ")[0]);
            viewHolder.tvMoney.setText(consumption.getTraffic_cost_total_amount());

            //viewHolder.tvOhterInfo.setVisibility(View.INVISIBLE);
            //viewHolder.tvOhterInfo.setText(consumption.getOtherInfo());
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvType;
        public TextView tvDate;
        public TextView tvMoney;
        //public TextView tvOhterInfo;
    }

    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
