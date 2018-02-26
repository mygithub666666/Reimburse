package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.adaptor.ExpenseItemSelectAdapter;
import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.util.DatabaseHelper;

/**
 * Created by hp1 on 2017/8/23.
 */
public class ExpenseItemSelectActivity extends Activity {
    private List<Consumption> consumptions;
    private ListView listView;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_expenseitemselect);
        listView = (ListView) findViewById(R.id.lv_expenseitemselect);
        databaseHelper = new DatabaseHelper(this);
        consumptions = new ArrayList<Consumption>();
        initCostData();
        listView.setAdapter(new ExpenseItemSelectAdapter(ExpenseItemSelectActivity.this,consumptions));
    }

    private void initCostData() {
        Cursor cusor =  databaseHelper.getAllConsumptionData();
        if(cusor != null){
            while(cusor.moveToNext()){
                Consumption consumption = new Consumption(String.valueOf(cusor.getInt(0)),cusor.getString(1),cusor.getString(2),cusor.getString(3),cusor.getString(4));
                consumptions.add(consumption);
            }
            cusor.close();
        }
    }
}
