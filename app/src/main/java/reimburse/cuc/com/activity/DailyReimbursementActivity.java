package reimburse.cuc.com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.DailyCost;
import reimburse.cuc.com.bean.DailyReim;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.bean.Travel_Reimbursement;
import reimburse.cuc.com.bean.User;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/12/25.
 */
public class DailyReimbursementActivity extends Activity {

    private static final String TAG = DailyReimbursementActivity.class.getSimpleName() ;
    private Integer selectedProjectUUID;

    TianjiaXiaofeiAdapter tianjiaXiaofeiAdapter;
    @Bind(R.id.tv_daily_reim_cause)
    TextView tvDailyReimCause;

    @Bind(R.id.et_daily_reim_cause)
    EditText etDailyReimCause;

    @Bind(R.id.et_project_name_daily_reim)
    EditText etProjectNameDailyReim;

    @Bind(R.id.et_amount_daily_reim)
    EditText etAmountDailyReim;

    @Bind(R.id.btn_add_daily_cost)
    Button btnAddDailyCost;

    @Bind(R.id.list_view_daily_reimburse_uniform_cost)
    ListView listViewDailyReimburseUniformCost;

    @Bind(R.id.btn_save_daily_reimbursement)
    Button btnSaveDailyReimbursement;

    private static final int DAILY_COST = 01;
    private static final int TRAFFIC_COST = 02;
    private static final int REQUEST_SELECT_EXPENSE_CODE = 1002;
    private static final int RESPONSE_SELECT_EXPENSE_COMPLETED = 1001;

    private PopupWindow popupWindow;

    private ListView projectListView;
    private List<String> projectNameList;

    /**
     * 项目的可报销余额
     */
    String p_reimbursable_amount;

    List<TongyiXiaofeiBean> tongyiXiaofeiBeanList;
    private User user;
    private List<Project> projectList;
    protected static  final int SHOW_ALL_PROJECT = 0001;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_PROJECT) {

                projectListView.setAdapter(new ProjectListAdapter());
            }
        }
    };

    Integer project_uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_reimbursement);
        ButterKnife.bind(this);
        projectListView = new ListView(this);
        projectList = new ArrayList<>();
        projectListView.setBackgroundResource(R.color.white);
        getAllProject();
        etProjectNameDailyReim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(DailyReimbursementActivity.this);
                    popupWindow.setWidth(etProjectNameDailyReim.getWidth());
                    popupWindow.setHeight(200);


                    popupWindow.setContentView(projectListView);
                    popupWindow.setFocusable(true);

                }
                popupWindow.showAsDropDown(etProjectNameDailyReim, 0, 0);
            }
        });
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //1.得到数据
                Project project = projectList.get(position);
                project_uuid = project.getId();
                String proName = project.getP_name();
                p_reimbursable_amount = project.getP_reimbursable_amount();
                Log.e(TAG, "---> 项目的剩余报销余额: " + p_reimbursable_amount);
                etProjectNameDailyReim.setText(proName);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }

                /**
                 * ========================================================
                 * ========================================================
                 * ========================================================
                 * ========================================================
                 * ========================================================
                 */
                /*String pro_uuid_pname = projectNameList.get(position);
                project_uuid = Integer.parseInt(pro_uuid_pname.split("\\|")[0]);
                selectedProjectUUID = Integer.parseInt(pro_uuid_pname.split("\\|")[0]);
                String proName = pro_uuid_pname.split("\\|")[1];
                //1.得到数据
                //String proName = projectNameList.get(position);
                etProjectNameDailyReim.setText(proName);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }*/
            }
        });

        btnAddDailyCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyReimbursementActivity.this, WaitSelectedCostActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_EXPENSE_CODE);
            }
        });


        listViewDailyReimburseUniformCost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeanList.get(position);
                String type = tongyiXiaofeiBean.getXiaofei_type();
                if ((type.equals("火车")) || (type.equals("飞机")) || (type.equals("轮船")) || (type.equals("汽车"))) {

                    Intent intent = new Intent(DailyReimbursementActivity.this, TrafficCostDisplayActivity.class);
                    String traffic_json = tongyiXiaofeiBean.getOtherInfo();
                    Traffic_Cost traffic_cost = JSON.parseObject(traffic_json, Traffic_Cost.class);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + traffic_json);
                    intent.putExtra("TrafficCostJsonString", traffic_json);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(DailyReimbursementActivity.this, DailyCostDisplayActivity.class);
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

        listViewDailyReimburseUniformCost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] items = new String[]{"确认删除"};
                new AlertDialog.Builder(DailyReimbursementActivity.this).setTitle("确认删除该消费？")
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

        btnSaveDailyReimbursement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String daily_reim_cause = etDailyReimCause.getText().toString();
                String daily_reim_project_name = etProjectNameDailyReim.getText().toString();
                String daily_reim_amount  = etAmountDailyReim.getText().toString();
                String daily_reim_submitTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                StringBuffer travel_reimbursement_daily_cost_ids = new StringBuffer("");
                StringBuffer travel_reimbursement_traffic_cost_ids = new StringBuffer("");
                for (int i=0;i<tongyiXiaofeiBeanList.size();i++){
                    TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeanList.get(i);
                    String cost_type = tongyiXiaofeiBean.getXiaofei_type();
                    Log.e("遍历日常报销选中的消费ITEM","消费类型为: "+cost_type);

                    if(cost_type.equals("火车") || cost_type.equals("飞机") || cost_type.equals("汽车") || cost_type.equals("轮船")) {

                        travel_reimbursement_traffic_cost_ids.append(tongyiXiaofeiBean.getId()+"|");

                    }else {
                        travel_reimbursement_daily_cost_ids.append(tongyiXiaofeiBean.getId()+"|");
                    }

                }

                String daily_reim_traffic_cost_ids = (travel_reimbursement_traffic_cost_ids.toString().equals("") ? "noCost" : travel_reimbursement_traffic_cost_ids.toString());
                String daily_reim_daily_cost_ids = (travel_reimbursement_daily_cost_ids.toString().equals("") ? "noCost" : travel_reimbursement_daily_cost_ids.toString());
                Log.e("日常报销包含的交通费ID：",daily_reim_daily_cost_ids);
                Log.e("日常报销包含的日常消费ID：",daily_reim_daily_cost_ids);


                Integer daily_reim_user_id = LauncherActivity.ANDROID_USER_ID;

                DailyReim dailyReim = new DailyReim(daily_reim_cause,daily_reim_project_name,daily_reim_amount,
                        daily_reim_traffic_cost_ids,daily_reim_daily_cost_ids,
                        daily_reim_submitTime,daily_reim_user_id,project_uuid);

                // Java对象转JSON串
                String dailyCost_jsonString = JSON.toJSONString(dailyReim);


                Float pro_can_reim = Float.parseFloat(p_reimbursable_amount);
                Float this_time_amount = Float.parseFloat(daily_reim_amount);
                Log.e(TAG,"项目剩余可报销余额： " + p_reimbursable_amount + ", 本次报销总额: " + daily_reim_amount);

                if(this_time_amount > pro_can_reim) {
                    Toast.makeText(DailyReimbursementActivity.this,"本次报销总额: "+this_time_amount+" 大于项目可报销余额："+p_reimbursable_amount,Toast.LENGTH_LONG).show();
                }else {

                    uploadDailyCost(dailyCost_jsonString);
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
                        .url(Constants.AndroidSaveDailyReimbursement)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                    String resultInfo = response.body().string();
                    Log.e(TAG, "上传日常费用返回的结果: " + resultInfo);
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
            etAmountDailyReim.setText(totalMoney.toString());
            Log.e("消费记录选择Activity","success return");
            Log.e("选择的消费条数: ",String.valueOf(tongyiXiaofeiBeanList.size()));
            if(tongyiXiaofeiBeanList != null && tongyiXiaofeiBeanList.size()>0) {
                tianjiaXiaofeiAdapter = new TianjiaXiaofeiAdapter(tongyiXiaofeiBeanList);
                listViewDailyReimburseUniformCost.setAdapter(tianjiaXiaofeiAdapter);
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




    /**
     * 得到所有的项目信息，展示成下拉框
     */
   /* public void getAllProject(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.GET_ALL_PROJECT_URL;
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
                        projectNameList = JSON.parseArray(result, String.class);

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_PROJECT;
                        msg.obj = projectNameList;
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
    }*/



    /*class ProjectListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return  projectNameList.size();
        }

        @Override
        public Object getItem(int position) {
            return  projectNameList.get(position);
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
            String  projectName = projectNameList.get(position);

            viewHolder.projectName.setText(projectName);
            return convertView;
        }
    }

    static  class  ProjectViewHolder{
        public TextView projectName;
    }*/

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
}