package reimburse.cuc.com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.DailyCost;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.bean.Travel_Reimbursement;
import reimburse.cuc.com.bean.User;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.reimburse.ReimburseFormActivity;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/12/25.
 */
public class TravelReimbursementActivity extends Activity {

    private Integer selected_pro_position;
    private static final int DAILY_COST = 01;
    private static final int TRAFFIC_COST = 02;
    private static final int REQUEST_SELECT_EXPENSE_CODE = 1002;
    private static final int RESPONSE_SELECT_EXPENSE_COMPLETED = 1001;
    private static final String TAG = TravelReimbursementActivity.class.getSimpleName();

    private EditText et_travel_reimbursement_start_city;
    private EditText et_travel_reimbursement_end_city;

    private EditText et_travel_start_date;
    private EditText et_travel_end_date;
    private EditText et_travel_reimbursement_total_amount;
    private EditText et_travel_reimbursement_project_name;


    private EditText  et_travel_urban_transport;
    private EditText  et_travel_food_allowance;
    private EditText  et_travel_user;
    private EditText  et_travel_user_title;
    private EditText  et_travel_cause;


    private Button btn_save_travel_reimbursement;
    private Button btn_add_travel_cost_detail;

    private PopupWindow popupWindow;

    private ListView projectListView;
    private List<String> projectNameList;

    private ListView list_view_travel_reimburse_uniform_cost;
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList;

    private User user;
    private List<Project> projectList;

    TianjiaXiaofeiAdapter tianjiaXiaofeiAdapter;


    /**
     * 项目的可报销余额
     */
    String p_reimbursable_amount;

    /**
     * 选择项目的PRO_UUID
     */

    Integer project_uuid;

    protected static  final int SHOW_ALL_PROJECT = 0001;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_PROJECT) {
                projectListView.setAdapter(new ProjectListAdapter());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvivity_travel_reimbursement);
        et_travel_start_date = (EditText) findViewById(R.id.et_travel_start_date);
        et_travel_end_date = (EditText) findViewById(R.id.et_travel_end_date);
        et_travel_reimbursement_total_amount = (EditText) findViewById(R.id.et_travel_reimbursement_total_amount);
        et_travel_reimbursement_project_name = (EditText) findViewById(R.id.et_travel_reimbursement_project_name);
        et_travel_reimbursement_start_city = (EditText) findViewById(R.id.et_travel_reimbursement_start_city);
        et_travel_reimbursement_end_city = (EditText) findViewById(R.id.et_travel_reimbursement_end_city);

        btn_save_travel_reimbursement = (Button) findViewById(R.id.btn_save_travel_reimbursement);
        et_travel_food_allowance = (EditText) findViewById(R.id.et_travel_food_allowance);
        et_travel_user = (EditText) findViewById(R.id.et_travel_user);
        et_travel_user_title = (EditText) findViewById(R.id.et_travel_user_title);
        et_travel_cause = (EditText) findViewById(R.id.et_travel_cause);
        et_travel_urban_transport = (EditText) findViewById(R.id.et_travel_urban_transport);
        btn_add_travel_cost_detail = (Button) findViewById(R.id.btn_add_travel_cost_detail);
        btn_save_travel_reimbursement = (Button) findViewById(R.id.btn_save_travel_reimbursement);
        list_view_travel_reimburse_uniform_cost = (ListView) findViewById(R.id.list_view_travel_reimburse_uniform_cost);

        tongyiXiaofeiBeanList = new ArrayList<>();

        projectListView = new ListView(this);
        projectListView.setBackgroundResource(R.color.white);


        defaultDate(et_travel_start_date);
        defaultDate(et_travel_end_date);
        et_travel_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        et_travel_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setEndDate(v);
            }
        });
        getAllProject();
        et_travel_reimbursement_project_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(TravelReimbursementActivity.this);
                    popupWindow.setWidth(et_travel_reimbursement_project_name.getWidth());
                    popupWindow.setHeight(200);


                    popupWindow.setContentView(projectListView);
                    popupWindow.setFocusable(true);

                }
                popupWindow.showAsDropDown(et_travel_reimbursement_project_name, 0, 0);
            }
        });
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_pro_position = position;
                //1.得到数据
                Project project = projectList.get(position);
                project_uuid = project.getId();
                String proName = project.getP_name();
                p_reimbursable_amount = project.getP_reimbursable_amount();
                Log.e(TAG, "---> 项目的剩余报销余额: " + p_reimbursable_amount);
                et_travel_reimbursement_project_name.setText(proName);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
        btn_add_travel_cost_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelReimbursementActivity.this, WaitSelectedCostActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_EXPENSE_CODE);
            }
        });

        list_view_travel_reimburse_uniform_cost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeanList.get(position);
                String type = tongyiXiaofeiBean.getXiaofei_type();
                if ((type.equals("火车")) || (type.equals("飞机")) || (type.equals("轮船")) || (type.equals("汽车"))) {

                    Intent intent = new Intent(TravelReimbursementActivity.this, TrafficCostDisplayActivity.class);
                    String traffic_json = tongyiXiaofeiBean.getOtherInfo();
                    Traffic_Cost traffic_cost = JSON.parseObject(traffic_json, Traffic_Cost.class);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + traffic_json);
                    intent.putExtra("TrafficCostJsonString", traffic_json);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(TravelReimbursementActivity.this, DailyCostDisplayActivity.class);
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

        list_view_travel_reimburse_uniform_cost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] items = new String[]{"确认删除" +
                        ""};
                new AlertDialog.Builder(TravelReimbursementActivity.this).setTitle("确认删除该消费？")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        tongyiXiaofeiBeanList.remove(position);
                                        tianjiaXiaofeiAdapter.notifyDataSetChanged();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).setNegativeButton("取消", null).show();
                return true;
            }
        });

        btn_save_travel_reimbursement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String travel_reimbursement_start_city = et_travel_reimbursement_start_city.getText().toString();
                String travel_reimbursement_end_city = et_travel_reimbursement_end_city.getText().toString();
                String travel_reimbursement_start_date = et_travel_start_date.getText().toString();
                String travel_reimbursement_end_date = et_travel_end_date.getText().toString();
                String travel_reimbursement_traffic_allowance  = et_travel_urban_transport.getText().toString() ;
                String travel_reimbursement_board_allowance = et_travel_food_allowance.getText().toString();
                String travel_reimbursement_name = et_travel_user.getText().toString();
                String travel_reimbursement_job_title = et_travel_user_title.getText().toString();
                String travel_reimbursement_cause = et_travel_cause.getText().toString();
                String travel_reimbursement_total_amount = et_travel_reimbursement_total_amount.getText().toString();
                //String travel_reimbursement_submit_date_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String travel_reimbursement_project_name = et_travel_reimbursement_project_name.getText().toString();
                StringBuffer travel_reimbursement_daily_cost_ids = new StringBuffer("");
                StringBuffer travel_reimbursement_traffic_cost_ids = new StringBuffer("");
                for (int i=0;i<tongyiXiaofeiBeanList.size();i++){
                    TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeanList.get(i);
                    String cost_type = tongyiXiaofeiBean.getXiaofei_type();

                    if(cost_type.equals("火车") || cost_type.equals("飞机") || cost_type.equals("汽车") || cost_type.equals("轮船")) {

                        travel_reimbursement_traffic_cost_ids.append(tongyiXiaofeiBean.getId()+"|");

                    }else {
                        travel_reimbursement_daily_cost_ids.append(tongyiXiaofeiBean.getId()+"|");
                    }

                }

                String travel_reim_traffic_cost_ids = ( travel_reimbursement_traffic_cost_ids.toString().equals("") ? "noCost" : travel_reimbursement_traffic_cost_ids.toString() );
                String travel_reim_daily_cost_ids = ( travel_reimbursement_daily_cost_ids.toString().equals("") ? "noCost" : travel_reimbursement_daily_cost_ids.toString() );
                Integer travel_reimbursement_user_id = LauncherActivity.ANDROID_USER_ID;
                Log.e(TAG,travel_reimbursement_start_city);
                Log.e(TAG,travel_reimbursement_end_city);
                Log.e(TAG,travel_reimbursement_start_date);
                Log.e(TAG,travel_reimbursement_end_date);
                Log.e(TAG,travel_reimbursement_traffic_allowance);
                Log.e(TAG,travel_reimbursement_board_allowance);
                Log.e(TAG,travel_reimbursement_name);
                Log.e(TAG,travel_reimbursement_job_title);
                Log.e(TAG,travel_reimbursement_cause);
                Log.e(TAG,travel_reimbursement_total_amount);
                //Log.e(TAG,travel_reimbursement_submit_date_time);
                Log.e(TAG,travel_reimbursement_project_name);
                Log.e(TAG,travel_reimbursement_daily_cost_ids.toString());
                Log.e(TAG,travel_reimbursement_traffic_cost_ids.toString());
                Log.e(TAG,travel_reimbursement_user_id+"");

                Integer daily_reim_user_id = LauncherActivity.ANDROID_USER_ID;

                SharedPreferences sp =  getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

                String user_jsonString = sp.getString("user_jsonString", "");

                User user = JSON.parseObject(user_jsonString,User.class);


                Travel_Reimbursement travel_reimbursement = new Travel_Reimbursement
                        (travel_reimbursement_start_city,
                                travel_reimbursement_end_city,
                                travel_reimbursement_start_date,
                                travel_reimbursement_end_date,
                                travel_reimbursement_traffic_allowance,
                                travel_reimbursement_board_allowance,
                                travel_reimbursement_name,
                                travel_reimbursement_job_title,
                                travel_reimbursement_cause,
                                travel_reimbursement_total_amount,
                                travel_reimbursement_project_name,
                                travel_reim_traffic_cost_ids,
                                travel_reim_daily_cost_ids,
                                project_uuid,user.getUser_uuid(),user.getUser_name(),user.getMobile_phone_number(),
                                user.getBank_number(),user.getBank_name());
                Log.e("差旅费的交通消费字符串: ",travel_reim_traffic_cost_ids);
                Log.e("差旅费的日常消费字符串: ",travel_reim_daily_cost_ids);
                // Java对象转JSON串
                String travelReim_jsonString = JSON.toJSONString(travel_reimbursement);
                Log.e("要准备上传的差率报销单: ",travelReim_jsonString);
                Float pro_can_reim = Float.parseFloat(p_reimbursable_amount);
                Float this_time_amount = Float.parseFloat(travel_reimbursement_total_amount);

                Log.e(TAG,"项目剩余可报销余额： " + p_reimbursable_amount + ", 本次报销总额: " + travel_reimbursement_total_amount);


                if(this_time_amount > pro_can_reim) {
                    Toast.makeText(TravelReimbursementActivity.this,"本次报销总额: "+travel_reimbursement_total_amount+" 大于项目可报销余额："+p_reimbursable_amount,Toast.LENGTH_LONG).show();
                }else {

                    uploadTravelReim(travelReim_jsonString);
                }

            }
        });
    }

    public void uploadDailyCost(final String jsonString){

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient mOkHttpClient = new OkHttpClient();


                MultipartBody.Builder builder = new MultipartBody.Builder();


                builder.addFormDataPart("jsonString", jsonString);

                RequestBody requestBody = builder.build();
                Request.Builder reqBuilder = new Request.Builder();
                Request request = reqBuilder
                        .url(Constants.AndroidSaveTravel_Reimbursement)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                    String resultInfo = response.body().string();
                    Log.e(TAG, "上传差旅费用返回的结果: " + resultInfo);
                    if(resultInfo.equals("保存成功")) {
                        showToastInAnyThread(resultInfo);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    public void uploadTravelReim(final String travelReim_jsonString){


        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidSaveTravel_Reimbursement;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    //String data = "user_name="+URLEncoder.encode(un, "utf-8")+"&password="+URLEncoder.encode(pawd);
                    String data ="travelReim_jsonString="+ URLEncoder.encode(travelReim_jsonString, "utf-8");

                    conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                    //post的请求是把数据以流的方式写给了服务器
                    //指定请求的输出模式
                    conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                    conn.getOutputStream().write(data.getBytes());
                    Log.e("post size", String.valueOf(data.getBytes().length));
                    int code = conn.getResponseCode();

                    if(code == 200){
                        InputStream is = conn.getInputStream();
                        final String resultInfo = StreamTools.readStream(is);
                        if(resultInfo.equals("保存成功")) {
                            showToastInAnyThread(resultInfo);
                            finish();
                        }


                    }else{
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("请求失败，出现异常!");
                }

            };
        }.start();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SELECT_EXPENSE_CODE && resultCode == RESPONSE_SELECT_EXPENSE_COMPLETED){
            BigDecimal totalMoney = new BigDecimal("0.0");
            Bundle bundle = data.getExtras();
            Log.e("resultCode", String.valueOf(resultCode));
            tongyiXiaofeiBeanList = (List <TongyiXiaofeiBean>)bundle.getSerializable("tongyiXiaofeiBeanList");
            for (TongyiXiaofeiBean c :tongyiXiaofeiBeanList){
                Log.e("选择好的消费: ",c.getId()+":"+c.getXiaofei_type()+":"+c.getRiqi()+":"+c.getTotalMoney()+":"+c.getOtherInfo());
                totalMoney = totalMoney.add(new BigDecimal(c.getTotalMoney()));
            }
            et_travel_reimbursement_total_amount.setText(totalMoney.toString());
            Log.e("消费记录选择Activity","success return");
            Log.e("选择的消费条数: ",String.valueOf(tongyiXiaofeiBeanList.size()));
            if(tongyiXiaofeiBeanList != null && tongyiXiaofeiBeanList.size()>0) {
                tianjiaXiaofeiAdapter = new TianjiaXiaofeiAdapter(tongyiXiaofeiBeanList);
                list_view_travel_reimburse_uniform_cost.setAdapter(tianjiaXiaofeiAdapter);
            }
        }
    }

    class ProjectListAdapter extends  BaseAdapter{

        @Override
        public int getCount() {
            return  projectList.size();
        }

        @Override
        public Object getItem(int position) {
            return  projectList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ProjectViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new ProjectViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_projectname,null);
                viewHolder.projectName = (TextView) convertView.findViewById(R.id.tv_project_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ProjectViewHolder) convertView.getTag();

            }
            Project  project = projectList.get(position);
            String pro_name = project.getP_name();
            String pro_left_reim = project.getP_reimbursable_amount();
            viewHolder.projectName.setText(pro_name+", 可报销 "+pro_left_reim);
            //viewHolder.projectName.setText(project.getP_name());
            return convertView;
        }
    }

    static  class  ProjectViewHolder{
        public TextView projectName;
    }

    /**
     * 得到所有的项目信息，展示成下拉框
     */
    public void getAllProject(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidGetAllProjectJson;
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

                        //projectNameList = JSON.parseArray(result, String.class);

                        user = JSON.parseObject(result,User.class);

                        projectList = user.getProjects();

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_PROJECT;
                        msg.obj = projectList;
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

    class TianjiaXiaofeiAdapter extends BaseAdapter{

        List < TongyiXiaofeiBean > data;
        public TianjiaXiaofeiAdapter(List < TongyiXiaofeiBean > data){
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

    /*22222222222222222222222222222222===日期显示处理开始===2222222222222222222222222222222222222222222222*/
    public void defaultDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        et_travel_start_date.setText(year + "-" + (month + 1) + "-" + day);
        et_travel_end_date.setText(year + "-" + (month + 1) + "-" + day);
    }
    public void defaultTime(View view){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //et_start_time.setText(hour+":"+minute);
        //et_end_time.setText(hour+":"+minute);
    }
    public void setDate(View view){
        SetDateDialog sdd = new SetDateDialog();
        sdd.show(getFragmentManager(), "datepicker");
    }
    //创建日期选择对话框
    @SuppressLint("ValidFragment")
    class SetDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
            return  dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            et_travel_start_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            //et_travel_end_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }
    }

    public void setEndDate(View view){
        SetEndDateDialog sdd = new SetEndDateDialog();
        sdd.show(getFragmentManager(), "datepicker_EndDate");
    }
    //创建日期选择对话框
    @SuppressLint("ValidFragment")
    class SetEndDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
            return  dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //et_travel_start_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            et_travel_end_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }
    }

    //创建时间选择对话框
    @SuppressLint("ValidFragment")
    class SetTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),this,hour,minute,true);
            return timePickerDialog;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //et_start_time.setText(hourOfDay+":"+minute);
            //et_end_time.setText(hourOfDay+":"+minute);
        }
    }

    public void setTime(View view){
        SetTimeDialog setTimeDialog = new SetTimeDialog();
        setTimeDialog.show(getFragmentManager(), "mytimePicker");
    }







/*22222222222222222222222222222222===日期显示处理结束===2222222222222222222222222222222222222222222222*/

}
