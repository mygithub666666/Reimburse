package reimburse.cuc.com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/12/30.
 */
public class AddOrDelImgGridView extends Activity {

    private static final int PICTURE = 01;
    private static final int CAMERA = 02;
    private static final int PHOTOVIEW = 03;
    private static final int PHOTOVIEW_RESULT = 04;
    private static final String TAG = AddOrDelImgGridView.class.getSimpleName();

    private EditText et;
    private GridView gw;
    private Button btn_save;
    private List<String> picUrls;
    AddOrDelGridViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_del_img_gridview);
        et = (EditText) findViewById(R.id.gv_et);
        gw = (GridView) findViewById(R.id.gw);
        btn_save = (Button) findViewById(R.id.btn_save_grid);
        adapter = new AddOrDelGridViewAdapter(6);
        picUrls = new ArrayList<>();

        gw.setAdapter(adapter);
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    Intent intent = new Intent(AddOrDelImgGridView.this, ShowInvoiceLargePicTestActivity.class);
                    intent.putExtra("invoice_pic_url", picPath);
                    startActivity(intent);


                } else {
                    final String[] items = new String[]{"图库", "相机"};
                    new AlertDialog.Builder(AddOrDelImgGridView.this).setTitle("选择来源")
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

        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String content = et.getText().toString();
                Log.e(TAG,"et: "+content);

                for (int i=0;i<picUrls.size();i++){
                    Log.e("第"+i+"个图片的路径: ",picUrls.get(i));
                }
                uploadDailyCost(content);
            }
        });


    }


    public void uploadDailyCost(final String content){

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient mOkHttpClient = new OkHttpClient();

                String path1 = "/storage/emulated/0/Tencent/QQfile_recv/2017-12-27_204922.png";
                String path2 = "/storage/emulated/0/截屏/截屏_20171222_104105.jpg";
                MultipartBody.Builder builder = new MultipartBody.Builder();
                // 这里演示添加用户ID
                builder.addFormDataPart("userId", "1");
                builder.addFormDataPart("totalCount", "6688");
                builder.addFormDataPart("cause",content);
                builder.addFormDataPart("image_1",new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new Random().nextInt(10)+".png",
                        RequestBody.create(MediaType.parse("image/jpeg"), new File(path1)));
                builder.addFormDataPart("image_2",new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+new Random().nextInt(10)+".png",
                        RequestBody.create(MediaType.parse("image/jpeg"), new File(path2)));

                RequestBody requestBody = builder.build();
                Request.Builder reqBuilder = new Request.Builder();
                Request request = reqBuilder
                        .url(Constants.DAILY_COST_FORM_UPLOAD)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                    String resultInfo = response.body().string();
                    Log.e(TAG, "上传图片返回的结果: " + resultInfo);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private String doPost(String imagePath) {
        /*new Thread(){

        }.start();*/
        OkHttpClient mOkHttpClient = new OkHttpClient();

        String result = "error";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        // 这里演示添加用户ID
        builder.addFormDataPart("userId", "20160519142605");
        builder.addFormDataPart("image", imagePath,
                RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath)));

        RequestBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        Request request = reqBuilder
                .url(Constants.DAILY_COST_FORM_UPLOAD)
                .post(requestBody)
                .build();

        Log.d(TAG, "请求地址 " + Constants.DAILY_COST_FORM_UPLOAD);
        try{
            Response response = mOkHttpClient.newCall(request).execute();
            Log.d(TAG, "响应码 " + response.code());
            if (response.isSuccessful()) {
                String resultValue = response.body().string();
                Log.d(TAG, "响应体 " + resultValue);
                return resultValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    class AddOrDelGridViewAdapter extends BaseAdapter{

        private Integer maxInvoicePicNum;

        AddOrDelGridViewAdapter(){

        }
        AddOrDelGridViewAdapter(Integer maxInvoicePicNum){
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
                LayoutInflater lf = (LayoutInflater) AddOrDelImgGridView.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                Glide.with(AddOrDelImgGridView.this)
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
                Glide.with(AddOrDelImgGridView.this)
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
            Intent intent = new Intent(AddOrDelImgGridView.this,ShowInvoiceLargePicTestActivity.class);
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
            Intent intent = new Intent(AddOrDelImgGridView.this,ShowInvoiceLargePicTestActivity.class);
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
            adapter.notifyDataSetChanged();

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
                return getDataColumn(AddOrDelImgGridView.this, contentUri, null, null);
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
                return getDataColumn(AddOrDelImgGridView.this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = AddOrDelImgGridView.this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(AddOrDelImgGridView.this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
