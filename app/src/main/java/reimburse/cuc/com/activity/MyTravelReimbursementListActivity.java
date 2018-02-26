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

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.DailyReim;
import reimburse.cuc.com.bean.Travel_Reimbursement;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2018/1/18.
 */
public class MyTravelReimbursementListActivity extends Activity {


    private static final int SHOW_MY_TRAVEL_REIM_LIST = 01;
    private List<Travel_Reimbursement> travel_reimbursementList;
    @Bind(R.id.listview_my_travel_reim_list)
    ListView listviewMyTravelReimList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_MY_TRAVEL_REIM_LIST) {
                listviewMyTravelReimList.setAdapter(new MyTravelReimbursementListAdapter());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_travel_reim_list);
        ButterKnife.bind(this);
        travel_reimbursementList = new ArrayList<>();
        androidGetOwnTravelReimListByUserID();

        listviewMyTravelReimList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Travel_Reimbursement tm = travel_reimbursementList.get(position);
                String travelReimJSONStr = JSON.toJSONString(tm);
                Intent intent = new Intent(MyTravelReimbursementListActivity.this,TravelReimDisplayActivity.class);
                // 用户组对象转JSON串
                //String jsonString = JSON.toJSONString(cost);
                Log.e("日常报销列表的点击事件", ", travelReimJSONStr:" + travelReimJSONStr);
                intent.putExtra("travelReimJSONStr",travelReimJSONStr);
                startActivity(intent);
            }
        });

    }

    public void androidGetOwnTravelReimListByUserID() {
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.ANDROID_GET_OWN_TRAVEL_REIM_LIST_BY_USER_ID;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    String data = "&user_id=" + URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID), "utf-8");

                    conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                    //post的请求是把数据以流的方式写给了服务器
                    //指定请求的输出模式
                    conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                    conn.getOutputStream().write(data.getBytes());
                    Log.e("post size", String.valueOf(data.getBytes().length));
                    int code = conn.getResponseCode();

                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        final String result = StreamTools.readStream(is);
                        travel_reimbursementList = JSON.parseArray(result, Travel_Reimbursement.class);
                        Message message = Message.obtain();
                        message.obj = travel_reimbursementList;
                        message.what = SHOW_MY_TRAVEL_REIM_LIST;
                        handler.sendMessage(message);
                        Log.e("从服务端得到的报销数据", travel_reimbursementList.size() + "");
                        Log.e("返回的JSON数据", result);
                        //showToastInAnyThread(result);
                        if (result.equals("保存成功")) {

                        }
                    } else {
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("请求失败");
                }

            }
        }.start();
    }


    class MyTravelReimbursementListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return travel_reimbursementList.size();
        }

        @Override
        public Object getItem(int position) {
            return travel_reimbursementList.get(position);
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
                convertView = layoutInflater.inflate(R.layout.lv_item_baoxiaodan,null);
                viewHolder.tvBaoxiaoShiyou = (TextView) convertView.findViewById(R.id.lv_item_baoxiaodan_type);
                viewHolder.tvTotalMoney = (TextView) convertView.findViewById(R.id.lv_item_baoxiaodan__date);
                viewHolder.tvCheckStatus = (TextView) convertView.findViewById(R.id.lv_item_baoxiaodan_count);
                viewHolder.tvtongyiXiaofeiBeanListStr = (TextView) convertView.findViewById(R.id.lv_item_baoxiaodan__date_picuris);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }
            Travel_Reimbursement tm = travel_reimbursementList.get(position);
            viewHolder.tvBaoxiaoShiyou.setText(tm.getTravel_reimbursement_cause());
            viewHolder.tvTotalMoney.setText(tm.getTravel_reimbursement_total_amount());
            viewHolder.tvCheckStatus.setText(tm.getCheck_status());
            viewHolder.tvtongyiXiaofeiBeanListStr.setVisibility(View.INVISIBLE);
            viewHolder.tvtongyiXiaofeiBeanListStr.setText("");
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvBaoxiaoShiyou;
        public TextView tvTotalMoney;
        public TextView tvCheckStatus;
        public TextView tvtongyiXiaofeiBeanListStr;
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
