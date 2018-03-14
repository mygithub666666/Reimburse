package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
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
import com.lzy.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.DailyCost;
import reimburse.cuc.com.bean.DailyReim;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/1/18.
 */
public class DailyReimDisplay extends Activity {

    private static final String TAG = DailyReimDisplay.class.getSimpleName();
    private Button btn_export_excel;
    @Bind(R.id.et_daily_reim_cause_display)
    EditText etDailyReimCauseDisplay;
    @Bind(R.id.et_daily_reim_amount_display)
    EditText etDailyReimAmountDisplay;
    @Bind(R.id.ettv_daily_reim_projectname_display)
    EditText ettvDailyReimProjectnameDisplay;
    @Bind(R.id.listview_tongyixiaofei_bean_list)
    ListView listviewTongyixiaofeiBeanList;
    @Bind(R.id.et_daily_reim_committime)
    EditText etDailyReimCommittime;
    @Bind(R.id.et_endtime)
    EditText etEndtime;
    @Bind(R.id.et_checkstatus)
    EditText etCheckstatus;
    @Bind(R.id.et_comments)
    EditText etComments;

    List<TongyiXiaofeiBean> tongyiXiaofeiBeans = new ArrayList<>();
    TongyiXiaofeiBeanListAdapter tongyiXiaofeiBeanListAdapter;


    protected static final int LOAD_IMAGE = 1;
    protected static final int LOAD_ERROR = 2;
    protected static final int EXCEL_DOWNLOAD_SUCCESS = 3;
    // 服务器所有图片的路径
    private List<String> paths;
    private ImageView iv;
    /**
     * 当前的位置
     */
    private int currentPosition = 0;

    //1.创建一个消息处理器
    private Handler handler = new Handler(){
        //3.处理消息
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_IMAGE:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    iv.setImageBitmap(bitmap);
                    break;

                case LOAD_ERROR:
                    Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case EXCEL_DOWNLOAD_SUCCESS:
                    Toast.makeText(getApplicationContext(), "Excel已保存至"+(String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
            }

        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_reim_display);
        ButterKnife.bind(this);
        btn_export_excel = (Button) findViewById(R.id.btn_export_excel);
        Intent intent = getIntent();

        /**
         * intent.putExtra("dailyReimJSONStr",dailyReimJSONStr);
           startActivity(intent);
         */

        String dailyReimJSONStr = intent.getStringExtra("dailyReimJSONStr");
        // JSON串转用户组对象
        DailyReim dailyReim = JSON.parseObject(dailyReimJSONStr, DailyReim.class);
        final Integer daily_reim_uuid = dailyReim.getDaily_reim_uuid();
        etDailyReimCauseDisplay.setText(dailyReim.getDaily_reim_cause());
        etDailyReimAmountDisplay.setText(dailyReim.getDaily_reim_amount());
        ettvDailyReimProjectnameDisplay.setText(dailyReim.getDaily_reim_project_name());
        etDailyReimCommittime.setText(dailyReim.getDaily_reim_submitTime());
        etEndtime.setText(dailyReim.getDaily_reim_endTime());
        etComments.setText(dailyReim.getDaily_reim_comments());
        etCheckstatus.setText(dailyReim.getDaily_reim_check_status());

        tongyiXiaofeiBeans = dailyReim.getTongyiXiaofeiBeans();

        for (int i=0;i<tongyiXiaofeiBeans.size();i++){
            TongyiXiaofeiBean tb = tongyiXiaofeiBeans.get(i);
            Log.e("第","日常报销单相关联的统一消费Bean: "+tb.toString());
        }

        if(tongyiXiaofeiBeans != null && tongyiXiaofeiBeans.size() >0) {
            tongyiXiaofeiBeanListAdapter = new TongyiXiaofeiBeanListAdapter(tongyiXiaofeiBeans);
            listviewTongyixiaofeiBeanList.setAdapter(tongyiXiaofeiBeanListAdapter);
        }

        btn_export_excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("当前报销的UUID: ",String.valueOf(daily_reim_uuid));

                excel_export(daily_reim_uuid);

            }
        });

        listviewTongyixiaofeiBeanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeans.get(position);
                String type = tongyiXiaofeiBean.getXiaofei_type();
                if ((type.equals("火车")) || (type.equals("飞机")) || (type.equals("轮船")) || (type.equals("汽车"))) {

                    Intent intent = new Intent(DailyReimDisplay.this, TrafficCostDisplayActivity.class);
                    String traffic_json = tongyiXiaofeiBean.getOtherInfo();
                    Traffic_Cost traffic_cost = JSON.parseObject(traffic_json, Traffic_Cost.class);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + traffic_json);
                    intent.putExtra("TrafficCostJsonString", traffic_json);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(DailyReimDisplay.this, DailyCostDisplayActivity.class);
                    String daily_json = tongyiXiaofeiBean.getOtherInfo();
                    /*DailyReim dailyReim = (DailyReim) JSON.parseObject(
                            dailyCost_JSON_STRING, DailyReim.class);*/
                    DailyCost cost = JSON.parseObject(daily_json, DailyCost.class);
                    // 用户组对象转JSON串
                    String jsonString = JSON.toJSONString(cost);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + jsonString);
                    intent.putExtra("DailyCostJsonString", jsonString);
                    startActivity(intent);
                }
            }
        });


    }

    private void excel_export(final Integer daily_reim_uuid){

        new Thread(){
            @Override
            public void run() {

                try{

                    /*String url = Constants.FILE_BASE_DIR+daily_reim_uuid+".xls";
                    Request request = new Request.Builder()
                                          .url(url)
                                          .get()
                                          .build();

                    Response response =*/
                    String urlStr = Constants.FILE_BASE_DIR+daily_reim_uuid+".xls";
                    Log.e("请求服务器的访问路径: ",urlStr);
                    URL url = new URL(urlStr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");//注意"GET"只能用大写
                    InputStream inputStream = connection.getInputStream();
                    //OkHttpUtils

                    Integer code = connection.getResponseCode();

                    Log.e("服务器返回的响应吗 code =  ",String.valueOf(code));

                    File dir = new File(Environment.getExternalStorageDirectory(), "reim_cost_imgs");
                    String excel_save_name = dir.getAbsolutePath() + File.separator + daily_reim_uuid+".xls";

                    File file = new File(excel_save_name);


                    FileOutputStream fos = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }

                    inputStream.close();
                    fos.close();

                    String success_path = file.getAbsolutePath();
                    Log.e("最终EXCEL保存的路径: ",success_path);
                    Message msg = Message.obtain();
                    msg.obj = success_path;
                    msg.what =  EXCEL_DOWNLOAD_SUCCESS;
                    handler.sendMessage(msg);
                    //Toast.makeText(getApplication(),"Excel导出完毕",Toast.LENGTH_LONG);



                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();



    }


    class TongyiXiaofeiBeanListAdapter extends BaseAdapter {

        List < TongyiXiaofeiBean > data;
        public TongyiXiaofeiBeanListAdapter(List < TongyiXiaofeiBean > data){
            this.data = data;
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
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
                /**
                 *   tv_lv_item_uniform_cost_amount
                 tv_lv_item_uniform_cost_start_date
                 tv_lv_item_uniform_cost_type
                 tv_lv_item_uniform_other_info
                 */
                convertView = layoutInflater.inflate(R.layout.lv_item_uniform_cost,null);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.tv_lv_item_uniform_cost_type);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_lv_item_uniform_cost_start_date);
                viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_lv_item_uniform_cost_amount);
                viewHolder.tvOhterInfo = (TextView) convertView.findViewById(R.id.tv_lv_item_uniform_other_info);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }
            TongyiXiaofeiBean consumption = data.get(position);
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
