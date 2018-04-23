package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.bean.CompanyUser;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.Employee;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.User;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2018/4/5.
 */
public class SearchReimUserActivity extends Activity {
    private static final int SHOW_COMPANY_USER = 01;
    private static final int REIM_USER_ADD_COMPLETED = 02;
    public static final int RESPONSE_AND_REIM_USER_SUCCESS = 03;
    private static final int SHOW_ADD_PROJECT_REIM_USER_SUCCESS = 04;
    public static final int RESPONSE_ADD_REIM_USER_SUCCESS = 06;
    @Bind(R.id.tv_project_daily_reim)
    TextView tvProjectDailyReim;
    @Bind(R.id.et_emp_jobnum)
    EditText etEmpJobnum;
    @Bind(R.id.btnSearchEmpByJobNumBtn)
    Button btnSearchEmpByJobNumBtn;
    @Bind(R.id.project_relativelayout)
    RelativeLayout projectRelativelayout;
    @Bind(R.id.tv_emp_info_user_name)
    TextView tvEmpInfoUserName;
    @Bind(R.id.et_emp_info_user_name)
    EditText etEmpInfoUserName;
    @Bind(R.id.tv_mobile_phone_number)
    TextView tvMobilePhoneNumber;
    @Bind(R.id.et_emp_info_mobile_phone_number)
    EditText etEmpInfoMobilePhoneNumber;
    @Bind(R.id.tv_emp_info_email)
    TextView tvEmpInfoEmail;
    @Bind(R.id.et_emp_info_email)
    EditText etEmpInfoEmail;

    Employee employee = null;
    CompanyUser companyUser = null;
    @Bind(R.id.btn_add_reim_user)
    Button btnAddReimUser;
    @Bind(R.id.et_emp_bank_number)
    EditText etEmpBankNumber;
    @Bind(R.id.et_emp_bank_name)
    EditText etEmpBankName;

    String addReimUserIsSuccess = null;
    User success_add_user = null;
    @Bind(R.id.btn_back_pro_display)
    Button btnBackProDisplay;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_COMPANY_USER:
                    CompanyUser companyUser = (CompanyUser) msg.obj;
                    etEmpInfoUserName.setText(companyUser.getCom_user_name());
                    etEmpInfoMobilePhoneNumber.setText(companyUser.getCom_user_mobile_phone_num());
                    etEmpInfoEmail.setText(companyUser.getCom_user_email());
                    etEmpBankNumber.setText(companyUser.getBank_number());
                    etEmpBankName.setText(companyUser.getBank_name());
                    break;
                case SHOW_ADD_PROJECT_REIM_USER_SUCCESS:
                    showToastInAnyThread("添加成功!");
                    break;
                default:
                    break;
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_reim_user);
        ButterKnife.bind(this);

        /**
         * intent.putExtra("projectJSONStr",projectJSONStr);
         */
        Intent intent = getIntent();
        String projectJSONStr = intent.getStringExtra("projectJSONStr");
        // JSON串转用户组对象
        final Project project = JSON.parseObject(projectJSONStr, Project.class);
        Integer id = project.getId();


        btnSearchEmpByJobNumBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String com_user_job_number = etEmpJobnum.getText().toString();
                if (com_user_job_number != null) {
                    Log.e("请求的工号：", com_user_job_number);
                    androidSearchCompanyUserByJobNum(com_user_job_number);
                } else {
                    showToastInAnyThread("工号不能为空!");
                }
            }
        });

        btnAddReimUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 用户组对象转JSON串
                //String jsonString = JSON.toJSONString(cost);
                String companyUserJsonString = JSON.toJSONString(companyUser);

                String pro_uuid = String.valueOf(project.getId());

                addProjectReimUser(companyUserJsonString, pro_uuid);


            }
        });

        btnBackProDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_user_json = JSON.toJSONString(success_add_user);
                //设置返回的数据
                Intent intent = new Intent();
                intent.putExtra("new_user_json", new_user_json);
                setResult(RESPONSE_ADD_REIM_USER_SUCCESS, intent);
                //关闭当前activity
                finish();
            }
        });
    }

    public void androidSearchCompanyUserByJobNum(final String com_user_job_number) {
        new Thread() {
            public void run() {
                try {

                    final SharedPreferences sp = getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

                    String user_jsonString = sp.getString("user_jsonString", "");

                    User user = JSON.parseObject(user_jsonString, User.class);
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    //androidSearchCompanyUserByJobNum
                    String path = Constants.ANDROID_SEARCH_COMPANY_USER_BY_JOBNUM;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    String data = "&com_user_job_number=" + URLEncoder.encode(com_user_job_number, "utf-8");

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
                        Log.e("根据工号查询员工返回的数据JSON数据", result);
                        //JSON.parseObject(projectJSONStr, Project.class);
                        companyUser = JSON.parseObject(result, CompanyUser.class);
                        Message message = Message.obtain();
                        message.obj = companyUser;
                        message.what = SHOW_COMPANY_USER;
                        handler.sendMessage(message);

                        //showToastInAnyThread(result);
                        if (result.equals("保存成功")) {

                            /*Intent intent = new Intent(getApplicationContext(),Pro.class);
                            startActivity(intent);*/
                        }
                    } else {
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("数据异常");
                }

            }
        }.start();
    }

    public void addProjectReimUser(final String companyUserJsonString, final String pro_uuid) {

        // 数据应该提交给服务器 由服务器比较是否正确
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    //String path = Constants.addProjectReimUser;
                    String path = Constants.ADD_PROJECT_REIM_USER_WITH_COMPANY_USER;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型
                    //username=abc&password=123
                    String data = "companyUserJsonString=" + URLEncoder.encode(companyUserJsonString, "utf-8")
                            + "&pro_uuid=" + URLEncoder.encode(pro_uuid, "utf-8");
                    conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                    //post的请求是把数据以流的方式写给了服务器
                    //指定请求的输出模式
                    conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                    conn.getOutputStream().write(data.getBytes());
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        final String reponseString = StreamTools.readStream(is);

                        // JSON串转JAVA对象
                        //UserGroup group2 = JSON.parseObject(jsonString, UserGroup.class);

                        //String user_jsonString = Json
                        if (!reponseString.equals("添加报销人员失败")) {
                            success_add_user = JSON.parseObject(reponseString, User.class);
                            Message message = Message.obtain();
                            message.obj = reponseString;
                            message.what = SHOW_ADD_PROJECT_REIM_USER_SUCCESS;
                            handler.sendMessage(message);
                            addReimUserIsSuccess = "success_add";

                        } else {
                            showToastInAnyThread("添加报销人员失败");
                        }


                    } else {
                        showToastInAnyThread("网络请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("网络请求失败");
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
}
