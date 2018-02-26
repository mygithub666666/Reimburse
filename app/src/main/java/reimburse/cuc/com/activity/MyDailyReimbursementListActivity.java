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
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2018/1/18.
 */
public class MyDailyReimbursementListActivity extends Activity {
    private static final int SHOW_MY_DAILY_REIM_LIST = 01;
    @Bind(R.id.listview_my_daily_reim_list)
    ListView listviewMyDailyReimList;
    private List<DailyReim> dailyReimList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_MY_DAILY_REIM_LIST) {
                listviewMyDailyReimList.setAdapter(new MyDailyReimbursementListAdapter());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dailyReimList = new ArrayList<>();
        setContentView(R.layout.activity_my_daily_reim_list);
        ButterKnife.bind(this);
        androidGetOwnDailyReimListByUserID();
        listviewMyDailyReimList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DailyReim dailyReim = dailyReimList.get(position);
                String dailyReimJSONStr = JSON.toJSONString(dailyReim);
                Intent intent = new Intent(MyDailyReimbursementListActivity.this,DailyReimDisplay.class);
                // 用户组对象转JSON串
                //String jsonString = JSON.toJSONString(cost);
                Log.e("日常报销列表的点击事件", ", dailyReimJSONStr:" + dailyReimJSONStr);
                intent.putExtra("dailyReimJSONStr",dailyReimJSONStr);
                startActivity(intent);

            }
        });
    }

    public void androidGetOwnDailyReimListByUserID() {
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.ANDROID_GET_OWN_DAILY_REIM_LIST_BY_USER_ID;
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
                        dailyReimList = JSON.parseArray(result, DailyReim.class);
                        Message message = Message.obtain();
                        message.obj = dailyReimList;
                        message.what = SHOW_MY_DAILY_REIM_LIST;
                        handler.sendMessage(message);
                        Log.e("从服务端得到的报销数据", dailyReimList.size() + "");
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


    class MyDailyReimbursementListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dailyReimList.size();
        }

        @Override
        public Object getItem(int position) {
            return dailyReimList.get(position);
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
            DailyReim dailyReim = dailyReimList.get(position);
            viewHolder.tvBaoxiaoShiyou.setText(dailyReim.getDaily_reim_cause());
            viewHolder.tvTotalMoney.setText(dailyReim.getDaily_reim_amount());
            viewHolder.tvCheckStatus.setText(dailyReim.getDaily_reim_check_status());
            viewHolder.tvtongyiXiaofeiBeanListStr.setVisibility(View.INVISIBLE);
            //String tongyiXiaofeiBeansStr = JSON.toJSONString(dailyReim.getTongyiXiaofeiBeans());
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
