package reimburse.cuc.com.activity;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.DailyReim;
import reimburse.cuc.com.bean.Loan;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2018/3/30.
 */
public class LoanList extends Activity {


    private static final int SHOW_MY_LOAN_LIST = 01;
    @Bind(R.id.listview_loan_list)
    ListView listviewLoanList;
    private List<Loan> loanList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_MY_LOAN_LIST) {
                listviewLoanList.setAdapter(new MyLoanListAdapter());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanList = new ArrayList<>();
        setContentView(R.layout.activity_loan_list);
        ButterKnife.bind(this);
        androidGetOwnLoanListByUserID();


        listviewLoanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Loan loan = loanList.get(position);
                        String loanJSONStr = JSON.toJSONString(loan);
                        Intent intent = new Intent(LoanList.this,LoanDisplayActivity.class);
                        // 用户组对象转JSON串
                        //String jsonString = JSON.toJSONString(cost);
                        Log.e("借款列表的点击事件", ", loanJSONStr:" + loanJSONStr);
                        intent.putExtra("loanJSONStr",loanJSONStr);
                        startActivity(intent);

            }
        });

    }


    public void androidGetOwnLoanListByUserID() {
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.ANDROID_GET_OWN_LOAN_LIST_BY_USER_ID;
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
                        loanList = JSON.parseArray(result, Loan.class);
                        Message message = Message.obtain();
                        message.obj = loanList;
                        message.what = SHOW_MY_LOAN_LIST;
                        handler.sendMessage(message);
                        Log.e("从服务端得到的借款数据", loanList.size() + "");
                        Log.e("返回的JSON数据", result);
                        //showToastInAnyThread(result);
                        if (result.equals("保存成功")) {

                        }
                    } else {
                        showToastInAnyThread("数据返回异常");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastInAnyThread("网络请求失败");
                }

            }
        }.start();
    }


    class MyLoanListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return loanList.size();
        }

        @Override
        public Object getItem(int position) {
            return loanList.get(position);
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
                convertView = layoutInflater.inflate(R.layout.lv_item_loan, null);
                viewHolder.tv_loan_project_name = (TextView) convertView.findViewById(R.id.lv_item_loan_project_name);
                viewHolder.tv_loan_status = (TextView) convertView.findViewById(R.id.lv_item_loan_status);
                viewHolder.tv_loan_amount = (TextView) convertView.findViewById(R.id.lv_item_loan_amount);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            Loan loan = loanList.get(position);
            viewHolder.tv_loan_project_name.setText(loan.getLoan_project_name());
            viewHolder.tv_loan_status.setText(loan.getLoan_status());
            viewHolder.tv_loan_amount.setText(loan.getLoan_amount());
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tv_loan_project_name;
        public TextView tv_loan_status;
        public TextView tv_loan_amount;
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




