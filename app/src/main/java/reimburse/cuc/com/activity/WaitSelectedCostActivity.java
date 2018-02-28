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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.DailyCost;
import reimburse.cuc.com.bean.DailyReim;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2018/1/2.
 */
public class WaitSelectedCostActivity extends Activity {
    private static final int SHOW_ALL_XIAOFEI = 01;
    private static final int RESPONSE_SELECT_EXPENSE_COMPLETED = 1001;
    private ListView listview_wait_selected_cost;
    private Button btn_complete_cost_selected;

    private TextView tvExpenseTotal;
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList= new ArrayList<TongyiXiaofeiBean>();
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanListTag= new ArrayList<TongyiXiaofeiBean>();
    private List<TongyiXiaofeiBean> returnList= new ArrayList<TongyiXiaofeiBean>();
    private EditText et_uniform_cost_tag;
    MyAdapter myAdapter;
    UniformCostTagAdapter uniformCostTagAdapter;
    private PopupWindow popupWindow;

    private ListView costListView;
    private List<String> costTagNameList = new ArrayList<>();



    //记录checkbox的状态
    static HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_XIAOFEI) {
                listview_wait_selected_cost.setAdapter(myAdapter);
                costListView.setAdapter(uniformCostTagAdapter);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_selected_cost);
        et_uniform_cost_tag = (EditText) findViewById(R.id.et_uniform_cost_tag);
        listview_wait_selected_cost = (ListView) findViewById(R.id.listview_wait_selected_cost);
        tvExpenseTotal = (TextView) findViewById(R.id.tv_wait_selected_amount);
        btn_complete_cost_selected = (Button) findViewById(R.id.btn_complete_cost_selected);


        costListView = new ListView(this);
        costListView.setBackgroundResource(R.color.white);
        get_All_DAILY_AND_TRAFFIC_COST_JSON();
        et_uniform_cost_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(WaitSelectedCostActivity.this);
                    popupWindow.setWidth(et_uniform_cost_tag.getWidth());
                    popupWindow.setHeight(200);
                    popupWindow.setContentView(costListView);
                    popupWindow.setFocusable(true);

                }
                popupWindow.showAsDropDown(et_uniform_cost_tag, 0, 0);
            }
        });
        costListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //1.得到数据
               /* TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeanListTag.get(position);*/
                String cost_tag_name = costTagNameList.get(position);
                Toast.makeText(getApplicationContext(),cost_tag_name,Toast.LENGTH_LONG).show();

                //get_All_DAILY_AND_TRAFFIC_COST_TAG_JSON();

                android_GET_All_DAILY_AND_TRAFFIC_COST_JSON_BY_TAG(cost_tag_name);
                //myAdapter.notifyDataSetChanged();

                //uniformCostTagAdapter.notifyDataSetChanged();


                /*for (int i=0;i<tongyiXiaofeiBeanList.size();i++){
                    TongyiXiaofeiBean tb = tongyiXiaofeiBeanList.get(i);
                    if(!tb.getCost_tag().contains(cost_tag_name)) {

                        tongyiXiaofeiBeanList.remove(tb);
                    }
                }*/

                //myAdapter.notifyDataSetChanged();

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });


        listview_wait_selected_cost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeanList.get(position);
                String type = tongyiXiaofeiBean.getXiaofei_type();
                if( (type.equals("火车")) || (type.equals("飞机")) || (type.equals("轮船")) || (type.equals("汽车")) ) {

                    Intent intent = new Intent(WaitSelectedCostActivity.this,TrafficCostDisplayActivity.class);
                    String traffic_json = tongyiXiaofeiBean.getOtherInfo();
                    Traffic_Cost traffic_cost = JSON.parseObject(traffic_json, Traffic_Cost.class);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + traffic_json);
                    intent.putExtra("TrafficCostJsonString",traffic_json);
                    startActivity(intent);

                }else {

                    Intent intent = new Intent(WaitSelectedCostActivity.this,DailyCostDisplayActivity.class);
                    String daily_json = tongyiXiaofeiBean.getOtherInfo();
                    /*DailyReim dailyReim = (DailyReim) JSON.parseObject(
                            dailyCost_JSON_STRING, DailyReim.class);*/
                    DailyCost cost = JSON.parseObject(daily_json, DailyCost.class);
                    // 用户组对象转JSON串
                    String jsonString = JSON.toJSONString(cost);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + jsonString);
                    intent.putExtra("DailyCostJsonString",jsonString);
                    startActivity(intent);
                }

            }
        });
        btn_complete_cost_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnList.clear();
                for (int i=0;i<tongyiXiaofeiBeanList.size();i++){
                    TongyiXiaofeiBean item = tongyiXiaofeiBeanList.get(i);
                    if(item.isSelected()) {
                        Log.e("遍历第"+i+"个消费条目",item.toString());
                        returnList.add(item);
                    }else {
                        continue;
                    }

                }

                for (int i=0;i<returnList.size();i++){
                    Log.e("遍历选中的消费集合：","第"+i+"个"+returnList.get(i).toString());
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("tongyiXiaofeiBeanList", (Serializable) returnList);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESPONSE_SELECT_EXPENSE_COMPLETED, intent);
                finish();
            }
        });
    }

    // String data = "user_name="+URLEncoder.encode(un, "utf-8")+"&password="+URLEncoder.encode(pawd);
    public void get_All_DAILY_AND_TRAFFIC_COST_JSON(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.ANDROID_GET_All_DAILY_AND_TRAFFIC_COST_JSON;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    //String data = "user_name="+URLEncoder.encode(un, "utf-8")+"&password="+URLEncoder.encode(pawd);
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

                        tongyiXiaofeiBeanList = JSON.parseArray(result,TongyiXiaofeiBean.class);
                        tongyiXiaofeiBeanListTag = JSON.parseArray(result,TongyiXiaofeiBean.class);

                        HashSet<String> hashSet = new HashSet<String>();

                        for (int j = 0;j<tongyiXiaofeiBeanListTag.size();j++){
                            TongyiXiaofeiBean tonyi = tongyiXiaofeiBeanListTag.get(j);
                            String cost_tag_name = tonyi.getCost_tag();
                            hashSet.add(cost_tag_name);
                        }

                        costTagNameList.addAll(hashSet);



                        for (int i=0;i<tongyiXiaofeiBeanList.size();i++){
                            TongyiXiaofeiBean item = tongyiXiaofeiBeanList.get(i);
                            Log.e("遍历从服务端得到的消费集合：", "第" + i + "个" + item.toString());
                        }

                        if(tongyiXiaofeiBeanList != null && tongyiXiaofeiBeanList.size() >0) {
                            myAdapter = new MyAdapter();
                        }


                        if(costTagNameList != null) {
                            uniformCostTagAdapter = new UniformCostTagAdapter();
                        }

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_XIAOFEI;
                        msg.obj = tongyiXiaofeiBeanList;
                        handler.sendMessage(msg);

                        /*if(result.equals("保存成功")) {
                           startActivity(intent);
                        }*/
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
    /*public void get_All_DAILY_AND_TRAFFIC_COST_TAG_JSON(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.ANDROID_GET_All_DAILY_AND_TRAFFIC_COST_JSON;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    //String data = "user_name="+URLEncoder.encode(un, "utf-8")+"&password="+URLEncoder.encode(pawd);
                    String data ="user_id="+ URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID), "utf-8");

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

                        //tongyiXiaofeiBeanList = JSON.parseArray(result,TongyiXiaofeiBean.class);
                        tongyiXiaofeiBeanListTag = JSON.parseArray(result,TongyiXiaofeiBean.class);

                        HashSet<String> hashSet = new HashSet<String>();

                        for (int j = 0;j<tongyiXiaofeiBeanListTag.size();j++){
                            TongyiXiaofeiBean tonyi = tongyiXiaofeiBeanListTag.get(j);
                            String cost_tag_name = tonyi.getCost_tag();
                            hashSet.add(cost_tag_name);
                        }

                        costTagNameList.addAll(hashSet);

                        for (int i=0;i<tongyiXiaofeiBeanList.size();i++){
                            TongyiXiaofeiBean item = tongyiXiaofeiBeanList.get(i);
                            Log.e("遍历从服务端得到的消费集合：","第"+i+"个"+item.toString());
                        }

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_XIAOFEI;
                        msg.obj = tongyiXiaofeiBeanList;
                        handler.sendMessage(msg);

                    }else{
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("请求失败");
                }

            };
        }.start();
    }*/
    public void android_GET_All_DAILY_AND_TRAFFIC_COST_JSON_BY_TAG(final String cost_tag){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    //android_GET_All_DAILY_AND_TRAFFIC_COST_JSON_BY_TAG
                    String path = Constants.android_GET_All_DAILY_AND_TRAFFIC_COST_JSON_BY_TAG;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    //String data ="&user_id="+ URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID), "utf-8");
                    String data ="&user_id="+ URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID), "utf-8")+"&cost_tag="+URLEncoder.encode(cost_tag,"utf-8");


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

                        tongyiXiaofeiBeanList = JSON.parseArray(result,TongyiXiaofeiBean.class);
                        //tongyiXiaofeiBeanListTag = JSON.parseArray(result,TongyiXiaofeiBean.class);

                        for (int i=0;i<tongyiXiaofeiBeanList.size();i++){
                            TongyiXiaofeiBean item = tongyiXiaofeiBeanList.get(i);
                            Log.e("遍历从服务端得到的消费集合：","第"+i+"个"+item.toString());
                        }

                        if(tongyiXiaofeiBeanList != null && tongyiXiaofeiBeanList.size() >0) {
                            myAdapter = new MyAdapter();
                        }
                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_XIAOFEI;
                        msg.obj = tongyiXiaofeiBeanList;
                        handler.sendMessage(msg);

                        /*if(result.equals("保存成功")) {
                           startActivity(intent);
                        }*/
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

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tongyiXiaofeiBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return tongyiXiaofeiBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.recycler_view_item_daily_traffic_uniform,null);
                viewHolder.check = (CheckBox) convertView.findViewById(R.id.checkbox_recyclerview_item_daily_traffic_uniform);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.tv_type_recyclerview_item_daily_traffic_uniform);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date_recyclerview_item_daily_traffic_uniform);
                viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_total_amount_recyclerview_item_daily_traffic_uniform);
                viewHolder.tvOtherInfo = (TextView) convertView.findViewById(R.id.tv_invoice_pic_urls_recyclerview_item_daily_traffic_uniform);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }

            final TongyiXiaofeiBean consumption = tongyiXiaofeiBeanList.get(position);
            //viewHolder.check.setChecked(consumption.isSelected());
            viewHolder.tvType.setText(consumption.getXiaofei_type());
            viewHolder.tvDate.setText(consumption.getRiqi());
            viewHolder.tvMoney.setText(consumption.getTotalMoney());

            viewHolder.tvOtherInfo.setVisibility(View.INVISIBLE);
            viewHolder.tvOtherInfo.setText(consumption.getOtherInfo());

            viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    consumption.setIsSelected(isChecked);
                    /*if (isChecked) {
                        tongyiXiaofeiBeanList.get(position).setIsSelected(true);
                        //state.put(position, isChecked);
                    } else {
                        //state.remove(position);
                        tongyiXiaofeiBeanList.get(position).setIsSelected(false);
                    }*/
                }
            });
            viewHolder.check.setChecked(consumption.isSelected());
            return convertView;
        }
    }

    static class ViewHolder{
        public CheckBox check;
        public TextView tvType;
        public TextView tvDate;
        public TextView tvMoney;
        public TextView tvOtherInfo;
    }
    class UniformCostTagAdapter extends  BaseAdapter{

        @Override
        public int getCount() {
            return  costTagNameList.size();
        }

        @Override
        public Object getItem(int position) {
            return  costTagNameList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CostTagNameViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new CostTagNameViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_cost_tag_name,null);
                viewHolder.cost_tag_name = (TextView) convertView.findViewById(R.id.tv_cost_tag_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (CostTagNameViewHolder) convertView.getTag();

            }
            /*TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeanList.get(position);
            Integer cost_uuid = tongyiXiaofeiBean.getId();*/
            String cost_tag_name = costTagNameList.get(position);
            viewHolder.cost_tag_name.setText(cost_tag_name);
            return convertView;
        }
    }

    static  class  CostTagNameViewHolder{
        public TextView cost_tag_name;
    }

}
