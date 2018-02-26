package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

import reimburse.cuc.com.adaptor.CaigouExpenseSubmitAdapter;
import reimburse.cuc.com.adaptor.ExpenseSubmitAdapter;
import reimburse.cuc.com.adaptor.TestRecyclerViewAdapter;
import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.bean.Caigou;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.util.DatabaseHelper;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/8/26.
 */
public class DengdaiXuanzedeXiaofeiList extends Activity{

    private static final int RESPONSE_SELECT_EXPENSE_COMPLETED = 1001;
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList= new ArrayList<TongyiXiaofeiBean>();
    private List<TongyiXiaofeiBean> returnList= new ArrayList<TongyiXiaofeiBean>();
    private CheckBox dengdaixuanze_xiaofei_quanxuan;
    private TextView tvExpenseTotal;
    private Button dengdaixuanze_xiaofei_btn_wancheng;
    private ListView listView;
    protected static  final int SHOW_ALL_XIAOFEI = 0001;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_XIAOFEI) {
                listView.setAdapter(new MyAdapter());
            }
        }
    };
    //记录checkbox的状态
    HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        testPost();
        //  listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToastInAnyThread(position + "");
                TongyiXiaofeiBean bean = tongyiXiaofeiBeanList.get(position);
                String type = bean.getXiaofei_type();
                String attrInfo = bean.getOtherInfo();
                String[] attrInfoArr = attrInfo.split("=");
                showToastInAnyThread(bean.getXiaofei_type());
                switch (type) {
                    case "采购费":
                        String caigoufei_wupinminchen = attrInfoArr[0];
                        String caigoufei_danwei = attrInfoArr[1];
                        String caigoufei_danjia = attrInfoArr[2];
                        String caigoufei_shuliang = attrInfoArr[3];
                        String caigoufei_jine = bean.getTotalMoney();
                        String caigoufei_riqi = bean.getRiqi();
                        String caigoufei_desc = attrInfoArr[4];
                        String fapiaoUrls = attrInfoArr[5];

                        Intent intent = new Intent(DengdaiXuanzedeXiaofeiList.this, CaigoufeiDisplayActivity.class);
                        intent.putExtra("caigoufei_wupinminchen", caigoufei_wupinminchen);
                        intent.putExtra("caigoufei_danwei", caigoufei_danwei);
                        intent.putExtra("caigoufei_danjia", caigoufei_danjia);
                        intent.putExtra("caigoufei_shuliang", caigoufei_shuliang);
                        intent.putExtra("caigoufei_jine", caigoufei_jine);
                        intent.putExtra("caigoufei_riqi", caigoufei_riqi);
                        intent.putExtra("caigoufei_desc", caigoufei_desc);
                        intent.putExtra("caigoufei_desc", caigoufei_desc);
                        intent.putExtra("fapiaoUrls", fapiaoUrls);
                        startActivity(intent);
                        break;
                    case "交通费":

                        Intent intent1 = new Intent(DengdaiXuanzedeXiaofeiList.this, JiaotongfeiDisplayActivity.class);
                        intent1.putExtra("chufadi", attrInfoArr[0]);
                        intent1.putExtra("mudidi", attrInfoArr[1]);
                        intent1.putExtra("chufariqi", bean.getRiqi());
                        intent1.putExtra("daodariqi", attrInfoArr[2]);
                        intent1.putExtra("jiaotonggongju", attrInfoArr[3]);
                        intent1.putExtra("piaojiaEt", bean.getTotalMoney());
                        intent1.putExtra("desc", attrInfoArr[4]);
                        intent1.putExtra("fapiaoUrls", attrInfoArr[5]);
                        startActivity(intent1);
                        break;
                    case "餐费":
                        Intent intent2 = new Intent(DengdaiXuanzedeXiaofeiList.this, CanfeiDisplayActivity.class);
                        intent2.putExtra("canfei_display_xiaofei_type", bean.getXiaofei_type());
                        intent2.putExtra("canfei_danwei", attrInfoArr[0]);
                        intent2.putExtra("canfei_danjia", attrInfoArr[2]);
                        intent2.putExtra("canfei_shuliang", attrInfoArr[1]);
                        intent2.putExtra("canfei_jine", bean.getTotalMoney());
                        intent2.putExtra(" canfei_riqi", bean.getRiqi());
                        intent2.putExtra("canfei_desc", attrInfoArr[3]);
                        intent2.putExtra("fapiaoUrls", attrInfoArr[4]);
                        startActivity(intent2);
                        break;
                }
            }
        });
        dengdaixuanze_xiaofei_btn_wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Map.Entry<Integer,Boolean> entry : state.entrySet()){
                    //showToastInAnyThread(entry.getKey()+": "+entry.getValue());
                    returnList.add(tongyiXiaofeiBeanList.get(entry.getKey()));
                }
                showToastInAnyThread(returnList.size()+"");
                Bundle bundle = new Bundle();
                bundle.putSerializable("tongyiXiaofeiBeanList", (Serializable) returnList);
                Intent intent = new Intent();
                //intent.putExtra("consumptionList", (Serializable) returnList);
                intent.putExtras(bundle);
                setResult(RESPONSE_SELECT_EXPENSE_COMPLETED, intent);
                finish();
            }
        });
    }



    public void initView() {
        setContentView(R.layout.activity_waitforselect);
        dengdaixuanze_xiaofei_quanxuan = (CheckBox) findViewById(R.id.dengdaixuanze_xiaofei_quanxuan);
        tvExpenseTotal = (TextView) findViewById(R.id.dengdaixuanze_xiaofei_tv_heji);
        dengdaixuanze_xiaofei_btn_wancheng = (Button) findViewById(R.id.dengdaixuanze_xiaofei_btn_wancheng);
        listView = (ListView) findViewById(R.id.dengdaixuanzexiaofei_lv);
    }


    public void testPost(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.GET_ALLXIAOFEI_URL;
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
                        tongyiXiaofeiBeanList = JSON.parseArray(result,TongyiXiaofeiBean.class);

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_XIAOFEI;
                        msg.obj = tongyiXiaofeiBeanList;
                        handler.sendMessage(msg);

                        Log.e("返回的JSON数据", result);
                        //showToastInAnyThread(result);
                        if(result.equals("保存成功")) {

                        }
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
                convertView = layoutInflater.inflate(R.layout.recycleview_item_xiafei,null);
                viewHolder.check = (CheckBox) convertView.findViewById(R.id.recyclerview_item_fuxuankuang);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.recyclerview_item_type);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.recyclerview_item_riqi);
                viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.recyclerview_item_jine);
                viewHolder.tvOtherInfo = (TextView) convertView.findViewById(R.id.recyclerview_item_picurl);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }

            TongyiXiaofeiBean consumption = tongyiXiaofeiBeanList.get(position);
            viewHolder.check.setChecked(consumption.isSelected());
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
                    if (isChecked) {
                        state.put(position, isChecked);
                    } else {
                        state.remove(position);
                    }
                }
            });
            viewHolder.check.setChecked((state.get(position) == null ? false : true));
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


}
