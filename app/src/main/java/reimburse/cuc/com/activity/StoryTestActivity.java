package reimburse.cuc.com.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/12/21.
 */
public class StoryTestActivity extends Activity {
    private TextView dataTv;
    private TextView sdTv;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_test);
        dataTv = (TextView) findViewById(R.id.data_tv);
        sdTv = (TextView) findViewById(R.id.sd_tv);
        imageView = (ImageView) findViewById(R.id.test_store_img);

        // /storage/emulated/0/formats/20171221015739.JPEG

        //内部存储空间
        File dataFile = Environment.getDataDirectory();
        File sdFile = Environment.getExternalStorageDirectory();
        String SDPATH = Environment.getExternalStorageDirectory()+"";
        long dataSize = dataFile.getTotalSpace();
        long sdSize = sdFile.getTotalSpace();
        String data = Formatter.formatFileSize(this, dataSize);
        String sd = Formatter.formatFileSize(this,sdSize);
        dataTv.setText("内部存储: "+data);
        sdTv.setText("SD卡存储: "+sd+"\n"+"sd路径: "+SDPATH);
        String s = Environment.getExternalStorageDirectory() + "/formats/20171221044713.JPEG";
        Bitmap bitmap = BitmapFactory.decodeFile(s);
        imageView.setImageBitmap(bitmap);

    }
}
