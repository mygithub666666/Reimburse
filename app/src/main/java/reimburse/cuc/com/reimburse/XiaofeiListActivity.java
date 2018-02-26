package reimburse.cuc.com.reimburse;

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
import android.widget.EditText;
import android.widget.ImageView;
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

import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.bean.Caigou;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.util.BitmapUtils;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/10/11.
 */
public class XiaofeiListActivity extends Activity {
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList = new ArrayList<>();
    private ListView xiaofeilist_lv;
    protected static  final int SHOW_ALL_XIAOFEI = 0001;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_XIAOFEI) {
                xiaofeilist_lv.setAdapter(new XiaoFeiListAdapter());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaofeilist);
        xiaofeilist_lv = (ListView) findViewById(R.id.xiaofeilist_lv);
        testPost();

        xiaofeilist_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // showToastInAnyThread(position+"");
                TongyiXiaofeiBean bean = tongyiXiaofeiBeanList.get(position);
                String type  = bean.getXiaofei_type();
                String attrInfo = bean.getOtherInfo();
                String[] attrInfoArr = attrInfo.split("=");
                //showToastInAnyThread(bean.getXiaofei_type());
                switch (type) {
                    case  "采购费":
                        //  "优盘=个=8=6=实验室办公=201710161541256000.png|201710161541256046.png",
                        String caigoufei_wupinminchen = attrInfoArr[0];
                        String caigoufei_danwei = attrInfoArr[1];
                        String caigoufei_danjia = attrInfoArr[2];
                        String caigoufei_shuliang = attrInfoArr[3];
                        String caigoufei_jine = bean.getTotalMoney();
                        String caigoufei_riqi = bean.getRiqi();
                        String caigoufei_desc = attrInfoArr[4];
                        String fapiaoUrls = attrInfoArr[5];

                        Intent intent = new Intent(XiaofeiListActivity.this,CaigoufeiDisplayActivity.class);
                        intent.putExtra("caigoufei_wupinminchen",caigoufei_wupinminchen);
                        intent.putExtra("caigoufei_danwei",caigoufei_danwei);
                        intent.putExtra("caigoufei_danjia",caigoufei_danjia);
                        intent.putExtra("caigoufei_shuliang",caigoufei_shuliang);
                        intent.putExtra("caigoufei_jine",caigoufei_jine);
                        intent.putExtra("caigoufei_riqi",caigoufei_riqi);
                        intent.putExtra("caigoufei_desc",caigoufei_desc);
                        intent.putExtra("caigoufei_desc",caigoufei_desc);
                        intent.putExtra("fapiaoUrls",fapiaoUrls);
                        startActivity(intent);
                        break;
                    case "交通费":

                        /*
                        *   chufadi = (EditText) findViewById(R.id.jiaotongfei_display_chufadi_et);
                            mudidi = (EditText) findViewById(R.id.jiaotongfei_display_mudidi_et);
                            chufariqi = (EditText) findViewById(R.id.jiaotongfei_display_churariqi_et);
                            daodariqi = (EditText) findViewById(R.id.jiaotongfei_display_daodariqi_et);
                            jiaotonggongju = (EditText) findViewById(R.id.jiaotongfei_display_jiaotonggongju_et);
                            piaojiaEt = (EditText) findViewById(R.id.jiaotongfei_display__piaojia_et);
                            desc = (EditText) findViewById(R.id.jiaotongfei_display_desc_et);
                        *   concat(c.startCity,"=",c.endCity,"=",c.daodaTime,"=",c.jiaotongleixing,"=",c.huafei_desc,"=",c.fapiaourls
                        * "北京=广州=高铁商务座=广州出差乘坐火车=201710161840395117.png|201710161840395117.png",
                        * */
                        Intent intent1 = new Intent(XiaofeiListActivity.this,JiaotongfeiDisplayActivity.class);
                        intent1.putExtra("chufadi",attrInfoArr[0]);
                        intent1.putExtra("mudidi",attrInfoArr[1]);
                        intent1.putExtra("chufariqi",bean.getRiqi());
                        intent1.putExtra("daodariqi",attrInfoArr[2]);
                        intent1.putExtra("jiaotonggongju",attrInfoArr[3]);
                        intent1.putExtra("piaojiaEt",bean.getTotalMoney());
                        intent1.putExtra("desc",attrInfoArr[4]);
                        intent1.putExtra("fapiaoUrls",attrInfoArr[5]);
                        startActivity(intent1);
                        break;
                    case "餐费":
                        /*
                        *       canfei_display_xiaofei_type_et = (EditText) findViewById(R.id.canfei_display_xiaofei_type_et);
                                canfei_danwei_et = (EditText) findViewById(R.id.canfei_display_danwei_et);
                                canfei_danjia_et  = (EditText) findViewById(R.id.canfei_display_danjia_et);
                                canfei_shuliang_et = (EditText) findViewById(R.id.canfei_display_shuliang_et);
                                canfei_jine_et = (EditText) findViewById(R.id.canfei_display_jine_et);
                                canfei_riqi_et = (EditText) findViewById(R.id.canfei_display_riqi_et);
                                canfei_desc_et = (EditText) findViewById(R.id.canfei_display_desc_et);
                                gridView = (GridView) findViewById(R.id.canfei_display_gridview_fapiao);
                                "份=1=8=招待高级专家用餐=201710161949263453.png|201710161949263426.png",
                        *
                        * */
                        //startActivity(new Intent(XiaofeiListActivity.this,CanfeiDisplayActivity.class));
                        Intent intent2 = new Intent(XiaofeiListActivity.this,CanfeiDisplayActivity.class);
                        intent2.putExtra("canfei_display_xiaofei_type",bean.getXiaofei_type());
                        intent2.putExtra("canfei_danwei",attrInfoArr[0]);
                        intent2.putExtra("canfei_danjia",attrInfoArr[2]);
                        intent2.putExtra("canfei_shuliang",attrInfoArr[1]);
                        intent2.putExtra("canfei_jine",bean.getTotalMoney());
                        intent2.putExtra(" canfei_riqi",bean.getRiqi());
                        intent2.putExtra("canfei_desc",attrInfoArr[3]);
                        intent2.putExtra("fapiaoUrls",attrInfoArr[4]);
                        startActivity(intent2);
                        break;
                }
            }
        });
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

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_XIAOFEI;
                        msg.obj = tongyiXiaofeiBeanList;
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

    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }

    class XiaoFeiListAdapter extends BaseAdapter{

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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_caigou,null);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.lv_item_caigou_type);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.lv_item_caigou__date);
                viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.lv_item_caigou_count);
                viewHolder.tvOhterInfo = (TextView) convertView.findViewById(R.id.lv_item_caigou__date_picuris);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }
            TongyiXiaofeiBean consumption = tongyiXiaofeiBeanList.get(position);
            viewHolder.tvType.setText(consumption.getXiaofei_type());
            viewHolder.tvDate.setText(consumption.getRiqi());
            viewHolder.tvMoney.setText(consumption.getTotalMoney());

            viewHolder.tvOhterInfo.setVisibility(View.INVISIBLE);
            viewHolder.tvOhterInfo.setText(consumption.getOtherInfo());
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvType;
        public TextView tvDate;
        public TextView tvMoney;
        public TextView tvOhterInfo;
    }

}
