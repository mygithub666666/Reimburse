package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/10/18.
 */
public class MyBaoxiaodanDetail extends Activity {
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList = new ArrayList<>();
    private ListView xiaofeilist_lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybaoxiaodan_detail);
        getAllBaoxiaoDanByUserId();
    }

    public void getAllBaoxiaoDanByUserId(){
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
                        //tongyiXiaofeiBeanList = JSON.parseArray(result, TongyiXiaofeiBean.class);


                        Log.e("返回的JSON数据",result);
                        showToastInAnyThread(result);
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

            }
        }.start();
    }

    class XiaoFeiListAdapter extends BaseAdapter {

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

    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
