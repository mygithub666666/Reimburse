package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import reimburse.cuc.com.bean.Cost;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/10/1.
 */
public class XuanzeJizhangLeixingAdapter extends BaseAdapter {
    private List<String> xuanzeJizhangLeixingList;
    private Context context;
    private LayoutInflater layoutInflater;
    public XuanzeJizhangLeixingAdapter(){

    }

    public XuanzeJizhangLeixingAdapter(Context context,List<String> xuanzeJizhangLeixingList) {
        this.xuanzeJizhangLeixingList = xuanzeJizhangLeixingList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return xuanzeJizhangLeixingList.size();
    }

    @Override
    public Object getItem(int position) {
        return xuanzeJizhangLeixingList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.xuanzejizhangleixing_lv_item,null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.xuanzejizhangleixing_leixing);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.xuanzejizhangleixing_leixing_iv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.tvTitle.setText(xuanzeJizhangLeixingList.get(position));
        return convertView;
    }

    public static class ViewHolder{

        public TextView tvTitle;
        public ImageView iv;

    }


}


