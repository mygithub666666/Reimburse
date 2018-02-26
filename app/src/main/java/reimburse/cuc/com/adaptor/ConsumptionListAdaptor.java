package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.bean.Cost;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/19.
 */
public class ConsumptionListAdaptor extends BaseAdapter {
    private List<Consumption> consumptionList;
    private Context context;
    private LayoutInflater layoutInflater;
    public ConsumptionListAdaptor(){

    }
    public ConsumptionListAdaptor(Context context, List<Consumption> consumptionList){
          this.context = context;
          this.consumptionList = consumptionList;
          this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return consumptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return consumptionList.get(position);
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
            viewHolder.tvPicUris = (TextView) convertView.findViewById(R.id.xiaofei_picuris);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

    }
    Consumption consumption = consumptionList.get(position);
    viewHolder.tvTitle.setText(consumption.getContent());
    viewHolder.tvDate.setText(consumption.getDate());
    viewHolder.tvCount.setText(consumption.getMoney());

    viewHolder.tvPicUris.setVisibility(View.INVISIBLE);
    viewHolder.tvPicUris.setText(consumption.getPicUris());
    return convertView;
}
    public static class ViewHolder{
            public TextView tvTitle;
            public TextView tvDate;
            public TextView tvCount;
            public TextView tvPicUris;
        }
    }
