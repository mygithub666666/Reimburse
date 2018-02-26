package reimburse.cuc.com.reimburse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.util.BitmapUtils;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/10/14.
 */
public class CaigoufeiDisplayActivity extends Activity{

    private EditText  caigoufei_display_wupinminchen_et;
    private EditText  caigoufei_display_danwei_et;
    private EditText caigoufei_display_danjia_et;
    private EditText caigoufei_display_shuliang_et;
    private EditText caigoufei_display_jine_et;
    private EditText caigoufei_display_riqi_et;
    private EditText caigoufei_display_desc_et;
    private GridView  gridView;
    private RecyclerView recyclerView;
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<String> imageUrls = new ArrayList<String>();
    private StringBuilder fapiaoUrls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caigoufei_display);
        caigoufei_display_wupinminchen_et = (EditText) findViewById(R.id.caigoufei_display_wupinminchen_et);
        caigoufei_display_danwei_et = (EditText) findViewById(R.id.caigoufei_display_danwei_et);
        caigoufei_display_danjia_et  = (EditText) findViewById(R.id.caigoufei_display_danjia_et);
        caigoufei_display_shuliang_et = (EditText) findViewById(R.id.caigoufei_display_shuliang_et);
        caigoufei_display_jine_et = (EditText) findViewById(R.id.caigoufei_display_jine_et);
        caigoufei_display_riqi_et = (EditText) findViewById(R.id.caigoufei_display_riqi_et);
        caigoufei_display_desc_et = (EditText) findViewById(R.id.caigoufei_display_desc_et);
        recyclerView = (RecyclerView) findViewById(R.id.caigoufei_display_recyclerview_fapiao);
        defaultDate(caigoufei_display_riqi_et);
        caigoufei_display_riqi_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        Intent intent = getIntent();

        String caigoufei_wupinminchen = intent.getStringExtra("caigoufei_wupinminchen");
        String caigoufei_danwei = intent.getStringExtra("caigoufei_danwei");
        String caigoufei_danjia = intent.getStringExtra("caigoufei_danjia");
        String caigoufei_shuliang = intent.getStringExtra("caigoufei_shuliang");
        String caigoufei_jine = intent.getStringExtra("caigoufei_jine");
        String caigoufei_riqi = intent.getStringExtra("caigoufei_riqi");
        String caigoufei_desc = intent.getStringExtra("caigoufei_desc");
        String fapiaoUrls = intent.getStringExtra("fapiaoUrls");


        Log.e("----汇总后的URI-------",fapiaoUrls);
        String[] split = fapiaoUrls.split("\\|");
        for (String str : split){
            imageUrls.add(Constants.FAPIAO_URL_PREFIX + str);
            Log.e("=======从Listview中得到的uri", Constants.FAPIAO_URL_PREFIX + str);

        }

       for (String str : images){
            Log.e("添加发票事件选择的图片uri", str);
       }


        caigoufei_display_wupinminchen_et.setText(caigoufei_wupinminchen);
        caigoufei_display_danwei_et.setText(caigoufei_danwei);
        caigoufei_display_danjia_et.setText(caigoufei_danjia);
        caigoufei_display_shuliang_et.setText(caigoufei_shuliang);
        caigoufei_display_jine_et.setText(caigoufei_jine);
        caigoufei_display_riqi_et.setText(caigoufei_riqi);
        caigoufei_display_desc_et.setText(caigoufei_desc);

        recyclerView.setAdapter(new RecyclerViewAdapter());
        recyclerView.setLayoutManager(new GridLayoutManager(CaigoufeiDisplayActivity.this, 3, GridLayoutManager.VERTICAL, false));


    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RVViewHolder>{


        @Override
        public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = View.inflate(CaigoufeiDisplayActivity.this,R.layout.gridview_fapiao_img,null);
            return new RVViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RVViewHolder holder, int position) {

            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, getResources().getDisplayMetrics());

            Glide.with(CaigoufeiDisplayActivity.this)
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
    static class RVViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public RVViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.gridview_fapiao_iv);
        }
    }

    /*3333333333333333333333333333333===适配器开始===333333333333333333333333333333333333333333333333333333333*/

    class MyCaigouActivityGridViewAdapter extends BaseAdapter {

        public MyCaigouActivityGridViewAdapter() {

        }


        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return imageUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.gridview_fapiao_img,null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.gridview_fapiao_iv);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //viewHolder.imageView.setImageURI("");
            return convertView;
        }

    }
    static class ViewHolder{
        ImageView imageView;
    }

/*3333333333333333333333333333333===适配器结束===333333333333333333333333333333333333333333333333333333333*/


/*22222222222222222222222222222222===日期显示处理开始===2222222222222222222222222222222222222222222222*/
    public void defaultDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        caigoufei_display_riqi_et.setText(year + "-" + (month + 1) + "-" + day);
    }
    public void setDate(View view){
        SetDateDialog sdd = new SetDateDialog();
        sdd.show(getFragmentManager(), "datepicker");
    }
    //创建日期选择对话框
    @SuppressLint("ValidFragment")
    class SetDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
            return  dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            caigoufei_display_riqi_et.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }
    }
/*22222222222222222222222222222222===日期显示处理结束===2222222222222222222222222222222222222222222222*/



    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }




}
