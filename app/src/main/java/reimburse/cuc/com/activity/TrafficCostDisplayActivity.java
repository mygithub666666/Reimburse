package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/12/26.
 */
public class TrafficCostDisplayActivity extends Activity {


    private static final String TAG = TrafficCostDisplayActivity.class.getSimpleName();
    private EditText et_traffic_cost_display_type;
    private EditText et_traffic_cost_display_fare_basis;
    private EditText et_traffic_cost_display_start_city;
    private EditText et_traffic_cost_display_end_city;
    private EditText et_traffic_cost_display_start_date;
    private EditText et_traffic_cost_display_start_time;
    private EditText et_traffic_cost_display_end_date;
    private EditText et_traffic_cost_display_end_time;
    private EditText et_traffic_cost_display_tag;
    /*private EditText et_traffic_cost_display_unit_price;
    private EditText et_traffic_cost_display_ticket_number;*/
    private EditText et_traffic_cost_display_total_amount;
    private EditText et_traffic_cost_display_desc;

    private ArrayList<String> imageUrls = new ArrayList<String>();
    private RecyclerView rv_tarffic_cost_display_invoice_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_cost_display);

        et_traffic_cost_display_type = (EditText) findViewById(R.id.et_traffic_cost_display_type);
        et_traffic_cost_display_fare_basis = (EditText) findViewById(R.id.et_traffic_cost_display_fare_basis);
        et_traffic_cost_display_start_city = (EditText) findViewById(R.id.et_traffic_cost_display_start_city);
        et_traffic_cost_display_end_city = (EditText) findViewById(R.id.et_traffic_cost_display_end_city);
        et_traffic_cost_display_start_date = (EditText) findViewById(R.id.et_traffic_cost_display_start_date);
        et_traffic_cost_display_start_time = (EditText) findViewById(R.id.et_traffic_cost_display_start_time);
        et_traffic_cost_display_end_date = (EditText) findViewById(R.id.et_traffic_cost_display_end_date);
        et_traffic_cost_display_end_time = (EditText) findViewById(R.id.et_traffic_cost_display_end_time);
        et_traffic_cost_display_tag = (EditText) findViewById(R.id.et_traffic_cost_display_tag);
        /*et_traffic_cost_display_unit_price = (EditText) findViewById(R.id.et_traffic_cost_display_unit_price);
        et_traffic_cost_display_ticket_number = (EditText) findViewById(R.id.et_traffic_cost_display_ticket_number);*/
        et_traffic_cost_display_total_amount = (EditText) findViewById(R.id.et_traffic_cost_display_total_amount);
        et_traffic_cost_display_desc = (EditText) findViewById(R.id.et_traffic_cost_display_desc);

        rv_tarffic_cost_display_invoice_url = (RecyclerView) findViewById(R.id.rv_tarffic_cost_display_invoice_url);





        Intent intent = getIntent();

        String TrafficCostJsonString = intent.getStringExtra("TrafficCostJsonString");
        // JSON串转用户组对象
        Traffic_Cost traffic_cost = JSON.parseObject(TrafficCostJsonString, Traffic_Cost.class);

        et_traffic_cost_display_type.setText(traffic_cost.getTraffic_cost_type());
        et_traffic_cost_display_fare_basis.setText(traffic_cost.getTraffic_cost_fare_basis());
        et_traffic_cost_display_start_city.setText(traffic_cost.getTraffic_cost_start_city());
        et_traffic_cost_display_end_city.setText(traffic_cost.getTraffic_cost_end_city());
        et_traffic_cost_display_start_date.setText(traffic_cost.getTraffic_cost_start_datetime().split(" ")[0]);
        et_traffic_cost_display_start_time.setText(traffic_cost.getTraffic_cost_start_datetime().split(" ")[1]);
        et_traffic_cost_display_end_date.setText(traffic_cost.getTraffic_cost_end_datetime().split(" ")[0]);
        et_traffic_cost_display_end_time.setText(traffic_cost.getTraffic_cost_end_datetime().split(" ")[1]);
        et_traffic_cost_display_tag.setText(traffic_cost.getTraffic_cost_tag());
        /*et_traffic_cost_display_unit_price.setText(traffic_cost.getTraffic_cost_unit_price());
        et_traffic_cost_display_ticket_number.setText(traffic_cost.getTraffic_cost_ticket_number());*/
        et_traffic_cost_display_total_amount.setText(traffic_cost.getTraffic_cost_total_amount());
        et_traffic_cost_display_desc.setText(traffic_cost.getTraffic_cost_desc());

        String traffic_cost_invoice_urls = traffic_cost.getTraffic_cost_invoice_pic_url();
        String[] split = traffic_cost_invoice_urls.split("\\|");
        for (String str : split){
            imageUrls.add(Constants.FAPIAO_URL_PREFIX + str);
            Log.e("=======从Listview中得到的uri", Constants.FAPIAO_URL_PREFIX + str);

        }

        rv_tarffic_cost_display_invoice_url.setAdapter(new RecyclerViewAdapter());
        rv_tarffic_cost_display_invoice_url.setLayoutManager(new GridLayoutManager(TrafficCostDisplayActivity.this, 3, GridLayoutManager.VERTICAL, false));



    }


    class RecyclerViewAdapter extends RecyclerView.Adapter<RVViewHolder>{


        @Override
        public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = View.inflate(TrafficCostDisplayActivity.this,R.layout.gridview_invoice_img,null);
            return new RVViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RVViewHolder holder, int position) {

            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, getResources().getDisplayMetrics());


            Glide.with(TrafficCostDisplayActivity.this)
                    .load(imageUrls.get(position))
                    .placeholder(R.mipmap.ic_launcher) //占位图
                    .error(R.mipmap.ic_launcher)  //出错的占位图
                    .override(width, height) //图片显示的分辨率 ，像素值 可以转化为DP再设置
                    .centerCrop()
                    .fitCenter()
                    .into(holder.img);
        }

        @Override
        public int getItemCount() {
            return imageUrls.size();
        }
    }
    class RVViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public RVViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_gridview_invoice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String invoice_pic_url = imageUrls.get(getLayoutPosition());
                    Log.e(TAG,"url="+invoice_pic_url);
                    Intent intent = new Intent(TrafficCostDisplayActivity.this,OnlyShowLargePicActivity.class);
                    intent.putExtra("invoice_pic_url",invoice_pic_url);
                    startActivity(intent);
                }
            });
        }
    }

}
