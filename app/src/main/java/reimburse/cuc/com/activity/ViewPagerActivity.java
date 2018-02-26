package reimburse.cuc.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/12/21.
 */
public class ViewPagerActivity extends Activity{
    private ViewPager viewPager;
    private TextView textView;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;

    static  final  String TAG = ViewPagerActivity.class.getSimpleName();

    // 图片资源ID
    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e };

    // 图片标题集合
    private final String[] imageDescriptions = {
            "尚硅谷波河争霸赛！",
            "凝聚你我，放飞梦想！",
            "抱歉没座位了！",
            "7月就业名单全部曝光！",
            "平均起薪11345元"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        viewPager = (ViewPager) findViewById(R.id.viewpager_test);
        textView = (TextView) findViewById(R.id.tv_title_vp);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        imageViews = new ArrayList<ImageView>();
        for (int i=0;i<imageIds.length;i++){
           ImageView imageView  = new ImageView(this);
           imageView.setBackgroundResource(imageIds[i]);
           imageViews.add(imageView);
        }
        viewPager.setAdapter(new MyPagerAdapter());
    }

    class  MyPagerAdapter extends PagerAdapter{
        /**
         * 得到总数
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
            Log.e(TAG,"destroyItem=="+position+", --object=="+object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object) ? true : false;
        }
    }
}
