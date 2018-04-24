package reimburse.cuc.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/4/24.
 */
public class UpdatePwdActivity extends Activity {
    @Bind(R.id.et_old_pwd)
    EditText etOldPwd;
    @Bind(R.id.et_new_pwd)
    EditText etNewPwd;
    @Bind(R.id.et_pwdagain)
    EditText etPwdagain;
    @Bind(R.id.btn_save_new_pwd)
    Button btnSaveNewPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepwd);
        ButterKnife.bind(this);

    }
}
