package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/25.
 */
public class ItemWaitSelectAdapter extends BaseAdapter {
    private Context context;
    private List<Consumption> consumptions;
    private LayoutInflater layoutInflater;
    public ItemWaitSelectAdapter() {
    }

    public ItemWaitSelectAdapter(Context context,List<Consumption> consumptions){
           this.context = context;
           this.consumptions = consumptions;
           this.layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return consumptions.size();
    }

    @Override
    public Object getItem(int position) {
        return consumptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.listview_item_layout,null);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.btn_listview_waitselect);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.xiaofei_title_waitselect);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.xiaofei_date_waitselect);
            viewHolder.tvCount = (TextView) convertView.findViewById(R.id.xiaofei_count_waitselect);
            viewHolder.tvPicUris = (TextView) convertView.findViewById(R.id.xiaofei_picuris_waitselect);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Consumption consumption = consumptions.get(position);
        viewHolder.tvTitle.setText(consumption.getContent());
        viewHolder.tvDate.setText(consumption.getDate());
        viewHolder.tvCount.setText(consumption.getMoney());
        viewHolder.tvPicUris.setVisibility(View.INVISIBLE);
        viewHolder.tvPicUris.setText(consumption.getPicUris());
        CheckBox check = viewHolder.checkbox;
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        Toast.makeText(context,"select",Toast.LENGTH_LONG);
                    }
            }
        });
        return convertView;
    }

    class ViewHolder{
        public CheckBox checkbox;
        public TextView tvTitle;
        public TextView tvDate;
        public TextView tvCount;
        public TextView tvPicUris;
    }

    public void OnCheckBoxChanged(View convertView, final int position){
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_select);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(context,consumptions.get(position).getContent(),Toast.LENGTH_LONG);
                }else {
                    Toast.makeText(context,"NO SELECTED",Toast.LENGTH_LONG);
                }
            }
        });
    }
}
