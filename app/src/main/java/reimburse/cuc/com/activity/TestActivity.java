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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.reimburse.CaigoufeiActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.BitmapUtils;

/**
 * Created by hp1 on 2017/12/21.
 */
public class TestActivity extends Activity {
    private static final int PICTURE = 01;
    private static final int CAMERA = 02;
    private static final int PHOTOVIEW = 03;
    private static final int PHOTOVIEW_RESULT = 04;
    private ListView listView;
    private List<String> data;
    private String[] title = {"拍照选择","ViewPager测试","安卓存储测试","HorizontalScrollViewActivityTest","采购费","拍照和相册选取",
       "AddOrDelImgGridView","OpenCV测试"
    };
    static  final  String TAG = TestActivity.class.getSimpleName();
    private ImageView img_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);
        listView = (ListView) findViewById(R.id.lv_test);
        img_test = (ImageView) findViewById(R.id.img_test);
        data = new ArrayList<>();
        for (int i=0;i<title.length;i++){
            data.add(title[i]);
        }
        listView.setAdapter(new MyBaseAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "position==" + position + ",id==" + id);
                switch (position) {
                    case  0:
                        startActivity(new Intent(TestActivity.this, DynamicActivity.class));
                        break;
                    case  1:
                        startActivity(new Intent(TestActivity.this,ViewPagerActivity.class));
                        break;
                    case  2:
                        startActivity(new Intent(TestActivity.this,StoryTestActivity.class));
                        break;
                    case  3:
                        startActivity(new Intent(TestActivity.this,HorizontalScrollViewActivityTest.class));
                        break;
                    case  4:
                        startActivity(new Intent(TestActivity.this,CaigoufeiActivity.class));
                        break;
                    case  5:
                        final String[] items = new String[]{"图库", "相机"};
                        new AlertDialog.Builder(TestActivity.this).setTitle("选择来源")
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
                        //startActivity(new Intent(TestActivity.this,CaigoufeiActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(TestActivity.this,AddOrDelImgGridView.class));
                        break;
                    case 7:
                        startActivity(new Intent(TestActivity.this,OpenCVTestActivity.class));
                }
            }
        });
    }



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
            Log.e("从相机返回的图片的绝对路径: ",photoPath);
            Intent intent = new Intent(TestActivity.this,ShowInvoiceLargePicTestActivity.class);
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
            Intent intent = new Intent(TestActivity.this,ShowInvoiceLargePicTestActivity.class);
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
                return getDataColumn(TestActivity.this, contentUri, null, null);
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
                return getDataColumn(TestActivity.this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = TestActivity.this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(TestActivity.this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    class  MyBaseAdapter extends BaseAdapter{

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

            TestViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new TestViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_test_activity,null);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_lv_item_test_activity);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (TestViewHolder) convertView.getTag();

            }
            viewHolder.textView.setText(data.get(position));
            return convertView;
        }
    }

    static  class TestViewHolder{
        TextView textView;
    }
}
