package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/12/25.
 */
public class ShowCostCategory extends Activity {

    private Button daily_cost;
    private Button traffic_cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cost_category);
        daily_cost = (Button) findViewById(R.id.btn_cost_category_daily_cost);

        traffic_cost = (Button) findViewById(R.id.btn_cost_category_traffic_cost);
        traffic_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowCostCategory.this,TrafficCostActivity.class);
                startActivity(intent);
            }
        });

        // TrafficCostActivity DailyCostListActivity
        daily_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowCostCategory.this,DailyCostListActivity.class);
                startActivity(intent);
            }
        });


    }
}
