package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import reimburse.cuc.com.adaptor.MyRecyclerViewAdapter;

/**
 * Created by hp1 on 2017/9/21.
 */
public class CaigouActivity extends Activity {
    private GridView caigougridview;
    private List<Map<String, Object>> caigoudata = new ArrayList<>();
    private int[] images = {R.drawable.expense_bangong, R.drawable.expense_daba, R.drawable.expense_canyin, R.drawable.expense_meeting};
    private String[] titles = {"采购", "交通费", "餐饮", "通讯费"};
    /**
     * ATTENTION: This was auto-generated to
     * implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caigou);
        caigougridview = (GridView) findViewById(R.id.caigou_grid);
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("img", images[i]);
            map.put("text", titles[i]);
            caigoudata.add(map);

        }
        MyGridAdapter myRecyclerViewAdapter = new MyGridAdapter();
        caigougridview.setAdapter(myRecyclerViewAdapter);
    }


    class MyGridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return caigoudata.size();
        }

        @Override
        public Object getItem(int position) {
            return caigoudata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewHolder viewHolder = null;
            if (convertView == null) {
                LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = lay.inflate(R.layout.jizhang_grid_item, null);
                viewHolder = new GridViewHolder();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.jizhang_grid_item_imageView);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.jizhang_grid_item_textview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (GridViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText((CharSequence) caigoudata.get(position).get("text"));
            viewHolder.imageView.setImageResource((Integer) caigoudata.get(position).get("img"));
            return convertView;
        }

    }
    static class GridViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
