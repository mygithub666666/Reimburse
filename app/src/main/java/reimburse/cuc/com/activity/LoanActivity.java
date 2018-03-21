package reimburse.cuc.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/3/10.
 */
public class LoanActivity extends Activity {

    @Bind(R.id.et_loan_cause)
    EditText etLoanCause;
    @Bind(R.id.et_project_loan)
    EditText etProjectLoan;
    @Bind(R.id.et_loan_amount)
    EditText etLoanAmount;
    @Bind(R.id.btn_commit_loan)
    Button btnCommitLoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_activity);
        ButterKnife.bind(this);
    }
}
