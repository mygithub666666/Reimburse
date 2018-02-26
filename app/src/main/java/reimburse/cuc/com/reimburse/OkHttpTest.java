package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lzy.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hp1 on 2017/8/28.
 */
public class OkHttpTest extends Activity {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;

    private Button btn_okhttp_formupload;
    private EditText et_ok_baoxiaojine;
    private EditText et_ok_fapiaoneirong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttptest);
        btn1 = (Button) findViewById(R.id.button1_okhttp);
        btn2 = (Button) findViewById(R.id.button2_okhttp);
        btn3 = (Button) findViewById(R.id.button3_okhttp);
        btn4 = (Button) findViewById(R.id.button4_okhttp);
        btn5 = (Button) findViewById(R.id.button5_okhttp);
        btn6 = (Button) findViewById(R.id.button6_okhttp);

        btn_okhttp_formupload = (Button) findViewById(R.id.btn_okhttp_formUpload);
        et_ok_baoxiaojine = (EditText) findViewById(R.id.et_ok_baoxiaojine);
        et_ok_fapiaoneirong  = (EditText) findViewById(R.id.et_ok_fapiaoneirong);


        btn_okhttp_formupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                String str = et_ok_baoxiaojine.getText().toString();
                String nr = et_ok_fapiaoneirong.getText().toString();
                Log.e("---" + str, "---" + nr);
                String url = "http://106.37.86.242:8080/ssmweb/movie/okhttp";
                //String url = "http://10.2.146.242:8080/ssmweb/okhttpServlet";
                //String url = "http://106.37.86.242:8080/ssmweb/okhttpServlet";
                Toast.makeText(v.getContext(), "金额："+ str+"内容："+nr, Toast.LENGTH_SHORT).show();
                // username password
                File file0 = new File("/storage/emulated/0/lagou/cache/avator/user_photo_1502949797828_crop.jpg");
                RequestBody filebody0 = RequestBody.create(MediaType.parse("application/octet-stream"), file0);
                MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("username","abc")
                        .addFormDataPart("password", "md5")
                        .addFormDataPart("pic0", "0.jpg", filebody0)
                        .build();
                Request request = new Request.Builder().post(requestBody).url(url).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }


                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.print(response.toString());
                    }
                });

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    class MyButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
             OkHttpClient okHttpClient = new OkHttpClient();
             String str = et_ok_baoxiaojine.getText().toString();
             String nr = et_ok_fapiaoneirong.getText().toString();
             Log.e("---" + str, "---" + nr);
             Toast.makeText(v.getContext(), "金额："+ str+"内容："+nr, Toast.LENGTH_SHORT).show();
            // username password
             MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("username","abc")
                    .addFormDataPart("password", "md5")
                    .build();
            Request request = new Request.Builder().post(requestBody).url("http://10.2.146.242:8080/ssmweb/okhttpServlet").build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.print(response.toString());
                }
            });

        }
    }
}
