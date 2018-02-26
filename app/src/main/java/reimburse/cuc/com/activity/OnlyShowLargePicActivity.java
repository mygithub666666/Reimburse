package reimburse.cuc.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import reimburse.cuc.com.reimburse.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by hp1 on 2018/1/18.
 */
public class OnlyShowLargePicActivity extends Activity {

    private static final int PHOTOVIEW_RESULT = 04;
    private static final String TAG = ShowInvoiceLargePicTestActivity.class.getSimpleName();
    private String invoice_pic_large_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_show_large_pic);


        invoice_pic_large_url = getIntent().getStringExtra("invoice_pic_url");

        Log.e(TAG,"展示大图的Activity的图片路径 = "+invoice_pic_large_url);
        PhotoView photoView = (PhotoView) findViewById(R.id.photoview_only_show_large_pic);

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        //File imgFile = new File(invoice_pic_large_url);

        Picasso.with(this)
                .load(invoice_pic_large_url)
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {
                    }
                });


    }

}
