package reimburse.cuc.com.reimburse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
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

import android.graphics.Bitmap;
import android.widget.GridView;
import android.widget.Toast;

import com.yongchun.library.view.ImageSelectorActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reimburse.cuc.com.adaptor.GlideRecyclerViewAdaptor;
import reimburse.cuc.com.adaptor.MyCaigouGridViewAdapter;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.util.BitmapUtils;
import reimburse.cuc.com.util.DividerListItemDecoration;
import reimburse.cuc.com.util.StreamTools;
import reimburse.cuc.com.util.UIUtils;

/**
 * Created by hp1 on 2017/9/21.
 */
public class MyCaigouActivity extends Activity {
    private EditText yuanyin;
    private EditText name;
    private EditText danwei;
    private EditText count;
    private EditText danjia;
    private EditText jine;
    private ImageView addFaPiao;
    private GridView gridView;
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<String> images = new ArrayList<String>();
    private EditText jzcg_riqi_et;
    private Button saveBtn;
    private StringBuilder fapiaoUrls= new StringBuilder();
    final String url = "http://1.202.37.157:8080/ssmweb/baoXiaoDan/saveBaoXiaoDan";
    final String saveCaigouUrl = "http://1.202.37.157:8080/ssmweb/caigou/saveCaigou";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycaigou);
        jzcg_riqi_et = (EditText) findViewById(R.id.jzcg_riqi_et);
        defaultDate(jzcg_riqi_et);
        gridView = (GridView) findViewById(R.id.mycaigou_gridview_fapiao);
        addFaPiao = (ImageView) findViewById(R.id.jzcg_fp_iv);
        saveBtn = (Button) findViewById(R.id.mycaigou_save_zhangdan);
        yuanyin = (EditText) findViewById(R.id.jzcg_shiyou_et);
        name = (EditText) findViewById(R.id.jzcg_mingchen_et);
        danwei = (EditText) findViewById(R.id.jzcg_danwei_et);
        count = (EditText) findViewById(R.id.jzcg_shuliang_et);
        danjia = (EditText) findViewById(R.id.jzcg_danjia_et);
        jine = (EditText) findViewById(R.id.jzcg_jine_et);
        addFaPiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MyCaigouActivity.this, SelectImgActivity.class));
                ImageSelectorActivity.start(MyCaigouActivity.this, 6, ImageSelectorActivity.MODE_MULTIPLE, true, true, true);
            }
        });
        jzcg_riqi_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

    }

/*11111111111111111111111111111111===处理选择图片返回的方法开始===11111111111111111111111111111111111111*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
            //images
            ArrayList<String> uris = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            images.addAll(uris);
            for (final String pic:images){
                Toast.makeText(MyCaigouActivity.this,pic,Toast.LENGTH_LONG).show();
                final File file = new File(pic);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBody filebody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                        OkHttpClient client = new OkHttpClient();
                        MultipartBody requestBody = new MultipartBody.Builder()
                                .addFormDataPart("name--", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new Random().nextInt(10)+".png", filebody)
                                .build();
                        Request request = new Request.Builder().post(requestBody).url(Constants.SAVE_PICURLS).build();
                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                            String picUrl = response.body().string();
                            Log.e("---上传图片返回的结果---", picUrl);
                            fapiaoUrls.append(picUrl + "|");
                            Log.e("所有的发票地址", fapiaoUrls.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
            for (String str : images){
                Log.e("添加发票事件选择的图片uri", str);
                Bitmap bitmap = BitmapFactory.decodeFile(str);
                bitmaps.add(bitmap);
            }

            gridView.setAdapter(new MyCaigouActivityGridViewAdapter());
        }


    }

/*11111111111111111111111111111111===处理选择图片返回的方法结束===11111111111111111111111111111111111111*/


/*22222222222222222222222222222222===日期显示处理开始===2222222222222222222222222222222222222222222222*/
    public void defaultDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        jzcg_riqi_et.setText(year + "-" + (month + 1) + "-" + day);
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
            jzcg_riqi_et.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }
    }
/*22222222222222222222222222222222===日期显示处理结束===2222222222222222222222222222222222222222222222*/


/*3333333333333333333333333333333===适配器开始===333333333333333333333333333333333333333333333333333333333*/

     class MyCaigouActivityGridViewAdapter extends BaseAdapter{


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
            viewHolder.imageView.setImageBitmap(BitmapUtils.zoom(bitmaps.get(position),80,80));
            return convertView;
        }

    }
    static class ViewHolder{
        ImageView imageView;
    }

/*3333333333333333333333333333333===适配器结束===333333333333333333333333333333333333333333333333333333333*/



/*4444444444444444444444444444444444===提交表单开始===444444444444444444444444444444444444444444444444444444*/

   public void SaveCaigou(View view){
      Log.e("得到输入框的值", yuanyin.getText().toString() + name.getText().toString() + danwei.getText().toString() + fapiaoUrls.toString());
      final String caigou =  yuanyin.getText().toString() +"="+name.getText().toString() +"="+
               danwei.getText().toString() +"="+count.getText().toString()
              +"="+ danjia.getText().toString()
              +"="+jine.getText().toString()+"="+jzcg_riqi_et.getText().toString()+"="+fapiaoUrls.toString();
       new Thread() {
           public void run() {
               try {
                   // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                   String path = Constants.SAVE_CAIGOU;
                   URL url = new URL(path);
                   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                   //2.指定请求方式为post
                   conn.setRequestMethod("POST");
                   //3.设置http协议的请求头
                   conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型
                   //username=abc&password=123
                   String data = "caigou_yuanyin="+ URLEncoder.encode(caigou,"utf-8");/*+
                           "&caigou_name="+URLEncoder.encode( name.getText().toString(),"utf-8")
                           +"&caigou_danwei="+URLEncoder.encode(danwei.getText().toString(),"utf-8")
                           +"&caigou_count="+URLEncoder.encode(count.getText().toString(),"utf-8")
                           +"&caigou_danjia="+URLEncoder.encode(danjia.getText().toString(),"utf-8")
                           +"&caigou_jine="+URLEncoder.encode(jine.getText().toString(),"utf-8")
                           +"&caigou_riqi="+URLEncoder.encode(riqi.getText().toString(),"utf-8");*/

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
                          /* Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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

/*4444444444444444444444444444444444===提交表单结束===444444444444444444444444444444444444444444444444444444*/






    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }



}
