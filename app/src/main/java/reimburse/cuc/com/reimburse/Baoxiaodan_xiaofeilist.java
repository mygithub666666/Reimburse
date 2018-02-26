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
import java.util.LinkedList;
import java.util.List;

import reimburse.cuc.com.adaptor.ConsumptionListAdaptor;
import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.util.DatabaseHelper;

/**
 * Created by hp1 on 2017/8/19.
 */
public class Baoxiaodan_xiaofeilist extends Activity {
    private String xiaofeiids = null;
    private List<Consumption> consumptions;
    private DatabaseHelper databaseHelper;
    private List<Consumption> finalConsumptions = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // baoxiaodan_xiaofeilist
        setContentView(R.layout.baoxiaodan_xiaofeilist);
        final ListView costList = (ListView) findViewById(R.id.lv_baoxiaodanxiaofeilist);
        databaseHelper = new DatabaseHelper(this);
        consumptions = new ArrayList<Consumption>();

        Intent intent = getIntent();
        xiaofeiids = intent.getStringExtra("xiaofeiids");
        String[] arr = xiaofeiids.split(",");
        for (int i=0;i<arr.length;i++){
            Log.e("----------第--"+i,arr[i]);
        }
        //getConsumptionsByIds();
        initCostData();
        for (Consumption c : consumptions){
            for (int i=0;i<arr.length;i++){
                Log.e("----------第--" + i, arr[i]);
                if(c.getXiaofei_id().equals(arr[i])) {
                    Log.e("选择的消费记录: ",c.getContent()+"=="+i);
                    System.out.println(c.toString());
                    finalConsumptions.add(c);
                }
            }

        }
        Log.e("该报销单的消费条数:",String.valueOf(finalConsumptions.size()));
        costList.setAdapter(new ConsumptionListAdaptor(this, finalConsumptions));
        costList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Consumption consumption = consumptions.get(position);
                Toast.makeText(Baoxiaodan_xiaofeilist.this,position+": "+consumption.getXiaofei_id()+","+consumption.getContent()+","+consumption.getDate()+","+consumption.getMoney()+","+consumption.getPicUris(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Baoxiaodan_xiaofeilist.this,ConsumptionDetaialActivity.class);
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
        String[] arr = xiaofeiids.split(",");
        for (int i=0;i<arr.length;i++){
            Log.e("----------第--"+i,arr[i]);
        }
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
    private void getConsumptionsByIds(){
       /* String[] arr = xiaofeiids.split(",");
        for (int i=0;i<arr.length;i++){
            Log.e("----------第--"+i,arr[i]);
        }*/
        String[] arr = {"1","2"};
        Cursor cursor = databaseHelper.getConsumptionsByIds(arr);
        if(cursor != null){
            while(cursor.moveToNext()){
                Consumption consumption = new Consumption(String.valueOf(cursor.getInt(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                Log.e("从数据库查询得到的消费记录：",consumption.getXiaofei_id()+"="+consumption.getContent()+"="+consumption.getDate()+"="+consumption.getMoney()+"="+consumption.getPicUris());
                consumptions.add(consumption);
            }
            cursor.close();
        }

    }
    private void deleteAllData() {
       databaseHelper.deleteAllData();
    }
}
