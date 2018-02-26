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
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.bean.Travel_Reimbursement;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/1/18.
 */
public class TravelReimDisplayActivity extends Activity {
    @Bind(R.id.et_travel_reimbursement_start_city_ds)
    EditText etTravelReimbursementStartCityDs;
    @Bind(R.id.et_travel_reimbursement_end_city_ds)
    EditText etTravelReimbursementEndCityDs;
    @Bind(R.id.et_travel_start_date_ds)
    EditText etTravelStartDateDs;
    @Bind(R.id.et_travel_end_date_ds)
    EditText etTravelEndDateDs;
    @Bind(R.id.et_travel_urban_transport_ds)
    EditText etTravelUrbanTransportDs;
    @Bind(R.id.et_travel_food_allowance_ds)
    EditText etTravelFoodAllowanceDs;
    @Bind(R.id.et_travel_reim_amount_ds)
    EditText etTravelReimAmountDs;
    @Bind(R.id.et_travel_reim_status_ds)
    EditText etTravelReimStatusDs;
    @Bind(R.id.et_travel_cause_ds)
    EditText etTravelCauseDs;
    @Bind(R.id.et_travel_reimbursement_project_name_ds)
    EditText etTravelReimbursementProjectNameDs;
    @Bind(R.id.et_travel_user_ds)
    EditText etTravelUserDs;
    @Bind(R.id.et_travel_user_title_ds)
    EditText etTravelUserTitleDs;
    @Bind(R.id.list_view_travel_reimburse_uniform_cost_ds)
    ListView listViewTravelReimburseUniformCostDs;

    List<TongyiXiaofeiBean> tongyiXiaofeiBeans = new ArrayList<>();
    TongyiXiaofeiBeanListAdapter tongyiXiaofeiBeanListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_reim_display);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        /**
         *
         * intent.putExtra("travelReimJSONStr",travelReimJSONStr);
         */


        String travelReimJSONStr = intent.getStringExtra("travelReimJSONStr");
        // JSON串转用户组对象
        Travel_Reimbursement travel_reimbursement = JSON.parseObject(travelReimJSONStr, Travel_Reimbursement.class);

        etTravelCauseDs.setText(travel_reimbursement.getTravel_reimbursement_cause());
        etTravelReimbursementStartCityDs.setText(travel_reimbursement.getTravel_reimbursement_start_city());
        etTravelReimbursementEndCityDs.setText(travel_reimbursement.getTravel_reimbursement_end_city());
        etTravelStartDateDs.setText(travel_reimbursement.getTravel_reimbursement_start_date());
        etTravelEndDateDs.setText(travel_reimbursement.getTravel_reimbursement_end_date());
        etTravelFoodAllowanceDs.setText(travel_reimbursement.getTravel_reimbursement_board_allowance());
        etTravelUrbanTransportDs.setText(travel_reimbursement.getTravel_reimbursement_traffic_allowance());
        etTravelUserDs.setText(travel_reimbursement.getTravel_reimbursement_name());
        etTravelUserTitleDs.setText(travel_reimbursement.getTravel_reimbursement_job_title());
        etTravelReimbursementProjectNameDs.setText(travel_reimbursement.getTravel_reimbursement_project_name());
        etTravelReimAmountDs.setText(travel_reimbursement.getTravel_reimbursement_total_amount());

        tongyiXiaofeiBeans = travel_reimbursement.getTongyiXiaofeiBeans();

        for (int i=0;i<tongyiXiaofeiBeans.size();i++){
            TongyiXiaofeiBean tb = tongyiXiaofeiBeans.get(i);
            Log.e("第", "日常报销单相关联的统一消费Bean: " + tb.toString());
        }

        if(tongyiXiaofeiBeans != null && tongyiXiaofeiBeans.size() >0) {
            tongyiXiaofeiBeanListAdapter = new TongyiXiaofeiBeanListAdapter(tongyiXiaofeiBeans);
            listViewTravelReimburseUniformCostDs.setAdapter(tongyiXiaofeiBeanListAdapter);
        }

        listViewTravelReimburseUniformCostDs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TongyiXiaofeiBean tongyiXiaofeiBean = tongyiXiaofeiBeans.get(position);
                String type = tongyiXiaofeiBean.getXiaofei_type();
                if ((type.equals("火车")) || (type.equals("飞机")) || (type.equals("轮船")) || (type.equals("汽车"))) {

                    Intent intent = new Intent(TravelReimDisplayActivity.this, TrafficCostDisplayActivity.class);
                    String traffic_json = tongyiXiaofeiBean.getOtherInfo();
                    Traffic_Cost traffic_cost = JSON.parseObject(traffic_json, Traffic_Cost.class);
                    Log.e("等待选择消费列表的点击事件", ", jsonString:" + traffic_json);
                    intent.putExtra("TrafficCostJsonString", traffic_json);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(TravelReimDisplayActivity.this, DailyCostDisplayActivity.class);
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
