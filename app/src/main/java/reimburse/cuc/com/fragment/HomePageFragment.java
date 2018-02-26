package reimburse.cuc.com.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import reimburse.cuc.com.activity.DailyCostActivity;
import reimburse.cuc.com.activity.DailyCostActivity_back;
import reimburse.cuc.com.activity.DailyCostListActivity;
import reimburse.cuc.com.activity.DailyReimbursementActivity;
import reimburse.cuc.com.activity.MyDailyReimbursementListActivity;
import reimburse.cuc.com.activity.MyTravelReimbursementListActivity;
import reimburse.cuc.com.activity.TestActivity;
import reimburse.cuc.com.activity.TrafficCostActivity;
import reimburse.cuc.com.activity.TrafficCostFormActivity;
import reimburse.cuc.com.activity.TravelReimbursementActivity;
import reimburse.cuc.com.base.BaseFragment;
import reimburse.cuc.com.reimburse.ListActivity;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/6.
 */
public class HomePageFragment extends BaseFragment {

    private static final String TAG = HomePageFragment.class.getSimpleName();
    private static final String[] names = new String[]{"借款","记账","报销","账单","查看报销"};
    //private ImageView imageViewView;
    private GridView gridView;
    //private ImageView grad_img;

    private List<Map<String, Object>> list;
    private int[] imageIds = {R.drawable.jiekuan_meitu_1, R.drawable.jizhang_meitu_2, R.drawable.baoxiao_meitu_5,R.drawable.zhangdan_meitu_3,R.drawable.chakanbaoxiao_meitu_4};


    @Override
    protected View initView() {

        Log.e(TAG, "首页框架Fragment页面被初始化了=========================-----");
        View view = View.inflate(mContext, R.layout.fragment_main, null);
        //imageViewView = (ImageView) getActivity().findViewById(R.id.imageView1);
        gridView = (GridView) view.findViewById(R.id.home_gridview);
        //grad_img = (ImageView) view.findViewById(R.id.gv_image);


        list = new ArrayList<Map<String, Object>>();


        for (int i = 0; i < imageIds.length; i++) {
            Map<String, Object> map = new ConcurrentHashMap<String, Object>();
            map.put("img", imageIds[i]);
            map.put("text",names[i]);
            list.add(map);
        }

        MyBaseAdaptor myBaseAdaptor = new MyBaseAdaptor();
        gridView.setAdapter(myBaseAdaptor);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] items = new String[]{"日常费用",
                        "交通费"};
                String[] reim_items = new String[]{"日常报销",
                        "差旅报销"};
                switch ((int) id) {
                    case 0:
                        Log.e(TAG,"首页Fragment中点击的id是: " + id);
                        startActivity(new Intent(getActivity(),TestActivity.class));
                        break;
                    case 1:
                        Log.e(TAG,"首页Fragment中点击的id是: " + id);
                        new AlertDialog.Builder(getActivity()).setTitle("消费类型")
                                .setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                Intent dailyCostIntent  = new Intent(getActivity(), DailyCostActivity.class);
                                                startActivity(dailyCostIntent);
                                                break;
                                            case 1:
                                                Intent trafficCostIntent = new Intent(getActivity(), TrafficCostFormActivity.class);
                                                startActivity(trafficCostIntent);
                                                break;
                                        }
                                    }
                                }).setNegativeButton("取消", null).show();

                        break;
                    case 2:
                        Log.e(TAG,"首页Fragment中点击的id是: " + id);
                        new AlertDialog.Builder(getActivity()).setTitle("报销类型")
                                .setItems(reim_items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                Intent dailyCostIntent  = new Intent(getActivity(), DailyReimbursementActivity.class);
                                                startActivity(dailyCostIntent);
                                                break;
                                            case 1:
                                                Intent trafficCostIntent = new Intent(getActivity(), TravelReimbursementActivity.class);
                                                startActivity(trafficCostIntent);
                                                break;
                                        }
                                    }
                                }).setNegativeButton("取消", null).show();
                        //startActivity(new Intent(getActivity(), ReimburseFormAllActivity.class));
                        break;
                    case 3:
                        Log.e(TAG,"首页Fragment中点击的id是: " + id);
                        String[] my_costs = new String[]{"日常消费",
                                "交通费"};
                        // TrafficCostActivity DailyCostListActivity
                        new AlertDialog.Builder(getActivity()).setTitle("查看我的消费")
                                .setItems(my_costs, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                Intent dailyCostIntent  = new Intent(getActivity(), TrafficCostActivity.class);
                                                startActivity(dailyCostIntent);
                                                break;
                                            case 1:
                                                Intent trafficCostIntent = new Intent(getActivity(), DailyCostListActivity.class);
                                                startActivity(trafficCostIntent);
                                                break;
                                        }
                                    }
                                }).setNegativeButton("取消", null).show();

                        break;
                        /*startActivity(new Intent(getActivity(), ShowCostCategory.class));
                        break;*/
                    case 4:
                       /* Log.e(TAG,"首页Fragment中点击的id是: " + id);
                        startActivity(new Intent(getActivity(), MyBaoxiaodanList.class));*/

                        String[] my_reims = new String[]{"日常报销",
                                "差旅报销"};

                        new AlertDialog.Builder(getActivity()).setTitle("查看我的报销")
                                .setItems(my_reims, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                Intent dailyCostIntent  = new Intent(getActivity(), MyDailyReimbursementListActivity.class);
                                                startActivity(dailyCostIntent);
                                                break;
                                            case 1:
                                                Intent trafficCostIntent = new Intent(getActivity(), MyTravelReimbursementListActivity.class);
                                                startActivity(trafficCostIntent);
                                                break;
                                        }
                                    }
                                }).setNegativeButton("取消", null).show();

                        break;

                }
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "首页框架Fragment的数据被初始化了=========================");
    }

    public void open() {
        startActivity(new Intent(getActivity(),ListActivity.class));
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File("/sdcard/myphoto.jpg");
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent, 0);
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        imageViewView.setImageBitmap(bp);
    }*/

    static class ViewHolder {
        ImageView iv;
        TextView tv;
    }

    class MyBaseAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = lf.inflate(R.layout.grad_layout, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.gv_image);
                holder.tv = (TextView) convertView.findViewById(R.id.gv_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText((CharSequence) list.get(position).get("text"));
            holder.iv.setImageResource((Integer) list.get(position).get("img"));
            return convertView;
        }
    }
}
