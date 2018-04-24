package reimburse.cuc.com.fragment;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.activity.UpdatePwdActivity;
import reimburse.cuc.com.base.BaseFragment;
import reimburse.cuc.com.bean.User;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/6.
 */
public class UserInfoFragment extends BaseFragment {

    private static final String TAG = UserInfoFragment.class.getSimpleName();
    /* @Bind(R.id.iv_title_back)
     ImageView ivTitleBack;
     @Bind(R.id.tv_title)
     TextView tvTitle;
     @Bind(R.id.iv_title_setting)
     ImageView ivTitleSetting;*/
    @Bind(R.id.tv_user_info_user_name)
    TextView tvUserInfoUserName;
    @Bind(R.id.et_user_info_user_name)
    EditText etUserInfoUserName;
    @Bind(R.id.tv_mobile_phone_number)
    TextView tvMobilePhoneNumber;
    @Bind(R.id.et_user_info_mobile_phone_number)
    EditText etUserInfoMobilePhoneNumber;
    @Bind(R.id.tv_user_info_email)
    TextView tvUserInfoEmail;
    @Bind(R.id.et_user_info_email)
    EditText etUserInfoEmail;
    @Bind(R.id.tv_user_info_bank_number)
    TextView tvUserInfoBankNumber;
    @Bind(R.id.et_user_info_bank_number)
    EditText etUserInfoBankNumber;
    @Bind(R.id.tv_user_info_bank_name)
    TextView tvUserInfoBankName;
    @Bind(R.id.et_user_info_bank_name)
    EditText etUserInfoBankName;
    @Bind(R.id.btn_user_logout)
    Button btnUserLogout;
    private Button btn_to_updatepwdactivity;
    @Override
    protected View initView() {
        Log.e(TAG, "个人中心Fragment的页面初始化22222222222222222222222222222222222222222222");
        View view = View.inflate(mContext, R.layout.userinfo_activity, null);
        //ButterKnife.bind(this,view);

        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        ButterKnife.bind(this, view);
        btn_to_updatepwdactivity = (Button) view.findViewById(R.id.btn_to_updatepwd_activity);
        /**
         *
         public void saveLoginedUser(String user_jsonString){
         SharedPreferences sp =  this.getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor =  sp.edit();
         editor.putString("user_jsonString",user_jsonString);
         editor.commit();

         }
         */
        final SharedPreferences sp = this.getActivity().getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);

        String user_jsonString = sp.getString("user_jsonString", "");

        User user = JSON.parseObject(user_jsonString, User.class);

        etUserInfoUserName.setText(user.getUser_name());
        etUserInfoMobilePhoneNumber.setText(user.getMobile_phone_number());
        etUserInfoEmail.setText(user.getEmail());
        etUserInfoBankNumber.setText(user.getBank_number());
        etUserInfoBankName.setText(user.getBank_name());

        btnUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.将保存在sp中的数据清除
                //SharedPreferences sp =  this.getActivity().getSharedPreferences("user_jsonString", Context.MODE_PRIVATE);
                sp.edit().clear().commit();

                Intent intent = new Intent(getActivity(), LauncherActivity.class);
                startActivity(intent);

            }
        });

        btn_to_updatepwdactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePwdActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    protected void initData() {
        Log.e(TAG, "个人中心Fragment的数据初始化22222222222222222222222222222222222222222222");
        super.initData();

    }

    //销毁所有的activity
    public void removeAll() {
        //ActivityManager
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
                return getDataColumn(getActivity(), contentUri, null, null);
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
                return getDataColumn(getActivity(), contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(getActivity(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
