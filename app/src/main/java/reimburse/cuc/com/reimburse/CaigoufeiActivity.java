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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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
import reimburse.cuc.com.adaptor.ExpenseSubmitAdapter;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.util.BitmapUtils;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/10/14.
 */
public class CaigoufeiActivity extends Activity{

    private EditText  caigoufei_wupinminchen_et;
    private EditText  caigoufei_danwei_et;
    private EditText caigoufei_danjia_et;
    private EditText caigoufei_shuliang_et;
    private EditText caigoufei_jine_et;
    private EditText caigoufei_riqi_et;
    private EditText caigoufei_desc_et;
    private GridView  gridView;
    private ImageView caigoufei_fp_iv;
    private Button saveBtn;
    private Button btn_download_test;
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<String> imageUrls = new ArrayList<String>();
    private StringBuilder fapiaoUrls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caigoufei);
        caigoufei_wupinminchen_et = (EditText) findViewById(R.id.caigoufei_wupinminchen_et);
        caigoufei_danwei_et = (EditText) findViewById(R.id.caigoufei_danwei_et);
        caigoufei_danjia_et  = (EditText) findViewById(R.id.caigoufei_danjia_et);
        caigoufei_shuliang_et = (EditText) findViewById(R.id.caigoufei_shuliang_et);
        caigoufei_jine_et = (EditText) findViewById(R.id.caigoufei_jine_et);
        caigoufei_riqi_et = (EditText) findViewById(R.id.caigoufei_riqi_et);
        caigoufei_desc_et = (EditText) findViewById(R.id.caigoufei_desc_et);
        gridView = (GridView) findViewById(R.id.caigoufei_gridview_fapiao);
        caigoufei_fp_iv = (ImageView) findViewById(R.id.caigoufei_fp_iv);
        saveBtn = (Button) findViewById(R.id.caigoufei_save_btn);
        btn_download_test = (Button) findViewById(R.id.btn_download_test);

        defaultDate(caigoufei_riqi_et);
        caigoufei_riqi_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        caigoufei_fp_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorActivity.start(CaigoufeiActivity.this, 6, ImageSelectorActivity.MODE_MULTIPLE, true, true, true);
            }
        });


        btn_download_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*String xiaofei_type,
                String wupin_name, String danwei, String shuliang, String danjia,
                        String riqi, String totalMoney, String huafei_desc,
                        String fapiaourls, Integer user_id*/
                final String xiaofei_type = "采购费";
                final String wupin_name = caigoufei_wupinminchen_et.getText().toString();
                final String  danwei = caigoufei_danwei_et.getText().toString();
                final String shuliang = caigoufei_shuliang_et.getText().toString();
                final String danjia = caigoufei_danjia_et.getText().toString();
                final String riqi  = caigoufei_riqi_et.getText().toString();
                final String totalMoney = caigoufei_jine_et.getText().toString();
                final String huafei_desc = caigoufei_desc_et.getText().toString();
                final Integer user_id = LauncherActivity.ANDROID_USER_ID;
                fapiaoUrls= new StringBuilder();
                for(int i=0;i<imageUrls.size();i++){
                    if(i == imageUrls.size()-1){
                                Log.e("第"+i+"个图片",imageUrls.get(i));
                                fapiaoUrls.append(imageUrls.get(i));
                    }else{
                                Log.e("第"+i+"个图片",imageUrls.get(i));
                                fapiaoUrls.append(imageUrls.get(i) + "|");
                    }
                }
                final String fapiaourls = fapiaoUrls.toString();
                showToastInAnyThread(xiaofei_type+":"+
                        wupin_name+":"+
                        danwei+":"+
                        shuliang+":"+
                        danjia+":"+
                        riqi+":"+
                        totalMoney+":"+
                        huafei_desc+":"+
                        user_id+":"+fapiaoUrls.toString());
               /* Log.e("得到输入框的值", yuanyin.getText().toString() + name.getText().toString() + danwei.getText().toString() + fapiaoUrls.toString());
                final String caigou =  yuanyin.getText().toString() +"="+name.getText().toString() +"="+
                        danwei.getText().toString() +"="+count.getText().toString()
                        +"="+ danjia.getText().toString()
                        +"="+jine.getText().toString()+"="+jzcg_riqi_et.getText().toString()+"="+fapiaoUrls.toString();*/
                new Thread() {
                    public void run() {
                        try {
                            // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                            String path = Constants.SAVE_CAIGOUFEI_URL;
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            //2.指定请求方式为post
                            conn.setRequestMethod("POST");
                            //3.设置http协议的请求头
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型
                            //username=abc&password=123
                            String data = "xiaofei_type="+ URLEncoder.encode(xiaofei_type, "utf-8")
                           +"&wupin_name="+URLEncoder.encode(wupin_name,"utf-8")
                           +"&danwei="+URLEncoder.encode(danwei,"utf-8")
                           +"&shuliang="+URLEncoder.encode(shuliang,"utf-8")
                           +"&danjia="+URLEncoder.encode(danjia,"utf-8")
                           +"&riqi="+URLEncoder.encode(riqi,"utf-8")
                           +"&totalMoney="+URLEncoder.encode(totalMoney,"utf-8")
                           +"&huafei_desc="+URLEncoder.encode(huafei_desc,"utf-8")
                           +"&user_id="+URLEncoder.encode(String.valueOf(user_id),"utf-8")
                           +"&fapiaourls="+URLEncoder.encode(fapiaoUrls.toString(),"utf-8");

                            conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                            //post的请求是把数据以流的方式写给了服务器
                            //指定请求的输出模式
                            conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                            conn.getOutputStream().write(data.getBytes());
                            Log.e("post size",String.valueOf(data.getBytes().length));
                            int code = conn.getResponseCode();
                            if(code == 200){
                                InputStream is = conn.getInputStream();
                                final String result = StreamTools.readStream(is);
                                showToastInAnyThread(result);
                                if(result.equals("保存成功")) {
                                    finish();
                                    /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);*/
                                }
                            }else{
                                showToastInAnyThread("请求失败");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            showToastInAnyThread("请求失败");
                        }

                    };
                }.start();

            }
        });

        /**
         * 点击图片进行放大操作
         */
        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToastInAnyThread(position+" : "+view.getClass().toString());
                Bitmap bitmap = bitmaps.get(position);
                showToastInAnyThread(bitmap.getWidth()+" : "+bitmap.getHeight());
                *//*Matrix matrix = new Matrix();
                matrix.setScale(2,2);
                Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth() * 3, bitmap.getHeight() * 3, bitmap.getConfig());
                Canvas canvas  = new Canvas(newBitmap);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                canvas.drawBitmap(bitmap,matrix,paint);*//*
                //.setImageBitmap(newBitmap);

            }
        });*/

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
            //images
            ArrayList<String> uris = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            images.addAll(uris);
            for (final String pic:images){
                Toast.makeText(CaigoufeiActivity.this, pic, Toast.LENGTH_LONG).show();
                final File file = new File(pic);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBody filebody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                        OkHttpClient client = new OkHttpClient();
                        MultipartBody requestBody = new MultipartBody.Builder()
                                .addFormDataPart("fapiaoImg", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new Random().nextInt(10)+".png", filebody)
                                .build();
                        Request request = new Request.Builder().post(requestBody).url(Constants.SAVE_FAPIAO_IMG_URL).build();
                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                            String picUrl = response.body().string();
                            showToastInAnyThread(picUrl);
                            Log.e("---上传图片返回的结果---", picUrl);
                            imageUrls.add(picUrl);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
            //Log.e("所有的发票地址", fapiaoUrls.toString());
            for (String str : images){
                Log.e("添加发票事件选择的图片uri", str);
                Bitmap bitmap = BitmapFactory.decodeFile(str);
                bitmaps.add(bitmap);
            }

            gridView.setAdapter(new MyCaigouActivityGridViewAdapter());
        }


    }

    /*3333333333333333333333333333333===适配器开始===333333333333333333333333333333333333333333333333333333333*/

    class MyCaigouActivityGridViewAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return bitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return bitmaps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
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
            viewHolder.imageView.setImageBitmap(BitmapUtils.zoom(bitmaps.get(position), 80, 80));
            return convertView;
        }

    }
    static class ViewHolder{
        ImageView imageView;
    }

/*3333333333333333333333333333333===适配器结束===333333333333333333333333333333333333333333333333333333333*/


/*6666666666666666666666666666666点击图片放大操作开始666666666666666666666666666666666666666666666666666666666*/

    public void toBig(ImageView imageView){

    }


/*6666666666666666666666666666666点击图片放大操作结束666666666666666666666666666666666666666666666666666666666*/


/*22222222222222222222222222222222===日期显示处理开始===2222222222222222222222222222222222222222222222*/
    public void defaultDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        caigoufei_riqi_et.setText(year + "-" + (month + 1) + "-" + day);
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
            caigoufei_riqi_et.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
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
