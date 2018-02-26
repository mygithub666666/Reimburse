package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import reimburse.cuc.com.adaptor.XuanzeJizhangLeixingAdapter;
import reimburse.cuc.com.base.Consumption;

/**
 * Created by hp1 on 2017/10/1.
 */
public class XuanzeJizhangLeixing extends Activity{
    private ListView xuanzeJizhangLeixing_lv;
    private List<String> jizhangleixingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xuanzejizhangleixing);
        xuanzeJizhangLeixing_lv = (ListView) findViewById(R.id.xuanzejizhangleixing_lv);
        jizhangleixingList = new ArrayList<>();
        jizhangleixingList.add("采购费");
        jizhangleixingList.add("交通费");
        jizhangleixingList.add("住宿费");
        jizhangleixingList.add("餐费");
        jizhangleixingList.add("通讯费");
        jizhangleixingList.add("会议费");
        jizhangleixingList.add("劳务费");
        jizhangleixingList.add("其它");
        for (String str : jizhangleixingList){
            Log.e("===========",jizhangleixingList.size()+"");
        }

        final XuanzeJizhangLeixingAdapter adapter = new XuanzeJizhangLeixingAdapter(this,jizhangleixingList);
        xuanzeJizhangLeixing_lv.setAdapter(adapter);

        xuanzeJizhangLeixing_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //表示当ListView中的item被点击时回调的方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 1.从数据源中获取
                String s1 = jizhangleixingList.get(position);
                // 2.从适配器中获得
                String s2 = (String) adapter.getItem(position);
                // 3.从parent中获取
                String s3 = parent.getItemAtPosition(position).toString();
                switch (position){
                    case 0:
                        startActivity(new Intent(XuanzeJizhangLeixing.this,CaigoufeiActivity.class));
                        Toast.makeText(XuanzeJizhangLeixing.this,position+": "+s1,Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        startActivity(new Intent(XuanzeJizhangLeixing.this, JiaotongfeiActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(XuanzeJizhangLeixing.this,CanfeiActivity.class));
                        break;

                }

            }
        });
    }
}
