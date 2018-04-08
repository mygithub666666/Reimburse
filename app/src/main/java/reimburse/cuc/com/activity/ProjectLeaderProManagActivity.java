package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.DailyReim;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.User;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2018/4/4.
 */
public class ProjectLeaderProManagActivity extends Activity {
    private static final int SHOW_MY_PROJECT_LIST = 01;
    @Bind(R.id.listview_proleader_pro_list)
    ListView listviewProleaderProList;
    private List<Project> projectList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_MY_PROJECT_LIST) {
                listviewProleaderProList.setAdapter(new MyProjectListAdapter());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_leader_pro_manage);
        ButterKnife.bind(this);
        //androidGetOwnDailyReimListByUserID();
        androidGetGetAllProjectWithPro_BudgetByLeaderJonNum();
        listviewProleaderProList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project project = projectList.get(position);
                String projectJSONStr = JSON.toJSONString(project);
                Intent intent = new Intent(ProjectLeaderProManagActivity.this, ProjectDisplayActivity.class);
                // 用户组对象转JSON串
                //String jsonString = JSON.toJSONString(cost);
                Log.e("项目信息列表的点击事件", ", projectJSONStr:" + projectJSONStr);
                intent.putExtra("projectJSONStr", projectJSONStr);
                startActivity(intent);

            }
        });

    }

    public void androidGetOwnDailyReimListByUserID() {
        new Thread() {
            public void run() {
                try {

                    final SharedPreferences sp = getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

                    String user_jsonString = sp.getString("user_jsonString", "");

                    User user = JSON.parseObject(user_jsonString, User.class);

                    String pro_leader_job_num = user.getReim_user_job_number();
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidGetProLeaderAllProjectByJobNum;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    String data = "&pro_leader_job_num=" + URLEncoder.encode(pro_leader_job_num, "utf-8");

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
                        projectList = JSON.parseArray(result, Project.class);
                        Message message = Message.obtain();
                        message.obj = projectList;
                        message.what = SHOW_MY_PROJECT_LIST;
                        handler.sendMessage(message);
                        Log.e("从服务端得到的报销数据", projectList.size() + "");
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
        }.start();
    }

    public void androidGetGetAllProjectWithPro_BudgetByLeaderJonNum() {
        new Thread() {
            public void run() {
                try {

                    final SharedPreferences sp = getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

                    String user_jsonString = sp.getString("user_jsonString", "");

                    User user = JSON.parseObject(user_jsonString, User.class);

                    String pro_leader_job_num = user.getReim_user_job_number();
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.androidGetGetAllProjectWithPro_BudgetByLeaderJonNum;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    String data = "&pro_leader_job_num=" + URLEncoder.encode(pro_leader_job_num, "utf-8");

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
                        projectList = JSON.parseArray(result, Project.class);
                        Message message = Message.obtain();
                        message.obj = projectList;
                        message.what = SHOW_MY_PROJECT_LIST;
                        handler.sendMessage(message);
                        Log.e("从服务端得到的报销数据", projectList.size() + "");
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
        }.start();
    }


    class MyProjectListAdapter extends BaseAdapter {

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
            ViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_proleader_prolist, null);
                viewHolder.tvProjectName = (TextView) convertView.findViewById(R.id.lv_item_project_name);
                viewHolder.tvProTotalMoney = (TextView) convertView.findViewById(R.id.lv_item_pro_totalmoney);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            Project project = projectList.get(position);
            viewHolder.tvProjectName.setText(project.getP_name());
            viewHolder.tvProTotalMoney.setText(project.getP_totalmoney());
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tvProjectName;
        public TextView tvProTotalMoney;
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
