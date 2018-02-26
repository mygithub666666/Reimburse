package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import reimburse.cuc.com.base.Consumption;

/**
 * Created by hp1 on 2017/8/26.
 */
public class BaoxiaoXuanzeHuafeiAdapter extends BaseAdapter {
    private Context context;
    private List<Consumption> consumptionList;
    private LayoutInflater layoutInflater;
    public BaoxiaoXuanzeHuafeiAdapter() {
    }

    public BaoxiaoXuanzeHuafeiAdapter(Context context, List<Consumption> consumptionList) {
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
        return null;
    }

    class MyViewHolder{

    }
}
