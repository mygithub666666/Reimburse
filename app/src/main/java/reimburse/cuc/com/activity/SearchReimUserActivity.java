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
    private static final int SHOW_EMP = 01;
    private static final int REIM_USER_ADD_COMPLETED = 02;
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
    @Bind(R.id.btn_add_reim_user)
    Button btnAddReimUser;
    @Bind(R.id.et_emp_bank_number)
    EditText etEmpBankNumber;
    @Bind(R.id.et_emp_bank_name)
    EditText etEmpBankName;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Employee emp = (Employee) msg.obj;
            etEmpInfoUserName.setText(emp.getUser_name());
            etEmpInfoMobilePhoneNumber.setText(emp.getE_phone());
            etEmpInfoEmail.setText(emp.getEmail());
            etEmpBankNumber.setText(emp.getEmp_bank_number());
            etEmpBankName.setText(emp.getEmp_bank_name());

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
                    androidSearchEmpByJobNum(com_user_job_number);
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
                String empJsonString = JSON.toJSONString(employee);

                String pro_uuid = String.valueOf(project.getId());

                addProjectReimUser(empJsonString, pro_uuid);

                /*Bundle bundle = new Bundle();
                bundle.putSerializable("employee", (Serializable) employee);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(REIM_USER_ADD_COMPLETED, intent);
                finish();*/
            }
        });
    }

    public void androidSearchEmpByJobNum(final String com_user_job_number) {
        new Thread() {
            public void run() {
                try {

                    final SharedPreferences sp = getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

                    String user_jsonString = sp.getString("user_jsonString", "");

                    User user = JSON.parseObject(user_jsonString, User.class);
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidSearchEmpByJobNum;
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
                        employee = JSON.parseObject(result, Employee.class);
                        Message message = Message.obtain();
                        message.obj = employee;
                        message.what = SHOW_EMP;
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

    public void addProjectReimUser(final String empJsonString, final String pro_uuid) {

        // 数据应该提交给服务器 由服务器比较是否正确
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.addProjectReimUser;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型
                    //username=abc&password=123
                    String data = "empJsonString=" + URLEncoder.encode(empJsonString, "utf-8")
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
                        if (reponseString.equals("添加报销人员成功")) {
                           /* showToastInAnyThread("添加报销人员成功!");
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("employee", (Serializable) employee);
                            intent.putExtras(bundle);*/
                            /*setResult(REIM_USER_ADD_COMPLETED, intent);*/
                            showToastInAnyThread("添加报销人员成功!");
                            finish();


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

            ;
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
