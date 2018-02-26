package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import reimburse.cuc.com.bean.Cost;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/19.
 */
public class CostListAdaptor extends BaseAdapter {
    private List<Cost> costList;
    private Context context;
    private LayoutInflater layoutInflater;
    public CostListAdaptor(){

    }
    public CostListAdaptor(Context context,List<Cost> costList){
          this.context = context;
          this.costList = costList;
          this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return costList.size();
    }

    @Override
    public Object getItem(int position) {
        return costList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_note_item,null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.xiaofei_title);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.xiaofei_date);
            viewHolder.tvCount = (TextView) convertView.findViewById(R.id.xiaofei_count);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

    }
    Cost bean = costList.get(position);
    viewHolder.tvTitle.setText(bean.getTitle());
    viewHolder.tvDate.setText(bean.getDate());
    viewHolder.tvCount.setText(bean.getCount());
    return convertView;
}
    public static class ViewHolder{
            public TextView tvTitle;
            public TextView tvDate;
            public TextView tvCount;
    }
}
