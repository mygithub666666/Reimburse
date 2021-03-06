package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.adaptor.ConsumptionListAdaptor;
import reimburse.cuc.com.adaptor.CostListAdaptor;
import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.bean.Cost;
import reimburse.cuc.com.util.DatabaseHelper;

/**
 * Created by hp1 on 2017/8/19.
 */
public class NoteActivity extends Activity {
    private List<Consumption> consumptions;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        final ListView costList = (ListView) findViewById(R.id.lv_note);
        databaseHelper = new DatabaseHelper(this);
        consumptions = new ArrayList<Consumption>();
        //deleteAllData();
        initCostData();
        for (Consumption c : consumptions){
            System.out.println(c.toString());
        }
        costList.setAdapter(new ConsumptionListAdaptor(this, consumptions));
        costList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Consumption consumption = consumptions.get(position);
                Toast.makeText(NoteActivity.this,position+": "+consumption.getXiaofei_id()+","+consumption.getContent()+","+consumption.getDate()+","+consumption.getMoney()+","+consumption.getPicUris(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NoteActivity.this,ConsumptionDetaialActivity.class);
                intent.putExtra("content",consumption.getContent());
                intent.putExtra("date",consumption.getDate());
                intent.putExtra("money",consumption.getMoney());
                intent.putExtra("picURIS",consumption.getPicUris());
                startActivity(intent);
                Log.e("---------------position",String.valueOf(position));
            }
        });
    }

    private void initCostData() {
       Cursor cusor =  databaseHelper.getAllConsumptionData();
       if(cusor != null){
           while(cusor.moveToNext()){
               Consumption consumption = new Consumption(String.valueOf(cusor.getInt(0)),cusor.getString(1),cusor.getString(2),cusor.getString(3),cusor.getString(4));
               Log.e("从数据库查询得到的消费记录：",consumption.getXiaofei_id()+"="+consumption.getContent()+"="+consumption.getDate()+"="+consumption.getMoney()+"="+consumption.getPicUris());
               consumptions.add(consumption);
           }
           cusor.close();
       }
    }
    private void deleteAllData() {
       databaseHelper.deleteAllData();
    }
}
