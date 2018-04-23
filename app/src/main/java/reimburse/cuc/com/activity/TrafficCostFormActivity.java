package reimburse.cuc.com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.Pro_Budget;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.bean.Traffic_Fare_Basis;
import reimburse.cuc.com.bean.Traffic_Type;
import reimburse.cuc.com.bean.User;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.DensityUtil;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/12/27.
 */
public class TrafficCostFormActivity extends Activity {


    private static final int PICTURE = 01;
    private static final int CAMERA = 02;
    private static final int PHOTOVIEW = 03;
    private static final int PHOTOVIEW_RESULT = 04;
    private static final int SHOW_ALL_PROJECT = 05;
    @Bind(R.id.et_traffic_cost_pro_name)
    EditText etTrafficCostProName;
    @Bind(R.id.et_traffic_cost_pro_budget_type)
    EditText etTrafficCostProBudgetType;

    private List<String> picUrls;

    static final String TAG = TrafficCostFormActivity.class.getSimpleName();
    @Bind(R.id.gw_traffic_cost_invoice)
    GridView gwTrafficCostInvoice;
    @Bind(R.id.btn_save_traffic_cost)
    Button btnSaveTrafficCost;
    private StringBuffer invoice_pic_urls = new StringBuffer();


    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public List<String> drr = new ArrayList<String>();

    List<String> urList = new ArrayList<String>();

    /*填充下拉列表的数据*/
    List<String> popWindowList;

    private EditText et_traffic_cost_tag;

    private EditText et_input;

    /*票价级别*/
    private EditText et_fare_basis;


    /**
     *
     */
    private PopupWindow popupWindow;
    private PopupWindow popupWindowFareBasis;


    private EditText et_traffic_cost_start_city;
    private EditText et_traffic_cost_end_city;

    private EditText et_start_date;
    private EditText et_end_date;

    private EditText et_start_time;
    private EditText et_end_time;


    /*private EditText et_traffic_cost_unit_price;
    private EditText et_traffic_cost_ticket_number;*/
    private EditText et_traffic_cost_total_amount;
    private EditText et_traffic_cost_desc;


    private ListView listview;
    private ListView traffic_fare_basis_listview;

    List<Traffic_Type> traffic_typeList;
    List<Traffic_Fare_Basis> traffic_fare_basisList;

    private List<String> traffic_cost_invoice_pic_url_list = new ArrayList<>();
    static final int SHOW_ALL_TRAFFIC_TYPE = 0066;

    TrafficCostAddOrDelGridViewAdapter trafficCostAddOrDelGridViewAdapter;


    /**
     * 用于选择消费项目和预算类型
     */
    private ListView listview_project;
    private ListView listview_project_budget;

    private PopupWindow popupWindowOfProject;
    private PopupWindow popupWindowOfProBudget;

    List<Project> projectList;
    List<Pro_Budget> pro_budgetList;

    User user;
    Integer traffic_cost_pro_uuid;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_ALL_TRAFFIC_TYPE) {
                listview.setAdapter(new MyBaseAdapter());
            }else if(msg.what == SHOW_ALL_PROJECT) {
                //daily_cost_type_popupWindow_listview.setAdapter(new DailyCostTypePopwindowListViewAdapter());
                listview_project.setAdapter(new ListViewProjectAdapter());
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_cost);
        ButterKnife.bind(this);
        et_traffic_cost_tag = (EditText) findViewById(R.id.et_traffic_cost_tag);
        et_input = (EditText) findViewById(R.id.et_input);
        et_fare_basis = (EditText) findViewById(R.id.et_fare_basis);

        picUrls = new ArrayList<>();
        et_traffic_cost_start_city = (EditText) findViewById(R.id.et_traffic_cost_start_city);
        et_traffic_cost_end_city = (EditText) findViewById(R.id.et_traffic_cost_end_city);

        et_start_date = (EditText) findViewById(R.id.et_start_date);
        et_end_date = (EditText) findViewById(R.id.et_end_date);

        et_start_time = (EditText) findViewById(R.id.et_start_time);
        et_end_time = (EditText) findViewById(R.id.et_end_time);

       /* et_traffic_cost_unit_price = (EditText) findViewById(R.id.et_traffic_cost_unit_price);
        et_traffic_cost_ticket_number = (EditText) findViewById(R.id.et_traffic_cost_ticket_number);*/
        et_traffic_cost_total_amount = (EditText) findViewById(R.id.et_traffic_cost_total_amount);

        et_traffic_cost_desc = (EditText) findViewById(R.id.et_traffic_cost_desc);

        trafficCostAddOrDelGridViewAdapter = new TrafficCostAddOrDelGridViewAdapter(7);

        /**
         * 设置日期和时间
         */
        setDateAndTime();


        getUserOwnProjectWithBudget();


        /**
         * 初始化二级级联
         */

        projectList = new ArrayList<>();
        pro_budgetList = new ArrayList<>();

        listview_project = new ListView(this);
        listview_project.setBackgroundResource(R.drawable.listview_background);

        listview_project_budget = new ListView(this);
        listview_project_budget.setBackgroundResource(R.drawable.listview_background);

        getUserOwnProjectWithBudget();



        etTrafficCostProName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindowOfProject == null) {
                    popupWindowOfProject = new PopupWindow(TrafficCostFormActivity.this);
                    popupWindowOfProject.setWidth(etTrafficCostProName.getWidth());
                    int height = DensityUtil.dip2px(TrafficCostFormActivity.this, 200);//dp->px
                    //Toast.makeText(TrafficCostFormActivity.this, "height==" + height, Toast.LENGTH_SHORT).show();
                    popupWindowOfProject.setHeight(height);//px

                    popupWindowOfProject.setContentView(listview_project);
                    popupWindowOfProject.setFocusable(true);//设置焦点
                }

                popupWindowOfProject.showAsDropDown(etTrafficCostProName, 0, 0);
            }
        });

        listview_project.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project project = projectList.get(position);
                //1.得到数据
                String pro_name = project.getP_name();
                traffic_cost_pro_uuid = project.getId();
                //2.设置输入框
                etTrafficCostProName.setText(pro_name);
                etTrafficCostProBudgetType.setText("预算类别");
                pro_budgetList = project.getPro_Budgets();
                listview_project_budget.setAdapter(new ProBudgetBaseAdapter());

                if (popupWindowOfProject != null && popupWindowOfProject.isShowing()) {
                    popupWindowOfProject.dismiss();
                    popupWindowOfProject = null;
                }
            }
        });

        etTrafficCostProBudgetType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindowOfProBudget == null) {
                    popupWindowOfProBudget = new PopupWindow(TrafficCostFormActivity.this);
                    popupWindowOfProBudget.setWidth(etTrafficCostProBudgetType.getWidth());
                    int height = DensityUtil.dip2px(TrafficCostFormActivity.this, 200);//dp->px
                    Toast.makeText(TrafficCostFormActivity.this, "height==" + height, Toast.LENGTH_SHORT).show();
                    popupWindowOfProBudget.setHeight(height);//px

                    popupWindowOfProBudget.setContentView(listview_project_budget);
                    popupWindowOfProBudget.setFocusable(true);//设置焦点
                }

                popupWindowOfProBudget.showAsDropDown(etTrafficCostProBudgetType, 0, 0);
            }
        });


        listview_project_budget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pro_Budget pro_budget = pro_budgetList.get(position);
                //1.得到数据
                String budget_name = pro_budget.getPro_budget_name();
                //2.设置输入框
                etTrafficCostProBudgetType.setText(budget_name);

                if (popupWindowOfProBudget != null && popupWindowOfProBudget.isShowing()) {

                    popupWindowOfProBudget.dismiss();
                    popupWindowOfProBudget = null;
                }
            }
        });



        traffic_typeList = new ArrayList<>();
        traffic_fare_basisList = new ArrayList<>();

        listview = new ListView(this);
        listview.setBackgroundResource(R.drawable.listview_background);

        traffic_fare_basis_listview = new ListView(this);
        traffic_fare_basis_listview.setBackgroundResource(R.drawable.listview_background);


        GetAllTrafficTypeWithFareBasisList();

        et_input.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindow == null) {
                    popupWindow = new PopupWindow(TrafficCostFormActivity.this);
                    popupWindow.setWidth(et_input.getWidth());
                    int height = DensityUtil.dip2px(TrafficCostFormActivity.this, 200);//dp->px
                    //Toast.makeText(TrafficCostFormActivity.this, "height==" + height, Toast.LENGTH_SHORT).show();
                    popupWindow.setHeight(height);//px

                    popupWindow.setContentView(listview);
                    popupWindow.setFocusable(true);//设置焦点
                }

                popupWindow.showAsDropDown(et_input, 0, 0);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Traffic_Type traffic_type = traffic_typeList.get(position);
                //1.得到数据
                String msg = traffic_typeList.get(position).getTra_type_name();
                //2.设置输入框
                et_input.setText(msg);
                et_fare_basis.setText("客票级别");
                traffic_fare_basisList = traffic_type.getTraffic_Fare_Basis_List();
                traffic_fare_basis_listview.setAdapter(new FareBasisBaseAdapter());

                if (popupWindow != null && popupWindow.isShowing()) {

                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

        et_fare_basis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindowFareBasis == null) {
                    popupWindowFareBasis = new PopupWindow(TrafficCostFormActivity.this);
                    popupWindowFareBasis.setWidth(et_fare_basis.getWidth());
                    int height = DensityUtil.dip2px(TrafficCostFormActivity.this, 200);//dp->px
                    Toast.makeText(TrafficCostFormActivity.this, "height==" + height, Toast.LENGTH_SHORT).show();
                    popupWindowFareBasis.setHeight(height);//px

                    popupWindowFareBasis.setContentView(traffic_fare_basis_listview);
                    popupWindowFareBasis.setFocusable(true);//设置焦点
                }

                popupWindowFareBasis.showAsDropDown(et_fare_basis, 0, 0);
            }
        });


        traffic_fare_basis_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Traffic_Fare_Basis traffic_fare_basis = traffic_fare_basisList.get(position);
                //1.得到数据
                String msg = traffic_fare_basis.getBl_traffic_fare_basis_name();
                //2.设置输入框
                et_fare_basis.setText(msg);

                if (popupWindowFareBasis != null && popupWindowFareBasis.isShowing()) {

                    popupWindowFareBasis.dismiss();
                    popupWindowFareBasis = null;
                }
            }
        });


        /**
         *
         *                   GridView的相关设置
         * =================================================================
         * =================================================================
         * =================================================================
         * =================================================================
         * =================================================================
         * =================================================================
         *
         */
        //设置水平横向滑动的参数
        int size = 6;
        int length = 80;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = 110;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gwTrafficCostInvoice.setLayoutParams(params); //设置GirdView布局参数,横向布局的关键
        gwTrafficCostInvoice.setColumnWidth(itemWidth);
        gwTrafficCostInvoice.setHorizontalSpacing(15);
        gwTrafficCostInvoice.setStretchMode(GridView.NO_STRETCH);
        gwTrafficCostInvoice.setNumColumns(size);

        gwTrafficCostInvoice.setAdapter(trafficCostAddOrDelGridViewAdapter);


        /**
         * 设置图片选择的点击事件
         */

        gwTrafficCostInvoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int top_rr_id = R.id.relativelayout_gridview_item_top;
                int rr_id = R.id.relativelayout_gridview_item;
                Log.e("rr_id: ", rr_id + ",btn_del id= " + R.id.btn_del_gridview_item + ",top_id=" + top_rr_id);
                Log.e(TAG, "viewId= " + view.getId());
                Button button = (Button) view.findViewById(R.id.btn_del_gridview_item);

                Boolean isVisible = button.getVisibility() == View.VISIBLE;
                Log.e("button isVisible : ", isVisible + ",btn id=" + button.getId());

                if (isVisible) {

                    String picPath = picUrls.get(position);
                    Intent intent = new Intent(TrafficCostFormActivity.this, OnlyShowLocalLargePicActivity.class);
                    intent.putExtra("invoice_pic_url", picPath);
                    startActivity(intent);


                } else {
                    final String[] items = new String[]{"图库", "相机"};
                    new AlertDialog.Builder(TrafficCostFormActivity.this).setTitle("选择来源")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            //UIUtils.toast(items[0], false);
                                            //启动其他应用的activity:使用隐式意图
                                            Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            startActivityForResult(picture, PICTURE);
                                            break;
                                        case 1:
                                            //UIUtils.toast(items[1],false);
                                            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(camera, CAMERA);
                                            break;
                                    }
                                }
                            }).setNegativeButton("取消", null).show();
                }


            }
        });


        btnSaveTrafficCost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadTrafficCost();
            }
        });




    }



    class ListViewProjectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return projectList.size();
        }

        @Override
        public Object getItem(int position) {
            return projectList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProjectViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(TrafficCostFormActivity.this, R.layout.project_list_popwindow_item, null);
                viewHolder = new ProjectViewHolder();
                viewHolder.tv_project_popwindow_name = (TextView) convertView.findViewById(R.id.tv_project_popwindow_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ProjectViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            final Project project = projectList.get(position);
            viewHolder.tv_project_popwindow_name.setText(project.getP_name());

            return convertView;
        }
    }

    static class ProjectViewHolder {
        TextView tv_project_popwindow_name;
    }

    class ProBudgetBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pro_budgetList.size();
        }

        @Override
        public Object getItem(int position) {
            return pro_budgetList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProBudgetViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(TrafficCostFormActivity.this, R.layout.pro_budget_item_popwindow, null);
                viewHolder = new ProBudgetViewHolder();
                viewHolder.tv_pro_budget_name = (TextView) convertView.findViewById(R.id.tv_pro_budget_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ProBudgetViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            final Pro_Budget pro_budget = pro_budgetList.get(position);
            viewHolder.tv_pro_budget_name.setText(pro_budget.getPro_budget_name());

            return convertView;
        }
    }

    static class ProBudgetViewHolder {
        TextView tv_pro_budget_name;
    }


    /**
     * 得到所有的项目信息，展示成下拉框
     */
    public void getUserOwnProjectWithBudget(){
        new Thread() {
            public void run() {
                try {


                    final SharedPreferences sp =  getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

                    String user_jsonString = sp.getString("user_jsonString", "");

                    User user_logined = JSON.parseObject(user_jsonString, User.class);

                    Integer user_uuid = user_logined.getUser_uuid();

                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidGetAllProjectJson;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    String data ="&user_id="+ URLEncoder.encode(String.valueOf(user_uuid), "utf-8");

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
     * 向服务端保存数据
     * ======================================================================================
     * ======================================================================================
     * ======================================================================================
     * ======================================================================================
     * ======================================================================================
     */
    public void uploadTrafficCost() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient mOkHttpClient = new OkHttpClient();


                MultipartBody.Builder builder = new MultipartBody.Builder();


                Integer traffic_cost_user_id = LauncherActivity.ANDROID_USER_ID;

                String traffic_cost_pro_name = etTrafficCostProName.getText().toString();
                String traffic_cost_pro_budget_type = etTrafficCostProBudgetType.getText().toString();

                String traffic_cost_type = et_input.getText().toString();
                String traffic_cost_tag = et_traffic_cost_tag.getText().toString();
                String traffic_cost_fare_basis = et_fare_basis.getText().toString();
                String traffic_cost_start_city = et_traffic_cost_start_city.getText().toString();
                String traffic_cost_end_city = et_traffic_cost_end_city.getText().toString();
                String traffic_cost_start_datetime = et_start_date.getText().toString() + " " + et_start_time.getText().toString();
                String traffic_cost_end_datetime = et_end_date.getText().toString() + " " + et_end_time.getText().toString();
                String traffic_cost_total_amount = et_traffic_cost_total_amount.getText().toString();
                String traffic_cost_desc = et_traffic_cost_desc.getText().toString();


                StringBuffer traffic_cost_invoice_pic_urls_StringBufffer = new StringBuffer();


                for (int i = 0; i < picUrls.size(); i++) {
                    Log.e(TAG, "遍历最终选择的图片url,第" + i + "个图片的url: " + picUrls.get(i));
                    if (i == picUrls.size() - 1) {
                        String pic_save_name_last = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(10) + ".png";
                        Log.e(TAG, "发票url集合中第" + i + "个元素,pic_save_name_last = " + pic_save_name_last);
                        builder.addFormDataPart("image_1", pic_save_name_last,
                                RequestBody.create(MediaType.parse("image/jpeg"), new File(picUrls.get(i))));
                        traffic_cost_invoice_pic_urls_StringBufffer.append(pic_save_name_last);
                    } else {

                        String pic_save_name_before_last = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(10) + ".png";
                        Log.e(TAG, "发票url集合中第" + i + "个元素,pic_save_name_last = " + pic_save_name_before_last);
                        builder.addFormDataPart("image_1", pic_save_name_before_last,
                                RequestBody.create(MediaType.parse("image/jpeg"), new File(picUrls.get(i))));
                        traffic_cost_invoice_pic_urls_StringBufffer.append(pic_save_name_before_last + "|");
                    }
                }

                String traffic_cost_invoice_pic_url = traffic_cost_invoice_pic_urls_StringBufffer.toString();

                Log.e(TAG, "traffic_cost_invoice_pic_url=" + traffic_cost_invoice_pic_url);

                /**
                 *
                 * 	this.traffic_cost_type = traffic_cost_type;
                 this.traffic_cost_fare_basis = traffic_cost_fare_basis;
                 this.traffic_cost_start_city = traffic_cost_start_city;
                 this.traffic_cost_end_city = traffic_cost_end_city;
                 this.traffic_cost_start_datetime = traffic_cost_start_datetime;
                 this.traffic_cost_end_datetime = traffic_cost_end_datetime;
                 this.traffic_cost_unit_price = traffic_cost_unit_price;
                 this.traffic_cost_ticket_number = traffic_cost_ticket_number;
                 this.traffic_cost_total_amount = traffic_cost_total_amount;
                 this.traffic_cost_invoice_pic_url = traffic_cost_invoice_pic_url;
                 this.traffic_cost_desc = traffic_cost_desc;
                 this.traffic_cost_user_id = traffic_cost_user_id;
                 *
                 */
                Traffic_Cost traffic_cost = new Traffic_Cost(traffic_cost_pro_uuid,traffic_cost_pro_name,
                        traffic_cost_pro_budget_type,traffic_cost_type, traffic_cost_fare_basis, traffic_cost_start_city,
                        traffic_cost_end_city, traffic_cost_start_datetime, traffic_cost_end_datetime, "交通费单价",
                        "交通费票数", traffic_cost_total_amount, traffic_cost_invoice_pic_url, traffic_cost_desc, traffic_cost_user_id, traffic_cost_tag);
                // Java对象转JSON串

                Log.e("最终上传的交通费: ", traffic_cost.toString());
                Log.e("要准备上传的交通费的数据为: ", traffic_cost.toString());
                String trafficCost_jsonString = JSON.toJSONString(traffic_cost);
                builder.addFormDataPart("trafficCost_jsonString", trafficCost_jsonString);


                RequestBody requestBody = builder.build();
                Request.Builder reqBuilder = new Request.Builder();
                Request request = reqBuilder
                        .url(Constants.TRAFFIC_COST_SERVLET)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                    String resultInfo = response.body().string();
                    Log.e(TAG, "上传日常费用返回的结果: " + resultInfo);
                    if (resultInfo.equals("保存成功")) {
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK && data != null) {//相机
            //获取intent中的图片对象
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            //img_test.setImageBitmap(bitmap);

            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            String photoPath = getPath(uri);
            Log.e("从相机返回的图片的绝对路径: ", photoPath);
            Intent intent = new Intent(TrafficCostFormActivity.this, ShowInvoiceLargePicTestActivity.class);
            intent.putExtra("invoice_pic_url", photoPath);
            startActivityForResult(intent, PHOTOVIEW);
            /**
             * 从相机返回的图片的绝对路径:: /storage/emulated/0/DCIM/Camera/1514598888572.jpg
             */


            //对获取到的bitmap进行压缩、圆形处理
            //Bitmap zbitmap = BitmapUtils.zoom(bitmap, iv_user_icon.getWidth(), iv_user_icon.getHeight());
            //iv_user_icon.setImageBitmap(zbitmap);


        } else if (requestCode == PICTURE && resultCode == Activity.RESULT_OK && data != null) {//图库

            //图库
            Uri selectedImage = data.getData();
            //android各个不同的系统版本,对于获取外部存储上的资源，返回的Uri对象都可能各不一样,
            // 所以要保证无论是哪个系统版本都能正确获取到图片资源的话就需要针对各种情况进行一个处理了
            //这里返回的uri情况就有点多了
            //在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file://....
            // 在4.4.2返回的是content://com.android.providers.media.documents/document/image

            String pathResult = getPath(selectedImage);
            Log.e("从相册返回的绝对路径: ", pathResult);
            /**
             * 从相册返回的绝对路径:: /storage/emulated/0/DCIM/Camera/1514599043591.jpg
             * 从相册返回的绝对路径:: /storage/emulated/0/lagou/image/https:www.lgstatic.comiimageM007472CgpEMlo4uOSAF3PFAALGeI1RKvM745.JPG
             */
            //存储--->内存
            Bitmap decodeFile = BitmapFactory.decodeFile(pathResult);
            //img_test.setImageBitmap(decodeFile);
            Intent intent = new Intent(TrafficCostFormActivity.this, ShowInvoiceLargePicTestActivity.class);
            intent.putExtra("invoice_pic_url", pathResult);
            startActivityForResult(intent, PHOTOVIEW);
            //Bitmap zoomBitmap = BitmapUtils.zoom(decodeFile, iv_user_icon.getWidth(),iv_user_icon.getHeight());
            //bitmap圆形裁剪
            //Bitmap circleImage = BitmapUtils.circleBitmap(zoomBitmap);

            //加载显示
            //iv_user_icon.setImageBitmap(zoomBitmap);
            //上传到服务器（省略）

            //保存到本地
            //saveImage(circleImage);
        } else if (requestCode == PHOTOVIEW && resultCode == PHOTOVIEW_RESULT && data != null) {

            String resultPath = data.getStringExtra("Photo返回的filePath");
            Log.e("Photo返回的filePath", resultPath);
            picUrls.add(resultPath);
            trafficCostAddOrDelGridViewAdapter.notifyDataSetChanged();

        }
    }

    /**
     * GridView的适配器
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     */

    class TrafficCostAddOrDelGridViewAdapter extends BaseAdapter {

        private Integer maxInvoicePicNum;

        TrafficCostAddOrDelGridViewAdapter() {

        }

        TrafficCostAddOrDelGridViewAdapter(Integer maxInvoicePicNum) {
            this.maxInvoicePicNum = maxInvoicePicNum;
        }


        /**
         * 让GridView中的数据数目加1最后一个显示+号
         * 当到达最大张数时不再显示+号
         *
         * @return 返回GridView中的数量
         */
        @Override
        public int getCount() {
            Log.e(TAG, "Adapter: getCount");
            int count = (picUrls == null ? 1 : picUrls.size() + 1);
            if (count >= maxInvoicePicNum) {
                return picUrls.size();
            } else {

                return count;
            }
        }

        @Override
        public Object getItem(int position) {
            return picUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.e(TAG, "getView方法开始执行");
            GridViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater lf = (LayoutInflater) TrafficCostFormActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = lf.inflate(R.layout.gv_item_img, null);
                holder = new GridViewHolder();
                holder.ivimage = (ImageView) convertView.findViewById(R.id.img_gridview_item);
                holder.btdel = (Button) convertView.findViewById(R.id.btn_del_gridview_item);
                convertView.setTag(holder);
            } else {
                holder = (GridViewHolder) convertView.getTag();
            }

            /**代表+号之前有图片，需要显示图片**/
            if (picUrls != null && position < picUrls.size()) {
                Log.e(TAG, "getView: position=" + position + ", " + "picUrls.size(): " + picUrls.size());
                final String picPath = picUrls.get(position);
                File file = new File(picPath);
                Glide.with(TrafficCostFormActivity.this)
                        .load(file)
                        .priority(Priority.HIGH)
                        .into(holder.ivimage);
                /**
                 * 设置删除图标可见，并设置点击的删除事件
                 *
                 */
                holder.btdel.setVisibility(View.VISIBLE);
                holder.btdel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picUrls.remove(position);
                        notifyDataSetChanged();
                    }
                });
            } else {
                /**
                 * 还没有选择图片，图片为空，只需要显示+号图片
                 *
                 */
                Log.e(TAG, "getView: position=" + position + ", " + "picUrls.size(): " + picUrls.size());
                Glide.with(TrafficCostFormActivity.this)
                        .load(R.drawable.icon_addpic_unfocused)
                        .priority(Priority.HIGH)
                        .centerCrop()
                        .into(holder.ivimage);
                holder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.btdel.setVisibility(View.GONE);
            }


            return convertView;
        }
    }

    class GridViewHolder {

        ImageView ivimage;
        Button btdel;
    }


    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void GetAllTrafficTypeWithFareBasisList() {
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidGetAllTrafficTypeWithFareBasisList;
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
                        Log.e("返回的交通类型的JSON数据", result);
                        traffic_typeList = JSON.parseArray(result, Traffic_Type.class);

                        for (int i = 0; i < traffic_typeList.size(); i++) {
                            Traffic_Type traffic_type = traffic_typeList.get(i);
                            Log.e(TAG, "交通类型" + i + ": " + traffic_type.getTra_type_name());
                        }

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_TRAFFIC_TYPE;
                        msg.obj = traffic_typeList;
                        handler.sendMessage(msg);

                        //showToastInAnyThread(result);
                        if (result.equals("保存成功")) {
                            //finish();
                          /* Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                           startActivity(intent);*/
                        }
                    } else {
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
    }


    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return traffic_typeList.size();
        }

        @Override
        public Object getItem(int position) {
            return traffic_typeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(TrafficCostFormActivity.this, R.layout.popwindow_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            final Traffic_Type traffic_type = traffic_typeList.get(position);
            viewHolder.tv_msg.setText(traffic_type.getTra_type_name());

            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_msg;
    }

    class FareBasisBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return traffic_fare_basisList.size();
        }

        @Override
        public Object getItem(int position) {
            return traffic_fare_basisList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FareBasisBaseViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(TrafficCostFormActivity.this, R.layout.fare_basis_popwindow_item, null);
                viewHolder = new FareBasisBaseViewHolder();
                viewHolder.tv_fare_basis = (TextView) convertView.findViewById(R.id.tv_fare_basis);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (FareBasisBaseViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            final Traffic_Fare_Basis fare_basis = traffic_fare_basisList.get(position);
            viewHolder.tv_fare_basis.setText(fare_basis.getBl_traffic_fare_basis_name());

            return convertView;
        }
    }

    static class FareBasisBaseViewHolder {
        TextView tv_fare_basis;
    }


    /**
     * 抽取日期设置相关操作
     */
    public void setDateAndTime() {

        defaultDate(et_start_date);
        defaultDate(et_end_date);
        defaultTime(et_start_time);
        defaultTime(et_end_time);

        et_start_date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(et_start_date);
            }
        });

        et_end_date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(et_end_date);
            }
        });


        et_start_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(et_start_time);
            }
        });

        et_end_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(et_end_time);
            }
        });

    }


    /**
     * 日期显示处理开始
     */
    public void defaultDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        et_start_date.setText(year + "-" + (month + 1) + "-" + day);
        et_end_date.setText(year + "-" + (month + 1) + "-" + day);
    }

    public void defaultTime(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        et_start_time.setText(hour + ":" + minute);
        et_end_time.setText(hour + ":" + minute);
    }

    public void setDate(EditText view) {
        SetDateDialog sdd = new SetDateDialog(view);
        sdd.show(getFragmentManager(), "datepicker");
    }

    //创建日期选择对话框
    @SuppressLint("ValidFragment")
    class SetDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        EditText editTextview;

        SetDateDialog(EditText editTextview){
            this.editTextview = editTextview;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            return dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //et_start_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            editTextview.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

        }
    }

    //创建时间选择对话框
    @SuppressLint("ValidFragment")
    class SetTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        EditText editText;

        SetTimeDialog(EditText editText){
            this.editText = editText;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, true);
            return timePickerDialog;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //et_start_time.setText(hourOfDay + ":" + minute);
            editText.setText(hourOfDay + ":" + minute);
        }
    }

    public void setTime(EditText view) {
        SetTimeDialog setTimeDialog = new SetTimeDialog(view);
        setTimeDialog.show(getFragmentManager(), "mytimePicker");
    }


    /**
     *                             文件处理相关
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     * =======================================================================
     */
    /**
     * 文件处理相关
     *
     * @param uri
     * @return
     */

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(TrafficCostFormActivity.this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(TrafficCostFormActivity.this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = TrafficCostFormActivity.this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(TrafficCostFormActivity.this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

}
