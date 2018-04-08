package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.Loan;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/3/30.
 */
public class LoanDisplayActivity extends Activity {

    private static final int EXCEL_DOWNLOAD_SUCCESS = 01;
    @Bind(R.id.et_loan_cause)
    EditText etLoanCause;
    @Bind(R.id.et_loan_project_name)
    EditText etLoanProjectName;
    @Bind(R.id.et_loan_amount)
    EditText etLoanAmount;
    @Bind(R.id.et_loan_user_name)
    EditText etLoanUserName;
    @Bind(R.id.et_loan_user_bank_number)
    EditText etLoanUserBankNumber;
    @Bind(R.id.et_loan_user_bank_name)
    EditText etLoanUserBankName;
    @Bind(R.id.et_loan_status)
    EditText etLoanStatus;
    @Bind(R.id.et_loan_check_comments)
    EditText etLoanCheckComments;
    @Bind(R.id.btn_export_loan_excel)
    Button btnExportLoanExcel;


    //1.创建一个消息处理器
    private Handler handler = new Handler(){
        //3.处理消息
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case EXCEL_DOWNLOAD_SUCCESS:
                    Toast.makeText(getApplicationContext(), "Excel已保存至"+(String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
            }

        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_activity_display);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        /**
         intent.putExtra("loanJSONStr",loanJSONStr);
         startActivity(intent);
         */

        String loanJSONStr = intent.getStringExtra("loanJSONStr");
        // JSON串转用户组对象
        Loan loan = JSON.parseObject(loanJSONStr, Loan.class);
        final Integer loan_uuid = loan.getLoan_uuid();
        etLoanCause.setText(loan.getLoan_cause());
        etLoanProjectName.setText(loan.getLoan_project_name());
        etLoanAmount.setText(loan.getLoan_amount());
        etLoanUserName.setText(loan.getLoan_user_name());
        etLoanUserBankNumber.setText(loan.getLoan_user_bank_number());
        etLoanUserBankName.setText(loan.getLoan_user_bank_name());
        etLoanStatus.setText(loan.getLoan_status());
        etLoanCheckComments.setText(loan.getLoan_check_comments());

        btnExportLoanExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excel_export(loan_uuid);
            }
        });

    }


    private void excel_export(final Integer loan_uuid) {

        new Thread() {
            @Override
            public void run() {

                try {

                    String urlStr = Constants.FILE_BASE_DIR + loan_uuid + ".xls";
                    Log.e("请求服务器的访问路径: ", urlStr);
                    URL url = new URL(urlStr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");//注意"GET"只能用大写
                    InputStream inputStream = connection.getInputStream();
                    //OkHttpUtils

                    Integer code = connection.getResponseCode();

                    Log.e("服务器返回的响应吗 code =  ", String.valueOf(code));

                    File dir = new File(Environment.getExternalStorageDirectory(), "reim_cost_imgs");
                    String excel_save_name = dir.getAbsolutePath() + File.separator + loan_uuid + ".xls";

                    File file = new File(excel_save_name);


                    FileOutputStream fos = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }

                    inputStream.close();
                    fos.close();

                    String success_path = file.getAbsolutePath();
                    Log.e("最终EXCEL保存的路径: ", success_path);
                    Message msg = Message.obtain();
                    msg.obj = success_path;
                    msg.what = EXCEL_DOWNLOAD_SUCCESS;
                    handler.sendMessage(msg);
                    //Toast.makeText(getApplication(),"Excel导出完毕",Toast.LENGTH_LONG);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

}
