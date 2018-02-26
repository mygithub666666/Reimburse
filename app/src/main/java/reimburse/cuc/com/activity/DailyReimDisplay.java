package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.bean.DailyCost;
import reimburse.cuc.com.bean.DailyReim;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/1/18.
 */
public class DailyReimDisplay extends Activity {

    @Bind(R.id.et_daily_reim_cause_display)
    EditText etDailyReimCauseDisplay;
    @Bind(R.id.et_daily_reim_amount_display)
    EditText etDailyReimAmountDisplay;
    @Bind(R.id.ettv_daily_reim_projectname_display)
    EditText ettvDailyReimProjectnameDisplay;
    @Bind(R.id.listview_tongyixiaofei_bean_list)
    ListView listviewTongyixiaofeiBeanList;
    @Bind(R.id.et_daily_reim_committime)
    EditText etDailyReimCommittime;
    @Bind(R.id.et_endtime)
    EditText etEndtime;
    @Bind(R.id.et_checkstatus)
    EditText etCheckstatus;
    @Bind(R.id.et_comments)
    EditText etComments;

    List<TongyiXiaofeiBean> tongyiXiaofeiBeans = new ArrayList<>();
    TongyiXiaofeiBeanListAdapter tongyiXiaofeiBeanListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_reim_display);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        /**
         * intent.putExtra("dailyReimJSONStr",dailyReimJSONStr);
           startActivity(intent);
         */

        String dailyReimJSONStr = intent.getStringExtra("dailyReimJSONStr");
        // JSON串转用户组对象
        DailyReim dailyReim = JSON.parseObject(dailyReimJSONStr, DailyReim.class);

        etDailyReimCauseDisplay.setText(dailyReim.getDaily_reim_cause());
        etDailyReimAmountDisplay.setText(dailyReim.getDaily_reim_amount());
        ettvDailyReimProjectnameDisplay.setText(dailyReim.getDaily_reim_project_name());
        etDailyReimCommittime.setText(dailyReim.getDaily_reim_submitTime());
        etEndtime.setText(dailyReim.getDaily_reim_endTime());
        etComments.setText(dailyReim.getDaily_reim_comments());
        etCheckstatus.setText(dailyReim.getDaily_reim_check_status());

        tongyiXiaofeiBeans = dailyReim.getTongyiXiaofeiBeans();

        for (int i=0;i<tongyiXiaofeiBeans.size();i++){
            TongyiXiaofeiBean tb = tongyiXiaofeiBeans.get(i);
            Log.e("第","日常报销单相关联的统一消费Bean: "+tb.toString());
        }

        if(tongyiXiaofeiBeans != null && tongyiXiaofeiBeans.size() >0) {
            tongyiXiaofeiBeanListAdapter = new TongyiXiaofeiBeanListAdapter(tongyiXiaofeiBeans);
            listviewTongyixiaofeiBeanList.setAdapter(tongyiXiaofeiBeanListAdapter);
        }

        listviewTongyixiaofeiBeanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeans.get(position);
                String type = tongyiXiaofeiBean.getXiaofei_type();
                if ((type.equals("火车")) || (type.equals("飞机")) || (type.equals("轮船")) || (type.equals("汽车"))) {

                    Intent intent = new Intent(DailyReimDisplay.this, TrafficCostDisplayActivity.class);
                    String traffic_json = tongyiXiaofeiBean.getOtherInfo();
                    Traffic_Cost traffic_cost = JSON.parseObject(traffic_json, Traffic_Cost.class);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + traffic_json);
                    intent.putExtra("TrafficCostJsonString", traffic_json);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(DailyReimDisplay.this, DailyCostDisplayActivity.class);
                    String daily_json = tongyiXiaofeiBean.getOtherInfo();
                    /*DailyReim dailyReim = (DailyReim) JSON.parseObject(
                            dailyCost_JSON_STRING, DailyReim.class);*/
                    DailyCost cost = JSON.parseObject(daily_json, DailyCost.class);
                    // 用户组对象转JSON串
                    String jsonString = JSON.toJSONString(cost);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + jsonString);
                    intent.putExtra("DailyCostJsonString", jsonString);
                    startActivity(intent);
                }
            }
        });


    }


    class TongyiXiaofeiBeanListAdapter extends BaseAdapter {

        List < TongyiXiaofeiBean > data;
        public TongyiXiaofeiBeanListAdapter(List < TongyiXiaofeiBean > data){
            this.data = data;
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new ViewHolder();
                /**
                 *   tv_lv_item_uniform_cost_amount
                 tv_lv_item_uniform_cost_start_date
                 tv_lv_item_uniform_cost_type
                 tv_lv_item_uniform_other_info
                 */
                convertView = layoutInflater.inflate(R.layout.lv_item_uniform_cost,null);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.tv_lv_item_uniform_cost_type);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_lv_item_uniform_cost_start_date);
                viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_lv_item_uniform_cost_amount);
                viewHolder.tvOhterInfo = (TextView) convertView.findViewById(R.id.tv_lv_item_uniform_other_info);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }
            TongyiXiaofeiBean consumption = data.get(position);
            viewHolder.tvType.setText(consumption.getXiaofei_type());
            viewHolder.tvDate.setText(consumption.getRiqi());
            viewHolder.tvMoney.setText(consumption.getTotalMoney());

            viewHolder.tvOhterInfo.setVisibility(View.INVISIBLE);
            viewHolder.tvOhterInfo.setText(consumption.getOtherInfo());
            return convertView;
        }
    }


    static class ViewHolder{
        public TextView tvType;
        public TextView tvDate;
        public TextView tvMoney;
        public TextView tvOhterInfo;
    }
}
