package reimburse.cuc.com.reimburse;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import reimburse.cuc.com.base.BaseFragment;
import reimburse.cuc.com.fragment.HomePageFragment;
import reimburse.cuc.com.fragment.QueryListFragment;
import reimburse.cuc.com.fragment.UserInfoFragment;

/**
 * Created by hp1 on 2017/8/4.
 */
public class MainActivity extends FragmentActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private RadioGroup radioGroup;
    private List<BaseFragment> mBaseFragment;

    //选中的Fragment对应的id
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化View
        initView();
        //初始化Fragment
        initFragment();



        //设置RadioGroup的监听事件
        setRadioGroupListener();
        
    }

    private void setRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        radioGroup.check(R.id.rd_mainPage);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
                 switch(checkedId){
                     case R.id.rd_mainPage:
                         position = 0;
                         break;
                     case R.id.rd_approval:
                         position = 1;
                         break;
                     case R.id.rd_personal:
                         position = 2;
                         break;
                     default:
                         position = 0;
                         break;

                 }
                 Log.e(TAG,"点击的checkedId为： "+checkedId);
                 // 根据位置得到对应的Fragment
                 BaseFragment fragment = mBaseFragment.get(position);
                 Log.e(TAG,"-------------从集合中得到的第 ："+position+" 个Fragment是: "+fragment.toString()+"---------");
                 //替换
                 switchFragment(fragment);
        }
    }

    private void switchFragment(BaseFragment baseFragment) {
        //1.得到FragmentManager
       android.support.v4.app.FragmentManager fm =  getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = fm.beginTransaction();
        //3.替换
        ft.replace(R.id.fl_content,baseFragment);
        ft.commit();

    }

    private void initFragment() {
        mBaseFragment = new ArrayList<BaseFragment>();
        mBaseFragment.add(new HomePageFragment());
        mBaseFragment.add(new QueryListFragment());
        mBaseFragment.add(new UserInfoFragment());

    }

    private void initView(){
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.rg_main);

    }
}

