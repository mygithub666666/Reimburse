package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import cn.jpush.android.api.JPushInterface;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.util.StreamTools;

public class LauncherActivity extends Activity {
    public static Integer ANDROID_USER_ID;
    private EditText username;
    private EditText pwd;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        username = (EditText) findViewById(R.id.et_login_number);
        pwd = (EditText) findViewById(R.id.et_login_pwd);
        loginBtn = (Button) findViewById(R.id.btn_login);

    }


    public void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /*public void login(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }*/
    public void login(View view){
            final String un = username.getText().toString();
            final String pawd = pwd.getText().toString();
            if (TextUtils.isEmpty(un) || TextUtils.isEmpty(pawd)) {
                Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_LONG).show();
                return;
            }else {

                // 数据应该提交给服务器 由服务器比较是否正确
                new Thread() {
                    public void run() {
                        try {
                            // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                            String path = Constants.ANDROID_USER_LOGIN_URL;
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            //2.指定请求方式为post
                            conn.setRequestMethod("POST");
                            //3.设置http协议的请求头
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型
                            //username=abc&password=123
                            String data = "user_name="+URLEncoder.encode(un, "utf-8")+"&password="+URLEncoder.encode(pawd);
                            conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                            //post的请求是把数据以流的方式写给了服务器
                            //指定请求的输出模式
                            conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                            conn.getOutputStream().write(data.getBytes());
                            int code = conn.getResponseCode();
                            if(code == 200){
                                InputStream is = conn.getInputStream();
                                final String result = StreamTools.readStream(is);
                                showToastInAnyThread(result);
                                String infoAnduid = result;
                                String[] resultArr = infoAnduid.split("AND");
                                //showToastInAnyThread(resultArr[0]);
                                //showToastInAnyThread(resultArr[1]);
                                ANDROID_USER_ID = Integer.parseInt(resultArr[1]);
                                //showToastInAnyThread(String.valueOf(ANDROID_USER_ID));
                                if(resultArr[0].equals("登陆成功")) {
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
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
