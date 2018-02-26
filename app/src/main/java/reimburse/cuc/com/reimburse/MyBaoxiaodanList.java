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

import reimburse.cuc.com.bean.BaoXiaoDan;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/10/19.
 */
public class MyBaoxiaodanList extends Activity {
    private List<BaoXiaoDan> baoXiaoDanList = new ArrayList<>();
    private ListView listView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_BAOXIAO) {
                listView.setAdapter(new MyBaoXiaoListAdapter());
            }
        }
    };
    protected static int SHOW_ALL_BAOXIAO = 0002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybaoxiaodan_list);
        listView = (ListView) findViewById(R.id.my_baoxiaodan_list_lv);
        getAllBaoxiaoDanByUserId();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaoXiaoDan bean = baoXiaoDanList.get(position);
                String baoxiaoShiyou  = bean.getBaoxiaodan_shiyou();
                String project = bean.getBaoxiaodan_project();
                String jine  = bean.getBaoxiaodan_jine();
                String xiaofejsonStr = JSON.toJSONString(bean.getTongyiXiaofeiBeanList());
                // 交通费 出发地~目的地:北京~广州 起止日期:2017-10-16~2017-10-16 交通工具及座位类型:高铁商务座 票价:688 描述:广州出差乘坐火车===201710161840395117.pngxiaofei餐费 单位:份 单价:8 数量:1 总价:8 日期:2017-10-16 描述:招待高级专家用餐===201710161949263453.png|201710161949263426.pngxiaofei采购费 采购商品:macpro 单位:个 单价:8 数量:6 总价:48 日期:2017-11-19 描述:实验室办公===201711191559394979.png|201711191559394948.png


                /**
                 *   private EditText et_baoxiaoshiyou;
                     private EditText et_baoxiaojine;
                     private EditText et_baoxiaoproject;
                     private String endTime;
                     private String commitTime;
                     private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList;
                 */
                Intent intent = new Intent(MyBaoxiaodanList.this,ReimburseFormDisplayActivity.class);
                intent.putExtra("baoxiaoshiyou",baoxiaoShiyou);
                intent.putExtra("project",project);
                intent.putExtra("jine",jine);
                intent.putExtra("xiaofejsonStr",xiaofejsonStr);
                intent.putExtra("commitTime",bean.getCommitTime());
                intent.putExtra("endTime",bean.getEndTime());
                intent.putExtra("status",bean.getCheck_status());
                intent.putExtra("comments",bean.getComments());
                startActivity(intent);
            }
        });

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
                        baoXiaoDanList = JSON.parseArray(result, BaoXiaoDan.class);
                        Message message = Message.obtain();
                        message.obj = baoXiaoDanList;
                        message.what = SHOW_ALL_BAOXIAO;
                        handler.sendMessage(message);
                        Log.e("从服务端得到的报销数据",baoXiaoDanList.size()+"");
                        Log.e("返回的JSON数据",result);
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

    class MyBaoXiaoListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return baoXiaoDanList.size();
        }

        @Override
        public Object getItem(int position) {
            return baoXiaoDanList.get(position);
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
            BaoXiaoDan  baoXiaoDan = baoXiaoDanList.get(position);
            viewHolder.tvBaoxiaoShiyou.setText(baoXiaoDan.getBaoxiaodan_shiyou());
            viewHolder.tvTotalMoney.setText(baoXiaoDan.getBaoxiaodan_jine());
            viewHolder.tvCheckStatus.setText(baoXiaoDan.getCheck_status());
            viewHolder.tvtongyiXiaofeiBeanListStr.setVisibility(View.INVISIBLE);
            String tongyiXiaofeiBeansStr = JSON.toJSONString(baoXiaoDan.getTongyiXiaofeiBeanList());
            viewHolder.tvtongyiXiaofeiBeanListStr.setText(tongyiXiaofeiBeansStr);
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
