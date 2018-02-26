package reimburse.cuc.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import reimburse.cuc.com.reimburse.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowInvoiceLargePicActivity extends Activity {



    private String invoice_pic_large_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_invoice_large_pic);

        invoice_pic_large_url = getIntent().getStringExtra("invoice_pic_url");


        PhotoView photoView = (PhotoView) findViewById(R.id.photoview_invoice_large_pic);

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

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
