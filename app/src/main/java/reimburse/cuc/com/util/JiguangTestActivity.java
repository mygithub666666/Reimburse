package reimburse.cuc.com.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.jpush.android.api.JPushInterface;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/12/21.
 */
public class JiguangTestActivity extends Activity {
    private Button button;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.ceshi_jiguang_alias);
        editText = (EditText) findViewById(R.id.ceshi_jiguang_alias_et);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JPushInterface.setAlias(JiguangTestActivity.this, LauncherActivity.ANDROID_USER_ID, String.valueOf(LauncherActivity.ANDROID_USER_ID));
                Log.e("---===", LauncherActivity.ANDROID_USER_ID + "===");
            }
        });
    }
}
