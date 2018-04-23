package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import cn.jpush.android.api.JPushInterface;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.User;
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
                                final String user_jsonString = StreamTools.readStream(is);

                                // JSON串转JAVA对象
                                //UserGroup group2 = JSON.parseObject(jsonString, UserGroup.class);

                                //String user_jsonString = Json


                                if(!user_jsonString.equals("登陆失败")) {
                                    User loginedUser = JSON.parseObject(user_jsonString, User.class);
                                    Log.e("当前登录用户的信息:", loginedUser.toString());
                                    Integer reimuser_uuid = loginedUser.getUser_uuid();
                                    String alis = String.valueOf(reimuser_uuid);
                                    JPushInterface.setAlias(LauncherActivity.this,reimuser_uuid,alis);
                                    Log.e("------------>极光推送的别名: ",alis);
                                    //showToastInAnyThread(loginedUser.toString());
                                    ANDROID_USER_ID = loginedUser.getUser_uuid();
                                    saveLoginedUser(user_jsonString);
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    showToastInAnyThread("用户名或者密码错误！");
                                }


                            }else{
                                showToastInAnyThread("网络请求失败");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            showToastInAnyThread("网络请求成功，程序逻辑有异常！");
                        }

                    };
                }.start();
            }


    }

    public void saveLoginedUser(String user_jsonString){
        SharedPreferences sp =  this.getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sp.edit();
        editor.putString("user_jsonString",user_jsonString);
        editor.commit();

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
