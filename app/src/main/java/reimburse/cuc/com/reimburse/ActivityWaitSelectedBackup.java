package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.adaptor.ExpenseSubmitAdapter;
import reimburse.cuc.com.adaptor.TestRecyclerViewAdapter;
import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.util.DatabaseHelper;

/**
 * Created by hp1 on 2017/8/26.
 */
public class ActivityWaitSelectedBackup extends Activity{
   /* DatabaseHelper databaseHelper;
    List<Consumption> result;
    private ExpenseSubmitAdapter expenseSubmitAdapter;
    private static final int RESPONSE_SELECT_EXPENSE_COMPLETED = 1001;
    private TestRecyclerViewAdapter testRecyclerViewAdapter;
    //初始化view
    private TextView tvExpenseSelectEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvExpenseTotal;
    private Button btnSubmit;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        databaseHelper = new DatabaseHelper(ActivityWaitSelectedBackup.this);
        result = new ArrayList<Consumption>();
        initCostData();
        showData();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("选择好消费列表后的总金额：", tvExpenseTotal.getText().toString());
                Toast.makeText(ActivityWaitSelectedBackup.this,tvExpenseTotal.getText().toString(),Toast.LENGTH_LONG).show();
                List<Consumption> returnList = new ArrayList<Consumption>();
                List<Consumption> selectedConsuList = expenseSubmitAdapter.getConsumptionsIsSelected();
                Log.e("选择了",selectedConsuList.size()+"个消费内容");
                for (Consumption consumption : selectedConsuList){
                        Log.e("选择的消费内容为:",consumption.getContent()+": "+consumption.isSelected());
                        returnList.add(consumption);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("consumptionList", (Serializable) returnList);
                Intent intent = new Intent();
                //intent.putExtra("consumptionList", (Serializable) returnList);
                intent.putExtras(bundle);
                setResult(RESPONSE_SELECT_EXPENSE_COMPLETED, intent);
                finish();
            }
        });


    }

    private void initCostData() {
        Cursor cusor =  databaseHelper.getAllConsumptionData();
        if(cusor != null){
            while(cusor.moveToNext()){
                Consumption consumption = new Consumption(String.valueOf(cusor.getInt(0)),cusor.getString(1),cusor.getString(2),cusor.getString(3),cusor.getString(4));
                Log.e("----",consumption.getContent()+":"+consumption.getDate()+":"+consumption.getMoney());
                result.add(consumption);
            }
            cusor.close();
        }
    }
    private List<Consumption> getAllConsumptions() {
        List<Consumption> consumptions = new ArrayList<Consumption>();
        Cursor cusor =  databaseHelper.getAllConsumptionData();
        if(cusor != null){
            while(cusor.moveToNext()){
                Consumption consumption = new Consumption(String.valueOf(cusor.getInt(0)),cusor.getString(1),cusor.getString(2),cusor.getString(3),cusor.getString(4));
                Log.e("----",consumption.getXiaofei_id()+":"+consumption.getContent()+":"+consumption.getDate()+":"+consumption.getMoney());
                consumptions.add(consumption);
            }
            cusor.close();
        }
        return consumptions;
    }


    public void initView() {
        setContentView(R.layout.activity_waitforselect);
        //View view = View.inflate(ActivityWaitSelected.this, R.layout.activity_waitforselect, null);
       // tvExpenseSelectEdit = (TextView) findViewById(R.id.tv_shopcart_edit);
        llCheckAll = (LinearLayout) findViewById(R.id.ll_check_all);
        checkboxAll = (CheckBox) findViewById(R.id.checkbox_all);
        tvExpenseTotal = (TextView) findViewById(R.id.tv_expense_select_total_submit);
        btnSubmit = (Button) findViewById(R.id.btn_select_submit);
        //llDelete = (LinearLayout) findViewById(R.id.ll_delete);
        //cbAll = (CheckBox) findViewById(R.id.cb_all);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_select_item);
        //return view;
    }
    public void showData(){
         List<Consumption> allConsumptions = getAllConsumptions();
         if(allConsumptions != null){
               expenseSubmitAdapter = new ExpenseSubmitAdapter(ActivityWaitSelectedBackup.this,allConsumptions,tvExpenseTotal,checkboxAll);
              //testRecyclerViewAdapter = new TestRecyclerViewAdapter(ActivityWaitSelected.this,result);
              Log.e("-----result.size=", String.valueOf(result.size()));
              recyclerview.setLayoutManager(new LinearLayoutManager(ActivityWaitSelectedBackup.this, LinearLayoutManager.VERTICAL,false));
              recyclerview.setAdapter(expenseSubmitAdapter);
         }
    }
*/
}
