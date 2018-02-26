package reimburse.cuc.com.base;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/19.
 */
public class GlideBaseActivity extends Activity {
    private ImageView iv_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_glide_base);
        iv_1  = (ImageView) findViewById(R.id.iv_glide1);
        initData();
    }

    public void initData(){
        Glide.with(this).load(Uri.fromFile(new File("/storage/emulated/0/myphoto.jpg"))).into(iv_1);
    }
}
