package reimburse.cuc.com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
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
import reimburse.cuc.com.bean.DailyCost;
import reimburse.cuc.com.bean.ExpenseCategory;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.DensityUtil;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/12/27.
 */
public class DailyCostActivity extends Activity {


    private static final int PICTURE = 01;
    private static final int CAMERA = 02;
    private static final int PHOTOVIEW = 03;
    private static final int PHOTOVIEW_RESULT = 04;
    private static final int SHOW_ALL_EXPENSE_CATEGORY = 05;
    private static final String TAG = DailyCostActivity.class.getSimpleName();

    private List<ExpenseCategory> expenseCategoryList;
    
    private EditText et_daily_cost_type;

    private EditText et_daily_cost__date;
    private EditText et_daily_cost_tag;
    /*rivate EditText et_daily_cost_unit_price;
    private EditText et_daily_cost_ticket_number;*/
    private EditText et_daily_cost_total_amount;
    private EditText et_daily_cost_desc;

    private PopupWindow daily_cost_type_popupWindow;
    private ListView daily_cost_type_popupWindow_listview;


    private GridView gw_daily_cost_invoice;
    private Button btn_save_daily_cost;
    private List<String> picUrls;
    DailyCostAddOrDelGridViewAdapter dailyCostAddOrDelGridViewAdapter;
    
    
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_EXPENSE_CATEGORY) {
                daily_cost_type_popupWindow_listview.setAdapter(new DailyCostTypePopwindowListViewAdapter());
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_cost);

        et_daily_cost_type = (EditText) findViewById(R.id.et_daily_cost_type);

        et_daily_cost_tag = (EditText) findViewById(R.id.et_daily_cost_tag);

        et_daily_cost__date = (EditText) findViewById(R.id.et_daily_cost__date);
        /*et_daily_cost_unit_price = (EditText) findViewById(R.id.et_daily_cost_unit_price);
        et_daily_cost_ticket_number  = (EditText) findViewById(R.id.et_daily_cost_ticket_number)*/;
        et_daily_cost_total_amount = (EditText) findViewById(R.id.et_daily_cost_total_amount);
        et_daily_cost_desc = (EditText) findViewById(R.id.et_daily_cost_desc);

        btn_save_daily_cost = (Button) findViewById(R.id.btn_save_daily_cost);

        gw_daily_cost_invoice = (GridView) findViewById(R.id.gw_daily_cost_invoice);


        expenseCategoryList = new ArrayList<>();

        picUrls = new ArrayList<>();
        dailyCostAddOrDelGridViewAdapter = new DailyCostAddOrDelGridViewAdapter(7);
        daily_cost_type_popupWindow_listview = new ListView(DailyCostActivity.this);
        daily_cost_type_popupWindow_listview.setBackgroundResource(R.drawable.listview_background);

        defaultDate(et_daily_cost__date);

        et_daily_cost__date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(et_daily_cost__date);
            }
        });


        /**
         * 设置总金额
         *//*
        et_daily_cost_ticket_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {

                    String daily_cost_unit_price = et_daily_cost_unit_price.getText().toString();
                    String daily_cost_ticket_number = et_daily_cost_ticket_number.getText().toString();

                    if (daily_cost_ticket_number != null && daily_cost_unit_price != null) {

                        BigDecimal unit_price = new BigDecimal(daily_cost_unit_price);
                        BigDecimal ticket_number = new BigDecimal(daily_cost_ticket_number);
                        BigDecimal total_amount = unit_price.multiply(ticket_number);
                        et_daily_cost_total_amount.setText(total_amount.toString());
                    }

                }
            }
        });*/



        getAllDailyExpenseCategoryList();


        et_daily_cost_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (daily_cost_type_popupWindow == null) {
                    daily_cost_type_popupWindow = new PopupWindow(DailyCostActivity.this);
                    daily_cost_type_popupWindow.setWidth(et_daily_cost_type.getWidth());
                    int height = DensityUtil.dip2px(DailyCostActivity.this, 200);//dp->px
                    //Toast.makeText(TrafficCostFormActivity.this, "height==" + height, Toast.LENGTH_SHORT).show();
                    daily_cost_type_popupWindow.setHeight(height);//px

                    daily_cost_type_popupWindow.setContentView(daily_cost_type_popupWindow_listview);
                    daily_cost_type_popupWindow.setFocusable(true);//设置焦点
                }

                daily_cost_type_popupWindow.showAsDropDown(et_daily_cost_type, 0, 0);
            }
        });

        daily_cost_type_popupWindow_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseCategory ec = expenseCategoryList.get(position);
                //1.得到数据
                String msg = ec.getEc_name();
                //2.设置输入框
                et_daily_cost_type.setText(msg);

                if (daily_cost_type_popupWindow != null && daily_cost_type_popupWindow.isShowing()) {

                    daily_cost_type_popupWindow.dismiss();
                    daily_cost_type_popupWindow = null;
                }
            }
        });

        /**
         * GridView的相关设置
         */
        //设置水平横向滑动的参数
        int size = 6;
        int length = 80;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = 110;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gw_daily_cost_invoice.setLayoutParams(params); //设置GirdView布局参数,横向布局的关键
        gw_daily_cost_invoice.setColumnWidth(itemWidth);
        gw_daily_cost_invoice.setHorizontalSpacing(15);
        gw_daily_cost_invoice.setStretchMode(GridView.NO_STRETCH);
        gw_daily_cost_invoice.setNumColumns(size);

        gw_daily_cost_invoice.setAdapter(dailyCostAddOrDelGridViewAdapter);
        /**
         * 设置图片选择的点击事件
         */

        gw_daily_cost_invoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int top_rr_id = R.id.relativelayout_gridview_item_top;
                int rr_id = R.id.relativelayout_gridview_item;
                Log.e("rr_id: ", rr_id + ",btn_del id= " + R.id.btn_del_gridview_item + ",top_id=" + top_rr_id);
                Log.e(TAG, "viewId= " + view.getId());
                Button button = (Button) view.findViewById(R.id.btn_del_gridview_item);

                Boolean isVisible = button.getVisibility() == View.VISIBLE;
                Log.e("button isVisible : ", isVisible + ",btn id=" + button.getId());

                if (isVisible) {

                    String picPath = picUrls.get(position);
                    Intent intent = new Intent(DailyCostActivity.this, OnlyShowLocalLargePicActivity.class);
                    intent.putExtra("invoice_pic_url", picPath);
                    startActivity(intent);


                } else {
                    final String[] items = new String[]{"图库", "相机"};
                    new AlertDialog.Builder(DailyCostActivity.this).setTitle("选择来源")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            //UIUtils.toast(items[0], false);
                                            //启动其他应用的activity:使用隐式意图
                                            Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            startActivityForResult(picture, PICTURE);
                                            break;
                                        case 1:
                                            //UIUtils.toast(items[1],false);
                                            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(camera, CAMERA);
                                            break;
                                    }
                                }
                            }).setNegativeButton("取消", null).show();
                }


            }
        });

        /**
         * 向服务端保存日常报销数据
         */
        btn_save_daily_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<picUrls.size();i++){
                    Log.e("第"+i+"个图片的路径: ",picUrls.get(i));
                }


                uploadDailyCost("测试成功了");
            }
        });


    }



    public void uploadDailyCost(final String content){

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient mOkHttpClient = new OkHttpClient();

                /**
                 *   private Integer daily_cost_uuid; private String daily_cost_type;
                     private String daily_cost_date; private String daily_cost_unit_price;
                     private String daily_cost_ticket_number; private String daily_cost_total_amount;
                     private String daily_cost_desc;
                     private String daily_cost_invoice_pic_urls;
                     private Integer daily_cost_user_id;
                 */

                String daily_cost_type = et_daily_cost_type.getText().toString();
                String daily_cost_date = et_daily_cost__date.getText().toString();
                /*String daily_cost_unit_price = et_daily_cost_unit_price.getText().toString();
                String daily_cost_ticket_number = et_daily_cost_ticket_number.getText().toString();*/
                String daily_cost_total_amount = et_daily_cost_total_amount.getText().toString();

                StringBuffer daily_cost_invoice_pic_urls_StringBufffer = new StringBuffer();

                String daily_cost_desc = et_daily_cost_desc.getText().toString();





                //String daily_cost_invoice_pic_urls = .getText().toString();
                String path1 = "/storage/emulated/0/Tencent/QQfile_recv/2017-12-27_204922.png";
                String path2 = "/storage/emulated/0/截屏/截屏_20171222_104105.jpg";
                MultipartBody.Builder builder = new MultipartBody.Builder();


                // 这里演示添加用户ID
                /*builder.addFormDataPart("daily_cost_type", daily_cost_type);
                builder.addFormDataPart("daily_cost_date", daily_cost_date);
                builder.addFormDataPart("daily_cost_unit_price",daily_cost_unit_price);
                builder.addFormDataPart("daily_cost_ticket_number",daily_cost_ticket_number);
                builder.addFormDataPart("daily_cost_total_amount",daily_cost_total_amount);

                builder.addFormDataPart("daily_cost_user_id",android_user_id+"");*/
                Integer android_user_id = LauncherActivity.ANDROID_USER_ID;


                for (int i=0;i<picUrls.size();i++){
                    if(i == picUrls.size()-1) {
                        String pic_save_name_last = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new Random().nextInt(10)+".png";
                        Log.e(TAG,"发票url集合中第"+i+"个元素,pic_save_name_last = "+pic_save_name_last);
                        builder.addFormDataPart("image_1", pic_save_name_last,
                                RequestBody.create(MediaType.parse("image/jpeg"), new File(picUrls.get(i))));
                        daily_cost_invoice_pic_urls_StringBufffer.append(pic_save_name_last);
                    }else{

                        String pic_save_name_before_last = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new Random().nextInt(10)+".png";
                        Log.e(TAG,"发票url集合中第"+i+"个元素,pic_save_name_last = "+pic_save_name_before_last);
                        builder.addFormDataPart("image_1", pic_save_name_before_last,
                                RequestBody.create(MediaType.parse("image/jpeg"), new File(picUrls.get(i))));
                        daily_cost_invoice_pic_urls_StringBufffer.append(pic_save_name_before_last+"|");
                    }
                }

                String daily_cost_invoice_pic_urls = daily_cost_invoice_pic_urls_StringBufffer.toString();

                Log.e(TAG, "daily_cost_invoice_pic_urls=" + daily_cost_invoice_pic_urls);
                //builder.addFormDataPart("daily_cost_invoice_pic_urls", daily_cost_invoice_pic_urls);

                DailyCost dailyCost = new DailyCost(daily_cost_type,daily_cost_date,"单价",
                        "票据张数",daily_cost_total_amount,daily_cost_desc,
                        daily_cost_invoice_pic_urls,android_user_id,et_daily_cost_tag.getText().toString());
                // Java对象转JSON串
                String dailyCost_jsonString = JSON.toJSONString(dailyCost);
                builder.addFormDataPart("dailyCost_jsonString", dailyCost_jsonString);
                Log.e("日常消费数据: ",dailyCost_jsonString);

                /*builder.addFormDataPart("image_1",new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new Random().nextInt(10)+".png",
                        RequestBody.create(MediaType.parse("image/jpeg"), new File(path1)));
                builder.addFormDataPart("image_2",new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new Random().nextInt(10)+".png",
                        RequestBody.create(MediaType.parse("image/jpeg"), new File(path2)));*/

                RequestBody requestBody = builder.build();
                Request.Builder reqBuilder = new Request.Builder();
                Request request = reqBuilder
                        .url(Constants.OKHTTP_TEST_UPLOAD)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                    String resultInfo = response.body().string();
                    Log.e(TAG, "上传日常费用返回的结果: " + resultInfo);
                    if(resultInfo.equals("保存成功")) {
                        showToastInAnyThread(resultInfo);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    class DailyCostAddOrDelGridViewAdapter extends BaseAdapter{

        private Integer maxInvoicePicNum;

        DailyCostAddOrDelGridViewAdapter(){

        }
        DailyCostAddOrDelGridViewAdapter(Integer maxInvoicePicNum){
            this.maxInvoicePicNum = maxInvoicePicNum;
        }



        /**
         * 让GridView中的数据数目加1最后一个显示+号
         * 当到达最大张数时不再显示+号
         * @return 返回GridView中的数量
         */
        @Override
        public int getCount() {
            Log.e(TAG,"Adapter: getCount");
            int count = (picUrls == null ? 1:picUrls.size()+1);
            if(count >= maxInvoicePicNum) {
                return picUrls.size();
            }else {

                return count;
            }
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
                LayoutInflater lf = (LayoutInflater) DailyCostActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = lf.inflate(R.layout.gv_item_img, null);
                holder = new GridViewHolder();
                holder.ivimage = (ImageView) convertView.findViewById(R.id.img_gridview_item);
                holder.btdel = (Button) convertView.findViewById(R.id.btn_del_gridview_item);
                convertView.setTag(holder);
            } else {
                holder = (GridViewHolder) convertView.getTag();
            }

            /**代表+号之前有图片，需要显示图片**/
            if(picUrls != null && position < picUrls.size()) {
                Log.e(TAG,"getView: position="+position+", "+"picUrls.size(): "+picUrls.size());
                final String picPath = picUrls.get(position);
                File file = new File(picPath);
                Glide.with(DailyCostActivity.this)
                        .load(file)
                        .priority(Priority.HIGH)
                        .into(holder.ivimage);
                /**
                 * 设置删除图标可见，并设置点击的删除事件
                 *
                 */
                holder.btdel.setVisibility(View.VISIBLE);
                holder.btdel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picUrls.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }else {
                /**
                 * 还没有选择图片，图片为空，只需要显示+号图片
                 *
                 */
                Log.e(TAG,"getView: position="+position+", "+"picUrls.size(): "+picUrls.size());
                Glide.with(DailyCostActivity.this)
                        .load(R.drawable.icon_addpic_unfocused)
                        .priority(Priority.HIGH)
                        .centerCrop()
                        .into(holder.ivimage);
                holder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.btdel.setVisibility(View.GONE);
            }


            return convertView;
        }
    }

    class GridViewHolder{

        ImageView ivimage;
        Button btdel;
    }


    class DailyCostTypePopwindowListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return expenseCategoryList.size();
        }

        @Override
        public Object getItem(int position) {
            return expenseCategoryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView  == null){
                convertView = View.inflate(DailyCostActivity.this,R.layout.popwindow_item,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            final ExpenseCategory ec = expenseCategoryList.get(position);
            viewHolder.tv_msg.setText(ec.getEc_name());

            return convertView;
        }
    }

    class ViewHolder{
        TextView tv_msg;
    }


    /**
     * 图片选择相关
     */
    /**
     * 处理相册相关
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK&& data != null) {//相机
            //获取intent中的图片对象
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            //img_test.setImageBitmap(bitmap);

            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
            String photoPath = getPath(uri);
            Log.e("从相机返回的图片的绝对路径: ", photoPath);
            Intent intent = new Intent(DailyCostActivity.this,ShowInvoiceLargePicTestActivity.class);
            intent.putExtra("invoice_pic_url", photoPath);
            startActivityForResult(intent,PHOTOVIEW);
            /**
             * 从相机返回的图片的绝对路径:: /storage/emulated/0/DCIM/Camera/1514598888572.jpg
             */



            //对获取到的bitmap进行压缩、圆形处理
            //Bitmap zbitmap = BitmapUtils.zoom(bitmap, iv_user_icon.getWidth(), iv_user_icon.getHeight());
            //iv_user_icon.setImageBitmap(zbitmap);


        } else if (requestCode == PICTURE && resultCode == Activity.RESULT_OK && data != null) {//图库

            //图库
            Uri selectedImage = data.getData();
            //android各个不同的系统版本,对于获取外部存储上的资源，返回的Uri对象都可能各不一样,
            // 所以要保证无论是哪个系统版本都能正确获取到图片资源的话就需要针对各种情况进行一个处理了
            //这里返回的uri情况就有点多了
            //在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file://....
            // 在4.4.2返回的是content://com.android.providers.media.documents/document/image

            String pathResult = getPath(selectedImage);
            Log.e("从相册返回的绝对路径: ",pathResult);
            /**
             * 从相册返回的绝对路径:: /storage/emulated/0/DCIM/Camera/1514599043591.jpg
             * 从相册返回的绝对路径:: /storage/emulated/0/lagou/image/https:www.lgstatic.comiimageM007472CgpEMlo4uOSAF3PFAALGeI1RKvM745.JPG
             */
            //存储--->内存
            Bitmap decodeFile = BitmapFactory.decodeFile(pathResult);
            //img_test.setImageBitmap(decodeFile);
            Intent intent = new Intent(DailyCostActivity.this,ShowInvoiceLargePicTestActivity.class);
            intent.putExtra("invoice_pic_url",pathResult);
            startActivityForResult(intent,PHOTOVIEW);
            //Bitmap zoomBitmap = BitmapUtils.zoom(decodeFile, iv_user_icon.getWidth(),iv_user_icon.getHeight());
            //bitmap圆形裁剪
            //Bitmap circleImage = BitmapUtils.circleBitmap(zoomBitmap);

            //加载显示
            //iv_user_icon.setImageBitmap(zoomBitmap);
            //上传到服务器（省略）

            //保存到本地
            //saveImage(circleImage);
        }else if(requestCode == PHOTOVIEW && resultCode == PHOTOVIEW_RESULT && data != null){

            String resultPath = data.getStringExtra("Photo返回的filePath");
            Log.e("Photo返回的filePath",resultPath);
            picUrls.add(resultPath);
            dailyCostAddOrDelGridViewAdapter.notifyDataSetChanged();

        }
    }


    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(DailyCostActivity.this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(DailyCostActivity.this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = DailyCostActivity.this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(DailyCostActivity.this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public void getAllDailyExpenseCategoryList(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.AndroidGetAllCategoryListJson;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

                    String data ="&user_id="+ URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID), "utf-8");

                    conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                    //post的请求是把数据以流的方式写给了服务器
                    //指定请求的输出模式
                    conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                    conn.getOutputStream().write(data.getBytes());
                    Log.e("post size", String.valueOf(data.getBytes().length));
                    int code = conn.getResponseCode();

                    if(code == 200){
                        InputStream is = conn.getInputStream();
                        final String result = StreamTools.readStream(is);
                        Log.e("返回的消费类型的JSON数据",result);
                        expenseCategoryList = JSON.parseArray(result, ExpenseCategory.class);

                        for (int i=0;i<expenseCategoryList.size();i++){
                            ExpenseCategory expenseCategory = expenseCategoryList.get(i);
                            Log.e(TAG,"交通类型"+i+": "+expenseCategory.getEc_name());
                        }

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_EXPENSE_CATEGORY;
                        msg.obj = expenseCategoryList;
                        handler.sendMessage(msg);

                        if(result.equals("保存成功")) {
                            //finish();
                          /* Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                           startActivity(intent);*/
                        }
                    }else{
                        showToastInAnyThread("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * 日期显示处理开始
     */
    public void defaultDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        et_daily_cost__date.setText(year + "-" + (month + 1) + "-" + day);
    }
    public void defaultTime(View view){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
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
            et_daily_cost__date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }
    }

    //创建时间选择对话框
    @SuppressLint("ValidFragment")
    class SetTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),this,hour,minute,true);
            return timePickerDialog;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            /*et_start_time.setText(hourOfDay+":"+minute);
            et_end_time.setText(hourOfDay+":"+minute);*/
        }
    }

    public void setTime(View view){
        SetTimeDialog setTimeDialog = new SetTimeDialog();
        setTimeDialog.show(getFragmentManager(), "mytimePicker");
    }

}
