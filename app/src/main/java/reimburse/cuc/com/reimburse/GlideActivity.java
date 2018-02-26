package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import reimburse.cuc.com.base.GlideBaseActivity;
import reimburse.cuc.com.base.GlideRecycleViewActivity;

/**
 * Created by hp1 on 2017/8/19.
 */
public class GlideActivity extends Activity {
    private Button btn_base;
    private Button btn_recyleview;
    private Button btn_trans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_glide);
        btn_base = (Button) findViewById(R.id.btn_glide_base);
        btn_recyleview = (Button) findViewById(R.id.btn_glide_recycleview);
        btn_trans = (Button) findViewById(R.id.btn_glide_picswitch);

        /**
         * 基本使用 /storage/emulated/0/ImageSelector/CameraImage/ImageSelector_20170819_214104.JPEG
         */
        btn_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GlideBaseActivity.class));
            }
        });
        /**
         * RecycleView
         */
        btn_recyleview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),GlideRecycleViewActivity.class));
            }
        });
    }
}
