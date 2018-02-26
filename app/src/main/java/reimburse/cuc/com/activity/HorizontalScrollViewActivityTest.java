package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import reimburse.cuc.com.reimburse.PhotoActivity;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/12/29.
 */
public class HorizontalScrollViewActivityTest extends Activity {
    private HorizontalScrollView hsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsv_test);
        hsv = (HorizontalScrollView) findViewById(R.id.test_hsv);
        hsv.getViewTreeObserver()
                .addOnPreDrawListener(// 绘制完毕
                        new ViewTreeObserver.OnPreDrawListener() {
                            public boolean onPreDraw() {
                                hsv.scrollTo(300,
                                        0);
                                hsv.getViewTreeObserver().removeOnPreDrawListener(this);
                                return true;
                            }
                        });
    }


}
