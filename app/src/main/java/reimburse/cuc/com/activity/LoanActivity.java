package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.Loan;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.User;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2018/3/10.
 */
public class LoanActivity extends Activity {
    private static final String TAG = LoanActivity.class.getSimpleName();
    @Bind(R.id.et_loan_cause)
    EditText etLoanCause;
    @Bind(R.id.et_loan_project_name)
    EditText etProjectLoan;
    @Bind(R.id.et_loan_amount)
    EditText etLoanAmount;
    @Bind(R.id.btn_commit_loan)
    Button btnCommitLoan;
    @Bind(R.id.et_loan_user_name)
    EditText etLoanUserName;
    @Bind(R.id.et_loan_user_bank_number)
    EditText etLoanUserBankNumber;
    @Bind(R.id.et_loan_user_bank_name)
    EditText etLoanUserBankName;

    private PopupWindow popupWindow;

    User user;
    private ListView projectListView;
    private List<Project> projectList;
    protected static final int SHOW_ALL_PROJECT = 0001;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_ALL_PROJECT) {

                projectListView.setAdapter(new ProjectListAdapter());
            }
        }
    };

    Integer project_uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_activity);
        ButterKnife.bind(this);

        final SharedPreferences sp = getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

        String user_jsonString = sp.getString("user_jsonString", "");

        User loginedUser = JSON.parseObject(user_jsonString, User.class);

        etLoanUserName.setText(loginedUser.getUser_name());
        etLoanUserBankNumber.setText(loginedUser.getBank_number());
        etLoanUserBankName.setText(loginedUser.getBank_name());


        projectListView = new ListView(this);
        projectList = new ArrayList<>();
        projectListView.setBackgroundResource(R.color.white);
        getAllProject();
        etProjectLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(LoanActivity.this);
                    popupWindow.setWidth(etProjectLoan.getWidth());
                    popupWindow.setHeight(200);


                    popupWindow.setContentView(projectListView);
                    popupWindow.setFocusable(true);

                }
                popupWindow.showAsDropDown(etProjectLoan, 0, 0);
            }
        });
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //1.得到数据
                Project project = projectList.get(position);
                project_uuid = project.getId();
                String proName = project.getP_name();
                //p_reimbursable_amount = project.getP_reimbursable_amount();
                //Log.e(TAG, "---> 项目的剩余报销余额: " + p_reimbursable_amount);
                etProjectLoan.setText(proName);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }

            }
        });

        btnCommitLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SharedPreferences sp = getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

                String user_jsonString = sp.getString("user_jsonString", "");

                User user = JSON.parseObject(user_jsonString, User.class);

                String loan_cause = etLoanCause.getText().toString();
                String loan_amount = etLoanAmount.getText().toString();
                String loan_project_name = etProjectLoan.getText().toString();
                String loan_user_name = user.getUser_name();
                String loan_user_bank_number = user.getBank_number();
                String loan_user_bank_name = user.getBank_name();
                Integer loan_project_uuid = project_uuid;
                Integer loan_commit_user_id = user.getUser_uuid();

                Loan loan = new Loan(loan_cause, loan_amount, loan_project_name, loan_user_name,
                        loan_user_bank_number, loan_user_bank_name, loan_project_uuid, loan_commit_user_id);

                String loanJsonString = JSON.toJSONString(loan);

                uploadDailyCost(loanJsonString);
            }
        });


    }

    public void uploadDailyCost(final String loanJsonString) {


        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidSaveLoan;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    //String data = "user_name="+URLEncoder.encode(un, "utf-8")+"&password="+URLEncoder.encode(pawd);
                    String data = "loanJsonString=" + URLEncoder.encode(loanJsonString, "utf-8");

                    conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                    //post的请求是把数据以流的方式写给了服务器
                    //指定请求的输出模式
                    conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                    conn.getOutputStream().write(data.getBytes());
                    Log.e("post size", String.valueOf(data.getBytes().length));
                    int code = conn.getResponseCode();

                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        final String resultInfo = StreamTools.readStream(is);
                        if (resultInfo.equals("保存成功")) {
                            showToastInAnyThread(resultInfo);
                            finish();
                        }


                    } else {
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("请求失败");
                }

            }

            ;
        }.start();

    }


    class ProjectListAdapter extends BaseAdapter {

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
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                viewHolder = new ProjectViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_projectname, null);
                viewHolder.projectName = (TextView) convertView.findViewById(R.id.tv_project_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ProjectViewHolder) convertView.getTag();

            }
            Project project = projectList.get(position);
            String pro_name = project.getP_name();
            String pro_left_reim = project.getP_reimbursable_amount();
            viewHolder.projectName.setText(pro_name + ", 可报销 " + pro_left_reim);
            return convertView;
        }
    }

    static class ProjectViewHolder {
        public TextView projectName;
    }


    /**
     *   final SharedPreferences sp =  this.getActivity().getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

     String user_jsonString = sp.getString("user_jsonString", "");

     User user = JSON.parseObject(user_jsonString,User.class);
     */


    /**
     * 得到所有的项目信息，展示成下拉框
     */
    public void getAllProject() {
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

                        //projectNameList = JSON.parseArray(result, String.class);

                        user = JSON.parseObject(result, User.class);

                        projectList = user.getProjects();

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_PROJECT;
                        msg.obj = projectList;
                        handler.sendMessage(msg);
                        Log.e("返回的JSON数据", result);
                        //showToastInAnyThread(result);
                        if (result.equals("保存成功")) {
                        }
                    } else {
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("请求失败");
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
