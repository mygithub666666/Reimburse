package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/8/23.
 */
public class ReimburseFormDisplayActivity extends Activity {

    private static final int REQUEST_SELECT_EXPENSE_CODE = 1000;
    private ListView listView;
    private EditText et_baoxiaoshiyou;
    private EditText et_baoxiaojine;
    private EditText et_baoxiaoproject;
    private EditText et_commitTime;
    private EditText et_endTime;
    private EditText check_status;
    private EditText comments;
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList  = new ArrayList<TongyiXiaofeiBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reimburseform_display);

        listView = (ListView) findViewById(R.id.listview_form_display);
        et_baoxiaoshiyou = (EditText) findViewById(R.id.et_baoxiaoshiyou_display);
        et_baoxiaojine = (EditText) findViewById(R.id.et_baoxiaojine_display);
        et_baoxiaoproject = (EditText) findViewById(R.id.et_baoxiaoproject_display);
        et_commitTime = (EditText) findViewById(R.id.baoxiao_commitTime_et);
        et_endTime = (EditText) findViewById(R.id.baoxiao_endTime_et);
        check_status = (EditText) findViewById(R.id.baoxiao_checkstatus_et);
        comments = (EditText) findViewById(R.id.baoxiao_comments_et);


        Intent intent = getIntent();


        /**
         *   Intent intent = new Intent(MyBaoxiaodanList.this,ReimburseFormDisplayActivity.class);
             intent.putExtra("baoxiaoshiyou",baoxiaoShiyou);
             intent.putExtra("project",project);
             intent.putExtra("jine",jine);
             intent.putExtra("xiaofejsonStr",xiaofejsonStr);
         intent.putExtra("status",bean.getCheck_status());
         intent.putExtra("comments",bean.getComments());
             startActivity(intent);
         */

        String baoxiaoshiyou = intent.getStringExtra("baoxiaoshiyou");
        String project = intent.getStringExtra("project");
        final String jine = intent.getStringExtra("jine");
        String xiaofejsonStr = intent.getStringExtra("xiaofejsonStr");
        Log.e("从上一步的报销列表传过来的消费数据",xiaofejsonStr);
        String commitTime = intent.getStringExtra("commitTime");
        String endTime = intent.getStringExtra("endTime");
        String status = intent.getStringExtra("status");
        String commentsStr = intent.getStringExtra("comments");

        et_baoxiaoshiyou.setText(baoxiaoshiyou);
        et_baoxiaoproject.setText(project);
        et_baoxiaojine.setText(jine);
        et_commitTime.setText(commitTime);
        et_endTime.setText(endTime);
        check_status.setText(status);
        comments.setText(commentsStr);

        tongyiXiaofeiBeanList = JSON.parseArray(xiaofejsonStr,TongyiXiaofeiBean.class);

        listView.setAdapter(new TongyiXiaofeiListAdapter(tongyiXiaofeiBeanList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showToastInAnyThread(position+"");
                TongyiXiaofeiBean bean = tongyiXiaofeiBeanList.get(position);
                String type  = bean.getXiaofei_type();
                String attrInfo = bean.getOtherInfo();
                String[] attrInfoArr = attrInfo.split("=");
                showToastInAnyThread(bean.getXiaofei_type());
                switch (type) {
                    case "采购费":
                        String caigoufei_wupinminchen = attrInfoArr[0];
                        String caigoufei_danwei = attrInfoArr[1];
                        String caigoufei_danjia = attrInfoArr[2];
                        String caigoufei_shuliang = attrInfoArr[3];
                        String caigoufei_jine = bean.getTotalMoney();
                        String caigoufei_riqi = bean.getRiqi();
                        String caigoufei_desc = attrInfoArr[4];
                        String fapiaoUrls = attrInfoArr[5];
                        //showToastInAnyThread(caigoufei_wupinminchen+caigoufei_danwei+caigoufei_danjia+caigoufei_shuliang+caigoufei_jine+caigoufei_riqi+caigoufei_desc+fapiaoUrls);
                        Intent intent = new Intent(ReimburseFormDisplayActivity.this,CaigoufeiDisplayActivity.class);
                        intent.putExtra("caigoufei_wupinminchen",caigoufei_wupinminchen);
                        intent.putExtra("caigoufei_danwei",caigoufei_danwei);
                        intent.putExtra("caigoufei_danjia",caigoufei_danjia);
                        intent.putExtra("caigoufei_shuliang",caigoufei_shuliang);
                        intent.putExtra("caigoufei_jine",caigoufei_jine);
                        intent.putExtra("caigoufei_riqi",caigoufei_riqi);
                        intent.putExtra("caigoufei_desc",caigoufei_desc);
                        intent.putExtra("fapiaoUrls",fapiaoUrls);
                        startActivity(intent);
                        break;
                    case "交通费":
                        Intent intent1 = new Intent(ReimburseFormDisplayActivity.this,JiaotongfeiDisplayActivity.class);
                        intent1.putExtra("chufadi",attrInfoArr[0]);
                        intent1.putExtra("mudidi",attrInfoArr[1]);
                        intent1.putExtra("chufariqi",bean.getRiqi());
                        intent1.putExtra("daodariqi",attrInfoArr[2]);
                        intent1.putExtra("jiaotonggongju",attrInfoArr[3]);
                        intent1.putExtra("piaojiaEt",bean.getTotalMoney());
                        intent1.putExtra("desc",attrInfoArr[4]);
                        intent1.putExtra("fapiaoUrls",attrInfoArr[5]);
                        startActivity(intent1);
                        break;
                    case "餐费":
                        Intent intent2 = new Intent(ReimburseFormDisplayActivity.this,CanfeiDisplayActivity.class);
                        intent2.putExtra("canfei_display_xiaofei_type",bean.getXiaofei_type());
                        intent2.putExtra("canfei_danwei",attrInfoArr[0]);
                        intent2.putExtra("canfei_danjia",attrInfoArr[2]);
                        intent2.putExtra("canfei_shuliang",attrInfoArr[1]);
                        intent2.putExtra("canfei_jine",bean.getTotalMoney());
                        intent2.putExtra(" canfei_riqi",bean.getRiqi());
                        intent2.putExtra("canfei_desc",attrInfoArr[3]);
                        intent2.putExtra("fapiaoUrls",attrInfoArr[4]);
                        startActivity(intent2);
                        break;
                }
            }
        });

    }



    class TongyiXiaofeiListAdapter extends BaseAdapter{

        List < TongyiXiaofeiBean > data;
        public TongyiXiaofeiListAdapter(List < TongyiXiaofeiBean > data){
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
                convertView = layoutInflater.inflate(R.layout.lv_item_caigou,null);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.lv_item_caigou_type);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.lv_item_caigou__date);
                viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.lv_item_caigou_count);
                viewHolder.tvOhterInfo = (TextView) convertView.findViewById(R.id.lv_item_caigou__date_picuris);
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


    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
