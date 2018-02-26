package reimburse.cuc.com.activity;

import android.app.Activity;
import android.os.Bundle;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.reimburse.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by hp1 on 2018/1/18.
 */
public class OnlyShowLocalLargePicActivity extends Activity {
    @Bind(R.id.photoview_invoice_large_pic)
    PhotoView photoviewInvoiceLargePic;
    private static final int PHOTOVIEW_RESULT = 04;
    private static final String TAG = ShowInvoiceLargePicTestActivity.class.getSimpleName();
    private String invoice_pic_large_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_show_local_large_pic);
        ButterKnife.bind(this);
        invoice_pic_large_url = getIntent().getStringExtra("invoice_pic_url");


        //PhotoView photoView = (PhotoView) findViewById(R.id.photoview_invoice_large_pic);

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoviewInvoiceLargePic);

        File imgFile = new File(invoice_pic_large_url);

        Picasso.with(this)
                .load(imgFile)
                .into(photoviewInvoiceLargePic, new Callback() {
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
