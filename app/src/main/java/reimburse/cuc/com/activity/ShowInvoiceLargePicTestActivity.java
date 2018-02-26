package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import reimburse.cuc.com.reimburse.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowInvoiceLargePicTestActivity extends Activity {


    private static final int PHOTOVIEW_RESULT = 04;
    private static final String TAG = ShowInvoiceLargePicTestActivity.class.getSimpleName();
    private String invoice_pic_large_url;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_invoice_large_pic);

        button = (Button) findViewById(R.id.item_grida_bt);

        invoice_pic_large_url = getIntent().getStringExtra("invoice_pic_url");


        PhotoView photoView = (PhotoView) findViewById(R.id.photoview_invoice_large_pic);

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        File imgFile = new File(invoice_pic_large_url);

        Picasso.with(this)
                .load(imgFile)
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取启动该Activity之前的Activity对应的Intent
                Log.e(TAG,invoice_pic_large_url);
                Intent intent = new Intent();
                //intent.putExtra("consumptionList", (Serializable) returnList);
                intent.putExtra("Photo返回的filePath", invoice_pic_large_url);
                setResult(PHOTOVIEW_RESULT, intent);
                finish();

               /* //设置该Activity的结果码，并设置结束之后退回的Activity
                ShowInvoiceLargePicTestActivity.this.setResult(PHOTOVIEW_RESULT, intent);
                //结束本Activity
                ShowInvoiceLargePicTestActivity.this.finish();*/
            }
        });

    }
}
