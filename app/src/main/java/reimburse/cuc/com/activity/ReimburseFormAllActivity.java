package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/12/20.
 */
public class ReimburseFormAllActivity extends Activity {
    private Button btn_to_travel_reimbursement;
    private Button btn_to_daily_reimbursement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reimburse_form_all);
        btn_to_travel_reimbursement = (Button) findViewById(R.id.btn_travel_reimbursement);
        btn_to_daily_reimbursement = (Button) findViewById(R.id.btn_daily_reimbursement);

        btn_to_travel_reimbursement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReimburseFormAllActivity.this,TravelReimbursementActivity.class);
                startActivity(intent);
            }
        });

        btn_to_daily_reimbursement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReimburseFormAllActivity.this,DailyReimbursementActivity.class);
                startActivity(intent);
            }
        });
    }
}
