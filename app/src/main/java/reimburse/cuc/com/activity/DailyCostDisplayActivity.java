package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.DailyCost;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/1/1.
 */
public class DailyCostDisplayActivity extends Activity {

    private static final String TAG = DailyCostDisplayActivity.class.getSimpleName() ;
    private EditText et_daily_cost_type_display;
    private EditText et_daily_cost_display_tag;
    private EditText et_daily_cost__date_display;
    /*private EditText et_daily_cost_unit_price_display;
    private EditText et_daily_cost_ticket_number_display;*/
    private EditText et_daily_cost_total_amount_display;
    private EditText et_daily_cost_desc_display;
    private GridView gw_daily_cost_invoice_display;
    private List<String> picUrls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_cost_display);
        et_daily_cost_type_display = (EditText) findViewById(R.id.et_daily_cost_type_display);
        et_daily_cost_display_tag = (EditText) findViewById(R.id.et_daily_cost_display_tag);
        et_daily_cost__date_display = (EditText) findViewById(R.id.et_daily_cost__date_display);
      /*  et_daily_cost_unit_price_display = (EditText) findViewById(R.id.et_daily_cost_unit_price_display);
        et_daily_cost_ticket_number_display = (EditText) findViewById(R.id.et_daily_cost_ticket_number_display);*/
        et_daily_cost_total_amount_display = (EditText) findViewById(R.id.et_daily_cost_total_amount_display);
        et_daily_cost_desc_display = (EditText) findViewById(R.id.et_daily_cost_desc_display);
        gw_daily_cost_invoice_display = (GridView) findViewById(R.id.gw_daily_cost_invoice_display);

        picUrls = new ArrayList<>();

        //intent.putExtra("DailyCostJsonString",jsonString);

        Intent intent = getIntent();

        String DailyCostJsonString = intent.getStringExtra("DailyCostJsonString");
        // JSON串转用户组对象
        DailyCost cost = JSON.parseObject(DailyCostJsonString, DailyCost.class);

        et_daily_cost_type_display.setText(cost.getDaily_cost_type());
        et_daily_cost_display_tag.setText(cost.getDaily_cost_tag());
        et_daily_cost__date_display.setText(cost.getDaily_cost_date());
        /*et_daily_cost_unit_price_display.setText(cost.getDaily_cost_unit_price());
        et_daily_cost_ticket_number_display.setText(cost.getDaily_cost_ticket_number());*/
        et_daily_cost_total_amount_display.setText(cost.getDaily_cost_total_amount());
        et_daily_cost_desc_display.setText(cost.getDaily_cost_desc());

        String[] picUrlArr = cost.getDaily_cost_invoice_pic_urls().split("\\|");
        for (String url : picUrlArr){
            Log.e(TAG,Constants.FAPIAO_URL_PREFIX+url);
            picUrls.add(Constants.FAPIAO_URL_PREFIX+url);
        }

        gw_daily_cost_invoice_display.setAdapter(new DailyCostDisplayAdapter());
        gw_daily_cost_invoice_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String invoice_pic_url = picUrls.get(position);
                Log.e(TAG,"日常消息的发票点击事件， url="+invoice_pic_url);
                Intent intent = new Intent(DailyCostDisplayActivity.this,OnlyShowLargePicActivity.class);
                intent.putExtra("invoice_pic_url",invoice_pic_url);
                startActivity(intent);
            }
        });

    }

    class DailyCostDisplayAdapter extends BaseAdapter {


        DailyCostDisplayAdapter(){

        }

        @Override
        public int getCount() {
                return picUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return picUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.e(TAG,"getView方法开始执行");
            GridViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater lf = (LayoutInflater) DailyCostDisplayActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = lf.inflate(R.layout.gv_item_daily_cost_display, null);
                holder = new GridViewHolder();
                holder.ivimage = (ImageView) convertView.findViewById(R.id.img_gridview_item_daily_cost_display);
                convertView.setTag(holder);
            } else {
                holder = (GridViewHolder) convertView.getTag();
            }

            /**代表+号之前有图片，需要显示图片**/
            Log.e(TAG,"getView: position="+position+", "+"picUrls.size(): "+picUrls.size());
            final String picUrl = picUrls.get(position);
            //File file = new File(picPath);
            /*Glide.with(DailyCostDisplayActivity.this)
                    .load(file)
                    .priority(Priority.HIGH)
                    .into(holder.ivimage);*/

            Glide.with(DailyCostDisplayActivity.this)
                    .load(picUrl)
                    .placeholder(R.mipmap.ic_launcher) //占位图
                    .error(R.mipmap.ic_launcher)  //出错的占位图
                    .centerCrop()
                    .fitCenter()
                    .into(holder.ivimage);

            return convertView;
        }
    }

    class GridViewHolder{

        ImageView ivimage;
    }
}
